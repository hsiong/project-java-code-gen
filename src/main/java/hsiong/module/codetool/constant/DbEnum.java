package hsiong.module.codetool.constant;

import hsiong.module.codetool.factory.ConvertFactory;
import hsiong.module.codetool.factory.instance.ConvertFactoryMysql8;
import hsiong.module.codetool.factory.instance.ConvertFactoryPostgres;
import hsiong.module.codetool.module.ParamBO;
import lombok.Getter;

import java.util.Arrays;
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
            "SELECT t.TABLE_CATALOG, TABLE_SCHEMA, TABLE_NAME, COLUMN_NAME AS 'column_name', ORDINAL_POSITION AS 'ordinal_position', COLUMN_DEFAULT AS 'column_default', IS_NULLABLE AS 'is_nullable', DATA_TYPE AS 'data_type', CHARACTER_MAXIMUM_LENGTH AS 'character_maximum_length', CHARACTER_OCTET_LENGTH, NUMERIC_PRECISION AS 'numeric_precision', NUMERIC_SCALE AS 'numeric_scale', DATETIME_PRECISION, CHARACTER_SET_NAME, COLLATION_NAME, COLUMN_TYPE, COLUMN_KEY, EXTRA, PRIVILEGES, COLUMN_COMMENT AS 'column_description', GENERATION_EXPRESSION FROM information_schema.COLUMNS t WHERE TABLE_NAME = ':tableName'  AND TABLE_SCHEMA = ':scheme' ORDER BY ORDINAL_POSITION ",
            ConvertFactoryMysql8.class
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

    /**
     * rewrite getQueryStructureSql
     * @param tableName
     * @return return queryStructureSql 
     */
    public String getQueryStructureSql(ParamBO paramBO) {
        return convertFactory.getQueryStructSql(queryStructureSql, paramBO);
    }

    /**
     * @since v1.0.0 support POSTGRESQL
     * @return
     */
    public static List<DbEnum> getSupportedDbEnumList() {
        return Arrays.asList(DbEnum.POSTGRESQL, DbEnum.MYSQL_8);
    }
}
