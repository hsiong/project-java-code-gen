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
     * init postgresConvertMap 
     * https://www.educative.io/answers/how-to-initialize-a-static-map-in-java
     * https://blog.csdn.net/xiaojuge/article/details/101628157
     * https://www.cnblogs.com/pypua/articles/9907831.html
     */
    private static final LinkedHashMap<String, String> mysql8ConvertMap;
    static {
        mysql8ConvertMap = new LinkedHashMap<>();

        
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
