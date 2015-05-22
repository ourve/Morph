package org.matrixdata.morph.dal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDAL {
    static Logger logger = Logger.getLogger(UserDAL.class);

    static private UserDAL _INSTANCE = new UserDAL();

    static public UserDAL getInstance() {
        return _INSTANCE;
    }

    public List<RestUser> getUsers() {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);
        List<RestUser> ret = new ArrayList<RestUser>();
        RestUser currentUser = null;

        try {
            HTable table = new HTable(conf, Constant.TABLE_USER);
            Scan scan = new Scan();
            ResultScanner rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.raw()) {
                    if ((currentUser == null) || (!currentUser.name.equals(new String(kv.getRow())))) {
                        if (currentUser != null) {
                            ret.add(currentUser);
                        }
                        currentUser = new RestUser();
                        currentUser.name = new String(kv.getRow());
                    }

                    if(new String(kv.getQualifier()).equals(Constant.USER_COLUMN_SEX)) {
                        currentUser.sex = new String(kv.getValue());
                        continue;
                    }

                    if(new String(kv.getQualifier()).equals(Constant.USER_COLUMN_CREDIT)) {
                        currentUser.credit = new String(kv.getValue());
                    }
                }
            }
            rs.close();
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        if (currentUser != null) {
            ret.add(currentUser);
        }

        return ret;
    }

    public void addUser(RestUser user) throws RecordExistException {
        logger.info("start add user in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", Constant.HBASE_ZOOKEEPER_QUORUM);

        try {
            HTable table = new HTable(conf, Constant.TABLE_USER);

            Get get = new Get(Bytes.toBytes(user.name));
            Result result = table.get(get);
            if (!result.isEmpty()) {
                throw new RecordExistException();
            }

            Put put = new Put(Bytes.toBytes(user.name));
            put.add(Bytes.toBytes(Constant.USER_COLUMNFAMILY), Bytes.toBytes(Constant.USER_COLUMN_SEX), Bytes.toBytes(user.sex));
            put.add(Bytes.toBytes(Constant.USER_COLUMNFAMILY), Bytes.toBytes(Constant.USER_COLUMN_CREDIT), Bytes.toBytes(user.credit));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add user in DAL");
    }
}