package cn.com.bmsoft.dxms.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Repository;

import cn.com.bmsoft.base.dao.mybatis.Dao;
import cn.com.bmsoft.dxms.dao.face.IExecuteSQLInternalDao;

/**
 * 图表查询 DAO实现
 *
 * @author XenogearTang
 */
@Repository
public class ExecuteSQLInternalDao extends Dao<Map<String, Object>> implements IExecuteSQLInternalDao {
	
	private String getExecuteSql(Map<String, Object> executeParams){
		String filePath = String.valueOf(executeParams.get("filePath"));
		String fileName = String.valueOf(executeParams.get("fileName"));
		String executeName = String.valueOf(executeParams.get("executeName"));
		XMLOperator xmlOper = new XMLOperator();
		String executeSQL = xmlOper.getExecuteSQLInternal(filePath, fileName, executeName);
		String executeJSON = String.valueOf(executeParams.get("executeJSON"));
		Map<String, Object> params = null;
		if(executeJSON != null && executeJSON.length() > 0){
			ObjectMapper ob = new ObjectMapper(); 
			try{
				params = (HashMap<String, Object>)ob.readValue(executeJSON, HashMap.class);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		if(executeSQL != null && executeSQL.length() > 0){
			if(params != null && !params.isEmpty()){
			    for (Map.Entry<String, Object> entry : params.entrySet()) {
			    	executeSQL = executeSQL.replace("${" + String.valueOf(entry.getKey()) + "}", String.valueOf(entry.getValue()).replace("'", "''"));
			    }  
			}
		}
		return executeSQL;
	}
	
	@Override
	public List<Map<String, Object>> executeSelectListDirect(Map<String, Object> executeParams){
		return super.getSqlSession().selectList(this.mapperNamespace + ".executeSelectList", executeParams);
	}
	
	@Override
	public List<Map<String, Object>> executeSelectList(Map<String, Object> executeParams){
		executeParams.put("executeSQL", this.getExecuteSql(executeParams));
		return super.getSqlSession().selectList(this.mapperNamespace + ".executeSelectList", executeParams);
	}
	
}