package cn.com.bmsoft.dxms.service.face;

import java.util.List;
import java.util.Map;

/**
 * 图表查询 服务接口
 *
 * @author XenogearTang
 *
 */
public interface IExecuteSQLInternalService {
	
	List<Map<String, Object>> executeSelectListDirect(Map<String, Object> executeParams);
    
	List<Map<String, Object>> executeSelectList(Map<String, Object> executeParams);
	
}
