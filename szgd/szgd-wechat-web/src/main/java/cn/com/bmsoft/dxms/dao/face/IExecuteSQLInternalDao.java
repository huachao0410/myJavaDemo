package cn.com.bmsoft.dxms.dao.face;

import java.util.List;
import java.util.Map;

import cn.com.bmsoft.base.dao.face.IDao;

/**
 * 图表查询 DAO接口
 *
 * @author XenogearTang
 */
public interface IExecuteSQLInternalDao extends IDao<Map<String, Object>> {
	
	List<Map<String, Object>> executeSelectListDirect(Map<String, Object> executeParams);
	
	List<Map<String, Object>> executeSelectList(Map<String, Object> executeParams);	
	
}
