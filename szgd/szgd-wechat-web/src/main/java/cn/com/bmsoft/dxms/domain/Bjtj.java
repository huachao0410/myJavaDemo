package cn.com.bmsoft.dxms.domain;

import java.util.Date;

public class Bjtj {
    private String sGuid;

    private Date sCreationTime;

    private Date sLastUpdatedTime;

    private String operatetype;

    private String bm;

    private String ywl;

    private Date gxsj;

    public String getsGuid() {
        return sGuid;
    }

    public void setsGuid(String sGuid) {
        this.sGuid = sGuid == null ? null : sGuid.trim();
    }

    public Date getsCreationTime() {
        return sCreationTime;
    }

    public void setsCreationTime(Date sCreationTime) {
        this.sCreationTime = sCreationTime;
    }

    public Date getsLastUpdatedTime() {
        return sLastUpdatedTime;
    }

    public void setsLastUpdatedTime(Date sLastUpdatedTime) {
        this.sLastUpdatedTime = sLastUpdatedTime;
    }

    public String getOperatetype() {
        return operatetype;
    }

    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype == null ? null : operatetype.trim();
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm == null ? null : bm.trim();
    }

    public String getYwl() {
        return ywl;
    }

    public void setYwl(String ywl) {
        this.ywl = ywl == null ? null : ywl.trim();
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }
}