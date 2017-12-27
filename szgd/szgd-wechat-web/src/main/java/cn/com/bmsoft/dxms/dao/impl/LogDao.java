package cn.com.bmsoft.dxms.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.dxms.dao.face.ILogDao;
import cn.com.bmsoft.dxms.domain.Log;

/**
 * T_LOG(日志) DAO实现
 */
@Repository
public class LogDao extends Dao<Log> implements ILogDao {
    
}