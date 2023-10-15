package com.co.kr.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//import org.springframwork.security.crypto.bcrypt.BCryptPasswordEncoder;
public class StringUtil {

	/**
	 * 20210210
	 * @return
	 */
	public static String getToday() {
		return getFullYear()+getMonthMM()+getDayDD();
	}
	/**
	 * 2021-01-10
	 * @return
	 */
	public static String getTodayDash() {
		return getFullYear()+"-"+getMonthMM()+"-"+getDayDD();
	}
	/**
	 * -(하이픈)있음
	 * RandomUUID 생성
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}
	/**
	 * - 하이픈제거
	 * RandomUUID()
	 * @return
	 */
	public static String randomUUID() {
		String str=getRandomUUID();
		return str.replaceAll("-", "");
	}
	
	/**
	 * 현재년도를 구하는 함수 String
	 * ex) 2020
	 * @return
	 */
	public static String getFullYear() {
		return String.valueOf(getFullYearInt());
	}
	
	/**
	 * 현재년도를 구하는 함수 int
	 * @return
	 */
	public static int getFullYearInt() {
		Calendar cal=Calendar.getInstance();
		return cal.get(cal.YEAR);
	}
	/**
	 * 현재 달을 구하는 함수
	 * ex)2자리이며, 1~9월일경우 0을 붙임 09
	 * @return
	 */
	
	public static String getMonthMM() {
		int month = getMonthInt();
		String temp="";
		if(month<10) {
			temp="0"+month;
		}else {
			temp=""+month;
		}
		return temp;
	}

	/**
	 * 현재 달을 구하는 함수
	 * ex) 1월~9월은 1자리 //10~12월은 2자리
	 */
	 public static String getMonthString() {
		 return String.valueOf(getMonthInt());
	 }

	 /**
	  * 현재 달을 구하는 함수
	  * ex) 1월~9월은 1자리 //10월~12월은 2자리
	  */
	 public static int getMonthInt() {
		 Calendar cal=Calendar.getInstance();
		 int month=cal.get(cal.MONTH)+1;
		 return month;
	 }
	 /**
	  * 현재 일을 구하는 함수
	  * 1~9는 1자리
	  */
	 public static int getDayInt() {
		 Calendar cal=Calendar.getInstance();
		 return cal.get(cal.DATE);
	 }
	 /**
	  * 현재 일을 구하는 함수
	  * 1~9는 0을 붙여서 01~09이며, 나머지는 2자리
	  */
	 public static String getDayDD() {
		 int date=getDayInt();
		 String temp="";
		 if(date<10) {
			 temp="0"+date;
		 }else {
			 temp=""+date;
		 }
		 return temp;
	 }
	 /**
	  * 지정한 날짜에 일수를 더함
	  * @param ymd   yyyy-MM-dd
	  * @param amount
	  */
	 public static String addDate(String ymd, int amount) {
		 Calendar cal=Calendar.getInstance();
		 DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 try {
			 cal.setTime(df.parse(ymd));
			 cal.add(Calendar.DATE, amount);
		 }catch(ParseException e) {
			 e.printStackTrace();
		 }
		 return df.format(cal.getTime());
	 }
}
