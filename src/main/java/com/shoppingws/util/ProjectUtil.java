package com.shoppingws.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vor.jwtutil.JWTUtil;
import com.vor.onlinecheckin.model.base.ResponseData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProjectUtil {

	private ProjectUtil() {
		super();
	}

	
	public static List<String> getTaskRunnerList() {
		List<String> list = new ArrayList<>();
		String str = Helper.getAppMessage("scheduler.task.runner.list");
		list.addAll(Arrays.asList(str.split(",")));
		return list;
	}

	public static boolean havePermissionRunForTask(String ipAddress) {
		return ProjectUtil.getTaskRunnerList().contains(ipAddress);
	}


	public static Object getObjectFromKey(String objectKey, Class<?> clzz) {
		if (objectKey == null)
			return null;
		String jsonData = JWTUtil.getDataFromKey(objectKey, "data", true);
		if (jsonData == null)
			return null;
		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonData, clzz);
	}

	public static String calcElapedTimeAsString(Date beginDate, Date endDate) {

		long different = endDate.getTime() - beginDate.getTime();

		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;

		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;

		long elapsedHours = different / hoursInMilli;
		different = different % hoursInMilli;

		long elapsedMinutes = different / minutesInMilli;
		different = different % minutesInMilli;

		long elapsedSeconds = different / secondsInMilli;
		if (elapsedDays > 0) {
			return String.format("%d gün", elapsedDays);
		} else if (elapsedHours > 0) {
			return String.format("%d saat", elapsedHours);
		} else if (elapsedMinutes > 0) {
			return String.format("%d dakika", elapsedMinutes);
		} else if (elapsedSeconds > 0) {
			return String.format("%d saniye", elapsedSeconds);
		} else {
			return String.format("%d gün, %d saat, %d dakika, %d saniye", elapsedDays, elapsedHours, elapsedMinutes,
					elapsedSeconds);
		}
	}

	public static String getContentFromResource(String filePath) {
		String UTF8 = "utf8";
		int BUFFER_SIZE = 8192;
		String content = "";
		try {
			Resource resource = new ClassPathResource(filePath);

			InputStream is = resource.getInputStream();
			if (is != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is, UTF8), BUFFER_SIZE);
				String str;
				while ((str = br.readLine()) != null) {
					content += str;
				}
			}
			is.close();
		} catch (Exception e) {
			Helper.errorLogger(ProjectUtil.class, e);
		}
		return content;
	}
	
	public static ResponseData<String> getResponseStatus(String code, String message) {
		ResponseData<String> resp = new ResponseData();
		resp.setStatusCode(code);
		resp.setStatusMessage(message);
		return resp;
	}


	public static String getPersonnelId(String username) {
		return "1";
	}
	
	
}
