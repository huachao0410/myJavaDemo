package cn.com.bmsoft.weixin.service.test;

import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.IMaterialService;
import cn.com.bmsoft.weixin.service.IUserService;
import cn.com.bmsoft.weixin.service.impl.CommonServiceImpl;
import cn.com.bmsoft.weixin.service.impl.MaterialServiceImpl;
import cn.com.bmsoft.weixin.service.impl.UserServiceImpl;
import cn.com.bmsoft.weixin.util.HttpKit;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Map;

/**
 * Created by gzh on 2017/7/10.
 */
public class MaterialServiceTestCase {

	private ICommonService commonService = new CommonServiceImpl();

	private IMaterialService materialService = new MaterialServiceImpl();

	private IUserService userService = new UserServiceImpl();

	private static String appid = "wx92d8e722172cdde9";
	private static String appsecret = "d7a6afd39c72dc62f151a78d99bc379c";


	private static String getAccessTokenUrl = "http://msjwwx.szga.gov.cn/bmswx/weixin/component/getAccessToken?accountId=3";

	@Test
	public void getMaterialList() {
		try {
			String accessToken = commonService.getAccessToken(appid, appsecret);
//			String accessToken = HttpKit.get(getAccessTokenUrl);
			String params = "{\n" +
					"   \"type\":\"news\",\n" +
					"   \"offset\":0,\n" +
					"   \"count\":2\n" +
					"}";
			String result = materialService.getMaterialList(accessToken, params);
			System.out.println(result);

			Map<String,Object> map = JSON.parseObject(result,Map.class);
			if (map !=null) {
				Object subStr = map.get("item");
				if (subStr!=null && !subStr.equals("")) {

					JSONArray jsonArray = JSON.parseArray(subStr.toString());

					JSONObject jsonObject = jsonArray.getJSONObject(0);

					System.out.println(jsonObject.get("media_id").toString());
				}else {
					System.out.println(map.get("errcode") + ";" +map.get("errmsg"));
				}
			}else {
				System.out.println("服务异常");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getMaterial() {
		try {
			String accessToken = HttpKit.get(getAccessTokenUrl);
			String params = "{\n" +
					"\"media_id\":\"u14Em0jnJ26g0qdnQaxiHT0LlFygDdKuHt7Lhi2gGB8\"\n" +
					"}";
			Map<String, Object> result = materialService.getMaterial(accessToken, params);
			if (result.get("news_item") == null) {
				System.out.println(result.get("errcode") + ";" +result.get("errmsg"));

			}else {
				System.out.println(result.get("news_item"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void addMaterialEver() {
		try {
			String accessToken = "3TFJ5Nuh8XCWXXb7xJzhuUVuWcuw0o00wOLQ3o-KpLxTWjcsHYTmy7v9mY1Spa-vIZX-ehTz2zIDznybHOUOusAbQbpkW7XirfV5uZHYaqbX1NbY3G7vqzX13vGfQLYgKOTeAIANRW";
			String path = "/Users/gzh/Desktop/timg.jpg";
			JSONObject object = materialService.addMaterialEver(path,"thumb",accessToken);
			System.out.println(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
