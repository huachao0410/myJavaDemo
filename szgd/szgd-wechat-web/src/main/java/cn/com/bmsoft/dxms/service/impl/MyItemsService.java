package cn.com.bmsoft.dxms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bmsoft.dxms.dao.face.IMyItemsDao;
import cn.com.bmsoft.dxms.domain.Myitems;
import cn.com.bmsoft.dxms.service.face.IMyItemsService;

@Service
public class MyItemsService implements IMyItemsService {
   
    @Autowired
    private IMyItemsDao myItemsDao;
    
    @Override
    public int count(Map<String, Object> queryParams) {
        return this.myItemsDao.count(queryParams);
    }

    @Override
    public List<Myitems> page(int start, int limit, Map<String, Object> queryParams) {
        return this.myItemsDao.page(start, limit, queryParams);
    }

    @Override
    public List<Myitems> find(Map<String, Object> queryParams) {
        return this.myItemsDao.find(queryParams);
    }

    @Override
    public int generatePosition(Map<String, Object> queryParams) {
        return this.myItemsDao.generatePosition(queryParams);
    }

    @Override
    public Myitems load(int id) {
        return this.myItemsDao.load(id);
    }

    @Override
    @Transactional
    public int insert(Myitems myitems) {
        this.myItemsDao.insert(myitems);
        return myitems.getId();
    }

    @Override
    @Transactional
    public int update(Myitems myitems) {
        return this.myItemsDao.update(myitems);
    }

	@Override
    @Transactional
    public int deletes(int[] ids) {
        return this.myItemsDao.deletes(ids);
    }
}
