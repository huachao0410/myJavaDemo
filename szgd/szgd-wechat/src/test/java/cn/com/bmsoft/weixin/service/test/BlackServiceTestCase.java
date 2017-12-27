package cn.com.bmsoft.weixin.service.test;

import cn.com.bmsoft.weixin.service.IBlackService;
import cn.com.bmsoft.weixin.service.ICommonService;
import cn.com.bmsoft.weixin.service.impl.BlackServiceImpl;
import cn.com.bmsoft.weixin.service.impl.CommonServiceImpl;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzh on 2017/7/27.
 */
public class BlackServiceTestCase {

	private IBlackService blackService = new BlackServiceImpl();


	private static String appid = "wx92d8e722172cdde9";
	private static String appsecret = "d7a6afd39c72dc62f151a78d99bc379c";

	@Test
	public void getBlacklist() {
		try {
//			String accessToken = commonService.getAccessToken(appid, appsecret);
//			System.out.println(accessToken);
			String accessToken = "1gnSnwL5aTklL0vgRZWuFsiYyUKE7BYovRp_kO7ecc57HHSpievyHe_lR6q_EGM99RTwslmJ6sPZMvMJVe4B9Rb20VB7APFsCK21fTWeIA1kPcKqxcue4UD-f97VcRBSPXRdAFAJRL";
			JSONObject result = blackService.getBlacklist(accessToken,"");

			System.out.println(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void batchBlackList() {
		try {
			String accessToken = "1gnSnwL5aTklL0vgRZWuFsiYyUKE7BYovRp_kO7ecc57HHSpievyHe_lR6q_EGM99RTwslmJ6sPZMvMJVe4B9Rb20VB7APFsCK21fTWeIA1kPcKqxcue4UD-f97VcRBSPXRdAFAJRL";
			List<String> openids = new ArrayList<String>();
			openids.add("odEzEwv4EGYHktm6rHlrN6FgXUEE");
			JSONObject result = blackService.batchBlackList(accessToken,openids);

			System.out.println(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void batchUnblackList() {
		try {
			String accessToken = "1gnSnwL5aTklL0vgRZWuFsiYyUKE7BYovRp_kO7ecc57HHSpievyHe_lR6q_EGM99RTwslmJ6sPZMvMJVe4B9Rb20VB7APFsCK21fTWeIA1kPcKqxcue4UD-f97VcRBSPXRdAFAJRL";
			List<String> openids = new ArrayList<String>();
			openids.add("odEzEwv4EGYHktm6rHlrN6FgXUEE");
			JSONObject result = blackService.batchUnblackList(accessToken,openids);

			System.out.println(result.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
