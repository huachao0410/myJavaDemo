package cn.com.bmsoft.dxms.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * T_MYITEMS(我的事项) 表的映射
 */
public class Myitems implements Serializable {

    private static final long serialVersionUID = 1L;

    // 编号
    private Integer id;

    // 用户ID
    private String openid;

    // 事项名称
    private String itemsname;

    // 办理时间
    private Date bidtime;

    // 办理部门
    private String dept;

    // 状态
    private Boolean status;

    // 办理流水
    private String bidid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getItemsname() {
        return itemsname;
    }

    public void setItemsname(String itemsname) {
        this.itemsname = itemsname == null ? null : itemsname.trim();
    }

    public Date getBidtime() {
        return bidtime;
    }

    public void setBidtime(Date bidtime) {
        this.bidtime = bidtime;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getBidid() {
        return bidid;
    }

    public void setBidid(String bidid) {
        this.bidid = bidid == null ? null : bidid.trim();
    }
}