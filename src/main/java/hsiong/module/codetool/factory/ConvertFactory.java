package hsiong.module.codetool.factory;

import hsiong.module.codetool.module.ParamBO;
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ConvertFactory {
    
    String getQueryStructSql(String sql, ParamBO paramBO);

    /**
     * https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
     *
     * get SqlRet & parse ret to result TableStructureBO
     * 
     * for the compatibility purpose 
     * that some databases do not support the way getting table structure with SELECT SQL directly
     * 
     * @param resultSet
     * @return
     * @throws SQLException
     */
    TableStructureBO parseRet(ResultSet resultSet) throws SQLException;

    /**
     * convert database structure to java
     * @param list database structure boList
     * @return java structure boList
     */
    List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list);
    
}
