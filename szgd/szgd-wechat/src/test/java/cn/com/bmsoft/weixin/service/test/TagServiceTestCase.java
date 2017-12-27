package cn.com.bmsoft.weixin.service.test;

import cn.com.bmsoft.weixin.bean.Tag;
import cn.com.bmsoft.weixin.bean.TagInfo;
import cn.com.bmsoft.weixin.bean.Tags;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.ITagService;
import cn.com.bmsoft.weixin.service.impl.CommonServiceImpl;
import cn.com.bmsoft.weixin.service.impl.TagServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzh on 2017/7/27.
 */
public class TagServiceTestCase {

	private ICommonService commonService = new CommonServiceImpl();

	private ITagService tagService = new TagServiceImpl();


	private static String appid = "wx92d8e722172cdde9";
	private static String appsecret = "d7a6afd39c72dc62f151a78d99bc379c";

	@Test
	public void getTagList() {
		try {
//			String accessToken = commonService.getAccessToken(appid, appsecret);
			String accessToken = "1gnSnwL5aTklL0vgRZWuFsiYyUKE7BYovRp_kO7ecc57HHSpievyHe_lR6q_EGM99RTwslmJ6sPZMvMJVe4B9Rb20VB7APFsCK21fTWeIA1kPcKqxcue4UD-f97VcRBSPXRdAFAJRL";
			Tags result = tagService.get(accessToken);

			TagInfo[] tagInfos = result.getTags();
			for (int i = 0; i < tagInfos.length; i++) {
				System.out.println(tagInfos[i].getId());
				System.out.println(tagInfos[i].getName());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void create() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			String name = "测试组5";
			Tag result = tagService.create(accessToken, name);
			if (result != null) {
				System.out.println(result.getTag().getId() + ";" + result.getTag().getName());

			} else {
				System.out.println("tag is null");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void edit() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			String id = "105";
			String name = "测试组51";
			JSONObject result = tagService.updateTag(accessToken, id, name);
			System.out.println(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void detete() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			int id = 105;
			JSONObject result = tagService.delete(accessToken, id);
			System.out.println(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void tagGetOpenids() {
		try {
			String accessToken = "1gnSnwL5aTklL0vgRZWuFsiYyUKE7BYovRp_kO7ecc57HHSpievyHe_lR6q_EGM99RTwslmJ6sPZMvMJVe4B9Rb20VB7APFsCK21fTWeIA1kPcKqxcue4UD-f97VcRBSPXRdAFAJRL";
			int tagid = 102;
			String next_openid = "";
			JSONObject result = tagService.tagGetOpenids(accessToken, tagid, next_openid);
			System.out.println(result.toString());
//{"count":1,"data":{"openid":["odEzEwqhVs_tP1yTa9ipPcr3GtRc"]},"next_openid":"odEzEwqhVs_tP1yTa9ipPcr3GtRc"}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void batchTagging() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			int tagid = 104;
			List list = new ArrayList();
			list.add("odEzEwqhVs_tP1yTa9ipPcr3GtRc");
			JSONObject result = tagService.batchTagging(accessToken, list, tagid);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void batchuntagging() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			int tagid = 104;
			List<String> list = new ArrayList<String>();
			list.add("odEzEwqhVs_tP1yTa9ipPcr3GtRc");
			JSONObject result = tagService.batchuntagging(accessToken, list, tagid);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void getIdList() {
		try {
			String accessToken = "l3Wq4fh43yC6RgjbNaDYGs8i-qYhE8NIZNMl_3_q5twofsHxqpbhbAUyn24nO-fABzz3U2xF00Az0snnehkgS8UCKw_z0ZiDhiTh3WoKAq5tEnb1l5C9SVQj6K_nh6ceYZJjAEAGHR";
			String openid = "odEzEwqhVs_tP1yTa9ipPcr3GtRc";
			JSONObject result = tagService.getIdList(accessToken, openid);
			System.out.println(result.toString());
			//{"tagid_list":[100]}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}