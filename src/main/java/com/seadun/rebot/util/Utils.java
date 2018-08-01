package com.seadun.rebot.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.seadun.rebot.constant.RebotConstants;

public class Utils {
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("x-real-ip");
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 根据传入的类型获取spring管理的对应的bean
	 * 
	 * @param clazz
	 * @param request
	 * @return
	 */
	public static <T> T getSpringBean(Class<T> clazz, HttpServletRequest request) {
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return factory.getBean(clazz);
	}

	/**
	 * 得到几天前的凌晨时间
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateBefore(int before) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1 * before);
		Date date = calendar.getTime();
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

		return sdformat.format(date) + " 00:00:00";
	}

	public static String getSize(String sizeStr) {
		Long sizeLong = Long.valueOf(sizeStr);
		if (sizeLong >= RebotConstants.MIN_TB) {
			return sizeLong / RebotConstants.MIN_TB + RebotConstants.MIN_TB_STR;
		} else if (sizeLong >= RebotConstants.MIN_GB) {
			return sizeLong / RebotConstants.MIN_GB + RebotConstants.MIN_GB_STR;
		} else if (sizeLong >= RebotConstants.MIN_MB) {
			return sizeLong / RebotConstants.MIN_MB + RebotConstants.MIN_MB_STR;
		} else if (sizeLong >= RebotConstants.MIN_KB) {
			return sizeLong / RebotConstants.MIN_KB + RebotConstants.MIN_KB_STR;
		} else {
			return sizeLong / RebotConstants.MIN_B + RebotConstants.MIN_B_STR;
		}
	}

	/**
	 * 对字符串数据进行压缩
	 * 
	 * @param data
	 *            元数据
	 * @return String
	 */
	public static String compress(String data) throws Exception {
		if (null == data || "".equals(data) || data.length() == 0) {
			return data;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();// 带有缓存功能的字节输出流
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);

		System.out.println("压缩前的数据大小:" + data.getBytes().length);

		gzipOutputStream.write(data.getBytes());
		gzipOutputStream.close();

		String compressData = byteArrayOutputStream.toString("ISO-8859-1");

		System.out.println("压缩后的数据大小:" + byteArrayOutputStream.toByteArray().length);

		byteArrayOutputStream.close();

		// System.out.println("压缩后的数据:" + compressData);
		return compressData;
	}

	/**
	 * 对压缩后的数据,进行解压缩
	 * 
	 * @param data
	 * @return String
	 */
	public static String unCompress(String data) throws Exception {

		String retStr = "";

		if (null == data || "".equals(data) || data.length() == 0) {
			return data;
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
		GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);

		byte[] b = new byte[256];

		int length = -1;

		while (-1 != (length = gzipInputStream.read(b))) {
			byteArrayOutputStream.write(b, 0, length);
		}

		retStr = byteArrayOutputStream.toString("UTF-8");
		byteArrayOutputStream.close();
		byteArrayInputStream.close();
		gzipInputStream.close();

		System.out.println("解压缩后的数据为:" + retStr);

		return retStr;
	}

}
