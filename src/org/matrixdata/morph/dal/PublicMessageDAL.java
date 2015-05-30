package org.matrixdata.morph.dal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.location.Station;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublicMessageDAL {
    static Logger logger = Logger.getLogger(PublicMessageDAL.class);

    static private PublicMessageDAL _INSTANCE = new PublicMessageDAL();

    static public PublicMessageDAL getInstance() {
        return _INSTANCE;
    }

    public List<RestPublicMessage> getPublicMessages() {
        RestPublicMessage message1 = new RestPublicMessage("This is a test message", "116.359848", "39.989933", "id1", "0");
        RestPublicMessage message2 = new RestPublicMessage("Another test message", "116.344434", "39.998568", "id2", "0");


        List<RestPublicMessage> ret = new ArrayList<RestPublicMessage>();
        ret.add(message1);
        ret.add(message2);
        return ret;
    }

    public List<RestPublicMessage> getPublicMessages(Station station, long timestamp) {
        String timestampStr = String.valueOf(timestamp);
        if (timestampStr.length() != 10) {
            logger.error(String.format("unusual timestamp, timestamp=%s", timestampStr));
        }

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        List<RestPublicMessage> ret = new ArrayList<RestPublicMessage>();
        RestPublicMessage currentMessage = null;
        String currentRow = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_PUBLICMESSAGE);

            Scan scan = new Scan(Bytes.toBytes(_getKey(station.getName(), timestampStr)),
                    Bytes.toBytes(_getEndKey(station.getName())));

            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.raw()) {
                    if ((currentMessage == null) || (!currentRow.equals(new String(kv.getRow())))) {
                        if (currentMessage != null) {
                            ret.add(currentMessage);
                        }
                        currentMessage = new RestPublicMessage();
                        currentMessage.timestamp = _getTimestampStr(new String(kv.getRow()));
                        currentRow = new String(kv.getRow());
                    }

                    if(new String(kv.getQualifier()).equals(Constant.PUBLICMESSAGE_COLUMN_TEXT)) {
                        currentMessage.text = new String(kv.getValue());
                        continue;
                    }

                    if(new String(kv.getQualifier()).equals(Constant.PUBLICMESSAGE_COLUMN_LONGITUDE)) {
                        currentMessage.longitude = new String(kv.getValue());
                        continue;
                    }

                    if(new String(kv.getQualifier()).equals(Constant.PUBLICMESSAGE_COLUMN_LATITUDE)) {
                        currentMessage.latitude = new String(kv.getValue());
                        continue;
                    }

                    if(new String(kv.getQualifier()).equals(Constant.PUBLICMESSAGE_COLUMN_USERID)) {
                        currentMessage.userid = new String(kv.getValue());
                        continue;
                    }

                    if(new String(kv.getQualifier()).equals(Constant.PUBLICMESSAGE_COLUMN_TIMESTAMP)) {
                        currentMessage.timestamp = new String(kv.getValue());
                    }
                }
            }
            rs.close();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        if (currentMessage != null) {
            ret.add(currentMessage);
        }

        return ret;
    }

    public void addPublicMessage(RestPublicMessage message, Station station) throws RecordExistException {
        logger.info("start add public message in DAL");
        long timestamp = System.currentTimeMillis() / 1000;
        String timestampStr = String.valueOf(timestamp);


        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_PUBLICMESSAGE);
            String key = _getKey(station.getName(), timestampStr);

            Get get = new Get(Bytes.toBytes(key));
            Result result = table.get(get);
            if (!result.isEmpty()) {
                throw new RecordExistException();
            }

            Put put = new Put(Bytes.toBytes(key));

            put.add(Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMNFAMILY), Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMN_TEXT), Bytes.toBytes(message.text));
            put.add(Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMNFAMILY), Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMN_LONGITUDE), Bytes.toBytes(message.longitude));
            put.add(Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMNFAMILY), Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMN_LATITUDE), Bytes.toBytes(message.latitude));
            put.add(Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMNFAMILY), Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMN_USERID), Bytes.toBytes(message.userid));
            put.add(Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMNFAMILY), Bytes.toBytes(Constant.PUBLICMESSAGE_COLUMN_TIMESTAMP), Bytes.toBytes(timestampStr));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add publicmessage in DAL");
    }

    private String _getKey(String stationName, String timestampStr) {
        return stationName + "_" + timestampStr;
    }

    private String _getEndKey(String stationName) {
        return stationName + "`";
    }

    private String _getTimestampStr(String row) {
        int index = row.indexOf('_');
        return row.substring(index + 1);
    }
}