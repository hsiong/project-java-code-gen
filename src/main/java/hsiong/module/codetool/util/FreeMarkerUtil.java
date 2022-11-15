package hsiong.module.codetool.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import hsiong.module.codetool.constant.RegConstant;
import hsiong.module.codetool.module.TableInfoBO;
import hsiong.module.codetool.module.TableStructureBO;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FreeMarkerUtil {

    protected static void generateFreeMarker(TableInfoBO tableInfoBO, List<TableStructureBO> stuctureBOList) {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        try {
            // cfg configure
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            cfg.setDirectoryForTemplateLoading(new File(tableInfoBO.getTemplateDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Map<String, Object> root = new HashMap<>();
            root.put("boList", stuctureBOList);
            // TODO: test
            root.put("test", "test");

            File[] files = tableInfoBO.getTemplateFile();
            String outputDir = tableInfoBO.getOutputDir();
            for (File file : files) {
                String fileName = file.getName();
                String newFileName = fileName.replaceAll(RegConstant.FILE_REG, "");
                Template temp = cfg.getTemplate(fileName);
                File newFile = new File(outputDir + File.separator + newFileName);
                Writer out = new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8.name());
                temp.process(root, out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }


    }


}
