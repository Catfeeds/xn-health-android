package com.chengdai.ehealthproject.model.healthmanager.model;

/**
 * Created by 李先俊 on 2017/6/23.
 */

public class AssistantMenuListModel {


    /**
     * id : 7
     * type : 1
     * parentKey : calorie_kind
     * dkey : A
     * dvalue : 谷薯芋、杂豆、主食
     * updater : admin
     * updateDatetime : Jun 15, 2017 11:52:15 AM
     * remark :
     * systemCode : CD-JKEG000011
     *//**/

    private int id;
    private String type;
    private String parentKey;
    private String dkey;
    private String dvalue;
    private String updater;
    private String updateDatetime;
    private String remark;
    private String systemCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getDkey() {
        return dkey;
    }

    public void setDkey(String dkey) {
        this.dkey = dkey;
    }

    public String getDvalue() {
        return dvalue;
    }

    public void setDvalue(String dvalue) {
        this.dvalue = dvalue;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
