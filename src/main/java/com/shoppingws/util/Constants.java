package com.shoppingws.util;


public class Constants {
	
	public static final String 	APP_ACTIVE_PROFILE 		= Helper.getAppMessage("application", "spring.profiles.active");
	public static final boolean PERFORMANCE_LOG_OPEN 	= "true".equalsIgnoreCase(Helper.getAppMessage("performance.log.enable"));
	public static final String	DB2_READ_ONLY_QUERY 	= " FOR READ ONLY WITH UR ";
	public static final String 	FOLDER_PATH 			= Helper.getAppMessage("doc.path");
	public static final int		WS_TIMEOUT				= 10000; //Axis'le generate edilen WS'lerin *Locator.java sınıfları içinde _stub değişkenine _stub.setTimeout(WS_TIMEOUT)  atanmalı
	public static final String	SERVER_NAME				= System.getProperty("ServerName");
	
	public static  String USER_AIRLINE_CODE_VAL		= "N4";
	public static  String USER_SIRENA_ID_VAL		= "61000148";
	public static  String USER_AIRLINE_VALUE		= "ATLASJET";	
	public static  String USER_NAME_VALUE			= "ATLASJET";	
	public static  String USER_AGENCY_NUMBER_VALUE  = "2005";
	
	public static String PROJECT_NAME 	= "AtlasOnline";
	public static String PROJECT_PATH 	= "/AtlasOnline/";
	public static String IMAGE_PATH 	= PROJECT_PATH + "images";
	public static String CSS_PATH 		= PROJECT_PATH + "css";
	public static String JS_PATH 		= PROJECT_PATH + "js";
	public static String ENCODING		= "UTF-8";
	//public static String HOST_ADDRESS	= "http://localhost";
	//public static String HOST_ADDRESS	= "http://adstest.atlasglb.com";
	public static String HOST_ADDRESS	= "https://online.atlasglb.com";
	public static String SSL_PORT		= "443";	
	public static String PORT			= "80";	
	public static String FILE_PATH 		= "http://9.1.1.39/StaticDoc";
	public static String DEVELOPER_MAIL = "asenturk@altar-tr.com";
	public static String SITE_ADDRESS	= HOST_ADDRESS;
	
	
	//common strings
		public static final String CHOOSEALL= "ALL";
		public static final String CHOOSE	= "CHOOSE";
		public static final String SUCCESS 	= "success";
		public static final String FAILURE 	= "failure";
		public static final String ERROR	= "ERROR";
		public static final String EMPTY	= "";
		public static final String DASH		= "-";
	
	private Constants() {
		super();
	}

}
