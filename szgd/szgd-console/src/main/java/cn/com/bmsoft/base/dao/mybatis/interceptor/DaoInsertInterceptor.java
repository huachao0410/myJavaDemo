package cn.com.bmsoft.base.dao.mybatis.interceptor;

import cn.com.bmsoft.base.dao.mybatis.Dao;

/**
 * DAO插入操作拦截器
 *
 * @author huangdiwen
 */
public interface DaoInsertInterceptor<T> {

    void invoke(Dao dao, T obj);
}
