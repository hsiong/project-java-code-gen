package hsiong.module.codetool.module;

import hsiong.module.codetool.annotation.GenNotEmpty;
import hsiong.module.codetool.constant.RegConstant;
import lombok.Data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class TableInfoBO {
    
    @GenNotEmpty
    private String templateDir;

    @GenNotEmpty
    private String outputDir;

    @GenNotEmpty
    private String basePackage;
    
    /**
     * 生成实体名, 可以为空
     * default value: tableName -> camelize
     * @return
     */
    private String entityName;
    
    private String packageName;

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTemplateDir() {
        if (this.templateDir == null) {
            this.templateDir = getResourceDir() + "template";
        }
        File file = new File(this.templateDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return this.templateDir;
    }

    public String getOutputDir() {
        if (this.outputDir == null) {
            this.outputDir = getResourceDir() + "output" + File.separator + this.entityName;
        }
        File file = new File(this.outputDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return outputDir;
    }

    public File[] getTemplateFile() {
        String tempDir = getTemplateDir();
        File file = new File(tempDir);
        // only read files which match the Reg "i$"
        FilenameFilter fileNameFilter = (dir, name) -> {
            System.out.println("name");
            System.out.println(name);
            String regular = RegConstant.FILE_REG;
            Pattern p = Pattern.compile(regular);
            Matcher m = p.matcher(name);
            while (m.find()) {
                return true;
            }
            return false;
        };
        File[] tempList = file.listFiles();
        return tempList;
    }

    private String getResourceDir() {
        File file = new File("src/main/resource");
        String dir = file.getAbsolutePath() + File.separator;
        System.out.println(dir);
        return dir;
    }
    
    

}
