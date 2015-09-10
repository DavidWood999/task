package com.wsy.util;

/**
 * Description: ass ������
 * 
 * @author shizy
 * @version:V1.0
 * @createDateTime��2013-7-2����09:55:57
 * @Company:MSD
 * @Copyright:Copyright(c) 2013
 */

public abstract class AssConstant {
	
	/**
	 * Զ�̵�¼ʧ���Ƿ���б��ص�¼
	 */
	public static Boolean		LOCAL_LOGIN;
	
	/**
	 * �汾��
	 */
	public static String		VERSIONS;
	
	/**
	 * �������ʱ,�õ�����ֵ
	 */
	public static String		SALT_VALUE;
	
	/**
	 * �Ƿ���session
	 */
	public static Boolean		CHECK_SESSION;
	
	/**
	 * �Ƿ���Ȩ��
	 */
	public static Boolean		CHECK_PERMISSION;
			
	/**
	 * �����ļ�reload���,��λ����
	 */
	public static Long			propRefreshDelay			= 2000L;
	
	/**
	 * �ӿڷ��ʣ���ʱʱ��
	 */
	public static Integer		INTERFACE_TIMEOUT;
	
	/**
	 * �쳣�ʼ����Ϳ���
	 */
	public static Boolean		EXCEPTION_MAIL_SWITCH		= false;
	
	public static Boolean		DEBUG						= false;
	
	/**
	 * ��ʱ����������
	 */
	public static Boolean		QUARTZ_SWITCH				= false;
	
	public static Integer		startupDelay;
	
	public static Boolean		waitForJobsToCompleteOnShutdown;
	public static Boolean		overwriteExistingJobs		= true;
	
	/**
	 * ��վ��·��
	 */
	public static String		WEB_PATH;
	
	/**
	 * ��Ӧͷ��Server
	 */
	public static String		WEB_SERVER;
	
	/**
	 * �û���������ʱ���KEY,�����Session��
	 */
	public static final String	SESSION_LAST_REQUEST_TIME	= "SESSION_LAST_REQUEST_TIME";	
	
	/**
	 * ��ҳ·��,��վ��ʼ·��
	 */
	public static String		WEB_START_PATH				= "/menu/index";				
	
	/**
	 * ��ҳ·��,��վ��ʼ·��--�󲿷�
	 */
	public static String		LOGIN_PATH					= "/login";
	
	/**
	 * ���з�
	 */
	public static final String	LINE_SEPARATOR				= System.getProperty(
																	"line.separator",
																	"/r/n");
	
	/**
	 * Ĭ�ϱ����ּ�
	 */
	public static final String	UTF8						= "UTF-8";
	
	/**
	 *
	 */
	public static final String	GBK							= "GBK";
	
	/**
	 * (������������������ʱ)���ٸ�������,flushһ��
	 */
	public static final Integer	FLUSH_CRITICAL_VAL			= 100;
	
	/**
	 * (������������������ʱ)��������,flushһ��
	 */
	public static final Integer	FLUSH_BIG_CRITICAL_VAL		 = 99999;
	
	/**
	 * ����ɾ��ʱ,���ٸ�������,����ʹ��or���� Oracle IN���������ʽ��Ϊ 1000
	 */
	public static final Integer	DELETE_CRITICAL_VAL			= 999;
	
	/**
	 * varchar2
	 */
	public static final Integer	JDBC_VARCHAR2_MAXLEN		= 2000;
	
}
