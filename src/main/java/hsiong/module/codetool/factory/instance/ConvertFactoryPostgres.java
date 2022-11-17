package hsiong.module.codetool.factory.instance;

import hsiong.module.codetool.constant.JavaTypeConstant;
import hsiong.module.codetool.factory.ConvertFactory;
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;
import org.springframework.beans.BeanUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConvertFactoryPostgres implements ConvertFactory {

    /**
     * init postgresConvertMap 
     * https://www.educative.io/answers/how-to-initialize-a-static-map-in-java
     * https://blog.csdn.net/xiaojuge/article/details/101628157
     */
    private static final LinkedHashMap<String, String> postgresConvertMap;
    static {
        postgresConvertMap = new LinkedHashMap<>();
        postgresConvertMap.put("date", JavaTypeConstant.JAVA_DATE);
        postgresConvertMap.put("time", JavaTypeConstant.JAVA_TIME);
        postgresConvertMap.put("timestamp without timezone", JavaTypeConstant.JAVA_DATE_TIME);
        postgresConvertMap.put("timestamp with timezone", JavaTypeConstant.JAVA_DATE_TIME_OFFSET);
        postgresConvertMap.put("character", JavaTypeConstant.JAVA_STRING);
        postgresConvertMap.put("varchar", JavaTypeConstant.JAVA_STRING);
        postgresConvertMap.put("text", JavaTypeConstant.JAVA_STRING);
        postgresConvertMap.put("smallint", JavaTypeConstant.JAVA_INTEGER);
        postgresConvertMap.put("integer", JavaTypeConstant.JAVA_INTEGER);
        postgresConvertMap.put("int2", JavaTypeConstant.JAVA_INTEGER);
        postgresConvertMap.put("int4", JavaTypeConstant.JAVA_INTEGER);
        postgresConvertMap.put("int8", JavaTypeConstant.JAVA_LONG);
        postgresConvertMap.put("float4", JavaTypeConstant.JAVA_FLOAT);
        postgresConvertMap.put("float8", JavaTypeConstant.JAVA_DOUBLE);
        postgresConvertMap.put("numeric", JavaTypeConstant.JAVA_BIG_DECIMAL);
        postgresConvertMap.put("bool", JavaTypeConstant.JAVA_BOOLEAN);
        
    }

    @Override
    public List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list) {
        List<TableStructureJavaBO> javaBOList = list.stream().map(i -> {
            String dataType = i.getData_type();
            String javaDataType = convertPostgresStructureToJava(dataType);
            System.out.println("dataType");
            System.out.println(dataType);
            System.out.println("javaDataType");
            System.out.println(javaDataType);
            
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
     * @param postgresDataType Postgres data type
     * @return Java data type
     */
    private String convertPostgresStructureToJava(String postgresDataType) {
        for (Map.Entry<String, String> convertEntry : postgresConvertMap.entrySet()) {
            String key = convertEntry.getKey();
            if (postgresDataType.contains(key)) {
                return convertEntry.getValue();
            }
        }
        return JavaTypeConstant.JAVA_STRING;
    }
}
