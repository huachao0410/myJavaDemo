package cn.com.bmsoft.base.common.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 本地文件服务
 *
 */
public interface LocalFileService {

    String upload(MultipartFile multipartFile, String path) throws IllegalStateException, IOException;

    void download(HttpServletResponse response, String filePath, String fileType) throws IOException;

}
