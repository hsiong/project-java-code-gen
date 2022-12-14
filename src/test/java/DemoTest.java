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
import hsiong.module.codetool.module.ParamBO;
import hsiong.module.codetool.module.TableInfoBO;
import hsiong.module.codetool.util.CodeGenerate;
import org.junit.Test;

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

    @Test
    public void mainTest() {

        // 数据库链接信息
        ParamBO paramBO = new ParamBO(DbEnum.POSTGRESQL,
                                      "dbUrl",
                                      "user",
                                      "pwd",
                                      "database",
                                      "tableName");
        
        // 生成信息
        TableInfoBO tableInfoBO = new TableInfoBO("com.weishan",
                                                  "result",
                                                  "",
                                                  "识别结果",
                                                  paramBO.getTableName());
        CodeGenerate.codeGenerate(paramBO, tableInfoBO);
        

    }

}
