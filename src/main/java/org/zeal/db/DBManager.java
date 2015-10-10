package org.zeal.db;

import org.logicalcobwebs.proxool.configuration.JAXPConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by wangjw on 2015/10/10.
 */
public class DBManager {
    private static DBManager dBManager = null;

    private DBManager() {
        try {
            JAXPConfigurator.configure(DBPool.getDBPool().getPoolPath(), false);
            Class.forName("org.logicalcobwebs.proxool.ProxoolDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return DBManager
     * @Author: wangjw
     * @Description: 获取数据库连接池管理对象
     */
    protected static DBManager getDBManager() {
        if (dBManager == null) {
            synchronized (DBManager.class) {
                if (dBManager == null) {
                    dBManager = new DBManager();
                }
            }
        }
        return dBManager;
    }

    /**
     * @param poolName
     * @return Connection
     * @throws SQLException
     * @Author: wangjw
     * @Description: 获取数据库链接
     */
    protected Connection getConnection(String poolName) throws SQLException {
        return DriverManager.getConnection(poolName);
    }
}
