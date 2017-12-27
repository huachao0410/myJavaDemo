package cn.com.bmsoft.base.common.rest;

import cn.com.bmsoft.base.common.response.ResponseBean;
import cn.com.bmsoft.base.common.response.ResponseCode;
import cn.com.bmsoft.base.common.service.LocalFileService;
import cn.com.bmsoft.base.common.ui.UeditorUploadImageModel;
import cn.com.bmsoft.base.common.util.ControllerUtil;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 文件服务
 */
@RestService(name = "文件服务")
@Controller("fileRestService")
@RequestMapping("/rest/file")
public class FileRestService {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Value("${attachRootPath}")
    private String attachRootPath;

    @Autowired
    private LocalFileService localFileService;

    /**
     * 上传文件
     * @param request
     * @param response
     * @return
     */
    @RestMethod(name = "上传文件")
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseBean<String> upload(
            HttpServletRequest request,
            HttpServletResponse response) {
        ResponseBean<String> bean = new ResponseBean<String>();
        try {

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if(!isMultipart) {
                bean.setCode(ResponseCode.FAILED_404);
                return bean;
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

            if (fileMap == null || fileMap.isEmpty()) {
                bean.setCode(ResponseCode.FAILED_404);
                return bean;
            }

            Object[] filekeys = fileMap.keySet().toArray();
            MultipartFile multipartFile = fileMap.get(filekeys[0]);
            String filePath = this.localFileService.upload(multipartFile, attachRootPath);

            String url = ControllerUtil.getWebsiteUrl(request) + "/rest/file/download" + filePath;
            bean.getDatas().add(url);

        } catch (Exception e) {
            logger.error("上传文件失败。", e);
            bean.setCode(ResponseCode.FAILED_500);
        }
        return bean;
    }

    /**
     * 富文本编辑器上传图片服务（单张）
     * @param request
     * @param response
     * @return
     */
    @RestMethod(name = "富文本编辑器上传图片服务（单张）")
    @ResponseBody
    @RequestMapping(value = "/upload/ueditor/image", method = {RequestMethod.GET, RequestMethod.POST} )
    public UeditorUploadImageModel uploadUeditorImage(
            HttpServletRequest request,
            HttpServletResponse response) {
        UeditorUploadImageModel model = new UeditorUploadImageModel();
        try {

            boolean isMultipart = ServletFileUpload.isMultipartContent(request);

            if(!isMultipart) {
                model.setState("NOTFILE");//FAILURE
                return model;
            }

            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

            if (fileMap == null || fileMap.isEmpty()) {
                model.setState("NULLFILE");//FAILURE
                return model;
            }

            Object[] filekeys = fileMap.keySet().toArray();
            MultipartFile multipartFile = fileMap.get(filekeys[0]);
            String filePath = this.localFileService.upload(multipartFile, attachRootPath);
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            String url = ControllerUtil.getWebsiteUrl(request) + "/rest/file/download" + filePath;

            // 设置返回对象信息
            model.setTitle(fileName);
            model.setOriginal(fileName);
            model.setUrl(url);

        } catch (Exception e) {
            logger.error("上传文件失败。", e);
            model.setState("FAILURE");
        }
        return model;
    }

    /**
     * 富文本编辑器上传图片服务（多张）
     * @param request
     * @param response
     * @return
     */
    @RestMethod(name = "富文本编辑器上传图片服务（多张）")
    @ResponseBody
    @RequestMapping(value = "/upload/ueditor/images", method = {RequestMethod.GET, RequestMethod.POST} )
    public UeditorUploadImageModel uploadUeditorImages(
            MultipartHttpServletRequest request,
            HttpServletResponse response) {
        UeditorUploadImageModel model = new UeditorUploadImageModel();
        try {

            CommonsMultipartFile multipartFile = null;
            Iterator<String> itr =  request.getFileNames();
            while(itr.hasNext()){
                String str = itr.next();
                multipartFile = (CommonsMultipartFile)request.getFile(str);
                String originalFileName = multipartFile.getOriginalFilename();   //原文件名
                MultipartFile mpFile = request.getFile(str);

                String filePath = this.localFileService.upload(mpFile, attachRootPath);
                String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
                String url = ControllerUtil.getWebsiteUrl(request) + "/rest/file/download" + filePath;

                // 设置返回对象信息
                model.setTitle(fileName);
                model.setOriginal(originalFileName);
                model.setUrl(url);

            }

        } catch (Exception e) {
            logger.error("上传文件失败。", e);
            model.setState("FAILURE");
        }
        return model;
    }


    /**
     * 下载文件
     * @param date
     * @param file
     * @param request
     * @param response
     * @throws IOException
     */
    @RestMethod(name = "下载文件")
    @ResponseBody
    @RequestMapping(value = "/download/{date}/{file:[a-zA-Z0-9\\\\.]+}", method = RequestMethod.GET)
    public void download(
            @RestParam(name = "日期") @PathVariable("date") String date,
            @RestParam(name = "文件名") @PathVariable("file") String file,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        String[] strings = file.split("\\.");
        String fileName = strings[0];//md5转码作为文件名
        String fileType = "txt";
        if(strings.length > 1) {
            fileType = strings[1];
        }
        this.localFileService.download(response, attachRootPath + "/" + date + "/" + file, fileType);
    }


}
