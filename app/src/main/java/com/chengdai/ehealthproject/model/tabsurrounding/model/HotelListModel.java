package com.chengdai.ehealthproject.model.tabsurrounding.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 李先俊 on 2017/6/13.
 */

public class HotelListModel {


    /**
     * pageNO : 1
     * start : 0
     * pageSize : 10
     * totalCount : 1
     * totalPage : 1
     * list : [{"code":"FW2017061314370406755962","name":"*房间名称","category":"0","type":"FL2017061016211611994528","storeCode":"SJ2017061310584541386934","storeUser":"U201706131058452908025","slogan":"广告语广告语","advPic":"th (1)_1497335873450.jpg","pic":"th (2)_1497335878288.jpg","description":"商家描述*商家描述*商家描述","price":10000000,"status":"1","totalNum":10000,"remainNum":10000,"companyCode":"CD-JKEG000011","systemCode":"CD-JKEG000011"}]
     */

    private int pageNO;
    private int start;
    private int pageSize;
    private int totalCount;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNO() {
        return pageNO;
    }

    public void setPageNO(int pageNO) {
        this.pageNO = pageNO;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * code : FW2017061314370406755962
         * name : *房间名称
         * category : 0
         * type : FL2017061016211611994528
         * storeCode : SJ2017061310584541386934
         * storeUser : U201706131058452908025
         * slogan : 广告语广告语
         * advPic : th (1)_1497335873450.jpg
         * pic : th (2)_1497335878288.jpg
         * description : 商家描述*商家描述*商家描述
         * price : 10000000
         * status : 1
         * totalNum : 10000
         * remainNum : 10000
         * companyCode : CD-JKEG000011
         * systemCode : CD-JKEG000011
         */

        private String code;
        private String name;
        private String category;
        private String type;
        private String storeCode;
        private String storeUser;
        private String slogan;
        private String advPic;
        private String pic;
        private String description;
        private BigDecimal price;
        private String status;
        private int totalNum;
        private int remainNum;
        private String companyCode;
        private String systemCode;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStoreCode() {
            return storeCode;
        }

        public void setStoreCode(String storeCode) {
            this.storeCode = storeCode;
        }

        public String getStoreUser() {
            return storeUser;
        }

        public void setStoreUser(String storeUser) {
            this.storeUser = storeUser;
        }

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getAdvPic() {
            return advPic;
        }

        public void setAdvPic(String advPic) {
            this.advPic = advPic;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getRemainNum() {
            return remainNum;
        }

        public void setRemainNum(int remainNum) {
            this.remainNum = remainNum;
        }

        public String getCompanyCode() {
            return companyCode;
        }

        public void setCompanyCode(String companyCode) {
            this.companyCode = companyCode;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }
    }
}
