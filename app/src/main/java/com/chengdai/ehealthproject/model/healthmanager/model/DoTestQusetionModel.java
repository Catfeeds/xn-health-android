package com.chengdai.ehealthproject.model.healthmanager.model;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/24.
 */

public class DoTestQusetionModel {

    /**
     * code : WT20170617008391582
     * wjCode : WJ20170617008324690
     * type : 0
     * content : 题目1
     * weight : 0.2
     * orderNo : 1
     * optionsList : [{"code":"XX20170617306274018","wtCode":"WT20170617008391582","content":"白子画","score":5,"orderNo":1},{"code":"XX20170617306274039","wtCode":"WT20170617008391582","content":"杀阡陌","score":2,"orderNo":2}]
     */

    private String code;
    private String wjCode;
    private String type;
    private String content;
    private double weight;
    private int orderNo;
    private List<OptionsListBean> optionsList;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getWjCode() {
        return wjCode;
    }

    public void setWjCode(String wjCode) {
        this.wjCode = wjCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public List<OptionsListBean> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<OptionsListBean> optionsList) {
        this.optionsList = optionsList;
    }

    public static class OptionsListBean {
        /**
         * code : XX20170617306274018
         * wtCode : WT20170617008391582
         * content : 白子画
         * score : 5
         * orderNo : 1
         */

        private String code;
        private String wtCode;
        private String content;
        private int score;
        private int orderNo;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getWtCode() {
            return wtCode;
        }

        public void setWtCode(String wtCode) {
            this.wtCode = wtCode;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(int orderNo) {
            this.orderNo = orderNo;
        }
    }
}
