package cn.com.bmsoft.base.common.web;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by Sam on 16/9/7.
 *
 */
@Deprecated
public class SystemLaunchClient {

	@Value("${attachRootPath}")
	private String attachRootPath;

	/**
	 * 提交附件接口
	 *
	 * @param url 服务地址
	 * @return
	 */
	public static String postFormFile(String url, MultipartFile file) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(url);
		try {
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.setBoundary("----------ThIs_Is_tHe_bouNdaRY_$");
			builder.setCharset(Charset.forName("utf-8"));
			String attach_rootpath = "";
			if (file != null) {
				if (StringUtils.isBlank(attach_rootpath)) {
					attach_rootpath = "D:/attach/";
				} else {
					attach_rootpath.replace("\\", "/");
				}
				//如果是windwos系统，调整路径格式为windwos风格
				if ("\\".equals(System.getProperty("file.separator"))) {
					attach_rootpath = attach_rootpath.replace("/", "\\\\");
				}

				try {
					String originalFilename = file.getOriginalFilename();
					String[] strings = originalFilename.split("\\.");
					String fileType = "txt";
					if (strings.length > 1) {
						fileType = strings[1];
					}
					// 转存文件
					String filePath = attach_rootpath + file.getName() + "." + fileType;
					File f = new File(filePath);
					if (!f.exists()) {
						f.mkdirs();
					}
					file.transferTo(f);
					builder.addPart(file.getName(), new FileBody(f));
				} catch (IOException e) {

				}
			}
			//Set to request bodys
			HttpEntity entity = builder.build();
			postRequest.setEntity(entity);
			postRequest.addHeader("Content-Type", "multipart/form-data; boundary=----------ThIs_Is_tHe_bouNdaRY_$");
			//Send request
			CloseableHttpResponse response = httpclient.execute(postRequest);
			String result = "";
			try {
				if (response != null && response.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
				}else {
					throw new RuntimeException("无法提交图片,错误码" + response.getStatusLine().getStatusCode());

				}
			} finally {
				if (response !=null) {
					response.close();
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException("无法提交图片：" + e.getMessage());
		}
	}


}
