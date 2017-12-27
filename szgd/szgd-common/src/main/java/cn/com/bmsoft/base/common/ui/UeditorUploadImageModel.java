package cn.com.bmsoft.base.common.ui;

import java.io.Serializable;

/**
 * 富文本编辑器中，上传图片的返回对象
 */
public class UeditorUploadImageModel implements Serializable {

    private static final long serialVersionUID = 1L;
    //
    private String state = "SUCCESS";//上传状态SUCCESS 一定要大写 FAILURE
    private String url = "";//上传地址
    private String title = "";//图片名称demo.jpg
    private String original = "";//图片名称demo.jpg

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }
}
