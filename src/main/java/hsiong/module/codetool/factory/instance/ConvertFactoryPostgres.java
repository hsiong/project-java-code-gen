package hsiong.module.codetool.factory.instance;

import hsiong.module.codetool.factory.ConvertFactory;
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertFactoryPostgres implements ConvertFactory {

    @Override
    public List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list) {
        List<TableStructureJavaBO> javaBOList = list.stream().map(i -> {
            TableStructureJavaBO javaBO = new TableStructureJavaBO();
            BeanUtils.copyProperties(i, javaBO);
            javaBO.setJava_column_name(i.getColumn_name());
            javaBO.setJava_data_type(i.getData_type());
            return javaBO;
        }).collect(Collectors.toList());
        return javaBOList;
    }

    /**
     * convert Postgres sructure to Java 
     * @param postgresDataType Postgres data type
     * @return Java data type
     */
    private String convertPostgresSructureToJava(String postgresDataType) {
        // TODO: convertPostgresSructureToJava
        return postgresDataType;
    }
}
