package org.matrixdata.morph.dal;

import ch.hsr.geohash.GeoHash;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAL {
    static Logger logger = Logger.getLogger(AreaDAL.class);

    static private AreaDAL _INSTANCE = new AreaDAL();

    static public AreaDAL getInstance() {
        return _INSTANCE;
    }

    public List<RestArea> getAreas() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        List<RestArea> ret = new ArrayList<RestArea>();
        RestArea currentArea = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_AREA);
            Scan scan = new Scan();
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.raw()) {
                    if ((currentArea == null) || (!currentArea.areacode.equals(new String(kv.getRow())))) {
                        if (currentArea != null) {
                            ret.add(currentArea);
                        }
                        currentArea = new RestArea();
                        currentArea.areacode = new String(kv.getRow());
                    }

                    if(new String(kv.getQualifier()).equals(Constant.AREA_COLUMN_STATION)) {
                        currentArea.station = new String(kv.getValue());
                        continue;
                    }
                }
            }
            rs.close();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        if (currentArea != null) {
            ret.add(currentArea);
        }

        return ret;
    }

    public void addArea(RestArea area) throws RecordExistException, MorphRestException {
        logger.info("start add area in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            GeoHash geoHash = GeoHash.fromGeohashString(area.areacode);
        }
        catch (Exception e) {
            throw new MorphRestException(Constant.BAD_REQUEST, "not a vaild gohash: " + area.areacode);
        }

        if (area.station == null) {
            area.station = Constant.DEFAULT_STATION;
        }

        try {
            HTable table = new HTable(conf, Constant.TABLE_AREA);

            Get get = new Get(Bytes.toBytes(area.areacode));
            Result result = table.get(get);
            if (!result.isEmpty()) {
                throw new RecordExistException();
            }

            Put put = new Put(Bytes.toBytes(area.areacode));
            put.add(Bytes.toBytes(Constant.AREA_COLUMNFAMILY), Bytes.toBytes(Constant.AREA_COLUMN_STATION), Bytes.toBytes(area.station));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add area in DAL");
    }

    public String getAreaFromGeohash(String areacode) {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        String lastArea = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_AREA);
            String startcode = areacode.substring(0, 1);
            Scan scan = new Scan(Bytes.toBytes(startcode));
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.raw()) {
                    String currentArea = new String(kv.getRow());
                    if (!areacode.startsWith(currentArea)) {
                        rs.close();
                        return lastArea;
                    }
                    lastArea = new String(kv.getRow());
                }
            }
            rs.close();
            return lastArea;
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        return null;
    }

    public RestArea getArea(String areacode) {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        RestArea ret = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_AREA);
            Get get = new Get(Bytes.toBytes(areacode));
            Result result = table.get(get);
            if (result.isEmpty()) {
                return ret;
            }
            KeyValue kv = result.getColumnLatest(Bytes.toBytes(Constant.AREA_COLUMNFAMILY),
                    Bytes.toBytes(Constant.AREA_COLUMN_STATION));
            String station = new String(kv.getValue());
            ret = new RestArea(areacode, station);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        return ret;
    }

    public void deleteArea(String areacode) {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_AREA);
            Delete delete = new Delete(Bytes.toBytes(areacode));
            table.delete(delete);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}