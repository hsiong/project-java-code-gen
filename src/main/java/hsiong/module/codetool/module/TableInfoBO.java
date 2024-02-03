/**
 * Copyright (C), 2006-2022,
 *
 * @FileName: TableStuctureBO
 * @Author: Hsiong
 * @Date: 2022/4/21 11:40 AM
 * @Description: History:
 * <author>              <time>              <version>              <desc>
 * 作者姓名               修改时间               版本号                  描述
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
public class TableInfoBO {

	/**
	 * 表名
	 */
	private String table_name;

	/**
	 * 表注释
	 */
	private String table_comment;


}
