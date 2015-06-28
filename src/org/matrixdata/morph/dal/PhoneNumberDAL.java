package org.matrixdata.morph.dal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.pojo.RestPhoneNumber;

import java.io.IOException;

public class PhoneNumberDAL {
    static Logger logger = Logger.getLogger(PhoneNumberDAL.class);

    static private PhoneNumberDAL _INSTANCE = new PhoneNumberDAL();

    static public PhoneNumberDAL getInstance() {
        return _INSTANCE;
    }

    public void addPhoneNumber(RestPhoneNumber phoneNumber) throws RecordExistException {
        logger.info("start add phone number in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_PHONENUMBER);

            Get get = new Get(Bytes.toBytes(phoneNumber.phoneNumber));
            Result result = table.get(get);
            if (!result.isEmpty()) {
                throw new RecordExistException();
            }

            updatePhoneNumber(phoneNumber);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add phone number in DAL");
    }

    public void updatePhoneNumber(RestPhoneNumber phoneNumber) {
        logger.info("start update phone number in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_PHONENUMBER);

            Put put = new Put(Bytes.toBytes(phoneNumber.phoneNumber));
            put.add(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_IDENTIFY), Bytes.toBytes(phoneNumber.identify));
            put.add(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_USERNAME), Bytes.toBytes(phoneNumber.username));
            put.add(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_CREATE_TIME), Bytes.toBytes(phoneNumber.createtime));
            put.add(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_STATUS), Bytes.toBytes(phoneNumber.status));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish update phone number in DAL");
    }

    public RestPhoneNumber getPhoneNumber(String phoneNumber) {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        RestPhoneNumber ret = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_PHONENUMBER);
            Get get = new Get(Bytes.toBytes(phoneNumber));
            Result result = table.get(get);
            if (result.isEmpty()) {
                return ret;
            }
            KeyValue kv = result.getColumnLatest(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_IDENTIFY));
            String identify = new String(kv.getValue());

            kv = result.getColumnLatest(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_USERNAME));
            String username = new String(kv.getValue());

            kv = result.getColumnLatest(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_CREATE_TIME));
            String createTime = new String(kv.getValue());

            kv = result.getColumnLatest(Bytes.toBytes(Constant.PHONENUMBER_COLUMNFAMILY),
                    Bytes.toBytes(Constant.PHONENUMBER_COLUMN_STATUS));
            String status = new String(kv.getValue());

            ret = new RestPhoneNumber(phoneNumber, identify, username, createTime, status);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        return ret;
    }
}