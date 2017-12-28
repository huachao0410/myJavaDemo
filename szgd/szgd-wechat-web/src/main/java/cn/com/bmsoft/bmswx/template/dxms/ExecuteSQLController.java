package cn.com.bmsoft.bmswx.template.dxms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.bmsoft.base.common.easyui.DataGridModel;
import cn.com.bmsoft.base.common.mvc.MutilCustomDateEditor;
import cn.com.bmsoft.base.common.web.RequestUtil;
import cn.com.bmsoft.dxms.service.face.IExecuteSQLInternalService;
/**
 * 图表查询 控制器
 *
 * @author XenogearTang
 */
@Controller("template.executeSQLController")
@RequestMapping("/weixin/executesql")
public class ExecuteSQLController {
	
    @Autowired
    private IExecuteSQLInternalService executeSQLInternalService;
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Date.class, new MutilCustomDateEditor("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" ));
    }
    
    @ResponseBody
	@RequestMapping(value = "/executeselectlistinternal", method = RequestMethod.POST)
	public Map<String, Object> executeSelectListInternal(String fileName, String executeName, String executeJSON, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();				
		Map<String, Object> executeParams = new HashMap<String, Object>();
		
		StringBuffer filePath = new StringBuffer();
		filePath.append(request.getSession().getServletContext().getRealPath("/"));
		filePath.append("\\resources\\template\\dxms\\executesql\\");
    	executeParams.put("filePath", filePath.toString());
		executeParams.put("fileName", fileName);
		executeParams.put("executeName", executeName);
		executeParams.put("executeJSON", executeJSON);
		List<Map<String, Object>> returnResult = this.executeSQLInternalService.executeSelectList(executeParams);
		if(returnResult != null && returnResult.size() > 0){
			result.put("result", "success");
			result.put("returnResult", returnResult);
		}else{
			result.put("result", "fail");
			result.put("reason", "not found");
		}
		return result;
    }
    
}
