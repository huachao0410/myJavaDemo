package cn.com.bmsoft.base.common.response;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用返回对象
 */
public class ResponseBean<T> {

    private String code;

    private String errorMessage;

    private String status;

    private int total = 0;

    private List<T> datas = new ArrayList<T>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResponseBean() {
        setCode(ResponseCode.SUCCEED);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setCode (ResponseCode responseCode) {
        this.setStatus(responseCode.getStatus());
        this.setCode(responseCode.getCode());
        this.setErrorMessage(responseCode.getMessage());
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
