/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: DbConnector
 * @Author: Hsiong
 * @Date: 2022/4/20 4:16 PM
 * @Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package hsiong.module.codetool.util;

import hsiong.module.codetool.constant.DbEnum;
import hsiong.module.codetool.module.ParamBO;
import hsiong.module.codetool.module.TableStructureBO;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author Hsiong
 * @create 2022/4/20
 * @since 1.0.0
 */
public class DbConnector {

    /**
     * using in local & single thread,
     * there is no need to consider high currency design
     */
    protected static List<TableStructureBO> getDbInfo(ParamBO paramBO) {

        DbEnum dbEnum = paramBO.getDbEnum();
        String dbDriver = dbEnum.getDbDriver();

        // init JDBC connection
        Connection connection = null;
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(paramBO.getDbUrl(), paramBO.getUser(), paramBO.getPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // The following statement is a try-with-resources statement, 
        // which declares one resource, stmt, that will be automatically closed when the try block terminates
        List<TableStructureBO> boList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String tableName = paramBO.getTableName();
            String queryTableSql = dbEnum.getQueryStructureSql(tableName);
            ResultSet resultSet = statement.executeQuery(queryTableSql);
            while (resultSet.next()) {
                // only one column
                TableStructureBO tableStructureBO = getRet(resultSet, TableStructureBO.class);
                boList.add(tableStructureBO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return boList;
    }

    /**
     * @param resultSet
     * @param tClass
     * @param <T>
     * @return
     * @apiNote https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
     */
    private static <T> T getRet(ResultSet resultSet, Class<T> tClass) throws SQLException {
        T t = null;
        try {
            t = tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            String msg = String.format("New Instance %s Error: %s", tClass.getName(), e.getMessage());
            throw new IllegalArgumentException(msg);
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldSqlName = ReflectUtil.camelToUnderline(fieldName);
            Object value = resultSet.getObject(fieldSqlName);
            try {
                field.set(t, String.valueOf(value));
            } catch (IllegalAccessException e) {
                String msg = String.format("IllegalAccessException %s Error: %s", field.getName(), e.getMessage());
                throw new IllegalArgumentException(msg);
            }
        }

        return t;


    }

}
