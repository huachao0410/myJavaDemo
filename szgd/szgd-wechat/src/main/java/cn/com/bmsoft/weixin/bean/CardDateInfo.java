package cn.com.bmsoft.weixin.bean;

/**
 * 卡券对象(日期信息)
 * Created by daniel on 2017/5/16.
 */
public class CardDateInfo {

    private String type = "DATE_TYPE_FIX_TIME_RANGE";//类型
    private Long begin_timestamp;
    private Long end_timestamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getBegin_timestamp() {
        return begin_timestamp;
    }

    public void setBegin_timestamp(Long begin_timestamp) {
        this.begin_timestamp = begin_timestamp;
    }

    public Long getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(Long end_timestamp) {
        this.end_timestamp = end_timestamp;
    }
}
