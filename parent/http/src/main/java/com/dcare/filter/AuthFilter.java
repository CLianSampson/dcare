package com.dcare.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.dcare.bean.InitBean;
import com.dcare.common.code.AppErrorEnums;
import com.dcare.common.code.AttributeConst;
import com.dcare.common.message.Packet;
import com.dcare.common.util.DateUtil;
import com.dcare.common.util.PrintWriterUtil;
import com.dcare.common.util.StringUtil;
import com.dcare.dao.UserDO;
import com.dcare.po.User;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {
	private static Logger logger = Logger.getLogger(AuthFilter.class);



	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		AppErrorEnums errorInfo = AppErrorEnums.APP_OK;
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
	
		try {

	        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
	        servletResponse.setHeader("Access-Control-Allow-Methods","POST");
	        servletResponse.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");

			/*****************调试代码*******************/
			logger.info("\n\n");
			logger.info("header");
			Enumeration<?> headers = servletRequest.getHeaderNames();
			while (null != headers && headers.hasMoreElements()) {
				String headerName = (String) headers.nextElement();
				String headerValue = servletRequest.getHeader(headerName);

				logger.info(headerName + " = " + headerValue);
			}
			logger.info("header end\n\n");
			

			/*****************调试代码*******************/

			String contextPath = servletRequest.getContextPath();
			String uri = servletRequest.getRequestURI();

			logger.info("contextPath=" + contextPath);
			logger.info("uri=" + uri);

			// 去除工程名
			if (null != contextPath && !contextPath.equals("") && !contextPath.equals("/")) {
				uri = uri.substring(contextPath.length());
			}

			logger.info("processuri=" + uri);

			// 其他请求，需要鉴权
			String tokenStr = servletRequest.getHeader("token");
			String userId = servletRequest.getHeader("userId");
			
			if (StringUtil.isNullOrBlank(tokenStr)) {
				tokenStr = request.getParameter("token");
			}
			
			if (StringUtil.isNullOrBlank(userId)) {
				userId = request.getParameter("userId");
			}
			
		
			
			// 业务中不要有太多的if else嵌套不要太深 建议用这种方式
			while (true) {
				
				//免登接口不需要经过shiro     changePassword
				if (  uri.equals("/verifyCode/smsVerify") 
					|| uri.equals("/verifyCode/voiceVerify") 
					|| uri.equals("/login")  
					|| uri.equals("/getLatestVersion")
					|| uri.equals("/healthInfo")
					|| uri.equals("/goodsList")
					|| uri.equals("/iostest")
					|| uri.equals("/upload")  
					|| uri.equals("/verifyCode/emailVerify")
					|| uri.equals("/register")
					|| uri.equals("/changePassword")){
					
					chain.doFilter(request, response);
					return;
				}
				
				if( StringUtil.isNullOrBlank(userId) ){
					logger.error("请求头中userId为空");
					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
					break;
				}


				if( StringUtil.isNullOrBlank(tokenStr) ){
					logger.error("token为空");
					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
					break;
				}
				
				//判断token是否过期
				UserDO userDO = InitBean.getUssrDao();
				User user = userDO.selectByPrimaryKey(Integer.valueOf(userId));
				if (null == user) {
					logger.error("请求头中userId错误，没有该用户");
					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
					break;
				}
				
				long dif = DateUtil.getMinute(user.getUpdateTime());
				if (dif > AttributeConst.USER_LOGIN_INVALID_TIME) {// 单位，分钟
					//需要用户重新登陆
					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
					break;
				}
				
				if( !user.getToken().equals(tokenStr) ){
					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
					break;
				}
				
				
				
				//判断deviceId是否改变, 换了设备之后token会改变
//				if (!TokenUtil.getDeviceIdByToken(tokenStr).equals(TokenUtil.getDeviceIdByToken(user.getToken()))) {
//					errorInfo = AppErrorEnums.APP_TOKEN_INVALID;
//					break;
//				}
				
				
				// 最后进入业务逻辑代码
				chain.doFilter(request, response);

				break;// 这句很重要不要删除
			} // while end
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("过滤器异常",e);
			errorInfo = AppErrorEnums.APP_ERROR;
		}
		
		if (AppErrorEnums.APP_OK != errorInfo) {
			
			logger.error("errorInfo:" + errorInfo);
			
			Packet rtvPacket = new Packet();
			rtvPacket.setCode(errorInfo.getCode());
			rtvPacket.setData(errorInfo.getMessage());
		

			PrintWriterUtil.writeResultToClient(servletResponse, JSON.toJSON(rtvPacket).toString());
		}	
	}

	
	
	
	public void destroy() {	
	}

	public void init(FilterConfig arg0) throws ServletException {	
	}

	
}
