package hsiong.module.codetool.factory.instance;

import hsiong.module.codetool.constant.JavaTypeConstant;
import hsiong.module.codetool.factory.ConvertFactory;
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;
import hsiong.module.codetool.util.CommonUtil;
import org.springframework.beans.BeanUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertFactoryMysql8 implements ConvertFactory {

    /**
     * init mysql8ConvertMap 
     * 
     */
    private static final LinkedHashMap<String, String> mysql8ConvertMap;
    static {
        mysql8ConvertMap = new LinkedHashMap<>();
        mysql8ConvertMap.put("date", JavaTypeConstant.JAVA_DATE);
        mysql8ConvertMap.put("time", JavaTypeConstant.JAVA_TIME);
        mysql8ConvertMap.put("timestamp without timezone", JavaTypeConstant.JAVA_DATE_TIME);
        mysql8ConvertMap.put("timestamp with timezone", JavaTypeConstant.JAVA_DATE_TIME_OFFSET);
        mysql8ConvertMap.put("character", JavaTypeConstant.JAVA_STRING);
        mysql8ConvertMap.put("varchar", JavaTypeConstant.JAVA_STRING);
        mysql8ConvertMap.put("text", JavaTypeConstant.JAVA_STRING);
        mysql8ConvertMap.put("smallint", JavaTypeConstant.JAVA_INTEGER);
        mysql8ConvertMap.put("integer", JavaTypeConstant.JAVA_INTEGER);
        mysql8ConvertMap.put("bigint", JavaTypeConstant.JAVA_LONG);
        mysql8ConvertMap.put("int2", JavaTypeConstant.JAVA_INTEGER);
        mysql8ConvertMap.put("int4", JavaTypeConstant.JAVA_INTEGER);
        mysql8ConvertMap.put("int8", JavaTypeConstant.JAVA_LONG);
        mysql8ConvertMap.put("float4", JavaTypeConstant.JAVA_FLOAT);
        mysql8ConvertMap.put("float8", JavaTypeConstant.JAVA_DOUBLE);
        mysql8ConvertMap.put("numeric", JavaTypeConstant.JAVA_BIG_DECIMAL);
        mysql8ConvertMap.put("bool", JavaTypeConstant.JAVA_BOOLEAN);
        
    }

    @Override
    public TableStructureBO parseRet(ResultSet resultSet) throws SQLException {
        TableStructureBO o = CommonUtil.parseResultRet(resultSet, TableStructureBO.class);
        return o;
    }

    @Override
    public List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list) {
        List<TableStructureJavaBO> javaBOList = list.stream().map(i -> {
            String dataType = i.getData_type();
            System.out.println("dataType");
            System.out.println(dataType);
            String javaDataType = convertMysql8StructureToJava(dataType);
            TableStructureJavaBO javaBO = new TableStructureJavaBO();
            BeanUtils.copyProperties(i, javaBO);
            javaBO.setJava_column_name(i.getColumn_name());
            javaBO.setJava_data_type(javaDataType);
            return javaBO;
        }).collect(Collectors.toList());
        return javaBOList;
    }

    /**
     * convert Postgres sructure to Java 
     * @param mysql8DataType Postgres data type
     * @return Java data type
     */
    private String convertMysql8StructureToJava(String mysql8DataType) {
        for (Map.Entry<String, String> convertEntry : mysql8ConvertMap.entrySet()) {
            String key = convertEntry.getKey();
            if (mysql8DataType.contains(key)) {
                return convertEntry.getValue();
            }
        }
        return JavaTypeConstant.JAVA_STRING;
    }
}
