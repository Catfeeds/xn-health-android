package com.chengdai.ehealthproject.model.healthmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/30.
 */

public class SexHealthInfoListModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"ZX20170617911185865","category":"FL2017061731054124210","kind":"2","type":"FL2017061731059115425","title":"111","content":"<p>111<\/p>","advPic":"下载_1498620009895.jpg","location":"1","orderNo":0,"status":"1","sumComment":0,"sumLike":0,"updater":"jkeg","updateDatetime":"Jun 30, 2017 9:25:22 PM","remark":"1"}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Parcelable {
        /**
         * code : ZX20170617911185865
         * category : FL2017061731054124210
         * kind : 2
         * type : FL2017061731059115425
         * title : 111
         * content : <p>111</p>
         * advPic : 下载_1498620009895.jpg
         * location : 1
         * orderNo : 0
         * status : 1
         * sumComment : 0
         * sumLike : 0
         * updater : jkeg
         * updateDatetime : Jun 30, 2017 9:25:22 PM
         * remark : 1
         */

        private String code;
        private String category;
        private String kind;
        private String type;
        private String title;
        private String content;
        private String advPic;
        private String location;
        private int orderNo;
        private String status;
        private int sumComment;
        private int sumLike;
        private String updater;
        private String updateDatetime;
        private String remark;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdvPic() {
            return advPic;
        }

        public String getSplitAdvPic() {

            List<String> s= StringUtils.splitAsList(advPic,"\\|\\|");
            if(s .size()>1)
            {
                return s.get(0);
            }

            return advPic;
        }

        public void setAdvPic(String advPic) {
            this.advPic = advPic;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getSumComment() {
            return sumComment;
        }

        public void setSumComment(int sumComment) {
            this.sumComment = sumComment;
        }

        public int getSumLike() {
            return sumLike;
        }

        public void setSumLike(int sumLike) {
            this.sumLike = sumLike;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.category);
            dest.writeString(this.kind);
            dest.writeString(this.type);
            dest.writeString(this.title);
            dest.writeString(this.content);
            dest.writeString(this.advPic);
            dest.writeString(this.location);
            dest.writeInt(this.orderNo);
            dest.writeString(this.status);
            dest.writeInt(this.sumComment);
            dest.writeInt(this.sumLike);
            dest.writeString(this.updater);
            dest.writeString(this.updateDatetime);
            dest.writeString(this.remark);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.category = in.readString();
            this.kind = in.readString();
            this.type = in.readString();
            this.title = in.readString();
            this.content = in.readString();
            this.advPic = in.readString();
            this.location = in.readString();
            this.orderNo = in.readInt();
            this.status = in.readString();
            this.sumComment = in.readInt();
            this.sumLike = in.readInt();
            this.updater = in.readString();
            this.updateDatetime = in.readString();
            this.remark = in.readString();
        }

        public static final Parcelable.Creator<ListBean> CREATOR = new Parcelable.Creator<ListBean>() {
            @Override
            public ListBean createFromParcel(Parcel source) {
                return new ListBean(source);
            }

            @Override
            public ListBean[] newArray(int size) {
                return new ListBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public SexHealthInfoListModel() {
    }

    protected SexHealthInfoListModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<SexHealthInfoListModel> CREATOR = new Parcelable.Creator<SexHealthInfoListModel>() {
        @Override
        public SexHealthInfoListModel createFromParcel(Parcel source) {
            return new SexHealthInfoListModel(source);
        }

        @Override
        public SexHealthInfoListModel[] newArray(int size) {
            return new SexHealthInfoListModel[size];
        }
    };
}
