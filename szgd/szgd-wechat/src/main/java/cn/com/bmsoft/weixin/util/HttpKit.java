package cn.com.bmsoft.weixin.util;


import cn.com.bmsoft.base.common.util.StringUtil;
import cn.com.bmsoft.weixin.bean.Attachment;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * https 请求 微信为https的请求
 *
 * @author L.cm
 * @date 2013-10-9 下午2:40:19
 */
public class HttpKit {

	private static final Logger logger = LoggerFactory.getLogger(HttpKit.class);

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

	/**
	 * 微信多媒体文件上传接口
	 * 获取永久media_id
	 *
	 * @param path
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String uploadMaterial(String path, File file) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		URL urlObj = new URL(path);
		//连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		String result = null;
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type",
				"multipart/form-data; boundary="
						+ BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + file.length() + "\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);

		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return result;
	}

	/**
	 * 下载资源
	 *
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static Attachment download(String url) throws ExecutionException, InterruptedException, IOException {
		Attachment att = new Attachment();
		AsyncHttpClient http = new AsyncHttpClient();
		AsyncHttpClient.BoundRequestBuilder builder = http.prepareGet(url);
		builder.setBodyEncoding(DEFAULT_CHARSET);
		Future<Response> f = builder.execute();
		if (f.get().getContentType().equalsIgnoreCase("text/plain")) {
			att.setError(f.get().getResponseBody(DEFAULT_CHARSET));
		} else {
			BufferedInputStream bis = new BufferedInputStream(f.get().getResponseBodyAsStream());
			String ds = f.get().getHeader("Content-disposition");
			String fullName = ds.substring(ds.indexOf("filename=\"") + 10, ds.length() - 1);
			String relName = fullName.substring(0, fullName.lastIndexOf("."));
			String suffix = fullName.substring(relName.length() + 1);

			att.setFullName(fullName);
			att.setFileName(relName);
			att.setSuffix(suffix);
			att.setContentLength(f.get().getHeader("Content-Length"));
			att.setContentType(f.get().getContentType());
			att.setFileStream(bis);
		}
		http.close();
		return att;
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

	public static void main(String[] args) throws Exception {
		// String accessToken = "ulhEL9F2CciJezmGj47C-d3hAJZwXiAANctVIwSHwBRK7Z1enIRWeZKZekk8jS5abIkCo2YmMSDlqUFKOKvSaw";
		// String openId = "oeZTVt6XlCphRnCI-DlpdTyk27p4";
		// UserInfo u = WeChat.user.getUserInfo(accessToken, openId);
		// System.out.println(JSON.toJSONString(u));
		// System.out.println(WeChat.message.sendText(accessToken , openId , "测试"));
		// Map<String, Object> mgs = WeChat.uploadMedia(accessToken, "image", new File("C:\\Users\\郭华\\Pictures\\13.jpg"));
		// System.out.println(JSON.toJSONString(mgs));
	}
}