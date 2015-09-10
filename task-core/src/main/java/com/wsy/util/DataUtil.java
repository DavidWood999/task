package com.wsy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * 工具类
 * @author Jesse Lu
 */
public class DataUtil {
	
	/**
	 * 产生32位唯一字符串
	 * @return
	 */
	public static String nextUUID(){
		return UUID.randomUUID().toString().trim().replaceAll("-", ""); 
	}
    
    /**
     * 验证是否是手机
     * @param str
     * @return
     * @author IXR
     */
    public static boolean isMobile(String str) {
        //return Pattern.matches("^[1][2-9]\\d{9}$", strNull(str));
    	return Pattern.matches("^[1-9]\\d{10}$", strNull(str)); //11位数字
    }
    
    /**
     * 验证是否是电子邮箱
     * 
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return Pattern.matches("^[A-Za-z0-9]+(\\.[_A-Za-z0-9\\-]+)*@([A-Za-z0-9\\-]+\\.){1,3}[A-Za-z]{2,5}$", strNull(str));
        
    }
    
    /**
     * 获取字符串字节长度，针对数据库保存时准确的判断字符长度
     * @param str 要判断的字符(字母、数字、英文符号算1个字符，其他算3个字符)
     * @return 字符的长度
     */
    public static int strBytesLength(String str) {
        return str.replaceAll("[^\\x00-\\xff]", "***").length();
    }
    
    /**
     * 将字符串转换成Unicode编码
     * @param str 原始字符串
     * @return 类似\u4e2d\u56fd
     */
    public static String strToUnicode(String str) {
        StringBuffer buffer = new StringBuffer();
        char[] chars = str.toCharArray();
        for (char ch : chars) {
            if (ch > 128) {
                buffer.append("\\u");
                buffer.append(Integer.toHexString(ch));
            } else {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }
    
    /**
     * 把XML生成为代BOM头信息字节的字节数组
     * @param xml
     * @return
     * @throws IOException 内存读写失败会出现此异常
     */
    public static byte[] xmlToUtf8BOMBytes(String xml) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(new byte[]{(byte) 239, (byte) 187, (byte) 191});
        // ... 添加BOM头信息，表示 EF BB BF（UTF-8）的头字节
        // 详细说明可见http://www.unicode.org/faq/utf_bom.html#BOM
        byteArrayOutputStream.write(xml.getBytes("UTF-8"));
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     * 判断字符串是否为正整数
     * @param code
     * @return 若是则返回true，否则返回false
     */
    public static boolean isUInteger(String code) {
        if (null == code) return false;
        return code.matches("^\\d+");
    }
    
    /**
     * 判断字符串是否为整数
     * @param code
     * @return 若是则返回true，否则返回false
     */
    public static boolean isInteger(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+");
    }
    
    /**
     * 判断字符串是否为正浮点数
     * @param code
     * @return 若是则返回true，否则返回false
     */
    public static boolean isUFloat(String code) {
        if (null == code) return false;
        return code.matches("^\\d+(\\.\\d+)?");
    }
    
    /**
     * 判断字符串是否为浮点数
     * @param code
     * @return 若是则返回true，否则返回false
     */
    public static boolean isFloat(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+(\\.\\d+)?");
    }
    
    /**
     * 将长日期字符串转换为Date
     * @param strValue
     * @return java.util.Date
     */
    public static java.util.Date str2date(String strValue) {
        if (strValue == null) return null;
        if (strValue.equals("")) return null;
        java.util.Date theDate;
        try {
            String str = strValue.substring(4, 5);
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd" + " " + "HH" + ":" + "mm" + ":" + "ss");
            theDate = theFormat.parse(strValue);
        } catch (Exception ex) {
            theDate = null;
        }
        return theDate;
    }
    
    /**
     * Double过滤方法
     * @param dou
     * @return 若dou为null，则返回0.0D
     */
    public static Double douNull(Double dou) {
        if (dou == null) {
            return 0.0D;
        } else {
            return dou;
        }
    }
    
    /**
     * 字符串过滤方法
     * @param str
     * @return 若str为null，则返回""
     */
    public static String strNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
    
    /**
     * 是否为空或空字符串
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 字符串过滤方法
     * @param str
     * @return 若str为null，则返回""
     */
    public static String strNull(Object o) {
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }
    
    /**
     * 将相关的长日期格式字符串转换日期对象 若字符串格式错误，则转换为参数中提供的默认日期
     * @param strValue 长日期格式字符串 "yyyy-MM-dd HH:mm:ss"
     * @param theDefault 默认日期
     * @return
     */
    public static java.util.Date str2date(String strValue, java.util.Date theDefault) {
        if (strValue == null) return theDefault;
        SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date theDate;
        try {
            theDate = theFormat.parse(strValue);
        } catch (ParseException ex) {
            theDate = theDefault;
        }
        return theDate;
    }
    
    /**
     * 将日期转换为长日期字符串格式
     * @param aDate
     * @return
     */
    public static String date2str(java.util.Date aDate) {
        if (aDate == null) {
            return "";
        } else {
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return theFormat.format(aDate);
        }
    }
    
    /**
     * 将日期转换为短日期字符串格式
     * @param date
     * @return
     */
    public static String date2shortstr(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy-MM-dd");
            return theFormat.format(date);
        }
    }
    
    /**
     * 将短日期格式字符串转换为Date
     * @param strValue
     * @return
     */
    public static java.util.Date shortstr2date(String strValue) {
        if (strValue == null) return null;
        if (strValue.equals("")) return null;
        java.util.Date theDate;
        try {
            String str = strValue.substring(4, 5);
            SimpleDateFormat theFormat = new SimpleDateFormat("yyyy" + str + "MM" + str + "dd");
            theDate = theFormat.parse(strValue);
        } catch (Exception ex) {
            theDate = null;
        }
        return theDate;
    }
    
    /**
     * 日期快捷格式化方法
     * @param date
     * @param parse 格式化格式
     * @return
     */
    public static String parseDate(java.util.Date date, String parse) {
        if (date == null || parse == null) return "";
        SimpleDateFormat theFormat = new SimpleDateFormat(parse);
        return theFormat.format(date);
    }
    
    /**
     * 日期快捷格式化方法
     * @param date
     * @param parse 格式化格式
     * @return
     */
    public static Date parseDate(String date, String parse) {
        if (date == null || parse == null) return null;
        SimpleDateFormat theFormat = new SimpleDateFormat(parse);
        try {
            return theFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将指定字符串转换为double基础类型 若转换出错或参数为null，则返回0.0D
     * @param strValue
     * @return
     */
    public static double str2double(String strValue) {
        if (strValue == null) return 0.0D;
        double nValue = 0.0D;
        try {
            String temp = strValue.trim();
            nValue = Double.parseDouble(temp);
        } catch (NumberFormatException numberformatexception) {
            return 0.0D;
        }
        return nValue;
    }
    
    /**
     * 将指定字符串转换为int基础类型 若转换出错或参数为，则返回0
     * @param strValue
     * @return
     */
    public static int str2int(String strValue) {
        if (isInteger(strValue)) return Integer.valueOf(strValue);
        return 0;
    }
    
    /**
     * 长整数转字符串
     * @param longValue
     * @return
     */
    public static String long2str(long longValue) {
        return String.valueOf(longValue);
    }
    
    /**
     * 将当前日期的时间清零并返回（清零时间包含小时，分钟，秒，毫秒）
     * @return
     */
    public static Date clearDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 返回当前日期,带时分秒
     * @param type short-短日期,long-长日期 
     * @return
     */
    public static String nowDate(String type){
        Date now = new Date();
        if("short".equals(type)){
            return date2shortstr(now);
        }else{
            return date2str(now);
        }
    }
    
    /**
     * 将指定日期的时间清零并返回（清零时间包含小时，分钟，秒，毫秒）
     * @param date 指定日期
     * @return
     */
    public static Date clearDateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal1 第一个日期
     * @param cal2 第二个日期
     * @return 相差天数，若返回0，则表示参数错误或是同一天
     */
    public static int DayDifference(Calendar cal1, Calendar cal2) {
        if (null == cal1 || null == cal2) return 0;
        Calendar c = Calendar.getInstance();
        c.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
        long millis1 = c.getTimeInMillis();
        c.set(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH), cal2.get(Calendar.DAY_OF_MONTH));
        long millis2 = c.getTimeInMillis();
        return Long.valueOf(Math.abs(millis1 - millis2) / 86400000).intValue();
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @return 相差天数，若返回0，则表示参数错误或是同一天
     */
    public static int DayDifference(Date date1, Date date2) {
        if (null == date1 || null == date2) return 0;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return DayDifference(c1, c2);
    }
    
    /**
     * @param date
     * @return
     */
    public static long DayDifference(Date date) {
        Calendar c = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        c.set(cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DAY_OF_MONTH));
        return c.getTimeInMillis();
    }
    
    /**
     * 计算日期天数差，该计算忽略时间差
     * @param cal 第一个日期
     * @param date 第二个日期
     * @return 相差天数，若返回0，则表示参数错误或是同一天
     */
    public static int DayDifference(Calendar cal, Date date) {
        if (null == cal || null == date) return 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return DayDifference(cal, c);
    }
    
    /**
     * 计算给出的日期相关sum天的日期
     * @param date 指定日期
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的日期
     */
    public static Date DayDifference(Date date, int sum) {
        return new Date(date.getTime() + (sum * 86400000l));
    }
    
    /**
     * 计算给出的日期相关sum天的日期
     * @param date 指定日期
     * @param sum 相差天数，负数就是前几天
     * @return 相差sum天的短日期格式
     */
    public static String DayDifference(String date, int sum) {
        return date2shortstr(DayDifference(shortstr2date(date), sum));
    }
    
    /**
     * 实现instanceof验证的方法
     * @param obj 要验证的对象
     * @param className 类型全路径名称
     * @return
     */
    public static boolean instanceOf(Object obj, String className) {
        return obj.getClass().getName().equals(className);
    }
    
    /**
     * 金额大写转换方法
     * @param currency 罗马数字金额
     * @return 中文大写金额
     */
    public static String convertRMB(String currency) {
        if ("".equals(currency)) return "";
        double MAXIMUM_NUMBER = 999999999999.99;
        // 中文转换输出变量
        String CN_ZERO = "零";
        String CN_ONE = "壹";
        String CN_TWO = "贰";
        String CN_THREE = "叁";
        String CN_FOUR = "肆";
        String CN_FIVE = "伍";
        String CN_SIX = "陆";
        String CN_SEVEN = "柒";
        String CN_EIGHT = "捌";
        String CN_NINE = "玖";
        String CN_TEN = "拾";
        String CN_HUNDRED = "佰";
        String CN_THOUSAND = "仟";
        String CN_TEN_THOUSAND = "万";
        String CN_HUNDRED_MILLION = "亿";
        // String CN_SYMBOL = "人民币";
        String CN_DOLLAR = "圆";
        String CN_TEN_CENT = "角";
        String CN_CENT = "分";
        String CN_INTEGER = "整";
        String integral; // 整数部分变量
        String decimal; // 小数部分变量
        String outputCharacters; // 输出结果
        String[] parts;
        String[] digits, radices, bigRadices, decimals;
        int zeroCount;
        int i, p;
        String d;
        int quotient, modulus;
        // 金额格式验证
        if (currency.matches("[^,.\\d]")) {
            System.out.println("金额中含有非法字符！");
            return "";
        }
        if (!currency.matches("^((\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)|(\\d+(.\\d+)?))$")) {
            System.out.println("金额格式错误！");
            return "";
        }
        // 金额格式化
        currency = currency.replaceAll(",", ""); // 移除逗号
        currency = currency.replaceAll("^0+", ""); // 移除前端的"0"字符
        // Assert the number is not greater than the maximum number.
        if (Double.valueOf(currency) > MAXIMUM_NUMBER) {
            System.out.println("金额超出可转换的数额大小！");
            return "";
        }
        // 整数小数分离
        parts = currency.split("\\.");
        if (parts.length > 1) {
            integral = parts[0];
            decimal = parts[1];
            // 小数截断2位
            decimal = decimal.substring(0, decimal.length() > 2 ? 2 : decimal.length());
        } else {
            integral = parts[0];
            decimal = "";
        }
        // 中文转换输出数组定义
        digits = new String[]{CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE};
        radices = new String[]{"", CN_TEN, CN_HUNDRED, CN_THOUSAND};
        bigRadices = new String[]{"", CN_TEN_THOUSAND, CN_HUNDRED_MILLION};
        decimals = new String[]{CN_TEN_CENT, CN_CENT};
        // 开始组合输出字符串
        outputCharacters = "";
        // 组合整数转换
        if (Long.valueOf(integral) > 0) {
            zeroCount = 0;
            for (i = 0; i < integral.length(); i++) {
                p = integral.length() - i - 1;
                d = integral.substring(i, i + 1);
                quotient = p / 4;
                modulus = p % 4;
                if ("0".equals(d)) {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        outputCharacters += digits[0];
                    }
                    zeroCount = 0;
                    outputCharacters += digits[Integer.valueOf(d)] + radices[modulus];
                }
                if (modulus == 0 && zeroCount < 4) {
                    outputCharacters += bigRadices[quotient];
                }
            }
            outputCharacters += CN_DOLLAR;
        }
        // 组合小数转换
        if (!decimal.matches("^[0]*$")) {
            for (i = 0; i < decimal.length(); i++) {
                d = decimal.substring(i, i + 1);
                if (d != "0") {
                    outputCharacters += digits[Integer.valueOf(d)] + decimals[i];
                }
            }
        }
        // Confirm and return the final output string:
        if ("".equals(outputCharacters)) {
            outputCharacters = CN_ZERO + CN_DOLLAR;
        }
        if (decimal.matches("^[0]*$")) {
            outputCharacters += CN_INTEGER;
        }
        return outputCharacters;
    }
    
    /**
     * 获取资源的静态方法
     * <p>
     * 
     * <pre>
     * e.g.
     * 1. Properties prop = Util.getProperties("classpath:/com/company/resource/prop.properties");
     * 2. Properties prop = Util.getProperties("classpath:com/company/resource/prop.properties");
     * 3. Properties prop = Util.getProperties("CLASSPATH:com/company/resource/prop.properties");
     * 4. Properties prop = Util.getProperties("C:/project/prop.properties");
     * </pre>
     * 
     * </p>
     * @param path 资源的完整路径或者项目中的classpath路径
     * @return 返回资源对象
     */
    public static Properties getProperties(String path) {
        Properties prop = new Properties();
        InputStream is;
        try {
            if (path.toLowerCase().indexOf("classpath:") == 0) {
                // 缓存加载方法
                // is =
                // Util.class.getResourceAsStream(path.substring(10).startsWith("/")
                // ? path.substring(10) : "/" + path.substring(10));
                // 实时加载方法
                path = DataUtil.class.getResource(path.substring(10).startsWith("/") ? path.substring(10) : "/" + path.substring(10)).getPath();
                is = new FileInputStream(URLDecoder.decode(path, System.getProperty("file.encoding")));
            } else {
                is = new FileInputStream(path);
            }
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
    
    /**
     * 获得对象的指定属性
     * @param obj
     * @param property 属性名称
     * @return
     */
    @SuppressWarnings("all")
    public static Object getProperty(Object obj, String property) {
        if ("".equals(strNull(property))) {
            return null;
        }
        Class clazz = obj.getClass();
        Method method = null;
        try {
            method = clazz.getMethod("is" + property.substring(0, 1).toUpperCase() + property.substring(1), null);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod("get" + property.substring(0, 1).toUpperCase() + property.substring(1), null);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        }
        if (method != null) {
            try {
                return method.invoke(obj, null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 时间转换
     * @param second 秒数
     * @return 字符串时间格式 HHH:mm:ss
     */
    public static String getTimeBySecond(long second) {
        StringBuilder time = new StringBuilder("");
        int h = (int) second / 60 / 60;
        int m = (int) second / 60 % 60;
        int s = (int) second % 60;
        DecimalFormat d = new DecimalFormat("00");
        if (h > 10) {
            time.append(h + ":");
        } else {
            time.append("0" + h + ":");
        }
        time.append(d.format(m) + ":");
        time.append(d.format(s));
        return time.toString();
    }
    
    /**
     * 根据给出星期规则从给出日期范围中获得所有符合规则的日期集合
     * @param weektime 日期规则 一个包含1-7的字符串，1-7分别对应周一至周日，如：1347或1,3,4,7
     * @param begtime 开始日期 yyyy-MM-dd
     * @param endtime 结束日期 yyyy-MM-dd
     * @return 符合规则的日期集合
     */
    public static List<Date> getDateList(String weektime, String begtime, String endtime) {
        List<Date> datelist = new ArrayList<Date>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar begdate = Calendar.getInstance();
            Calendar enddate = Calendar.getInstance();
            begdate.setTime(sdf.parse(begtime));
            enddate.setTime(sdf.parse(endtime));
            int weekday;
            while (begdate.before(enddate) || begdate.equals(enddate)) {
                weekday = begdate.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : begdate.get(Calendar.DAY_OF_WEEK) - 1;
                if (weektime.indexOf(weekday + "") >= 0) {
                    datelist.add(begdate.getTime());
                }
                begdate.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datelist;
    }
    
    /**
     * 生成主键方法
     * @return
     */
    public static String getPrimaryKey() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * 生成随机字符串（仅含数字及大小写字母）
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
    }
    
    /**
     * 生成随机字符串方法
     * 
     * <pre>
     * 示例
     * randomString("abc123ABC", 3) = "2Ba" 
     * randomString("#$%UZefop", 5) = "%Zfe#"
     * </pre>
     * @param range 随机字符串候选值
     * @param length 随机字符串长度
     * @return 随机字符串
     */
    public static String randomString(String range, int length) {
        Random rd = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(range.charAt(rd.nextInt(range.length())));
        }
        return result.toString();
    }
    
    /**
     * 根据经纬度和公里数获取经纬度的差值
     * @param a 经度
     * @param b 纬度
     * @param d 距离
     * @return double [经度，维度]
     */
    public static double[] dist(double a, double b, double d) {
        double R = 6378.137;
        double rb = Math.toRadians(b);
        double r = R * Math.cos(rb);
        double x = 0.0;
        double y = 0.0;
        x = d * 180 / (Math.PI * r);
        y = d * 180 / (Math.PI * R);
        return new double[]{x, y};
    }
    
    /**
     * @param strxy 经纬度字符串 ‘经度+’，‘+’维度‘；
     * @param dlong 范围公里数
     * @return double [max大经度,mix小经度,may大维度,miy小维度]
     */
    public static double[] dists(String strxy, String dlong) {
        String[] str = strxy.split(",");
        String strx = str[0];
        String stry = str[1];
        double x = Double.valueOf(strx);// 经度
        double y = Double.valueOf(stry);// 维度
        double d = Double.valueOf(dlong);// 范围
        double[] dxy = dist(x, y, d);
        
        double lat_max, lat_min, lon_max, lon_min;
        lon_max = x + Math.abs(dxy[0]);
        lon_min = x - Math.abs(dxy[0]);
        lat_max = y + Math.abs(dxy[1]);
        lat_min = (y - Math.abs(dxy[1]));
        
        double[] xy = new double[]{lon_max, lon_min, lat_max, lat_min};
        return xy;
    }
    
    public static double[] dists2(String strxy, String dlong) {
        String[] str = strxy.split(",");
        String strx = str[0];
        String stry = str[1];
        double x = Double.valueOf(strx);// 经度
        double y = Double.valueOf(stry);// 维度
        double d = Double.valueOf(dlong);// 范围
        double[] dxy = dist(x, y, d);
        // 获取最大、最小的经度
        strx = String.valueOf(x);
        String fx = "0" + strx.substring(strx.indexOf("."), strx.length());
        String fx2 = strx.substring(0, strx.indexOf("."));
        double dx2 = Double.valueOf(fx2);
        double dx = Double.valueOf(fx);
        double max = dx + dxy[0];
        double mix = dx - dxy[0];
        if (dxy[0] < 0) {
            max = dx - dxy[0];
            mix = dx + dxy[0];
        }
        if (x < 0) {
            max = dx - dxy[0];
            mix = dx + dxy[0];
            if (dxy[0] < 0) {
                max = dx + dxy[0];
                mix = dx - dxy[0];
            }
        }
        max = dx2 + max;
        mix = dx2 - mix;
        // 获取最大，最小的维度
        stry = String.valueOf(y);
        String fy = stry.substring(stry.indexOf("."), stry.length());
        String fy2 = stry.substring(0, stry.indexOf("."));
        double dy = Double.valueOf(fy);
        double dy2 = Double.valueOf(fy2);
        double may = dy + dxy[1];
        double miy = dy - dxy[1];
        if (dxy[1] < 0) {
            may = dy - dxy[1];
            miy = dy + dxy[1];
        }
        if (y < 0) {
            may = dy - dxy[1];
            miy = dy + dxy[1];
            if (dxy[1] < 0) {
                may = dy - dxy[1];
                miy = dy + dxy[1];
            }
        }
        may = dy2 + may;
        miy = dy2 - miy;
        double[] xy = new double[]{max, mix, may, miy};
        return xy;
    }
    
    /**
     * 获取选转角度
     * @param range
     * @param length
     * @return
     */
    public static Integer routate(List<Integer> range) {
        Random rd = new Random();
        return range.get(rd.nextInt(range.size()));
    }
    
    /**
     * 获取角度
     * @return
     */
    public static int routate() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = -45; i <= 45; i++) {
            list.add(i);
        }
        return routate(list);
    }
    
    @SuppressWarnings("all")
    public static int getmiisend() {
        Calendar Cld = Calendar.getInstance();
        int YY = Cld.get(Calendar.YEAR);
        int MM = Cld.get(Calendar.MONTH) + 1;
        int DD = Cld.get(Calendar.DATE);
        int HH = Cld.get(Calendar.HOUR_OF_DAY);
        int mm = Cld.get(Calendar.MINUTE);
        int SS = Cld.get(Calendar.SECOND);
        int MI = Cld.get(Calendar.MILLISECOND);
        return MI;
    }
    
    /**
     * 打印SQL执行时间
     * @param start 开始时间
     * @return
     */
    public static Long printSQLRunTime(Long start) {
        Long now = new Date().getTime();
        System.out.println(now - start);
        return now;
    }
    
    /**
     * 根据两点的经纬度计算两点间的距离
     * @param latitude1 纬度1
     * @param longitude1 经度1
     * @param latitude2 纬度2
     * @param longitude2 经度2
     * @return
     */
    public static double getDistanceForTwoPoints(double latitude1, double longitude1, double latitude2, double longitude2) {
        double earthRadius = 6378.137;// 地球半径
        double radLat1 = latitude1 * Math.PI / 180.0;
        double radLat2 = latitude2 * Math.PI / 180.0;
        double a = radLat1 - radLat2;
        double b = (longitude1 - longitude2) * Math.PI / 180.0;
        
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        distance = distance * earthRadius;
        distance = Math.round(distance * 10000) / 10000;
        return distance;
    }
    
    /**
     * 获取指定日期n年后的日期
     * @param date 当前日期
     * @param years n年后
     * @return
     */
    public static Date getYearsAfterDate(Date date, int years) {
        String dateStr = parseDate(date, "yyyy-MM-dd");
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, 7);
        String day = dateStr.substring(8);
        String nyear = String.valueOf(Integer.parseInt(year) + years); // n年后的年份
        if ("01".equals(day)) {
            if ("|02|05|07|08|10|".contains(month)) {
                if ("08".equals(month)) {
                    day = "31";
                    month = "0" + (Integer.parseInt(month.substring(1)) - 1);
                } else {
                    day = "30";
                    if ("10".equals(month)) {
                        month = "09";
                    } else {
                        month = "0" + (Integer.parseInt(month.substring(1)) - 1);
                    }
                }
            } else if ("12".equals(month)) {
                day = "30";
                month = "11";
            } else if ("|04|06|09|".contains(month)) {
                day = "31";
                month = "0" + (Integer.parseInt(month.substring(1)) - 1);
            } else if ("11".equals(month)) {
                day = "31";
                month = "10";
            } else if ("01".equals(month)) {
                day = "31";
                month = "12";
                nyear = String.valueOf(Integer.parseInt(nyear) - 1);
            } else if ("03".equals(month)) {
                int yearInt = Integer.parseInt(nyear);
                if ((0 == yearInt % 4 && 0 == yearInt % 100) || 0 == yearInt % 400) { // 闰年
                    day = "29";
                    month = "02";
                } else {
                    day = "28";
                    month = "02";
                }
            }
        } else {
            day = day.contains("0") ? "0" + (Integer.parseInt(day.substring(1)) - 1) : String.valueOf(Integer.parseInt(day) - 1);
        }
        return shortstr2date(nyear + "-" + month + "-" + day);
    }
    
    /**
     * 判断护照是否有效，判断依据（普通护照的有效期为：护照持有人未满十六周岁的五年，十六周岁以上的十年。）
     * @param dbirthday 出生日期
     * @param dpassbgndate 护照发证日期
     * @param denddate 团队返回日期
     * @return
     */
    public static boolean isPassportValid(Date dbirthday, Date dpassbgndate, Date denddate) {
        Date adultDate = getYearsAfterDate(dbirthday, 16); // 16周岁的日期
        Date dpassenddate = adultDate.before(dpassbgndate) ? getYearsAfterDate(dpassbgndate, 10) : getYearsAfterDate(dpassbgndate, 5); // 护照有效期至
        return dpassenddate.before(denddate) ? false : true; // 【false：返回前护照过期 true：返回后护照过期】
    }
    
    /*public static void main(String[] args) {
        double jl_jd = 102.83474258026089786013677476285;// 每经度单位米;
        double jl_wd = 111.71269150641055729984301412873;// 每纬度单位米;
        double n1 = 120.586109, n2 = 121.473704, e1 = 29.995762, e2 = 31.230393;
        double b = Math.abs((e1 - e2) * jl_jd);
        double a = Math.abs((n1 - n2) * jl_wd);
//        System.out.println(Math.sqrt((a * a + b * b)));
        //101.45379020, 12.56709430         沙美岛
        //100.49388890, 13.75222220         曼谷
        //100.01359290, 9.51201680          苏梅岛
        System.out.println(getDistanceForTwoPoints( 9.51201680,100.01359290,  13.75222220,100.49388890));
        System.out.println(GetDistance( 9.51201680,100.01359290,  13.75222220,100.49388890));
//        System.out.println(isPassportValid(new Date(1992 - 1900, 4, 16), new Date(2008 - 1900, 4, 15), new Date(2013 - 1900, 4, 14)));
    }*/
    
    private static double EARTH_RADIUS = 6378.137;//地球半径
    private static double rad(double d)
    {
       return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
       double radLat1 = rad(lat1);
       double radLat2 = rad(lat2);
       double a = radLat1 - radLat2;
       double b = rad(lng1) - rad(lng2);

       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * EARTH_RADIUS;
       s = Math.round(s * 10000) / 10000;
       return s;
    }
    
    /**
     * 将Clob转换成字符串
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws SQLException, IOException {
        String reString = "";
        Reader is = clob.getCharacterStream();// 得到流
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
            sb.append(s);
            s = br.readLine();
        }
        br.close();
        is.close();
        reString = sb.toString();
        return reString;
    }
    
    /**
     * 根据最大长度填充内容
     * @param content 内容
     * @param length 最大长度
     * @param str 补充的字符,默认为空白字符
     * @return
     */
    public static String addRestStr(String content, int length, String str) {
        if (content == null) return "";
        if (str == null) str = " ";
        // 已为最大长度，不需要填充
        if (strNull(content).length() >= length) {
            return content;
        } else {
            StringBuilder result = new StringBuilder(content);
            int restLength = length - content.length();
            for (int i = 0; i < restLength; i++) {
                result.append(str);
            }
            return result.toString();
        }
    }
    
    /**
     * 根据字母表顺序排序
     * @param params
     * @return
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    
    /**
     * 当天最后一秒的日期， 例如：2015-06-04   输出 2015-06-04 23:59:59
     * 如果日期为空，默认显示当前系统日期
     * @param date
     * @return
     * @author SongyangWu
     */
    public static Date getDateOnLastSecond(Date date){
    	if(date == null){
    		date = clearDateTime(); // 将当前日期的时间清零后返回
    	}else{
    		date = clearDateTime(date);
    	}
    	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, 1*60*60*24-1); //给日期添加 23:59:59
		date=calendar.getTime();
		
		return date;
    }
    
    /**
     * 给日期添加天数
     * 
     * @param date
     * @param day
     * @return
     * @author SongyangWu
     */
    public static Date addDay2Date(Date date, int day){
    	if(date == null){
    		date = clearDateTime(); // 将当前日期的时间清零后返回
    	}else{
    		date = clearDateTime(date);
    	}
    	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day); //给日期
		Date newdate=calendar.getTime();
		
		return newdate;
    }
    
    
    public static void main(String[] args) {
//        String twpasscodeRegex = "^T\\d{8}|25\\d{7}$";
//        System.out.println("T050000000".matches(twpasscodeRegex));
//        Date d = new Date();
//        
//        System.out.println(d.toLocaleString());
//        System.out.println(addDay2Date(d, 6).toLocaleString());
    	
    	System.out.println(DataUtil.nextUUID().length());
    }
}
