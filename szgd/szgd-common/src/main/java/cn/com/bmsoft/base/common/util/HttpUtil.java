package cn.com.bmsoft.base.common.util;


import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * http https 请求
 *
 */
public class HttpUtil {

    private static final Logger logger  = LoggerFactory.getLogger(HttpUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addQueryParameter(key, params.get(key));
            }
        }

        if (headers != null && !headers.isEmpty()) {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.addHeader(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        logger.debug("GET 返回值:" + StringUtil.encode(body));
        return body;
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: get 请求
     */
    public static String get(String url) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws UnsupportedEncodingException
     * @description 功能描述: get 请求
     */
    public static String get(String url, Map<String, String> params) throws KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException, IOException, ExecutionException, InterruptedException {
        return get(url, params, null);
    }

    /**
     * @return 返回类型:
     * @throws IOException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @description 功能描述: POST 请求
     */
    public static String post(String url, Map<String, String> params) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                builder.addParameter(key, params.get(key));
            }
        }
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        logger.debug("POST 返回值:" + StringUtil.encode(body));
        return body;
    }

    /**
     * 上传媒体文件
     *
     * @param url
     * @param file
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String url, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
        builder.setHeader("connection", "Keep-Alive");
        builder.setHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
        builder.setHeader("Charsert", "UTF-8");
        builder.setHeader("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
        builder.setBody(new UploadEntityWriter(end_data, file));

        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        logger.debug("UPLOAD 返回值:" + StringUtil.encode(body));
        return body;
    }

    public static String post(String url, String s) throws IOException, ExecutionException, InterruptedException {
        AsyncHttpClient http = new AsyncHttpClient();
        AsyncHttpClient.BoundRequestBuilder builder = http.preparePost(url);
        builder.setBodyEncoding(DEFAULT_CHARSET);
        builder.setBody(s);
        Future<Response> f = builder.execute();
        String body = f.get().getResponseBody(DEFAULT_CHARSET);
        http.close();
        logger.debug("POST 返回值:" + StringUtil.encode(body));
        return body;
    }

    public  static void main(String[] args) throws Exception {
        // String accessToken = "ulhEL9F2CciJezmGj47C-d3hAJZwXiAANctVIwSHwBRK7Z1enIRWeZKZekk8jS5abIkCo2YmMSDlqUFKOKvSaw";
        // String openId = "oeZTVt6XlCphRnCI-DlpdTyk27p4";
        // UserInfo u = WeChat.user.getUserInfo(accessToken, openId);
        // System.out.println(JSON.toJSONString(u));
        // System.out.println(WeChat.message.sendText(accessToken , openId , "测试"));
        // Map<String, Object> mgs = WeChat.uploadMedia(accessToken, "image", new File("C:\\Users\\郭华\\Pictures\\13.jpg"));
        // System.out.println(JSON.toJSONString(mgs));
    }
}