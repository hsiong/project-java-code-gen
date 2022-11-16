package hsiong.module.codetool.module;

import hsiong.module.codetool.annotation.GenNotEmpty;
import hsiong.module.codetool.constant.RegConstant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class TableInfoBO {

    @GenNotEmpty
    private String basePackage;

    private String packageName;
    
    private String templateDir;

    private String outputDir;
    
    /**
     * 生成实体名, 可以为空
     * default value: tableName -> camelize
     * @return
     */
    private String entityName;
    
    private String entityDesc;

    private String tableName;

    public void setEntityNameFromTableName(String tableName) {
        this.entityName = underlineToEntityName(tableName);
    }

    /**
     * 获取输出路径
     * @return
     */
    public String getOutputDir() {
        if (this.outputDir == null) {
            this.outputDir = getResourceDir() + "output" + File.separator + this.entityName.toLowerCase(Locale.ROOT);
        }
        File file = new File(this.outputDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return outputDir;
    }

    /**
     * 获取模板路径
     * @return
     */
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

    /**
     * 获取模板路径下的所有文件
     * @return
     */
    public List<File> getTemplateFile() {
        String tempDir = getTemplateDir();
        File file = new File(tempDir);

        List<File> tempList = listAllFile(file);
        return tempList;
    }

    /**
     * 数据非空处理
     */
    public void setInfo(String tableName) {
        if (this.getPackageName() == null) {
            // default packageName is entityName.toLowerCase()
            this.setPackageName(this.getEntityName().toLowerCase());
        }
        if (this.getEntityDesc() == null) {
            // TODO: or perhaps using Table Comment ? 
            this.setEntityDesc("BaseDesc");
        }
        this.setTableName(tableName);
    }

    /**
     * 获取当前 resource 路径
     * @return
     */
    private String getResourceDir() {
        File file = new File("src/main/resource");
        String dir = file.getAbsolutePath() + File.separator;
        return dir;
    }

    /**
     * 递归获取文件夹下所有文件
     * @param parent
     * @return
     */
    private List<File> listAllFile(File parent) {
        // only read files which match the Reg "i$"
        String regular = RegConstant.FILE_REG;
        Pattern p = Pattern.compile(regular);
        Matcher m = p.matcher(parent.getName());
        List<File> totalTemps = new ArrayList<>();
        if (parent.isDirectory()) {
            File[] directories = parent.listFiles();
            for (File directory : directories) {
                List<File> temps = listAllFile(directory);
                totalTemps.addAll(temps);
            }
        } else if (m.find()){
            totalTemps.add(parent);
        }
        return totalTemps;
    }

    /**
     * 将下划线命名转化成 EntityName
     *
     * https://www.delftstack.com/zh/howto/java/how-to-capitalize-the-first-letter-of-a-string-in-java/
     * @param param
     * @return
     */
    private String underlineToEntityName(String param) {
        String[] strings = param.split("_");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            String temp = strings[i];
            builder.append(Character.toUpperCase(temp.charAt(0)));
            builder.append(temp, 1, temp.length());
        }
        return builder.toString();
    }
    
}
