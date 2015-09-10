package com.wsy.util;

/**
 * Description: ass 常量类
 * 
 * @author shizy
 * @version:V1.0
 * @createDateTime：2013-7-2上午09:55:57
 * @Company:MSD
 * @Copyright:Copyright(c) 2013
 */

public abstract class AssConstant {
	
	/**
	 * 远程登录失败是否进行本地登录
	 */
	public static Boolean		LOCAL_LOGIN;
	
	/**
	 * 版本号
	 */
	public static String		VERSIONS;
	
	/**
	 * 密码加密时,用到的盐值
	 */
	public static String		SALT_VALUE;
	
	/**
	 * 是否检查session
	 */
	public static Boolean		CHECK_SESSION;
	
	/**
	 * 是否检查权限
	 */
	public static Boolean		CHECK_PERMISSION;
			
	/**
	 * 配置文件reload间隔,单位毫秒
	 */
	public static Long			propRefreshDelay			= 2000L;
	
	/**
	 * 接口访问，超时时间
	 */
	public static Integer		INTERFACE_TIMEOUT;
	
	/**
	 * 异常邮件发送开关
	 */
	public static Boolean		EXCEPTION_MAIL_SWITCH		= false;
	
	public static Boolean		DEBUG						= false;
	
	/**
	 * 定时器启动开关
	 */
	public static Boolean		QUARTZ_SWITCH				= false;
	
	public static Integer		startupDelay;
	
	public static Boolean		waitForJobsToCompleteOnShutdown;
	public static Boolean		overwriteExistingJobs		= true;
	
	/**
	 * 网站根路径
	 */
	public static String		WEB_PATH;
	
	/**
	 * 响应头的Server
	 */
	public static String		WEB_SERVER;
	
	/**
	 * 用户最后的请求时间的KEY,存放于Session中
	 */
	public static final String	SESSION_LAST_REQUEST_TIME	= "SESSION_LAST_REQUEST_TIME";	
	
	/**
	 * 首页路径,网站开始路径
	 */
	public static String		WEB_START_PATH				= "/menu/index";				
	
	/**
	 * 首页路径,网站开始路径--后部分
	 */
	public static String		LOGIN_PATH					= "/login";
	
	/**
	 * 换行符
	 */
	public static final String	LINE_SEPARATOR				= System.getProperty(
																	"line.separator",
																	"/r/n");
	
	/**
	 * 默认编码字集
	 */
	public static final String	UTF8						= "UTF-8";
	
	/**
	 *
	 */
	public static final String	GBK							= "GBK";
	
	/**
	 * (批量新增和批量更新时)多少个数量级,flush一次
	 */
	public static final Integer	FLUSH_CRITICAL_VAL			= 100;
	
	/**
	 * (批量新增和批量更新时)大数量级,flush一次
	 */
	public static final Integer	FLUSH_BIG_CRITICAL_VAL		 = 99999;
	
	/**
	 * 批量删除时,多少个数量级,重新使用or连接 Oracle IN语句的最大表达式数为 1000
	 */
	public static final Integer	DELETE_CRITICAL_VAL			= 999;
	
	/**
	 * varchar2
	 */
	public static final Integer	JDBC_VARCHAR2_MAXLEN		= 2000;
	
}
