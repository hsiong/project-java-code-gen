package hsiong.module.codetool.factory;

import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;

import java.util.List;

public interface ConvertFactory {

    /**
     * convert database structure to java
     * @param list database structure boList
     * @return java structure boList
     */
    List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list);
    
}
