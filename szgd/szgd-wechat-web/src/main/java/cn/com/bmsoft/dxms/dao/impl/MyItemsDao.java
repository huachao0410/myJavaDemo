package cn.com.bmsoft.dxms.dao.impl;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.dxms.dao.face.IMyItemsDao;
import cn.com.bmsoft.dxms.domain.Myitems;
import org.springframework.stereotype.Repository;

/**
 * T_MYITEMS(我的事项) DAO实现
 */
@Repository
public class MyItemsDao extends Dao<Myitems> implements IMyItemsDao {
    
}