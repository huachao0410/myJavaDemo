package cn.com.bmsoft.base.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.base.common.security.IContextService;
import cn.com.bmsoft.base.common.security.UserInfo;

/**
 * 登录用户信息服务
 */
@Service
public class ContextService implements IContextService {
	
    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
        	return null;
        }
        return authentication.getPrincipal().toString();
    }

    @Override
    public UserInfo getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) authentication.getDetails();
            return userInfo;
        } else {
            return null;
        }
    }
    
}
