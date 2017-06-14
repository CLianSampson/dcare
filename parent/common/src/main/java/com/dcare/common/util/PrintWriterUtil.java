package com.dcare.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

public class PrintWriterUtil {
	
	/**
	 * 结果返回
	 * @param response
	 * @param result
	 * @throws IOException 
	 */
	public static void writeResultToClient(HttpServletResponse response, String result) {
		try {			
			byte[] data = result.getBytes("utf-8");
			
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Length", Integer.toString(data.length));
			response.setStatus(HttpServletResponse.SC_OK);
			
			response.getOutputStream().write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 页面跳转
	 * @param response
	 * @param result
	 */
	public static void redirectResultToClient(HttpServletResponse response, String result) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.sendRedirect(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 错误返回
	 * @param response
	 * @param result
	 */
	public static void wirteErrorResultToClient(HttpServletResponse response,
			String result) {
		
		GZIPOutputStream gos = null;
		try {			
			byte[] data = result.getBytes("utf-8");
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			gos = new GZIPOutputStream(bos);
			gos.write(data);
			gos.flush();
			gos.finish();
			
			byte[] gzipdata = bos.toByteArray();
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Encoding", "gzip");
			response.setHeader("Content-Length", Integer.toString(gzipdata.length));
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			
			response.getOutputStream().write(gzipdata);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
