package hsiong.module.codetool.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommonUtil {

    /**
     * 判断字段为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字段不为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 将驼峰命名转化成下划线
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param.length() < 3) {
            return param.toLowerCase();
        }
        StringBuilder sb = new StringBuilder(param);
        int temp = 0;//定位
        //从第三个字符开始 避免命名不规范
        for (int i = 2; i < param.length(); i++) {
            if (Character.isUpperCase(param.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * parse Result Ret
     * @param resultSet statement.executeQuery Result
     * @param tClass parse Result Class
     * @param <T> ret type
     * @return result
     * @throws SQLException resultSet.getObject
     */
    public static <T> T parseResultRet(ResultSet resultSet, Class<T> tClass) throws SQLException {
        T o = null;
        try {
            o = tClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            String msg = String.format("New Instance %s Error: %s", tClass.getName(), e.getMessage());
            throw new IllegalArgumentException(msg);
        }
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String fieldSqlName = CommonUtil.camelToUnderline(fieldName);
            Object value = resultSet.getObject(fieldSqlName);
            try {
                field.set(o, String.valueOf(value));
            } catch (IllegalAccessException e) {
                String msg = String.format("IllegalAccessException %s Error: %s", field.getName(), e.getMessage());
                throw new IllegalArgumentException(msg);
            }
        }

        return o;
    }

}
