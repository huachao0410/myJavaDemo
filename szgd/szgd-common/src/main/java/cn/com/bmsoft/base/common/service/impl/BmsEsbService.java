package cn.com.bmsoft.base.common.service.impl;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.service.face.IBmsEsbService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 北明服务总线Rest服务实现
 */
@Service
public class BmsEsbService implements IBmsEsbService {

    private final static String TOKEN_SERVICE_URL = "http://10.194.186.4:3333/AuthorityService";
    private final static String SERVICE_URL = "http://10.194.186.4:3344/WGEsbService";
    private final static String APP_KEY="manager";
    private final static String APP_SECERT="123";

    private final static String charset = "utf-8";

    private String accessToken;// 缓存token
    private long accessTime; // 5分钟（300000毫秒）

    private synchronized String getAccessToken() {
        if (accessToken == null || (System.currentTimeMillis() - accessTime) / 1000 > 295) {
            accessToken();
        }

        return accessToken;
    }

    private void accessToken() {
        String params = "{\'appSecert\':\'" + APP_SECERT + "\',\'appKey\':\'" + APP_KEY + "\'}";
        String getResult = sendGet(TOKEN_SERVICE_URL, params);
        JSONObject objData = JSONObject.parseObject(getResult);
        if(objData != null && objData.getString("returnCode").equals("0001")) {
            accessToken = objData.getString("accecsToken");
            accessTime = System.currentTimeMillis();
        }
    }

    @Override
    public ResponseBean<Object> doGet(String cmd, Map<String, Object> params) throws Exception {
        ResponseBean<Object> bean = new ResponseBean<Object>();
        String getResult = sendGet(SERVICE_URL, params2String(cmd, params));
        putResult(getResult, bean);
        return bean;
    }

    @Override
    public ResponseBean<Object> doPost(String cmd, Map<String, Object> params) throws Exception {
        ResponseBean<Object> bean = new ResponseBean<Object>();
        String getResult = sendPost(params2String(cmd, params));
        putResult(getResult, bean);
        return bean;
    }

    /**
     * 参数转换为json字符串
     * @return
     */
    private String params2String(String cmd, Map<String, Object> queryParams) throws UnsupportedEncodingException {
        StringBuffer inputData = new StringBuffer();
        inputData.append("{");
        inputData.append("\'cmd\':\'" + cmd + "\'");
        inputData.append(",");
        inputData.append("\'accecsToken\':\'" + getAccessToken() + "\'");
        inputData.append(",");
        inputData.append("\'inputData\':{");
        inputData.append("\'params\':{");

        List<String> listKey = new ArrayList<String>();
        listKey.addAll(queryParams.keySet());
        for (int i = 0; i < listKey.size(); i++) {
            String key = listKey.get(i);
            inputData.append("\'" + key +"\':");
            inputData.append("\'" + queryParams.get(key) +"\'");
            if(i < listKey.size() - 1) {
                inputData.append(",");
            }
        }

        inputData.append("}");
        inputData.append("}");
        inputData.append("}");
        return inputData.toString();
    }

    private void putResult(String result, ResponseBean<Object> bean) {
        JSONObject objResult = JSONObject.parseObject(result);
        Object strData = objResult.get("data");
        JSONObject objData = JSONObject.parseObject(strData.toString());
        if(objData.size() > 0) {
            JSONArray arrayDatas = objData.getJSONArray("datas");// 有datas的情况
            for (int i = 0; i < arrayDatas.size(); i++) {
                try {
                    JSONObject dataObject = (JSONObject) arrayDatas.get(i);
                    if (dataObject.get("total") != null && dataObject.get("rows") != null) {// 政务服务返回的结果集
                        bean.setTotal(dataObject.getInteger("total"));
                        JSONArray rows = dataObject.getJSONArray("rows");
                        for (int j = 0; j < rows.size(); j++) {
                            bean.getDatas().add(rows.get(i));
                        }
                    }
                    if (dataObject.get("total") != null && dataObject.get("data") != null) {// 资源目录返回的结果集
                        bean.setTotal(dataObject.getInteger("total"));
                        JSONArray rows = dataObject.getJSONArray("data");
                        for (int j = 0; j < rows.size(); j++) {
                            bean.getDatas().add(rows.get(i));
                        }
                    } else {
                        bean.getDatas().add(arrayDatas.get(i));
                    }
                } catch (Exception e) {
                    bean.getDatas().add(arrayDatas.get(i));
                }
            }
        }
    }

    private static String sendGet(String url, String data) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("contentType", charset);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("data", data);

            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),charset));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {

            }
        }
        return result;
    }

    private static String sendPost(String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            URL realUrl = new URL(SERVICE_URL);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/json");
            //设置超时时间
            conn.setConnectTimeout(60);
            conn.setReadTimeout(60);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送 POST请求出现异常!"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){

            }
        }
        return result;
    }
}
