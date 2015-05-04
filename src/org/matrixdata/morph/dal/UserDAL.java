package org.matrixdata.morph.dal;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;
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
        RestUser user1 = new RestUser("id1");
        RestUser user2 = new RestUser("id1");

        List<RestUser> ret = new ArrayList<RestUser>();
        ret.add(user1);
        ret.add(user2);
        return ret;
    }

    public void addUser(RestUser user) {
        logger.info("start add user in DAL");

        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");

        try {
            HTable table = new HTable(conf, "users");
            Put put = new Put(Bytes.toBytes(user.id));
            put.add(Bytes.toBytes("cf"), Bytes.toBytes("testcolumn"), Bytes.toBytes("testvalue"));
            table.put(put);
        }
        catch (IOException e) {
            logger.info(e.getMessage());
        }

        logger.info("finish add user in DAL");
        return;
    }
}