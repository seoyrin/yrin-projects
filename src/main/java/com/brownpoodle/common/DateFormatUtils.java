package com.brownpoodle.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormatUtils {

	public static String ymdFormat(){
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}
	
	public static String ymFormat(){		
		return new SimpleDateFormat("yyyyMM").format(new Date());
	}
	
	public static String yFormat(){		
		return new SimpleDateFormat("yyyy").format(new Date());
	}
	
	public static String ymdFormats(String ymdFlag){
		
		String sd = "";		
		Date d = new Date();
		
		if ("B".equals(ymdFlag)){
			sd = DateFormatUtils.ymdFormat();		
		}
		
		return sd;
	}
	
//	public static void main(String[] args) {
//		System.out.println("DateFormatUtil.ymdFormat() >>> : " 
//								+ DateFormatUtils.ymdFormat());
//		System.out.println("DateFormatUtil.ymdFormats() >>> : " 
//				+ DateFormatUtils.ymdFormats("D"));		
//	}
}
