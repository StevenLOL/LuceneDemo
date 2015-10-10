package org.zeal.db;

import org.apache.log4j.Logger;
import org.zeal.util.ClassUtil;

/**
 * Created by wangjw on 2015/10/10.
 */
public class DBPool {
    private static DBPool dbPool = null;
    private String poolPath;
    private static Logger log = Logger.getLogger(DBPool.class);
    private static String path = ClassUtil.getClassRootPath(DBPool.class);

    public static DBPool getDBPool() {
        if (dbPool == null) {
            synchronized (DBPool.class) {
                if (dbPool == null) {
                    dbPool = new DBPool();
                }
            }
        }
        return dbPool;
    }

    private DBPool() {

    }

    /**
     * @param poolPath
     * @Author: wangjw
     * @Description: 设置数据库连接池配置文件路径
     */
    public void setPoolPath(String poolPath) {
        this.poolPath = poolPath;
    }

    /**
     * @return
     * @Author: wangjw
     * @Description: 返回数据库连接池配置文件路径
     */
    protected String getPoolPath() {
        //如果没有指定配置文件，则使用默认配置文件
        if (poolPath == null) {
            poolPath = path + "proxool.xml";
            log.info("Database's poolpath is null, use default path:" + poolPath);
        }
        return poolPath;
    }
}
