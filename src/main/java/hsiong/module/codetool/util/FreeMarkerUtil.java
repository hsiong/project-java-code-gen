package hsiong.module.codetool.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import hsiong.module.codetool.constant.RegConstant;
import hsiong.module.codetool.module.TableInfoDTO;
import hsiong.module.codetool.module.TableStructureJavaBO;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;

public class FreeMarkerUtil {

	/**
	 * 执行 freeMarker 文件, 生成代码
	 *
	 * @param tableInfoDTO
	 * @param stuctureBOList
	 */
	protected static void executeFreeMarker(TableInfoDTO tableInfoDTO,
											List<TableStructureJavaBO> stuctureBOList) {

		try {
			// init freeMarker param
			LinkedHashMap<String, Object> root = new LinkedHashMap<>();
			root.put("columns", stuctureBOList);
			Class c = tableInfoDTO.getClass();
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object filedValue = field.get(tableInfoDTO);
				root.put(fieldName, filedValue);
			}

			// execute freeMarker
			List<File> templateFiles = tableInfoDTO.listTemplateFile();
			String outputDir = tableInfoDTO.getOutputDir();
			String tempDir = tableInfoDTO.getTemplateDir();
			for (File templateFile : templateFiles) {
				String fileDir = templateFile.getAbsolutePath()
											 .replace(tempDir, "")
											 .replace("${entityName}", tableInfoDTO.getEntityName())
											 .replaceAll(RegConstant.FILE_REG, "");
				String newFileDir = outputDir + fileDir;
				File newFile = new File(newFileDir);
				if (!newFile.exists()) {
					newFile.getParentFile().mkdirs();
				}
				Writer out =
					new OutputStreamWriter(new FileOutputStream(newFile), StandardCharsets.UTF_8.name());
				Template template = getTemplate(templateFile);
				template.process(root, out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}


	}

	/**
	 * 根据每个模板获取 freeMarker - Template
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private static Template getTemplate(File file) throws IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
		try {
			// cfg configure
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			cfg.setLogTemplateExceptions(false);
			cfg.setWrapUncheckedExceptions(true);
			cfg.setFallbackOnNullLoopVariable(false);
			cfg.setDirectoryForTemplateLoading(file.getParentFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Template temp = cfg.getTemplate(file.getName());
		return temp;
	}


}
