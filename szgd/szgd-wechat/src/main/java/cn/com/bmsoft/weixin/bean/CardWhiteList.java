package cn.com.bmsoft.weixin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡券对象(测试白名单)
 * Created by daniel on 2017/5/16.
 */
public class CardWhiteList {

    private List<String> openid = new ArrayList<String>();
    private List<String> username = new ArrayList<String>();

    public List<String> getOpenid() {
        return openid;
    }

    public void addOpenid(String openid) {
        if(this.openid.size() < 10) {
            this.openid.add(openid);
        }
    }

    public List<String> getUsername() {
        return username;
    }

    public void addUsername(String username) {
        if(this.username.size() < 10) {
            this.username.add(username);
        }
    }
}
