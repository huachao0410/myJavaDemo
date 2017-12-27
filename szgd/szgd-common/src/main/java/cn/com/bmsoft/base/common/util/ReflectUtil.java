package cn.com.bmsoft.base.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 */
public class ReflectUtil {

    public static Field[] getFields(Class aClass) {
        Field[] fields = aClass.getDeclaredFields();
        while (aClass.getSuperclass() != null) {
            aClass = aClass.getSuperclass();
            fields = (Field[]) ArrayUtils.addAll(fields, aClass.getDeclaredFields());
        }
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);
            }
        }
        return fields;
    }

    public static Method[] getMethods(Class aClass) {
        Method[] methods = aClass.getDeclaredMethods();
        while (aClass.getSuperclass() != null) {
            aClass = aClass.getSuperclass();
            methods = (Method[]) ArrayUtils.addAll(methods, aClass.getDeclaredMethods());
        }
        if (methods != null) {
            for (Method method : methods) {
                method.setAccessible(true);
            }
        }
        return methods;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String firstLetter = fieldName.substring(0, 1).toUpperCase();
        String getter = "get" + firstLetter + fieldName.substring(1);
        Method method = o.getClass().getMethod(getter, new Class[] {});
        Object value = method.invoke(o, new Object[] {});
        return value;
    }

}
