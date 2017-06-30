package com.chengdai.ehealthproject.model.healthmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.chengdai.ehealthproject.uitls.StringUtils;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/24.
 */

public class TestPageListModel implements Parcelable {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"WJ20170617409563281","kind":"questionare_kind_2","type":"zhengzhuang","title":"生素D缺乏症的风险","advPic":"55eba2eaNd4796a43_1498182640643.jpg","summary":"维生素D是人体必需的营养素，是代谢调节因素之一。","content":"<p>温泉详情<\/p>","orderNo":1}]
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
         * code : WJ20170617409563281
         * kind : questionare_kind_2
         * type : zhengzhuang
         * title : 生素D缺乏症的风险
         * advPic : 55eba2eaNd4796a43_1498182640643.jpg
         * summary : 维生素D是人体必需的营养素，是代谢调节因素之一。
         * content : <p>温泉详情</p>
         * orderNo : 1
         */

        private String code;
        private String kind;
        private String type;
        private String title;
        private String advPic;
        private String summary;
        private String content;
        private int orderNo;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public String getAdvPic() {
            return advPic;
        }
        public String getSplitAdvPic() {

            List<String> s= StringUtils.splitBannerList(advPic);
            if(s .size()>1)
            {
                return s.get(0);
            }
            return advPic;
        }
        public void setAdvPic(String advPic) {
            this.advPic = advPic;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.kind);
            dest.writeString(this.type);
            dest.writeString(this.title);
            dest.writeString(this.advPic);
            dest.writeString(this.summary);
            dest.writeString(this.content);
            dest.writeInt(this.orderNo);
        }

        public ListBean() {
        }

        protected ListBean(Parcel in) {
            this.code = in.readString();
            this.kind = in.readString();
            this.type = in.readString();
            this.title = in.readString();
            this.advPic = in.readString();
            this.summary = in.readString();
            this.content = in.readString();
            this.orderNo = in.readInt();
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

    public TestPageListModel() {
    }

    protected TestPageListModel(Parcel in) {
        this.list = in.createTypedArrayList(ListBean.CREATOR);
    }

    public static final Parcelable.Creator<TestPageListModel> CREATOR = new Parcelable.Creator<TestPageListModel>() {
        @Override
        public TestPageListModel createFromParcel(Parcel source) {
            return new TestPageListModel(source);
        }

        @Override
        public TestPageListModel[] newArray(int size) {
            return new TestPageListModel[size];
        }
    };
}
