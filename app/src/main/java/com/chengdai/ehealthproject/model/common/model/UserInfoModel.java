package com.chengdai.ehealthproject.model.common.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 李先俊 on 2017/6/16.
 */

public class UserInfoModel implements Parcelable {


    /**
     * userId : 20150927203100933946755
     * mobile : 13958092437
     * nickname : 123456
     * loginPwdStrength : 1
     * userKind : 1001
     * userReferee :
     * idKind : 1
     * idNo : 330281198908118212
     * realName : 宓永宝
     * tradePwdStrength : 1
     * createDatetime : Sep 29, 2015 3:10:09 PM
     * status : 0
     * identityFlag : 1
     * tradepwdFlag : 1 //是否设置过支付密码
     * totalFansNum : 0
     * totalFollowNum : 0
     */

    private String userId;
    private String mobile;
    private String nickname;
    private String loginPwdStrength;
    private String userKind;
    private String userReferee;
    private String idKind;
    private String idNo;
    private String loginName;
    private String tradePwdStrength;
    private String createDatetime;
    private String status;
    private String identityFlag;
    private String tradepwdFlag;
    private String totalFansNum;
    private String totalFollowNum;


    private userExtBean userExt;

    public userExtBean getUserExt() {
        return userExt;
    }

    public void setUserExt(userExtBean userExt) {
        this.userExt = userExt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLoginPwdStrength() {
        return loginPwdStrength;
    }

    public void setLoginPwdStrength(String loginPwdStrength) {
        this.loginPwdStrength = loginPwdStrength;
    }

    public String getUserKind() {
        return userKind;
    }

    public void setUserKind(String userKind) {
        this.userKind = userKind;
    }

    public String getUserReferee() {
        return userReferee;
    }

    public void setUserReferee(String userReferee) {
        this.userReferee = userReferee;
    }

    public String getIdKind() {
        return idKind;
    }

    public void setIdKind(String idKind) {
        this.idKind = idKind;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getTradePwdStrength() {
        return tradePwdStrength;
    }

    public void setTradePwdStrength(String tradePwdStrength) {
        this.tradePwdStrength = tradePwdStrength;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentityFlag() {
        return identityFlag;
    }

    public void setIdentityFlag(String identityFlag) {
        this.identityFlag = identityFlag;
    }

    public String getTradepwdFlag() {
        return tradepwdFlag;
    }

    public void setTradepwdFlag(String tradepwdFlag) {
        this.tradepwdFlag = tradepwdFlag;
    }

    public String getTotalFansNum() {
        return totalFansNum;
    }

    public void setTotalFansNum(String totalFansNum) {
        this.totalFansNum = totalFansNum;
    }

    public String getTotalFollowNum() {
        return totalFollowNum;
    }

    public void setTotalFollowNum(String totalFollowNum) {
        this.totalFollowNum = totalFollowNum;
    }

    public static  class userExtBean implements Parcelable {
        /*    "userId":  "U2016091911281334310",
        "gender":  "1",
        "birthday":  "1990-01-01",
        "photo":  "www.baidu.com",
        "email":  "123@qq.com",
        "introduce":  "说明"*/

        private String userId;
        private String  gender;
        private String  birthday;
        private String  photo;
        private String  email;
        private String  introduce;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeString(this.gender);
            dest.writeString(this.birthday);
            dest.writeString(this.photo);
            dest.writeString(this.email);
            dest.writeString(this.introduce);
        }

        public userExtBean() {
        }

        protected userExtBean(Parcel in) {
            this.userId = in.readString();
            this.gender = in.readString();
            this.birthday = in.readString();
            this.photo = in.readString();
            this.email = in.readString();
            this.introduce = in.readString();
        }

        public static final Creator<userExtBean> CREATOR = new Creator<userExtBean>() {
            @Override
            public userExtBean createFromParcel(Parcel source) {
                return new userExtBean(source);
            }

            @Override
            public userExtBean[] newArray(int size) {
                return new userExtBean[size];
            }
        };
    }

    public UserInfoModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.mobile);
        dest.writeString(this.nickname);
        dest.writeString(this.loginPwdStrength);
        dest.writeString(this.userKind);
        dest.writeString(this.userReferee);
        dest.writeString(this.idKind);
        dest.writeString(this.idNo);
        dest.writeString(this.loginName);
        dest.writeString(this.tradePwdStrength);
        dest.writeString(this.createDatetime);
        dest.writeString(this.status);
        dest.writeString(this.identityFlag);
        dest.writeString(this.tradepwdFlag);
        dest.writeString(this.totalFansNum);
        dest.writeString(this.totalFollowNum);
        dest.writeParcelable(this.userExt, flags);
    }

    protected UserInfoModel(Parcel in) {
        this.userId = in.readString();
        this.mobile = in.readString();
        this.nickname = in.readString();
        this.loginPwdStrength = in.readString();
        this.userKind = in.readString();
        this.userReferee = in.readString();
        this.idKind = in.readString();
        this.idNo = in.readString();
        this.loginName = in.readString();
        this.tradePwdStrength = in.readString();
        this.createDatetime = in.readString();
        this.status = in.readString();
        this.identityFlag = in.readString();
        this.tradepwdFlag = in.readString();
        this.totalFansNum = in.readString();
        this.totalFollowNum = in.readString();
        this.userExt = in.readParcelable(userExtBean.class.getClassLoader());
    }

    public static final Creator<UserInfoModel> CREATOR = new Creator<UserInfoModel>() {
        @Override
        public UserInfoModel createFromParcel(Parcel source) {
            return new UserInfoModel(source);
        }

        @Override
        public UserInfoModel[] newArray(int size) {
            return new UserInfoModel[size];
        }
    };
}
