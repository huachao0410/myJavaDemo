package cn.com.bmsoft.base.dao.mybatis.management;

import org.springframework.stereotype.Repository;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.base.dao.face.management.ILogDao;
import cn.com.bmsoft.base.domain.management.Log;

/**
 * T_LOG(日志) DAO实现
 *
 * @作者 黄迪文
 * @创建时间 2012-12-11 14:58:32
 */
@Repository
public class LogDao extends Dao<Log> implements ILogDao {
    
}