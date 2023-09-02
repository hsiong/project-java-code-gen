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
import hsiong.module.codetool.module.ParamDTO;
import hsiong.module.codetool.module.TableInfoDTO;
import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;
import org.springframework.util.ObjectUtils;

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
     * @param paramDTO
     * @param tableInfoDTO
     */
    public static void codeGenerate(ParamDTO paramDTO, TableInfoDTO tableInfoDTO) {

        String entityDesc = tableInfoDTO.getEntityDesc();
        if (ObjectUtils.isEmpty(entityDesc)) { // if entityDesc is empty, get table comment
            entityDesc = DbConnector.getTableComment(paramDTO);
        }
        tableInfoDTO.setEntityDesc(entityDesc);

        // get table structure
        List<TableStructureBO> stuctureBOList = DbConnector.getDbInfo(paramDTO);

        // convert structure to java
        DbEnum dbEnum = paramDTO.getDbEnum();
        List<TableStructureJavaBO> list = dbEnum.getConvertFactory().convertStructureToJava(stuctureBOList);
        // execute FreeMarker
        FreeMarkerUtil.executeFreeMarker(tableInfoDTO, list);

        System.out.println();
        System.out.println(" proceed successfully  (ฅ´ω`ฅ) ");

    }

    /**
     * 生成代码文件 - 多表
     * @param paramDTO
     * @param tableInfoDTO
     * @param multiTableList 多个表
     */
    public static void codeGenerateMulti(ParamDTO paramDTO, TableInfoDTO tableInfoDTO, List<String> multiTableList) {

        for (String mutiTable : multiTableList) {
            paramDTO.setTableName(mutiTable);
            codeGenerate(paramDTO, tableInfoDTO);
        }

    }
    
    

}
