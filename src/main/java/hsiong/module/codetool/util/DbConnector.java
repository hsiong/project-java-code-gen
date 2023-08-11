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
import hsiong.module.codetool.module.ParamDTO;
import hsiong.module.codetool.module.TableInfoBO;
import hsiong.module.codetool.module.TableStructureBO;
import org.springframework.util.ObjectUtils;

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
     * get table info
     * @param paramDTO
     * @return
     */
    protected static String getTableComment(ParamDTO paramDTO) {
        DbEnum dbEnum = paramDTO.getDbEnum();
        Connection connection = initJDBC(paramDTO);

        TableInfoBO tableStructureBO = null;
        String tableName = paramDTO.getTableName();

        try (Statement statement = connection.createStatement()) {
            String queryTableSql = dbEnum.getQueryTableSql(paramDTO);
            ResultSet resultSet = statement.executeQuery(queryTableSql);
            while (resultSet.next()) {
                // only one column
                tableStructureBO  = CommonUtil.parseResultRet(resultSet, TableInfoBO.class);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ObjectUtils.isEmpty(tableStructureBO)) {
            throw new IllegalArgumentException("Table Not Exist! " + tableName);
        }

        String tableComment = tableStructureBO.getTable_comment();
        if (ObjectUtils.isEmpty(tableComment)) {
            throw new IllegalArgumentException("tableComment can not be empty!");
        }
        return tableComment;

    }

    /**
     * using in local & single thread,
     * there is no need to consider high currency design
     */
    protected static List<TableStructureBO> getDbInfo(ParamDTO paramDTO) {
        DbEnum dbEnum = paramDTO.getDbEnum();
        Connection connection = initJDBC(paramDTO);

        // The following statement is a try-with-resources statement, 
        // which declares one resource, stmt, that will be automatically closed when the try block terminates
        List<TableStructureBO> boList = new ArrayList<>();
        String tableName = paramDTO.getTableName();
        try (Statement statement = connection.createStatement()) {
            String queryTableSql = dbEnum.getQueryStructureSql(paramDTO);
            ResultSet resultSet = statement.executeQuery(queryTableSql);
            while (resultSet.next()) {
                // only one column
                TableStructureBO tableStructureBO = CommonUtil.parseResultRet(resultSet, TableStructureBO.class);
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

    /**
     * init JDBC connection
     * @param paramDTO
     * @return
     */
    private static Connection initJDBC(ParamDTO paramDTO) {
        DbEnum dbEnum = paramDTO.getDbEnum();
        String dbDriver = dbEnum.getDbDriver();

        Connection connection = null;
        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(paramDTO.getDbUrl(), paramDTO.getUser(), paramDTO.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
