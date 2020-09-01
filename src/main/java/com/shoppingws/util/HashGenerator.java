package com.shoppingws.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vor.jwtutil.JWTUtil;
import org.springframework.security.util.FieldUtils;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.*;

public class HashGenerator<T> {
	
	public String generateHash(Object t,String[] fields){
		return generateHash(t, fields, null);
	}

	public String generateHash(Object t,String[] fields,String extraValue){
		List<String> listFields = Arrays.asList(fields);
		Class<? extends Object> clzz = t.getClass();
		boolean todayAdded=false;
		Map<String,String> claims = new HashMap<>();
		for(Field field:clzz.getDeclaredFields()){	
			if(listFields.contains(field.getName())){					
				claims.put(field.getName(),readFieldValue(t, field.getName()));
			}else if(!todayAdded && listFields.contains("today")){
				todayAdded=true;
				claims.put(field.getName(),Helper.date2String(new Date()));
				
			}
		}
		if(extraValue!=null){
			claims.put("extra",extraValue);
		}
		
		String strHash = "";
		if(!claims.isEmpty()){
			JWTUtil jwt = JWTUtil.create().setEnvironment(Constants.APP_ACTIVE_PROFILE).build();
			 try {
				strHash = jwt.generateTokenWithJWT(claims);
			} catch (UnsupportedEncodingException e) {
				Helper.errorLogger(getClass(), e);
			}
		}
		
		return strHash;
	}
	
	
	private static String readFieldValue(Object obj,String fieldName){
		try{
			Object pd ;
			String returnType = "";
			if("serialVersionUID".equalsIgnoreCase(fieldName)){
				pd = FieldUtils.getFieldValue(obj,fieldName);
			}else{
				PropertyDescriptor pd2 = new PropertyDescriptor(fieldName,obj.getClass());
				pd2.getPropertyType();
				returnType =  pd2.getReadMethod().getReturnType().getName();
				pd = pd2.getReadMethod().invoke(obj);
			}
			if(pd!=null){
				
				String strVal;
				if(returnType.indexOf("java.util.List")>-1) {
					strVal = new Gson().toJson(pd);
				}else{
					strVal = String.valueOf(pd).trim();					
				}
				
				return strVal.trim();
			}else{
				return null;
			}
		}catch(Exception e){
			Helper.errorLogger(HashGenerator.class, e);
		}
		return "";
	}
	public String generateHashForData(Object t,String[] fields){
		StringBuilder hashVal 	= new StringBuilder();
		hashVal.append("{");
		List<String> listFields = Arrays.asList(fields);
		Class<? extends Object> clzz = t.getClass();
		boolean todayAdded = false;		
		String val ;
		for(Field field:clzz.getDeclaredFields()){
			if(listFields.contains(field.getName())){
				val = readFieldValue(t, field.getName());
				if(val==null) {
					hashVal.append("\""+field.getName()+"\":null,");
				}else if(val.startsWith("[")) {
					hashVal.append("\""+field.getName()+"\":"+ val + ",");
				}else {
					hashVal.append("\""+field.getName()+"\":\""+ val + "\",");
				}
			}else if(!todayAdded && listFields.contains("today")){
				todayAdded=true;
				hashVal.append("\"today\":\""+ Helper.date2String(new Date()) + "\",");				
			}
		}
		String strHash = "";

		if(hashVal.toString().length()>3){			
			strHash = hashVal.toString().substring(0,hashVal.toString().length()-1) + "}";
		}		
		strHash = Helper.generateKeyForData(strHash);
		return strHash;
	}
	public String generateHashForDataWithToString(Object t){
		String strHash = t.toString();
		strHash = Helper.generateKeyForData(strHash);
		return strHash;
	}
	public <T extends Object> T generateObject(String objectKey,Class clzz) {
		try{
			String jsonData = JWTUtil.getDataFromKey(objectKey,"data",true);
			Gson gson = new GsonBuilder().create();
			
			return (T) gson.fromJson(jsonData,clzz);
			
		}catch(Exception e) {
			Helper.errorLogger(getClass(), e);
		}
		return null;
	}
}
