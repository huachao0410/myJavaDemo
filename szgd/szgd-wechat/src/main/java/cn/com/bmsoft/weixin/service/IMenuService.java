package cn.com.bmsoft.weixin.service;

import java.util.Map;

/**
 * 微信菜单服务接口
 * 
 * @author daniel
 *
 */
public interface IMenuService {
	
	/**
	 * 创建菜单
	 * 
	 * @param accessToken
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean createMenu(String accessToken,String params) throws Exception;
	
	/**
	 * 查询菜单
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getMenuInfo(String accessToken) throws Exception;
	
	/**
	 * 删除自定义菜单
	 * 
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public boolean deleteMenu(String accessToken) throws Exception;

}
