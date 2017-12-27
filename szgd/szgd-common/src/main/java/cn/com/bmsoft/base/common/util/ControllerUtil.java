package cn.com.bmsoft.base.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器辅助类
 * 
 * @author daniel
 *
 */
public class ControllerUtil {
	
	/**
	 * 获取当前域地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getWebsiteUrl(HttpServletRequest request) {
		String contextPath = request.getContextPath().toString();
		int index = request.getRequestURL().indexOf(contextPath);
		String url = request.getRequestURL().substring(0, index) + contextPath;
		return url;
	}

}
