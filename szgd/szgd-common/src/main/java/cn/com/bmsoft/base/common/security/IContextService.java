package cn.com.bmsoft.base.common.security;

/**
 * 上下文接口
 */
public interface IContextService {

    String getUsername();
    
    UserInfo getUserInfo();

}
