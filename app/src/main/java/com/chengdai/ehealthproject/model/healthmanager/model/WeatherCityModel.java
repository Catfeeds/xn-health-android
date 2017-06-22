package com.chengdai.ehealthproject.model.healthmanager.model;

import java.util.List;

/**用于获取城市编码
 * Created by 李先俊 on 2017/6/22.
 */

public class WeatherCityModel {


    /**
     * status : 1
     * info : OK
     * infocode : 10000
     * count : 1
     * suggestion : {"keywords":[],"cities":[]}
     * districts : [{"citycode":"0571","adcode":"330100","name":"杭州市","center":"120.153576,30.287459","level":"city","districts":[]}]
     */

    private String status;
    private String info;
    private String infocode;
    private String count;
    private SuggestionBean suggestion;
    private List<DistrictsBean> districts;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public SuggestionBean getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionBean suggestion) {
        this.suggestion = suggestion;
    }

    public List<DistrictsBean> getDistricts() {
        return districts;
    }

    public void setDistricts(List<DistrictsBean> districts) {
        this.districts = districts;
    }

    public static class SuggestionBean {
        private List<?> keywords;
        private List<?> cities;

        public List<?> getKeywords() {
            return keywords;
        }

        public void setKeywords(List<?> keywords) {
            this.keywords = keywords;
        }

        public List<?> getCities() {
            return cities;
        }

        public void setCities(List<?> cities) {
            this.cities = cities;
        }
    }

    public static class DistrictsBean {
        /**
         * citycode : 0571
         * adcode : 330100
         * name : 杭州市
         * center : 120.153576,30.287459
         * level : city
         * districts : []
         */

        private String citycode;
        private String adcode;
        private String name;
        private String center;
        private String level;
        private List<?> districts;

        public String getCitycode() {
            return citycode;
        }

        public void setCitycode(String citycode) {
            this.citycode = citycode;
        }

        public String getAdcode() {
            return adcode;
        }

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCenter() {
            return center;
        }

        public void setCenter(String center) {
            this.center = center;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public List<?> getDistricts() {
            return districts;
        }

        public void setDistricts(List<?> districts) {
            this.districts = districts;
        }
    }
}
