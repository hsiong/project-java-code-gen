package hsiong.module.codetool.constant;

import hsiong.module.codetool.factory.ConvertFactory;
import hsiong.module.codetool.factory.instance.ConvertFactoryPostgres;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public enum DbEnum {

    ORACLE("oracle", 
           "", 
           "oracle", 
           "",
           null
    ),
    
    POSTGRESQL("postgresql", 
               "org.postgresql.Driver", 
               "postgresql", 
               "select col.ordinal_position, col.column_name, col.data_type, col.character_maximum_length, col.numeric_precision, col.numeric_scale, col.is_nullable, col.column_default, des.description AS column_description from information_schema.columns col left join pg_description des on col.table_name::regclass = des.objoid and col.ordinal_position = des.objsubid where table_schema = 'public' and table_name = ':tableName' order by ordinal_position;",
               ConvertFactoryPostgres.class
    ),
    
    MYSQL_5("mysql_5.xx", 
            "com.mysql.jdbc.Driver", 
            "mysql", 
            "",
            null
    ),
    
    MYSQL_8("mysql_8.xx", 
            "com.mysql.cj.jdbc.Driver", 
            "mysql", 
            "",
            null
    );

    private String dbName;
    private String dbDriver;
    private String dbType;
    private String queryStructureSql;
    private ConvertFactory convertFactory;

    DbEnum(String dbName, String dbDriver, String dbType, String queryStructureSql, Class<? extends ConvertFactory> tClass) {
        this.dbName = dbName;
        this.dbDriver = dbDriver;
        this.dbType = dbType;
        this.queryStructureSql = queryStructureSql;
        if (tClass != null) {
            try {
                this.convertFactory = tClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                String msg = String.format("New Instance %s Error: %s", tClass.getName(), e.getMessage());
                throw new IllegalArgumentException(msg);
            }
        }
    }

    public String getQueryStructureSql(String tableName) {
        queryStructureSql = queryStructureSql.replace(":tableName", tableName);
        return queryStructureSql;
    }

    /**
     * @since v1.0.0 support POSTGRESQL
     * @return
     */
    public static List<DbEnum> getSupportedDbEnumList() {
        return Collections.singletonList(DbEnum.POSTGRESQL);
    }
}
