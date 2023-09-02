/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: CodeGenrateUtil
 * @Author: hsiong
 * @Date: 2022/4/20 4:06 PM
 * @Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

import hsiong.module.codetool.constant.DbEnum;
import hsiong.module.codetool.module.ParamDTO;
import hsiong.module.codetool.module.TableInfoDTO;
import hsiong.module.codetool.util.CodeGenerate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 〈〉
 *
 * @author hsiong
 * @create 2022/4/20
 * @since 1.0.0
 */
public class DemoTest {

    public static void initProperty() {

    }

    /**
     * 单表
     */
    @Test
    public void mainTest() {

        // 数据库链接信息
        ParamDTO paramBO = new ParamDTO(DbEnum.POSTGRESQL, "dbUrl", "user", "pwd", "database", "tableName");

        // 生成信息
        TableInfoDTO tableInfoBO = new TableInfoDTO("com.weishan", "result", "", paramBO.getTableName());
        CodeGenerate.codeGenerate(paramBO, tableInfoBO);

    }

    /**
     * 忽略表前缀
     */
    public void ignoreTableEntityNameTest() {
        // 数据库链接信息
        ParamDTO paramBO = new ParamDTO(DbEnum.POSTGRESQL, "dbUrl", "user", "pwd", "database", "tableName");

        // 生成信息
        TableInfoDTO tableInfoBO = new TableInfoDTO("com.weishan", "result", "", paramBO.getTableName(), "ignoreTablePrefix");
        CodeGenerate.codeGenerate(paramBO, tableInfoBO);
    }

    /**
     * 多表测试
     */
    public void multiTest() {
        // 数据库链接信息
        ParamDTO paramBO = new ParamDTO(DbEnum.POSTGRESQL, "dbUrl", "user", "pwd", "database", "tableName");

        // 生成信息
        TableInfoDTO tableInfoBO = new TableInfoDTO("com.weishan", "result", "", paramBO.getTableName(), "ignoreTablePrefix");

        List<String> multiTableList = Arrays.asList("table1", "table2");
        CodeGenerate.codeGenerateMulti(paramBO, tableInfoBO, multiTableList);
    }

}
