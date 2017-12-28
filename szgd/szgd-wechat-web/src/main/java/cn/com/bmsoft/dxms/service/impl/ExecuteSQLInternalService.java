package cn.com.bmsoft.dxms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.bmsoft.dxms.dao.face.IExecuteSQLInternalDao;
import cn.com.bmsoft.dxms.service.face.IExecuteSQLInternalService;


/**
 * 图表查询 服务实现
 *
 * @author XenogearTang
 */
@Service
public class ExecuteSQLInternalService implements IExecuteSQLInternalService {
   
    @Autowired
    private IExecuteSQLInternalDao executeSQLInternalDao;
    
    @Override
    public List<Map<String, Object>> executeSelectListDirect(Map<String, Object> executeParams){
    	return this.executeSQLInternalDao.executeSelectListDirect(executeParams);
    }
    
    @Override
    public List<Map<String, Object>> executeSelectList(Map<String, Object> executeParams){
    	return this.executeSQLInternalDao.executeSelectList(executeParams);
    }
    
}
