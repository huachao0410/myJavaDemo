package cn.com.bmsoft.weixin.service;
import cn.com.bmsoft.weixin.bean.Tag;
import cn.com.bmsoft.weixin.bean.Tags;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
/**
 * Created by gzh on 2017/7/27.
 */
public interface ITagService {

	/**
	 * 1.创建标签
	 *
	 * @param accessToken
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Tag create(String accessToken, String name) throws Exception;

	/**
	 * 2.获取公众号已创建的标签
	 *
	 * @param accessToken
	 * @return
	 * @throws Exception
	 */
	public Tags get(String accessToken) throws Exception;

	/**
	 * 3.编辑标签
	 *
	 * @param accessToken
	 * @param id
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public JSONObject updateTag(String accessToken, String id, String name) throws Exception;

	/**
	 * 4.删除标签
	 *
	 * @param accessToken
	 * @param grouid
	 * @return
	 * @throws Exception
	 */
	public JSONObject delete(String accessToken, int grouid) throws Exception;

	/**
	 * 5. 获取标签下粉丝列表
	 *
	 * @param tagid
	 * @param next_openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject tagGetOpenids(String accessToken,int tagid, String next_openid) throws Exception;

	/**
	 * 6. 批量为用户打标签
	 *
	 * @param openid_list
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public JSONObject batchTagging(String accessToken,List<String> openid_list, int tagid) throws Exception;

	/**
	 * 7.批量为用户取消标签
	 *
	 * @param openid_list
	 * @param tagid
	 * @return
	 * @throws Exception
	 */
	public JSONObject batchuntagging(String accessToken,List<String> openid_list, int tagid) throws Exception;

	/**
	 * 8.获取用户身上的标签列表
	 *
	 * @param accessToken
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	public JSONObject getIdList(String accessToken, String openid) throws Exception;



}
