package hsiong.module.codetool.module;

import hsiong.module.codetool.annotation.GenNotEmpty;
import hsiong.module.codetool.constant.RegConstant;
import hsiong.module.codetool.util.CommonUtil;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Data
public class TableInfoDTO {

    @GenNotEmpty
    private String basePackage;

    private String packageName;

    private String templateDir;

    private String outputDir;

    /**
     * 生成实体名
     * 若为null, default value: tableName -> camelize
     *
     * @return
     */
    private String entityName;

    /**
     * 实体描述
     */
    private String entityDesc;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 忽略表前缀
     */
    private String ignoreTablePrefix;

    public TableInfoDTO(String basePackage, String packageName, String entityDesc, String tableName) {
        
        if (CommonUtil.isEmpty(packageName)) {
            // default packageName is entityName.toLowerCase()
            packageName = this.getEntityName().toLowerCase();
        }

        this.entityName = underlineToEntityName(tableName);
        this.packageName = packageName;
        this.basePackage = basePackage;
        this.entityDesc = entityDesc;
        this.tableName = tableName;
    }

    public TableInfoDTO(String basePackage,
                        String packageName,
                        String entityDesc,
                        String tableName,
                        String ignoreTablePrefix) {

        this(basePackage, packageName, entityDesc, tableName);
        
        if (CommonUtil.isNotEmpty(ignoreTablePrefix)) {
            this.entityName = entityName.replaceFirst(ignoreTablePrefix, "");
        }

        this.ignoreTablePrefix = ignoreTablePrefix;
    }

    /**
     * rewrite getOutputDir
     *
     * @return 获取输出路径
     */
    public String getOutputDir() {
        if (CommonUtil.isEmpty(this.outputDir)) {
            this.outputDir = getResourceDir() + "output";
            // clear default output dir
            Path path = Paths.get(this.outputDir);
            try (Stream<Path> walk = Files.walk(path)) {
                walk.sorted(Comparator.reverseOrder()).forEach(this::deleteDirectoryStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String packageDir = this.outputDir + File.separator + this.packageName.toLowerCase(Locale.ROOT);
        File packageFile = new File(packageDir);
        if (!packageFile.exists()) {
            packageFile.mkdirs();
        }

        File file = new File(this.outputDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return this.outputDir;
    }

    /**
     * rewrite getTemplateDir
     *
     * @return 获取模板路径
     */
    public String getTemplateDir() {
        if (CommonUtil.isEmpty(this.templateDir)) {
            this.templateDir = getResourceDir() + "template";
        }
        File file = new File(this.templateDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return this.templateDir;
    }

    /**
     * list all matched files in TemplateDir
     *
     * @return 获取模板路径下的所有文件
     */
    public List<File> listTemplateFile() {
        String tempDir = getTemplateDir();
        File file = new File(tempDir);

        List<File> tempList = listAllFile(file);
        return tempList;
    }

    /**
     * get project-resource absolute path
     *
     * @return 获取当前 resource 绝对路径
     */
    private String getResourceDir() {
        File file = new File("src/main/resource");
        String dir = file.getAbsolutePath() + File.separator;
        return dir;
    }

    /**
     * using recursive to list all files in aim director
     *
     * @param parent aim director
     * @return 递归获取文件夹下所有文件
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
        } else if (m.find()) {
            totalTemps.add(parent);
        }
        return totalTemps;
    }

    /**
     * convert underline name to EntityName
     *
     * @param param underline name
     * @return 将下划线命名转化成 EntityName
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

    private void deleteDirectoryStream(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
