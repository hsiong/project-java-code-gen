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

import hsiong.module.codetool.module.ParamBO;
import hsiong.module.codetool.module.TableInfoBO;
import hsiong.module.codetool.module.TableStructureBO;

import java.util.List;

/**
 * 〈〉
 *
 * @author hsiong
 * @create 2022/4/20
 * @since 1.0.0
 */
public class CodeGenerate {

    public static void codeGenerate(ParamBO paramBO, TableInfoBO tableInfoBO) {

        List<TableStructureBO> stuctureBOList = DbConnector.getDbInfo(paramBO);
        FreeMarkerUtil.generateFreeMarker(tableInfoBO, stuctureBOList);

    }
    
    

}
