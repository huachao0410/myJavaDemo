package cn.com.bmsoft.editor.controllers;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.base.common.service.LocalFileService;
import cn.com.bmsoft.base.common.ui.TreeNodeModel;
import cn.com.bmsoft.base.common.ui.TreeNodesModel;
import cn.com.bmsoft.base.common.util.HttpUtil;
import cn.com.bmsoft.base.common.web.Upload;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 编辑器控制器
 */
@Controller
public class EditorController {

	@Value("${basepath}")
	private String basepath;

	@Value("${siteServiceUrl}")
	private String siteServiceUrl;

	private static Map<Integer, File> fileMap = new HashMap<Integer, File>();

	private static Integer fileId = 0;

	@Autowired
	private cn.com.bmsoft.base.common.service.LocalFileService localFileService;


	@ResponseBody
	@RequestMapping("/tree")
	public TreeNodesModel tree(HttpServletRequest request) throws IllegalAccessException {

		TreeNodesModel model = new TreeNodesModel();

		try {
			String siteId = request.getParameter("siteId");
			if (siteId != null) {
				String result = HttpUtil.get(siteServiceUrl + siteId);
				JSONObject obj = JSONObject.parseObject(result);
				JSONArray datas = obj.getJSONArray("datas");
				JSONObject site = (JSONObject) datas.get(0);
				Object sitePath = site.get("domainRedirect");
				addTreeNodeModels(model, sitePath.toString(), 0);
			} else {
				addTreeNodeModels(model, basepath, 0);
			}

			model.setTotal(model.getDatas().size());
		} catch (Exception e) {
			model.setCode(ResponseCode.FAILED_500);
		}

		return model;
	}

	/**
	 * 添加树节点
	 *
	 * @param model
	 * @param path
	 * @param parentId
	 */
	private void addTreeNodeModels(TreeNodesModel model, String path, Integer parentId) {
		File[] files = new File(path).listFiles();
		for (File file : files) {
			fileId++;
			TreeNodeModel node = new TreeNodeModel();
			node.setId(fileId);
			node.setpId(parentId);
			node.setName(file.getName());
			node.setT(file.getName());
			if (file.isDirectory()) {
				addTreeNodeModels(model, file.getPath(), node.getId());
			}
			model.addData(node);
			fileMap.put(node.getId(), file);
		}

	}

	/**
	 * 获取文件内容
	 *
	 * @param id
	 * @param request
	 * @return
	 * @throws IllegalAccessException
	 */
	@ResponseBody
	@RequestMapping("/file/{id}")
	public ResponseBean<String> loadFile(@PathVariable("id") int id, HttpServletRequest request) throws IllegalAccessException {

		ResponseBean<String> bean = new ResponseBean<String>();

		try {
			File file = fileMap.get(new Integer(id));

			if (file != null && file.isFile()) {
				/*
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"utf-8");//考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				StringBuffer sb = new StringBuffer();
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null){
					sb.append(lineTxt);
				}
				read.close();
				bean.setErrorMessage(sb.toString());
				*/
				String fileName = file.getName();        //获取文件名
				String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);         //获取文件名后缀
				String imgeArray[][] = {                                  // 声明图片后缀名数组
						{"bmp", "0"}, {"dib", "1"}, {"gif", "2"},
						{"jfif", "3"}, {"jpe", "4"}, {"jpeg", "5"},
						{"jpg", "6"}, {"png", "7"}, {"tif", "8"},
						{"tiff", "9"}, {"ico", "10"}
				};
				int isImage = 0;
				for (int i = 0; i < imgeArray.length; i++) {
					if (imgeArray[i][0].equals(suffix.toLowerCase())) {          //判断文件是否为图片
						BufferedImage image = ImageIO.read(file);
						String imageString = null;
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						try {
							ImageIO.write(image, suffix, bos);
							byte[] imageBytes = bos.toByteArray();
							BASE64Encoder encoder = new BASE64Encoder();
							imageString = encoder.encode(imageBytes);
							bos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						bean.getDatas().add(imageString);
						bean.getDatas().add("images");
					} else {
						isImage++;
					}
					if (isImage == 11) {
						String fileContent = FileUtils.readFileToString(file, "UTF-8");
						bean.getDatas().add(fileContent);
						bean.getDatas().add("file");
					}
				}
			} else {
				bean.setCode(ResponseCode.FAILED_404);
			}
		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}

		return bean;
	}

	@ResponseBody
	@RequestMapping(value = "/savefile/{id}", method = RequestMethod.POST)
	public ResponseBean saveFile(
			@PathVariable("id") int id,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "newFile") String newFile,
			HttpServletRequest request) throws IllegalAccessException {
			ResponseBean bean = new ResponseBean();

		try {
			File file = fileMap.get(new Integer(id));

			if(type=="file"){              //保存的文件为heml
				if (file != null && file.isFile()) {
					FileUtils.writeStringToFile(file, content, "UTF-8");
				} else {
					bean.setCode(ResponseCode.FAILED_404);
				}
			} else {                        //保存的文件为图片
				if(newFile=="true"){          //创建新文件保存
//					uploadFile.upload(content, file);
				}else{                      //覆盖原文件

				}
			}
		} catch (Exception e) {
			bean.setCode(ResponseCode.FAILED_500);
		}

		return bean;
	}


	public class uploadFile implements LocalFileService {

		private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		@Override
		public String upload(MultipartFile multipartFile, String path) throws IllegalStateException, IOException {
			String originalFilename = multipartFile.getOriginalFilename();
			String[] strings = originalFilename.split("\\.");
			String fileName = DigestUtils.md5Hex(strings[0] + System.currentTimeMillis());//md5转码作为文件名
			String fileType = "jpg";
			if (strings.length > 1) {
				fileType = strings[1];
			}
			// 转存文件
			String filePath = "/" + sdf.format(new Date()) + "/" + fileName + "." + fileType;
			if (!createFile(path + filePath)) {
				throw new IOException("创建文件失败");
			}
			File file = new File(path + filePath);
			multipartFile.transferTo(file);
			return filePath;
		}

		private boolean createFile(String destFileName) {
			File file = new File(destFileName);
			if(file.exists()) {//失败，目标文件已存在!
				return false;
			}
			if (destFileName.endsWith(File.separator)) {//失败，目标文件不能为目录！
				return false;
			}
			//判断目标文件所在的目录是否存在
			if(!file.getParentFile().exists()) {
				//如果目标文件所在的目录不存在，则创建父目录
				//目标文件所在目录不存在，准备创建它！
				if(!file.getParentFile().mkdirs()) {//创建目标文件所在目录失败！
					return false;
				}
			}
			//创建目标文件
			try {
				if (file.createNewFile()) {//成功！
					return true;
				} else { //失败！
					return false;
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		public void download(HttpServletResponse response, String filePath, String fileType) throws IOException {

		}
	}

}
