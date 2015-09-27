package org.matrixdata.morph.dal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StationDAL {
    static Logger logger = Logger.getLogger(StationDAL.class);

    static private StationDAL _INSTANCE = new StationDAL();

    static public StationDAL getInstance() {
        return _INSTANCE;
    }

    public List<RestStation> getStations() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        List<RestStation> ret = new ArrayList<RestStation>();
        List<String> currentArea = new ArrayList<>();
        RestStation currentStation = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_STATION);
            Scan scan = new Scan();
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.raw()) {
                    if ((currentStation == null) || (!currentStation.name.equals(new String(kv.getRow())))) {
                        if (currentStation != null) {
                            currentStation.areas = currentArea;
                            ret.add(currentStation);
                            currentArea = new ArrayList<>();
                        }
                        currentStation = new RestStation();
                        currentStation.name = new String(kv.getRow());
                    }

                    if(new String(kv.getQualifier()).equals(Constant.AREA_COLUMN_STATION)) {
                        String number = new String(kv.getValue());
                        continue;
                    }
                    else {
                        currentArea.add(new String(kv.getValue()));
                    }
                }
            }
            rs.close();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        if (currentStation != null) {
            currentStation.areas = currentArea;
            ret.add(currentStation);
        }

        return ret;
    }

    public void addStation(RestStation station) throws RecordExistException, MorphRestException {
        logger.info("start add station in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_STATION);

            Get get = new Get(Bytes.toBytes(station.name));
            Result result = table.get(get);
            if (!result.isEmpty()) {
                throw new RecordExistException();
            }

            Put put = new Put(Bytes.toBytes(station.name));
            put.add(Bytes.toBytes(Constant.STATION_COLUMNFAMILY), Bytes.toBytes(Constant.STATION_COLUMN_AREANUM), Bytes.toBytes("0"));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add station in DAL");
    }

    public RestStation getStation(String name) {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        RestStation ret = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_STATION);
            Get get = new Get(Bytes.toBytes(name));
            Result result = table.get(get);
            if (result.isEmpty()) {
                return ret;
            }
            KeyValue kv = result.getColumnLatest(Bytes.toBytes(Constant.STATION_COLUMNFAMILY),
                    Bytes.toBytes(Constant.STATION_COLUMN_AREANUM));
            String number = new String(kv.getValue());
            List<String> areas = new ArrayList<>();
            for (int i = 1; i <= Integer.parseInt(number); i++) {
                KeyValue areakv = result.getColumnLatest(Bytes.toBytes(Constant.STATION_COLUMNFAMILY),
                        Bytes.toBytes(Constant.STATION_COLUMN_AREA_PREFIX + i));
                String area = new String(areakv.getValue());
                areas.add(area);
            }
            ret = new RestStation(name, areas);
        }
        catch (Exception e) {
            logger.info(e.getMessage());
        }

        return ret;
    }

    public void addArea(String stationName, String areacode) throws MorphRestException {
        logger.info("start add area " + areacode +  "in station" + stationName);
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        RestStation ret = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_STATION);
            Get get = new Get(Bytes.toBytes(stationName));
            Result result = table.get(get);
            if (result.isEmpty()) {
                throw new MorphRestException(Constant.BAD_REQUEST, "station not exist");
            }
            KeyValue kv = result.getColumnLatest(Bytes.toBytes(Constant.STATION_COLUMNFAMILY),
                    Bytes.toBytes(Constant.STATION_COLUMN_AREANUM));
            String number = new String(kv.getValue());
            String newNumber = "" + (Integer.parseInt(number) + 1);

            Put put = new Put(Bytes.toBytes(stationName));
            put.add(Bytes.toBytes(Constant.STATION_COLUMNFAMILY), Bytes.toBytes(Constant.STATION_COLUMN_AREANUM), Bytes.toBytes(newNumber));
            put.add(Bytes.toBytes(Constant.STATION_COLUMNFAMILY),
                    Bytes.toBytes(Constant.STATION_COLUMN_AREA_PREFIX + newNumber), Bytes.toBytes(areacode));
            table.put(put);
            logger.info("finish add area " + areacode +  "in station" + stationName);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}