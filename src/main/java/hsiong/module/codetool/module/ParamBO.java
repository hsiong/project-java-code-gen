/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: ParamBO
 * @Author: Hsiong
 * @Date: 2022/4/20 4:58 PM
 * @Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package hsiong.module.codetool.module;

import hsiong.module.codetool.annotation.GenNotEmpty;
import hsiong.module.codetool.constant.DbEnum;
import lombok.Getter;

/**
 * 〈〉
 *
 * @author Hsiong
 * @create 2022/4/20
 * @since 1.0.0
 */
@Getter
public class ParamBO {

    /**
     * dbEnum
     */
    @GenNotEmpty
    private DbEnum dbEnum;

    /**
     * 数据库连接地址
     */
    @GenNotEmpty
    private String dbUrl;

    /**
     * 数据库用户和密码
     */
    @GenNotEmpty
    private String user;
    @GenNotEmpty
    private String password;

    /**
     * 数据库名称
     */
    @GenNotEmpty
    private String database;

    /**
     * 数据库表名
     * @return
     */
    @GenNotEmpty
    private String tableName;

    /**
     * rewrite setDbEnum(), check dbEnum value while setting dbEnum
     * @param dbEnum dbEnum
     */
    private void setDbEnum(DbEnum dbEnum) {
        if (!DbEnum.getSupportedDbEnumList().contains(dbEnum)) {
            throw new IllegalArgumentException("NOT SUPPORT DATABASE");
        }
        this.dbEnum = dbEnum;
    }

    /**
     * rewrite setDbUrl(), set dbUrl by dbUrl & dbEnum & database
     * @param dbUrl dbUrl
     */
    private void setDbUrl(String dbUrl) {
        String url = "jdbc:" + this.dbEnum.getDbType() + "://" + dbUrl + "/" + this.database;
        this.dbUrl = url;
    }

    public ParamBO(DbEnum dbEnum, String dbUrl, String user, String password, String database, String tableName) {
        setDbEnum(dbEnum);
        this.database = database;
        this.user = user;
        this.password = password;
        this.tableName = tableName;
        setDbUrl(dbUrl);
    }
    
    
}
