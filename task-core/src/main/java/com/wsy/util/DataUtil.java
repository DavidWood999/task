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
 * ������
 * @author Jesse Lu
 */
public class DataUtil {
	
	/**
	 * ����32λΨһ�ַ���
	 * @return
	 */
	public static String nextUUID(){
		return UUID.randomUUID().toString().trim().replaceAll("-", ""); 
	}
    
    /**
     * ��֤�Ƿ����ֻ�
     * @param str
     * @return
     * @author IXR
     */
    public static boolean isMobile(String str) {
        //return Pattern.matches("^[1][2-9]\\d{9}$", strNull(str));
    	return Pattern.matches("^[1-9]\\d{10}$", strNull(str)); //11λ����
    }
    
    /**
     * ��֤�Ƿ��ǵ�������
     * 
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        return Pattern.matches("^[A-Za-z0-9]+(\\.[_A-Za-z0-9\\-]+)*@([A-Za-z0-9\\-]+\\.){1,3}[A-Za-z]{2,5}$", strNull(str));
        
    }
    
    /**
     * ��ȡ�ַ����ֽڳ��ȣ�������ݿⱣ��ʱ׼ȷ���ж��ַ�����
     * @param str Ҫ�жϵ��ַ�(��ĸ�����֡�Ӣ�ķ�����1���ַ���������3���ַ�)
     * @return �ַ��ĳ���
     */
    public static int strBytesLength(String str) {
        return str.replaceAll("[^\\x00-\\xff]", "***").length();
    }
    
    /**
     * ���ַ���ת����Unicode����
     * @param str ԭʼ�ַ���
     * @return ����\u4e2d\u56fd
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
     * ��XML����Ϊ��BOMͷ��Ϣ�ֽڵ��ֽ�����
     * @param xml
     * @return
     * @throws IOException �ڴ��дʧ�ܻ���ִ��쳣
     */
    public static byte[] xmlToUtf8BOMBytes(String xml) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(new byte[]{(byte) 239, (byte) 187, (byte) 191});
        // ... ���BOMͷ��Ϣ����ʾ EF BB BF��UTF-8����ͷ�ֽ�
        // ��ϸ˵���ɼ�http://www.unicode.org/faq/utf_bom.html#BOM
        byteArrayOutputStream.write(xml.getBytes("UTF-8"));
        byteArrayOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊ������
     * @param code
     * @return �����򷵻�true�����򷵻�false
     */
    public static boolean isUInteger(String code) {
        if (null == code) return false;
        return code.matches("^\\d+");
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊ����
     * @param code
     * @return �����򷵻�true�����򷵻�false
     */
    public static boolean isInteger(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+");
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊ��������
     * @param code
     * @return �����򷵻�true�����򷵻�false
     */
    public static boolean isUFloat(String code) {
        if (null == code) return false;
        return code.matches("^\\d+(\\.\\d+)?");
    }
    
    /**
     * �ж��ַ����Ƿ�Ϊ������
     * @param code
     * @return �����򷵻�true�����򷵻�false
     */
    public static boolean isFloat(String code) {
        if (null == code) return false;
        return code.matches("^[-]?\\d+(\\.\\d+)?");
    }
    
    /**
     * ���������ַ���ת��ΪDate
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
     * Double���˷���
     * @param dou
     * @return ��douΪnull���򷵻�0.0D
     */
    public static Double douNull(Double dou) {
        if (dou == null) {
            return 0.0D;
        } else {
            return dou;
        }
    }
    
    /**
     * �ַ������˷���
     * @param str
     * @return ��strΪnull���򷵻�""
     */
    public static String strNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }
    
    /**
     * �Ƿ�Ϊ�ջ���ַ���
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
     * �ַ������˷���
     * @param str
     * @return ��strΪnull���򷵻�""
     */
    public static String strNull(Object o) {
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }
    
    /**
     * ����صĳ����ڸ�ʽ�ַ���ת�����ڶ��� ���ַ�����ʽ������ת��Ϊ�������ṩ��Ĭ������
     * @param strValue �����ڸ�ʽ�ַ��� "yyyy-MM-dd HH:mm:ss"
     * @param theDefault Ĭ������
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
     * ������ת��Ϊ�������ַ�����ʽ
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
     * ������ת��Ϊ�������ַ�����ʽ
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
     * �������ڸ�ʽ�ַ���ת��ΪDate
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
     * ���ڿ�ݸ�ʽ������
     * @param date
     * @param parse ��ʽ����ʽ
     * @return
     */
    public static String parseDate(java.util.Date date, String parse) {
        if (date == null || parse == null) return "";
        SimpleDateFormat theFormat = new SimpleDateFormat(parse);
        return theFormat.format(date);
    }
    
    /**
     * ���ڿ�ݸ�ʽ������
     * @param date
     * @param parse ��ʽ����ʽ
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
     * ��ָ���ַ���ת��Ϊdouble�������� ��ת����������Ϊnull���򷵻�0.0D
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
     * ��ָ���ַ���ת��Ϊint�������� ��ת����������Ϊ���򷵻�0
     * @param strValue
     * @return
     */
    public static int str2int(String strValue) {
        if (isInteger(strValue)) return Integer.valueOf(strValue);
        return 0;
    }
    
    /**
     * ������ת�ַ���
     * @param longValue
     * @return
     */
    public static String long2str(long longValue) {
        return String.valueOf(longValue);
    }
    
    /**
     * ����ǰ���ڵ�ʱ�����㲢���أ�����ʱ�����Сʱ�����ӣ��룬���룩
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
     * ���ص�ǰ����,��ʱ����
     * @param type short-������,long-������ 
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
     * ��ָ�����ڵ�ʱ�����㲢���أ�����ʱ�����Сʱ�����ӣ��룬���룩
     * @param date ָ������
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
     * ��������������ü������ʱ���
     * @param cal1 ��һ������
     * @param cal2 �ڶ�������
     * @return ���������������0�����ʾ�����������ͬһ��
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
     * ��������������ü������ʱ���
     * @param date1 ��һ������
     * @param date2 �ڶ�������
     * @return ���������������0�����ʾ�����������ͬһ��
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
     * ��������������ü������ʱ���
     * @param cal ��һ������
     * @param date �ڶ�������
     * @return ���������������0�����ʾ�����������ͬһ��
     */
    public static int DayDifference(Calendar cal, Date date) {
        if (null == cal || null == date) return 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return DayDifference(cal, c);
    }
    
    /**
     * ����������������sum�������
     * @param date ָ������
     * @param sum �����������������ǰ����
     * @return ���sum�������
     */
    public static Date DayDifference(Date date, int sum) {
        return new Date(date.getTime() + (sum * 86400000l));
    }
    
    /**
     * ����������������sum�������
     * @param date ָ������
     * @param sum �����������������ǰ����
     * @return ���sum��Ķ����ڸ�ʽ
     */
    public static String DayDifference(String date, int sum) {
        return date2shortstr(DayDifference(shortstr2date(date), sum));
    }
    
    /**
     * ʵ��instanceof��֤�ķ���
     * @param obj Ҫ��֤�Ķ���
     * @param className ����ȫ·������
     * @return
     */
    public static boolean instanceOf(Object obj, String className) {
        return obj.getClass().getName().equals(className);
    }
    
    /**
     * ����дת������
     * @param currency �������ֽ��
     * @return ���Ĵ�д���
     */
    public static String convertRMB(String currency) {
        if ("".equals(currency)) return "";
        double MAXIMUM_NUMBER = 999999999999.99;
        // ����ת���������
        String CN_ZERO = "��";
        String CN_ONE = "Ҽ";
        String CN_TWO = "��";
        String CN_THREE = "��";
        String CN_FOUR = "��";
        String CN_FIVE = "��";
        String CN_SIX = "½";
        String CN_SEVEN = "��";
        String CN_EIGHT = "��";
        String CN_NINE = "��";
        String CN_TEN = "ʰ";
        String CN_HUNDRED = "��";
        String CN_THOUSAND = "Ǫ";
        String CN_TEN_THOUSAND = "��";
        String CN_HUNDRED_MILLION = "��";
        // String CN_SYMBOL = "�����";
        String CN_DOLLAR = "Բ";
        String CN_TEN_CENT = "��";
        String CN_CENT = "��";
        String CN_INTEGER = "��";
        String integral; // �������ֱ���
        String decimal; // С�����ֱ���
        String outputCharacters; // ������
        String[] parts;
        String[] digits, radices, bigRadices, decimals;
        int zeroCount;
        int i, p;
        String d;
        int quotient, modulus;
        // ����ʽ��֤
        if (currency.matches("[^,.\\d]")) {
            System.out.println("����к��зǷ��ַ���");
            return "";
        }
        if (!currency.matches("^((\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)|(\\d+(.\\d+)?))$")) {
            System.out.println("����ʽ����");
            return "";
        }
        // ����ʽ��
        currency = currency.replaceAll(",", ""); // �Ƴ�����
        currency = currency.replaceAll("^0+", ""); // �Ƴ�ǰ�˵�"0"�ַ�
        // Assert the number is not greater than the maximum number.
        if (Double.valueOf(currency) > MAXIMUM_NUMBER) {
            System.out.println("������ת���������С��");
            return "";
        }
        // ����С������
        parts = currency.split("\\.");
        if (parts.length > 1) {
            integral = parts[0];
            decimal = parts[1];
            // С���ض�2λ
            decimal = decimal.substring(0, decimal.length() > 2 ? 2 : decimal.length());
        } else {
            integral = parts[0];
            decimal = "";
        }
        // ����ת��������鶨��
        digits = new String[]{CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE};
        radices = new String[]{"", CN_TEN, CN_HUNDRED, CN_THOUSAND};
        bigRadices = new String[]{"", CN_TEN_THOUSAND, CN_HUNDRED_MILLION};
        decimals = new String[]{CN_TEN_CENT, CN_CENT};
        // ��ʼ�������ַ���
        outputCharacters = "";
        // �������ת��
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
        // ���С��ת��
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
     * ��ȡ��Դ�ľ�̬����
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
     * @param path ��Դ������·��������Ŀ�е�classpath·��
     * @return ������Դ����
     */
    public static Properties getProperties(String path) {
        Properties prop = new Properties();
        InputStream is;
        try {
            if (path.toLowerCase().indexOf("classpath:") == 0) {
                // ������ط���
                // is =
                // Util.class.getResourceAsStream(path.substring(10).startsWith("/")
                // ? path.substring(10) : "/" + path.substring(10));
                // ʵʱ���ط���
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
     * ��ö����ָ������
     * @param obj
     * @param property ��������
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
     * ʱ��ת��
     * @param second ����
     * @return �ַ���ʱ���ʽ HHH:mm:ss
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
     * ���ݸ������ڹ���Ӹ������ڷ�Χ�л�����з��Ϲ�������ڼ���
     * @param weektime ���ڹ��� һ������1-7���ַ�����1-7�ֱ��Ӧ��һ�����գ��磺1347��1,3,4,7
     * @param begtime ��ʼ���� yyyy-MM-dd
     * @param endtime �������� yyyy-MM-dd
     * @return ���Ϲ�������ڼ���
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
     * ������������
     * @return
     */
    public static String getPrimaryKey() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * ��������ַ������������ּ���Сд��ĸ��
     * @param length ����ַ�������
     * @return ����ַ���
     */
    public static String randomString(int length) {
        return randomString("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
    }
    
    /**
     * ��������ַ�������
     * 
     * <pre>
     * ʾ��
     * randomString("abc123ABC", 3) = "2Ba" 
     * randomString("#$%UZefop", 5) = "%Zfe#"
     * </pre>
     * @param range ����ַ�����ѡֵ
     * @param length ����ַ�������
     * @return ����ַ���
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
     * ���ݾ�γ�Ⱥ͹�������ȡ��γ�ȵĲ�ֵ
     * @param a ����
     * @param b γ��
     * @param d ����
     * @return double [���ȣ�ά��]
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
     * @param strxy ��γ���ַ��� ������+������+��ά�ȡ���
     * @param dlong ��Χ������
     * @return double [max�󾭶�,mixС����,may��ά��,miyСά��]
     */
    public static double[] dists(String strxy, String dlong) {
        String[] str = strxy.split(",");
        String strx = str[0];
        String stry = str[1];
        double x = Double.valueOf(strx);// ����
        double y = Double.valueOf(stry);// ά��
        double d = Double.valueOf(dlong);// ��Χ
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
        double x = Double.valueOf(strx);// ����
        double y = Double.valueOf(stry);// ά��
        double d = Double.valueOf(dlong);// ��Χ
        double[] dxy = dist(x, y, d);
        // ��ȡ�����С�ľ���
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
        // ��ȡ�����С��ά��
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
     * ��ȡѡת�Ƕ�
     * @param range
     * @param length
     * @return
     */
    public static Integer routate(List<Integer> range) {
        Random rd = new Random();
        return range.get(rd.nextInt(range.size()));
    }
    
    /**
     * ��ȡ�Ƕ�
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
     * ��ӡSQLִ��ʱ��
     * @param start ��ʼʱ��
     * @return
     */
    public static Long printSQLRunTime(Long start) {
        Long now = new Date().getTime();
        System.out.println(now - start);
        return now;
    }
    
    /**
     * ��������ľ�γ�ȼ��������ľ���
     * @param latitude1 γ��1
     * @param longitude1 ����1
     * @param latitude2 γ��2
     * @param longitude2 ����2
     * @return
     */
    public static double getDistanceForTwoPoints(double latitude1, double longitude1, double latitude2, double longitude2) {
        double earthRadius = 6378.137;// ����뾶
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
     * ��ȡָ������n��������
     * @param date ��ǰ����
     * @param years n���
     * @return
     */
    public static Date getYearsAfterDate(Date date, int years) {
        String dateStr = parseDate(date, "yyyy-MM-dd");
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, 7);
        String day = dateStr.substring(8);
        String nyear = String.valueOf(Integer.parseInt(year) + years); // n�������
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
                if ((0 == yearInt % 4 && 0 == yearInt % 100) || 0 == yearInt % 400) { // ����
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
     * �жϻ����Ƿ���Ч���ж����ݣ���ͨ���յ���Ч��Ϊ�����ճ�����δ��ʮ����������꣬ʮ���������ϵ�ʮ�ꡣ��
     * @param dbirthday ��������
     * @param dpassbgndate ���շ�֤����
     * @param denddate �Ŷӷ�������
     * @return
     */
    public static boolean isPassportValid(Date dbirthday, Date dpassbgndate, Date denddate) {
        Date adultDate = getYearsAfterDate(dbirthday, 16); // 16���������
        Date dpassenddate = adultDate.before(dpassbgndate) ? getYearsAfterDate(dpassbgndate, 10) : getYearsAfterDate(dpassbgndate, 5); // ������Ч����
        return dpassenddate.before(denddate) ? false : true; // ��false������ǰ���չ��� true�����غ��չ��ڡ�
    }
    
    /*public static void main(String[] args) {
        double jl_jd = 102.83474258026089786013677476285;// ÿ���ȵ�λ��;
        double jl_wd = 111.71269150641055729984301412873;// ÿγ�ȵ�λ��;
        double n1 = 120.586109, n2 = 121.473704, e1 = 29.995762, e2 = 31.230393;
        double b = Math.abs((e1 - e2) * jl_jd);
        double a = Math.abs((n1 - n2) * jl_wd);
//        System.out.println(Math.sqrt((a * a + b * b)));
        //101.45379020, 12.56709430         ɳ����
        //100.49388890, 13.75222220         ����
        //100.01359290, 9.51201680          ��÷��
        System.out.println(getDistanceForTwoPoints( 9.51201680,100.01359290,  13.75222220,100.49388890));
        System.out.println(GetDistance( 9.51201680,100.01359290,  13.75222220,100.49388890));
//        System.out.println(isPassportValid(new Date(1992 - 1900, 4, 16), new Date(2008 - 1900, 4, 15), new Date(2013 - 1900, 4, 14)));
    }*/
    
    private static double EARTH_RADIUS = 6378.137;//����뾶
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
     * ��Clobת�����ַ���
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String clobToString(Clob clob) throws SQLException, IOException {
        String reString = "";
        Reader is = clob.getCharacterStream();// �õ���
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
            sb.append(s);
            s = br.readLine();
        }
        br.close();
        is.close();
        reString = sb.toString();
        return reString;
    }
    
    /**
     * ������󳤶��������
     * @param content ����
     * @param length ��󳤶�
     * @param str ������ַ�,Ĭ��Ϊ�հ��ַ�
     * @return
     */
    public static String addRestStr(String content, int length, String str) {
        if (content == null) return "";
        if (str == null) str = " ";
        // ��Ϊ��󳤶ȣ�����Ҫ���
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
     * ������ĸ��˳������
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
            if (i == keys.size() - 1) {// ƴ��ʱ�����������һ��&�ַ�
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }
    
    /**
     * �������һ������ڣ� ���磺2015-06-04   ��� 2015-06-04 23:59:59
     * �������Ϊ�գ�Ĭ����ʾ��ǰϵͳ����
     * @param date
     * @return
     * @author SongyangWu
     */
    public static Date getDateOnLastSecond(Date date){
    	if(date == null){
    		date = clearDateTime(); // ����ǰ���ڵ�ʱ������󷵻�
    	}else{
    		date = clearDateTime(date);
    	}
    	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, 1*60*60*24-1); //��������� 23:59:59
		date=calendar.getTime();
		
		return date;
    }
    
    /**
     * �������������
     * 
     * @param date
     * @param day
     * @return
     * @author SongyangWu
     */
    public static Date addDay2Date(Date date, int day){
    	if(date == null){
    		date = clearDateTime(); // ����ǰ���ڵ�ʱ������󷵻�
    	}else{
    		date = clearDateTime(date);
    	}
    	
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, day); //������
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
