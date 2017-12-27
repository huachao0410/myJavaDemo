package cn.com.bmsoft.base.common.service.face;

import cn.com.bmsoft.base.common.response.ResponseBean;

import java.util.Map;

/**
 * 北明服务总线Rest服务接口
 */
public interface IBmsEsbService {

    ResponseBean<Object> doGet(String cmd, Map<String, Object> params) throws Exception;

    ResponseBean<Object> doPost(String cmd, Map<String, Object> params) throws Exception;

}
