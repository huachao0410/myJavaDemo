package cn.com.bmsoft.base.common.rest;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.base.common.service.face.IBmsEsbService;
import cn.com.bmsoft.base.common.web.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 北明服务总线提供的服务
 */
@RestService(name = "北明服务总线提供的服务")
@Controller("bmsEsbRestService")
@RequestMapping("/rest/bmsesb")
public class BmsEsbRestService {

    @Autowired
    private IBmsEsbService bmsEsbService;

    /**
     * get服务请求
     *
     * @param cmd
     * @param request
     */
    @RestMethod(name = "get服务请求")
    @ResponseBody
    @RequestMapping(value = "/doget", method = RequestMethod.GET)
    public ResponseBean<Object> doGet(
            @RestParam(name = "业务代码") @RequestParam(value="cmd")String cmd,
            @RestParam(name = "根据接口文档传递") HttpServletRequest request) {
        ResponseBean<Object> bean = new ResponseBean<Object>();
        try {
            Map<String, Object> queryParams = RequestUtil.asMap(request, false);
            queryParams.remove("cmd");
            return bmsEsbService.doGet(cmd, queryParams);
        } catch (Exception e) {
            bean.setCode(ResponseCode.FAILED_500);
        }
        return bean;
    }

    /**
     * post服务请求
     *
     * @param cmd
     * @param request
     */
    @RestMethod(name = "post服务请求")
    @ResponseBody
    @RequestMapping(value = "/dopost", method = RequestMethod.POST)
    public ResponseBean<Object> doPost(
            @RestParam(name = "业务代码") @RequestParam(value="cmd")String cmd,
            @RestParam(name = "根据接口文档传递") HttpServletRequest request) {
        ResponseBean<Object> bean = new ResponseBean<Object>();
        try {
            Map<String, Object> queryParams = RequestUtil.asMap(request, false);
            queryParams.remove("cmd");
            return bmsEsbService.doPost(cmd, queryParams);
        } catch (Exception e) {
            bean.setCode(ResponseCode.FAILED_500);
        }
        return bean;
    }

}
