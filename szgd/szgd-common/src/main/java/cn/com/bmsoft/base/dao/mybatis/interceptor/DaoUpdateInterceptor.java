package cn.com.bmsoft.base.dao.mybatis.interceptor;

import cn.com.bmsoft.base.dao.mybatis.Dao;

/**
 * DAO修改操作拦截器
 *
 * @author huangdiwen
 */
public interface DaoUpdateInterceptor<T> {

    void invoke(Dao dao, T obj);
}
