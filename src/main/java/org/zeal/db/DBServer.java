package org.zeal.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by wangjw on 2015/10/10.
 */
public class DBServer {
    private DBOperation dBOperation;

    /**
     * @param poolName
     * @Description: 在使用该类之前，请保证函数DBPool.getDBPool().setPoolPath()已经运行
     */
    public DBServer(String poolName) {
        dBOperation = new DBOperation(poolName);
    }

    /**
     * @Author: wangjw
     * @Description: 释放链接，在执行完数据库操作，必须执行此命令
     */
    public void close() {
        dBOperation.close();
    }

    /**
     * @param table
     * @param columns
     * @param params
     * @return int
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description: insert 执行完此命令后，执行close()操作
     */
    public int insert(String table, String columns, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        String sql = insertSql(columns, table);
        return dBOperation.executeUpdate(sql, params);
    }

    /**
     * @param sql
     * @return int
     * @throws SQLException
     * @Author: wangjw
     * @Description: insert 执行完此命令后，执行close()操作
     */
    public int insert(String sql) throws SQLException {
        return dBOperation.executeUpdate(sql);
    }

    /**
     * @param table
     * @param columns
     * @param params
     * @return ResultSet
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description: insertGetGeneratedKeys 执行完此命令后，执行close()操作
     */
    public ResultSet insertGetGeneratedKeys(String table, String columns, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        String sql = insertSql(columns, table);
        return dBOperation.getGeneratedKeys(sql, params);
    }

    /**
     * @param sql
     * @return ResultSet
     * @throws SQLException
     * @Author: wangjw
     * @Description: insertGetGeneratedKeys 执行完此命令后，执行close()操作
     */
    public ResultSet insertGetGeneratedKeys(String sql) throws SQLException {
        return dBOperation.getGeneratedKeys(sql);
    }

    /**
     * @param table
     * @param condition
     * @return int
     * @throws SQLException
     * @Author: wangjw
     * @Description: delete 执行完此命令后，执行close()操作
     */
    public int delete(String table, String condition) throws SQLException {
        if (null == table) {
            return 0;
        }
        String sql = "delete from " + table + " " + condition;
        return dBOperation.executeUpdate(sql);
    }

    /**
     * @param sql
     * @return int
     * @throws SQLException
     * @Author: wangjw
     * @Description: delete 执行完此命令后，执行close()操作
     */
    public int delete(String sql) throws SQLException {
        return dBOperation.executeUpdate(sql);
    }

    /**
     * @param columns
     * @param table
     * @param condition
     * @return ResultSet
     * @throws SQLException
     * @Author: wangjw
     * @Description: select 执行完此命令后，执行close()操作
     */
    public ResultSet select(String columns, String table, String condition) throws SQLException {
        String sql = "select " + columns + " from " + table + " " + condition;
        return dBOperation.executeQuery(sql);
    }

    /**
     * @param sql
     * @return ResultSet
     * @throws SQLException
     * @Author: wangjw
     * @Description: select 执行完此命令后，执行close()操作
     */
    public ResultSet select(String sql) throws SQLException {
        return dBOperation.executeQuery(sql);
    }

    /**
     * @param table
     * @param columns
     * @param condition
     * @param params
     * @return int
     * @throws SQLException
     * @throws ClassNotFoundException
     * @Author: wangjw
     * @Description: update 执行完此命令后，执行close()操作
     */
    public int update(String table, String columns, String condition, HashMap<Integer, Object> params) throws SQLException, ClassNotFoundException {
        String sql = updateString(table, columns, condition);
        return dBOperation.executeUpdate(sql, params);
    }

    /**
     * @param sql
     * @return int
     * @throws SQLException
     * @Author: wangjw
     * @Description: update 执行完此命令后，执行close()操作
     */
    public int update(String sql) throws SQLException {
        return dBOperation.executeUpdate(sql);
    }

    /**
     * @param table
     * @param columns
     * @param condition
     * @return String
     * @Author: wangjw
     * @Description: 组装updateString
     */
    private String updateString(String table, String columns, String condition) {
        if (null == columns || null == table) {
            return "";
        }
        String[] column = columns.split(",");
        StringBuilder stringBuilder = new StringBuilder("update ");
        stringBuilder.append(table);
        stringBuilder.append(" set ");
        stringBuilder.append(column[0]);
        stringBuilder.append("=?");
        for (int i = 1; i < column.length; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(column[i]);
            stringBuilder.append("=?");
        }
        stringBuilder.append(" ");
        stringBuilder.append(condition);
        return stringBuilder.toString();
    }

    /**
     * @param columns
     * @param table
     * @return String
     * @Author: wangjw
     * @Description: 组装insertSql
     */
    private String insertSql(String columns, String table) {
        if (null == columns || null == table) {
            return "";
        }
        int colNum = columns.split(",").length;
        StringBuilder stringBuilder = new StringBuilder("insert into ");
        stringBuilder.append(table);
        stringBuilder.append(" (");
        stringBuilder.append(columns);
        stringBuilder.append(") values (?");
        for (int i = 1; i < colNum; i++) {
            stringBuilder.append(",?");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
