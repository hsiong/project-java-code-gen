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
public class TableStructureBO {

    /**
     * 字段在表的位置
     */
    private String ordinal_position;

    /**
     * 字段名
     */
    private String column_name;

    /**
     * 字段类型
     */
    private String data_type;

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
    private String description;
    

}
