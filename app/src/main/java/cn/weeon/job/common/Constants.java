/**
 * Project Name:hengdian-film
 * File Name:Constants.java
 * Package Name:com.ly.hengdian_film.util
 * Date:2013-11-11下午10:36:48
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
*/

package cn.weeon.job.common;

import org.apache.http.protocol.HTTP;


public class Constants {

	public static final String CHARSET = HTTP.UTF_8; // 设置编码
	public static final int READ_TIME_OUT = 10 * 1000; // 读取超时
	public static final int CONNECT_TIME_OUT = 10 * 1000; // 超时时间
	
	
	
	//public static final String REG_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	public static final String REG_MOBILE = "^(1[3,5,8,7,4])\\d{9}$";
	public static final String REG_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	
	public static final String REG_NUM = "[0-9]{1,}";
	public static final String REG_NICKNAME = "([\u4E00-\u9FA5]+[^@]+$)|(^[^@0-9]+([\u4E00-\u9FA5]+[^@]+|[^@]+[A-Za-z_]+|[^@]+\\d+)[^@]+$){1,64}";
	public static final String REG_PASSWORD = "[a-z0-9A-Z]{4,30}";
	
	public static final int CACHE_SIZE = 25;// 缓存大小25M
	public static final int FREE_SD_SPACE_NEEDED_TO_CACHE = 2;//最小剩余空间2M
	public static final int MB = 1024 * 1024;
	public static final int EXPIRED_TIME = 5 * 24 * 60 * 60 * 1000;//5天
	//public static final int EXPIRED_TIME =  5 * 60 * 1000;//5分钟

}

