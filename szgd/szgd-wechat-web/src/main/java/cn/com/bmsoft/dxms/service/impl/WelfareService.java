package cn.com.bmsoft.dxms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.bmsoft.dxms.dao.face.IWelfareDao;
import cn.com.bmsoft.dxms.domain.Welfare;
import cn.com.bmsoft.dxms.service.face.IWelfareService;

@Service
public class WelfareService implements IWelfareService {
   
    @Autowired
    private IWelfareDao welfareDao;
    
    @Override
    public int count(Map<String, Object> queryParams) {
        return this.welfareDao.count(queryParams);
    }

    @Override
    public List<Welfare> page(int start, int limit, Map<String, Object> queryParams) {
        return this.welfareDao.page(start, limit, queryParams);
    }

    @Override
    public List<Welfare> find(Map<String, Object> queryParams) {
        return this.welfareDao.find(queryParams);
    }

    @Override
    public int generatePosition(Map<String, Object> queryParams) {
        return this.welfareDao.generatePosition(queryParams);
    }

    @Override
    public Welfare load(int id) {
        return this.welfareDao.load(id);
    }

    @Override
    @Transactional
    public int insert(Welfare welfare) {
        this.welfareDao.insert(welfare);
        return welfare.getId();
    }

    @Override
    @Transactional
    public int update(Welfare welfare) {
        return this.welfareDao.update(welfare);
    }

	@Override
    @Transactional
    public int deletes(int[] ids) {
        return this.welfareDao.deletes(ids);
    }
}
