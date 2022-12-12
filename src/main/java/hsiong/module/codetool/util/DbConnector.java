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
        String tableName = paramBO.getTableName();
        try (Statement statement = connection.createStatement()) {
            String queryTableSql = dbEnum.getQueryStructureSql(tableName);
            ResultSet resultSet = statement.executeQuery(queryTableSql);
            while (resultSet.next()) {
                // only one column
                TableStructureBO tableStructureBO = dbEnum.getConvertFactory().parseRet(resultSet);
                boList.add(tableStructureBO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        if (boList.size() == 0) {
            throw new IllegalArgumentException("Table Not Exist! " + tableName);
        }

        return boList;
    }



}
