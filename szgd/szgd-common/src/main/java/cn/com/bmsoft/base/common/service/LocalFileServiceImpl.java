package cn.com.bmsoft.base.common.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件服务实现
 *
 */
@Service
public class LocalFileServiceImpl implements LocalFileService {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public String upload(MultipartFile multipartFile, String path) throws IllegalStateException, IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String[] strings = originalFilename.split("\\.");
        String fileName = DigestUtils.md5Hex(strings[0] + System.currentTimeMillis());//md5转码作为文件名
        String fileType = "txt";
        if(strings.length > 1) {
            fileType = strings[1];
        }
        // 转存文件
        String filePath = "/" + sdf.format(new Date()) + "/" + fileName + "." + fileType;
        if(!createFile(path + filePath)){
            throw new IOException("创建文件失败");
        }
        File file = new File(path + filePath);
        multipartFile.transferTo(file);
        return filePath;
    }

    private boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {//失败，目标文件已存在！
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

//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("UTF-8");

        if (fileType.equals("text")) {
            response.setContentType("text/plain;");
        } else if (fileType.equals("doc")) {
            response.setContentType("application/msword;");
        } else if (fileType.equals("xls")) {
            response.setContentType("application/ms-excel;");
        } else if (fileType.equals("pdf")) {
            response.setContentType("application/pdf;");
        } else if (fileType.equals("jpg") || fileType.equals("jpeg") || fileType.equals("png")) {
            response.setContentType("image/jpeg");
        } else {
            response.setContentType("application/x-msdownload;");
        }

        InputStream bis = null;
        BufferedOutputStream bos = null;
        File file = new File(filePath);
        long fileLength = file.length();
        try {
            bis = new BufferedInputStream(new FileInputStream(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        response.setHeader("Content-disposition", "attachment; filename=" + new String((file.getName() + "." + fileType).getBytes("GBK"), "ISO8859-1"));
        response.setHeader("Content-Length", "" + fileLength);
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }

        if (bis != null) {
            bis.close();
        }
        if (bos != null) {
            bos.flush();
            bos.close();
        }

    }
}
