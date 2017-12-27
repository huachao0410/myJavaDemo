package cn.com.bmsoft.dxms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bmsoft.dxms.dao.face.IOnlineBidDao;
import cn.com.bmsoft.dxms.domain.OnlineBid;
import cn.com.bmsoft.dxms.service.face.IOnlineBidService;

@Service
public class OnlineBidService implements IOnlineBidService {
   
    @Autowired
    private IOnlineBidDao onlineBidDao;
    
    @Override
    public int count(Map<String, Object> queryParams) {
        return this.onlineBidDao.count(queryParams);
    }

    @Override
    public List<OnlineBid> page(int start, int limit, Map<String, Object> queryParams) {
        return this.onlineBidDao.page(start, limit, queryParams);
    }

    @Override
    public List<OnlineBid> find(Map<String, Object> queryParams) {
        return this.onlineBidDao.find(queryParams);
    }

    @Override
    public int generatePosition(Map<String, Object> queryParams) {
        return this.onlineBidDao.generatePosition(queryParams);
    }

    @Override
    public OnlineBid load(int id) {
        return this.onlineBidDao.load(id);
    }

    @Override
    @Transactional
    public int insert(OnlineBid OnlineBid) {
        this.onlineBidDao.insert(OnlineBid);
        return OnlineBid.getId();
    }

    @Override
    @Transactional
    public int update(OnlineBid onlineBid) {
        return this.onlineBidDao.update(onlineBid);
    }

	@Override
    @Transactional
    public int deletes(int[] ids) {
        return this.onlineBidDao.deletes(ids);
    }
}
