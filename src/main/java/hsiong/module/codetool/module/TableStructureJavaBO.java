/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: TableStuctureBO
 * @Author: Hsiong
 * @Date: 2022/4/21 11:40 AM
 * @Description: History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package hsiong.module.codetool.module;

import lombok.Data;

/**
 * 〈〉
 *
 * @author Hsiong
 * @create 2022/4/21
 * @since 1.0.0
 */
@Data
public class TableStructureJavaBO {

    /**
     * 字段在表的位置
     */
    private String ordinal_position;

    /**
     * 字段名
     */
    private String column_name;

    /**
     * java 字段名
     */
    private String java_column_name;

    /**
     * 字段类型
     */
    private String data_type;

    /**
     * java 字段类型
     */
    private String java_data_type;

    /**
     * 字段长度
     * may null
     */
    private String character_maximum_length;

    /**
     * 精度
     * may null
     */
    private String numeric_precision;


    /**
     * 小数位
     * may null
     */
    private String numeric_scale;

    /**
     * 是否可以为空
     * NO: support null value
     * YES: not support null value
     */
    private String is_nullable;

    /**
     * 字段默认值
     * column default value
     */
    private String column_default;

    /**
     * column description
     */
    private String column_description;
    
    

    public void setJava_column_name(String java_column_name) {
        this.java_column_name = underlineToCamel(java_column_name);
    }

    /**
     * 将下划线命名转化成驼峰
     *
     * https://www.delftstack.com/zh/howto/java/how-to-capitalize-the-first-letter-of-a-string-in-java/
     * @param param
     * @return
     */
    private String underlineToCamel(String param) {
        String[] strings = param.split("_");
        StringBuilder builder = new StringBuilder();
        builder.append(strings[0]);
        if (strings.length > 1) {
            for (int i = 1; i < strings.length; i++) {
                String temp = strings[i];
                builder.append(Character.toUpperCase(temp.charAt(0)));
                builder.append(temp, 1, temp.length());
            }
        }
        return builder.toString();
    }

}
