//package com.service.MyJDBC;
//
//import com.mysql.jdbc.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author rtw
// * @since 2019/1/17
// */
//public class MyJDBC {
//    /**
//     * 通过数据库连接信息获得连接对象
//     * @param dataConnection 连接详细对象
//     * @return 连接
//     */
//    private Connection getConnection(DataConnection dataConnection) {
//        //注册驱动
//        try {
//            Class.forName(dataConnection.getDriver());
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("注册驱动失败：{}", e);
//        }
//        //定义数据库连接
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(dataConnection.getUrl(),
//                    dataConnection.getUserName(), dataConnection.getPassWord());
//        } catch (SQLException e) {
//            LOGGER.error("建立数据库连接:{}", e);
//        }
//        return connection;
//    }
//
//    /**
//     * 查询返回多个JavaBean(通过java反射机制)
//     * @param sql sql语句
//     * @param params 传参条件
//     * @param cls 反射对象CLass
//     * @param <T> 返回对象的类型
//     * @param dataConnection 数据库连接对象
//     * @return 返回的对象(List)
//     */
//    public <T> List<T> findMoreRefResult(String sql, List<Object> params,
//                                         Class<T> cls, DataConnection dataConnection) {
//        //获得数据库连接对象
//        Connection connection = this.getConnection(dataConnection);
//        //定义SQL预查询对象
//        PreparedStatement pstmt = null;
//        //查询结果对象
//        ResultSet resultSet = null;
//        if (null == connection) {
//            LOGGER.error("数据库连接未实例化");
//            return null;
//        }
//        List<T> list = new ArrayList<T>();
//        try {
//            int index = 1; //占位符
//            pstmt = connection.prepareStatement(sql);
//            if (params != null && !params.isEmpty()) {
//                //循环设置sql的传参，会自动替代sql中的？号
//                for (int i = 0; i < params.size(); i++) {
//                    pstmt.setObject(index++, params.get(i));
//                }
//            }
//            // 返回查询结果集合
//            resultSet = pstmt.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData(); // 返回列的信息
//            int colsLen = metaData.getColumnCount(); //结果集中总的列数
//            while (resultSet.next()) {
//                // 通过反射机制创建一个java实例
//                T resultObject = cls.newInstance();
//                for (int i = 0; i < colsLen; i++) {
//                    String colName = metaData.getColumnName(i + 1); // 获得第i列的名称
//                    Object colValue = resultSet.getObject(colName); // 获得第i列的内容
//                    Field field = cls.getDeclaredField(colName);
//                    field.setAccessible(true); // 打开JavaBean的访问private权限
//                    field.set(resultObject, colValue);
//                }
//                list.add(resultObject);
//            }
//        } catch (Exception e) {
//            LOGGER.error("异常信息:", e);
//        } finally {
//            this.closeStream(connection, pstmt, resultSet);
//        }
//        return list;
//    }
//
//    /**
//     * 查询返回单个JavaBean(通过java反射机制)，返回的是第一行数据
//     * @param sql sql语句
//     * @param params 传参条件
//     * @param cls 反射对象CLass
//     * @param <T> 返回对象类型
//     * @param dataConnection 数据库连接对象
//     * @return 返回的单个对象
//     */
//    public <T> T findOneRefResult(String sql, List<Object> params, Class<T> cls,
//                                  DataConnection dataConnection) {
//        //获得数据库连接对象
//        Connection connection = this.getConnection(dataConnection);
//        //定义SQL预查询对象
//        PreparedStatement pstmt = null;
//        //查询结果对象
//        ResultSet resultSet = null;
//        if (null == connection) {
//            LOGGER.error("数据库连接未实例化");
//            return null;
//        }
//        T result = null;
//        try {
//            result = cls.newInstance();
//        } catch (InstantiationException e) {
//            LOGGER.error("异常信息:", e);
//        } catch (IllegalAccessException e) {
//            LOGGER.error("异常信息:", e);
//        }
//        try {
//            int index = 1; //占位符
//            pstmt = connection.prepareStatement(sql);
//            if (params != null && !params.isEmpty()) {
//                //循环设置sql的传参，会自动替代sql中的？号
//                for (int i = 0; i < params.size(); i++) {
//                    pstmt.setObject(index++, params.get(i));
//                }
//            }
//            // 返回查询结果集合
//            resultSet = pstmt.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData(); // 返回列的信息
//            int colsLen = metaData.getColumnCount(); //结果集中总的列数
//            Boolean flag = false; //创建一个是否查询到一行数据的标志位
//            // 通过反射机制创建一个java实例
//            while (resultSet.next()) {
//                for (int i = 0; i < colsLen; i++) {
//                    String colName = metaData.getColumnName(i + 1); // 获得第i列的名称
//                    Object colValue = resultSet.getObject(colName); // 获得第i列的内容
//                    Field field = cls.getDeclaredField(colName);
//                    field.setAccessible(true); // 打开JavaBean的访问private权限
//                    field.set(result, colValue);
//                    flag = true;
//                }
//                if (flag) {
//                    //如果有一行数据查询出来，就直接返回。
//                    break;
//                }
//            }
//        } catch (Exception e) {
//            LOGGER.error("异常信息", e);
//        } finally {
//            this.closeStream(connection, pstmt, resultSet);
//        }
//        return result;
//    }
//
//    /**
//     * 查询返回单个基本类型(请使用包装类！)，返回的是第一行数据
//     * @param sql sql语句
//     * @param params 传参条件
//     * @param cls 反射对象CLass
//     * @param <T> 返回对象类型
//     * @param dataConnection 数据库连接对象
//     * @return 返回的单个对象
//     */
//    public <T> T findOneBaseResult(String sql, List<Object> params,
//                                   Class<T> cls, DataConnection dataConnection) {
//        //获得数据库连接对象
//        Connection connection = this.getConnection(dataConnection);
//        //定义SQL预查询对象
//        PreparedStatement pstmt = null;
//        //查询结果对象
//        ResultSet resultSet = null;
//        if (null == connection) {
//            LOGGER.error("数据库连接未实例化");
//            return null;
//        }
//        //定义空的返回对象
//        T result = null;
//        try {
//            int index = 1; //占位符
//            pstmt = connection.prepareStatement(sql);
//            if (params != null && !params.isEmpty()) {
//                //循环设置sql的传参，会自动替代sql中的？号
//                for (int i = 0; i < params.size(); i++) {
//                    pstmt.setObject(index++, params.get(i));
//                }
//            }
//            // 返回查询结果集合
//            resultSet = pstmt.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData(); // 返回列的信息
//            // 通过反射机制创建一个java实例
//            while (resultSet.next()) {
//                String colName = metaData.getColumnName(1); // 获得第i列的名称
//                Object colValue = resultSet.getObject(colName); // 获得第i列的内容
//                result = (T) colValue;
//                break;
//            }
//        } catch (Exception e) {
//            LOGGER.error("异常信息", e);
//        } finally {
//            this.closeStream(connection, pstmt, resultSet);
//        }
//        return result;
//    }
//
//
//    /**
//     * 关闭相应的流
//     * @param connection 数据库连接对象
//     * @param pstmt sql预处理对象
//     * @param resultSet 查询返回对象
//     */
//    private void closeStream(Connection connection, PreparedStatement pstmt,
//                             ResultSet resultSet) {
//        //关闭打开的流
//        try {
//            if (null != resultSet) {
//                resultSet.close();
//            }
//            if (null != pstmt) {
//                pstmt.close();
//            }
//            if (null != connection) {
//                connection.close();
//            }
//        } catch (Exception e) {
//            LOGGER.error("异常信息:", e);
//        }
//    }
//}
