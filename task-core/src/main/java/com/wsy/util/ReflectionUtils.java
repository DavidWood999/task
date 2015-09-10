package com.wsy.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * Description:���乤����
 */
public abstract class ReflectionUtils {
     
    private static final Logger    logger    = LoggerFactory
            .getLogger(ReflectionUtils.class);
    /**
     * ���淽��
     */
    private static final Map<Class<?>, Method[]>    METHODS_CACHEMAP    = new HashMap<Class<?>, Method[]>();
     
    /**
     * ���� ȡֵ����ֵ,�ϲ���������(Field same only )
     * 
     * @param from
     * @param to
     */
    public static <T> void copyProperties(T fromobj, T toobj,
            String... fieldspec) {
        for (String filename : fieldspec) {
            Object val = ReflectionUtils.invokeGetterMethod(fromobj, filename);
            ReflectionUtils.invokeSetterMethod(toobj, filename, val);
        }
         
    }
     
    /**
     * ����Getter����
     * 
     * @param obj
     *            ����
     * @param propertyName
     *            ������
     * @return
     */
    public static Object invokeGetterMethod(Object obj, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, null, null);
    }
     
    /**
     * ����Setter����,��ָ������������
     * 
     * @param obj
     * @param propertyName
     * @param value
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value) {
        invokeSetterMethod(obj, propertyName, value, null);
    }
     
    /**
     * ����Setter����,ָ������������
     * 
     * @param obj
     * @param propertyName  �ֶ���
     * @param value
     * @param propertyType
     *            Ϊ�գ���ȡvalue��Class
     */
    public static void invokeSetterMethod(Object obj, String propertyName,
            Object value, Class<?> propertyType) {
        value = handleValueType(obj,propertyName,value);
        propertyType = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        invokeMethod(obj, setterMethodName, new Class<?>[] { propertyType },
                new Object[] { value });
    }
     
    private static Object handleValueType(Object obj, String propertyName,
            Object value){
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        Class<?> argsType = value.getClass();;
        Class<?> returnType = obtainAccessibleMethod(obj, getterMethodName).getReturnType();
        if(argsType == returnType){
            return value;
        }
         
        if (returnType == Boolean.class) {
            String temp = value.toString();
            value = (StringUtils.isNotBlank(temp) && Long.valueOf(temp) > 0) ? true : false;
        } else if (returnType == Long.class) {
            value = Long.valueOf(value.toString());
        }else if(returnType == Date.class){
            try {
            	value = DataUtil.str2date(value.toString());
            	
            } catch (Exception e) {
                logger.error("����ת��Timpestap-->Dateʱ����������! " + e.getMessage() + "("+value.toString()+")");
            }
        } else if (returnType == Short.class) {
            value = Short.valueOf(value.toString());
        } else if (returnType == BigDecimal.class) {
            value = BigDecimal.valueOf(Long.valueOf(value.toString()));
        } else if (returnType == BigInteger.class) {
            value = BigInteger.valueOf(Long.valueOf(value.toString()));
        } else if(returnType == String.class){
            value = String.valueOf(value);
        }else if(returnType == Integer.class){
            value = Integer.valueOf(value.toString());
        }
        return value;
    }
     
    public static void main(String[] args) throws Exception {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
       // System.out.println(SimpleDateFormatFactory.getInstance(DateUtil.FULL_TIME_FORMAT).parse(ts.toString()));
        System.out.println(DataUtil.str2date(ts.toString()));
    }
     
    /**
     * ֱ�ӵ��ö��󷽷�������private/protected���η�
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @param params
     * @return
     */
    public static Object invokeMethod(final Object obj,
            final String methodName, final Class<?>[] parameterTypes,
            final Object[] args) {
        Method method = obtainAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) { throw new IllegalArgumentException(
                "Devkit: Could not find method [" + methodName
                        + "] on target [" + obj + "]."); }
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
     
    /**
     * ѭ������ת�ͣ���ȡ�����DeclaredMethod,��ǿ������Ϊ�ɷ��� ������ת�͵�Object���޷��ҵ�������null
     * 
     * ���ڷ�����Ҫ����ε��õ��������ʹ�ñ�������ȡ��Method,Ȼ�����Method.invoke(Object obj,Object...
     * args)
     * 
     * @param obj
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public static Method obtainAccessibleMethod(final Object obj,
            final String methodName, final Class<?>... parameterTypes) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            Method method = null;
            try {
                method = superClass.getDeclaredMethod(methodName,
                        parameterTypes);
                method.setAccessible(true);
                return method;
            } catch (NoSuchMethodException e) {
                // Method���ڵ�ǰ�ඨ�壬����ת��
            } catch (SecurityException e) {
                // Method���ڵ�ǰ�ඨ�壬����ת��
            }
        }
        return null;
    }
     
    /**
     * ����ȷ�������Ƿ��������ʱ��ͨ��������ƥ���÷���
     * 
     * @param obj
     * @param methodName
     * @return
     */
    public static Method obtainMethod(final Object obj, final String methodName) {
        Class<?> clazz = obj.getClass();
        Method[] methods = METHODS_CACHEMAP.get(clazz);
        if (methods == null) { // ��δ����
            methods = clazz.getDeclaredMethods();
            METHODS_CACHEMAP.put(clazz, methods);
        }
        for (Method method : methods) {
            if (method.getName().equals(methodName))
                return method;
        }
        return null;
         
    }
     
    /**
     * ֱ�Ӷ�ȡ��������ֵ ����private/protected���η���������getter����
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object obtainFieldValue(final Object obj,
            final String fieldName) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        Object retval = null;
        try {
            retval = field.get(obj);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return retval;
         
    }
     
    /**
     * ֱ�����ö�������ֵ ����private/protected���η���������setter����
     * 
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(final Object obj, final String fieldName,
            final Object value) {
        Field field = obtainAccessibleField(obj, fieldName);
        if (field == null) { throw new IllegalArgumentException(
                "Devkit: could not find field [" + fieldName + "] on target ["
                        + obj + "]"); }
        try {
            field.set(obj, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * ѭ������ת�ͣ���ȡ�����DeclaredField,��ǿ����Ϊ�ɷ��� ������ת��Object���޷��ҵ�������null
     * 
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field obtainAccessibleField(final Object obj,
            final String fieldName) {
        Class<?> superClass = obj.getClass();
        Class<Object> objClass = Object.class;
        for (; superClass != objClass; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // Field���ڵ�ǰ�ඨ�壬����ת��
            } catch (SecurityException e) {
                // Field���ڵ�ǰ�ඨ�壬����ת��
            }
        }
        return null;
    }
}