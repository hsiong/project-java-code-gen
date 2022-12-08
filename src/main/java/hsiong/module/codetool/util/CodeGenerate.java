package hsiong.module.codetool.util; /**
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
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;

import java.util.List;

/**
 * 〈〉
 *
 * @author hsiong
 * @create 2022/4/20
 * @since 1.0.0
 */
public class CodeGenerate {

    /**
     * 生成代码文件
     * @param paramBO
     * @param tableInfoBO
     */
    public static void codeGenerate(ParamBO paramBO, TableInfoBO tableInfoBO) {

        // get table structure
        List<TableStructureBO> stuctureBOList = DbConnector.getDbInfo(paramBO);

        // convert structure to java
        DbEnum dbEnum = paramBO.getDbEnum();
        List<TableStructureJavaBO> list = dbEnum.getConvertFactory().convertStructureToJava(stuctureBOList);

        // execute FreeMarker
        if (tableInfoBO.getEntityName() == null || tableInfoBO.getEntityName().length() == 0) {
            tableInfoBO.setEntityNameFromTableName(paramBO.getTableName());
        }
        tableInfoBO.setInfo(paramBO.getTableName());
        FreeMarkerUtil.executeFreeMarker(tableInfoBO, list);

    }


    
    

}
