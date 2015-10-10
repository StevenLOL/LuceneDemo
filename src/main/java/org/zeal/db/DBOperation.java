package org.zeal.db;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;

/**
 * Created by wangjw on 2015/10/10.
 */
public class DBOperation {
    private static Logger log = Logger.getLogger(DBOperation.class);
    private Connection conn = null;
    private String poolName;

    /**
     * @param poolName
     */
    public DBOperation(String poolName) {
        this.poolName = poolName;
    }

    /**
     * @throws SQLException
     * @Author: wangjw
     * @Description: 获取Connection
     */
    private void open() throws SQLException {
        this.conn = DBManager.getDBManager().getConnection(poolName);
    }

    /**
     * @Author: wangjw
     * @Description: 关闭Connection
     */
    public void close() {
        try {
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param sql    组装的sql字符串
     * @param params 传入的参数
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description: 组装PreparedStatement
     */
    private PreparedStatement setPres(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        if (null != params) {
            if (0 < params.size()) {
                PreparedStatement pres = this.conn.prepareStatement(sql);
                for (int i = 1; i <= params.size(); i++) {
                    if (params.get(i).getClass() == Class.forName("java.lang.String")) {
                        pres.setString(i, params.get(i).toString());
                    } else if (params.get(i).getClass() == Class.forName("java.lang.Integer")) {
                        pres.setInt(i, (Integer) params.get(i));
                    } else if (params.get(i).getClass() == Class.forName("java.lang.Boolean")) {
                        pres.setBoolean(i, (Boolean) params.get(i));
                    } else if (params.get(i).getClass() == Class.forName("java.lang.Float")) {
                        pres.setFloat(i, (Float) params.get(i));
                    } else if (params.get(i).getClass() == Class.forName("java.lang.Double")) {
                        pres.setDouble(i, (Double) params.get(i));
                    } else if (params.get(i).getClass() == Class.forName("java.lang.Long")) {
                        pres.setLong(i, (Long) params.get(i));
                    } else if (params.get(i).getClass() == Class.forName("java.sql.Date")) {
                        pres.setDate(i, java.sql.Date.valueOf(params.get(i).toString()));
                    } else {
                        log.info("not found class : " + params.get(i).getClass().toString());
                        return null;
                    }
                }
                return pres;
            }
        }
        return null;
    }

    /**
     * @param sql
     * @return int
     * @throws SQLException
     * @Author: wangjw
     * @Description: executeUpdate
     */
    protected int executeUpdate(String sql) throws SQLException {
        this.open();
        Statement state = this.conn.createStatement();
        int re = state.executeUpdate(sql);
        return re;
    }

    /**
     * executeUpdate
     *
     * @param sql
     * @param params
     * @return int
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description:
     */
    protected int executeUpdate(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        this.open();
        PreparedStatement pres = setPres(sql, params);
        int re = 0;
        if (null != pres) {
            re = pres.executeUpdate();
        }
        return re;
    }

    /**
     * getGeneratedKeys
     *
     * @param sql
     * @return ResultSet
     * @throws SQLException
     * @Author: wangjw
     * @Description:
     */
    protected ResultSet getGeneratedKeys(String sql) throws SQLException {
        this.open();
        Statement state = this.conn.createStatement();
        state.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        ResultSet re = state.getGeneratedKeys();
        return re;
    }

    /**
     * getGeneratedKeys
     *
     * @param sql
     * @param params
     * @return ResultSet
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description:
     */
    protected ResultSet getGeneratedKeys(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        this.open();
        PreparedStatement pres = setPres(sql, params);
        if (null != pres) {
            pres.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet re = pres.getGeneratedKeys();
            return re;
        }
        return null;
    }

    /**
     * @param sql
     * @return ResultSet
     * @throws SQLException
     * @Author: wangjw
     * @Description: executeQuery
     */
    protected ResultSet executeQuery(String sql) throws SQLException {
        this.open();
        Statement state = this.conn.createStatement();
        ResultSet re = state.executeQuery(sql);
        return re;
    }

    /**
     * @param sql
     * @param params
     * @return ResultSet
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description: executeQuery
     */
    protected ResultSet executeQuery(String sql, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        this.open();
        PreparedStatement pres = setPres(sql, params);
        if (null != pres) {
            ResultSet re = pres.executeQuery();
            return re;
        }
        return null;
    }
}
