package cn.com.bmsoft.dxms.domain;

import java.util.Date;

public class Datashare {
    private String sGuid;

    private Date sCreationTime;

    private Date sLastUpdatedTime;

    private String operatetype;

    private String sjtgbm;

    private String xxlmc;

    private String sjl;

    private Integer xh;

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

    public String getSjtgbm() {
        return sjtgbm;
    }

    public void setSjtgbm(String sjtgbm) {
        this.sjtgbm = sjtgbm == null ? null : sjtgbm.trim();
    }

    public String getXxlmc() {
        return xxlmc;
    }

    public void setXxlmc(String xxlmc) {
        this.xxlmc = xxlmc == null ? null : xxlmc.trim();
    }

    public String getSjl() {
        return sjl;
    }

    public void setSjl(String sjl) {
        this.sjl = sjl == null ? null : sjl.trim();
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }
}