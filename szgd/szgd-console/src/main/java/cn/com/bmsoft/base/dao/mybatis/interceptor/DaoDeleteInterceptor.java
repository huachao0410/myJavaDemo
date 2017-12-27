package cn.com.bmsoft.base.dao.mybatis.interceptor;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import java.util.Map;

/**
 * DAO删除操作拦截器
 *
 * @author huangdiwen
 */
public interface DaoDeleteInterceptor<T> {
    void invoke(Dao dao, Map<String, Object> queryParams);
}
