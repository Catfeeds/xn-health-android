package com.chengdai.ehealthproject.model.healthmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/26.
 */

public class TaskNowModel implements Parcelable {

    /**
     * code : UT20170617705313727
     * userId : U201706161048120928135
     * hTaskCode : HT20170617702413558
     * txDatetime : 20:00
     * txWeek : 2
     * status : 1
     * zxNum : 0
     * healthTask : {"code":"HT20170617702413558","logo":"睡前减肥操@2x_1498459346224.png","name":"睡前减肥操","summary":"睡前进行10分钟消除一天疲劳","cjNum":1,"orderNo":2,"remark":""}
     * isZX : 0
     */

    private String code;
    private String userId;
    private String hTaskCode;
    private String txDatetime;
    private String txWeek;
    private String status;
    private int zxNum;
    private HealthTaskBean healthTask;
    private String isZX; //0未完成 1已完成

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHTaskCode() {
        return hTaskCode;
    }

    public void setHTaskCode(String hTaskCode) {
        this.hTaskCode = hTaskCode;
    }

    public String getTxDatetime() {
        return txDatetime;
    }

    public void setTxDatetime(String txDatetime) {
        this.txDatetime = txDatetime;
    }

    public String getTxWeek() {
        return txWeek;
    }

    public void setTxWeek(String txWeek) {
        this.txWeek = txWeek;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getZxNum() {
        return zxNum;
    }

    public void setZxNum(int zxNum) {
        this.zxNum = zxNum;
    }

    public HealthTaskBean getHealthTask() {
        return healthTask;
    }

    public void setHealthTask(HealthTaskBean healthTask) {
        this.healthTask = healthTask;
    }

    public String getIsZX() {
        return isZX;
    }

    public void setIsZX(String isZX) {
        this.isZX = isZX;
    }

    public static class HealthTaskBean implements Parcelable {
        /**
         * code : HT20170617702413558
         * logo : 睡前减肥操@2x_1498459346224.png
         * name : 睡前减肥操
         * summary : 睡前进行10分钟消除一天疲劳
         * cjNum : 1
         * orderNo : 2
         * remark :
         */

        private String code;
        private String logo;
        private String name;
        private String summary;
        private int cjNum;
        private int orderNo;
        private String remark;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getCjNum() {
            return cjNum;
        }

        public void setCjNum(int cjNum) {
            this.cjNum = cjNum;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.logo);
            dest.writeString(this.name);
            dest.writeString(this.summary);
            dest.writeInt(this.cjNum);
            dest.writeInt(this.orderNo);
            dest.writeString(this.remark);
        }

        public HealthTaskBean() {
        }

        protected HealthTaskBean(Parcel in) {
            this.code = in.readString();
            this.logo = in.readString();
            this.name = in.readString();
            this.summary = in.readString();
            this.cjNum = in.readInt();
            this.orderNo = in.readInt();
            this.remark = in.readString();
        }

        public static final Creator<HealthTaskBean> CREATOR = new Creator<HealthTaskBean>() {
            @Override
            public HealthTaskBean createFromParcel(Parcel source) {
                return new HealthTaskBean(source);
            }

            @Override
            public HealthTaskBean[] newArray(int size) {
                return new HealthTaskBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.userId);
        dest.writeString(this.hTaskCode);
        dest.writeString(this.txDatetime);
        dest.writeString(this.txWeek);
        dest.writeString(this.status);
        dest.writeInt(this.zxNum);
        dest.writeParcelable(this.healthTask, flags);
        dest.writeString(this.isZX);
    }

    public TaskNowModel() {
    }

    protected TaskNowModel(Parcel in) {
        this.code = in.readString();
        this.userId = in.readString();
        this.hTaskCode = in.readString();
        this.txDatetime = in.readString();
        this.txWeek = in.readString();
        this.status = in.readString();
        this.zxNum = in.readInt();
        this.healthTask = in.readParcelable(HealthTaskBean.class.getClassLoader());
        this.isZX = in.readString();
    }

    public static final Creator<TaskNowModel> CREATOR = new Creator<TaskNowModel>() {
        @Override
        public TaskNowModel createFromParcel(Parcel source) {
            return new TaskNowModel(source);
        }

        @Override
        public TaskNowModel[] newArray(int size) {
            return new TaskNowModel[size];
        }
    };
}
