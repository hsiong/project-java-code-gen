package hsiong.module.codetool.factory;

import hsiong.module.codetool.module.TableStructureBO;
import hsiong.module.codetool.module.TableStructureJavaBO;

import java.util.List;

public interface ConvertFactory {
    
    List<TableStructureJavaBO> convertStructureToJava(List<TableStructureBO> list);
    
}
