package cn.com.bmsoft.base.controllers;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.base.common.security.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private IContextService contextService;

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String login() {
        return "views/account/login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/register")
    public String register() {
        return "views/account/register";
    }

    @ResponseBody
    @RequestMapping("/username")
    public String username() {
        return this.contextService.getUsername();
    }

    @ResponseBody
    @RequestMapping("/userinfo")
    public UserInfo userInfo() {
        return this.contextService.getUserInfo();
    }
}
