package hsiong.module.codetool.module;

import hsiong.module.codetool.constant.RegConstant;
import lombok.Data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class TableInfoBO {

    private String basePackage;

    private String templateDir;

    private String outputDir;

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
            this.outputDir = getResourceDir() + "output";
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
        java.net.URL uri = this.getClass().getResource("/");
        System.out.println(uri.getPath());
        return uri.getPath();
    }

}
