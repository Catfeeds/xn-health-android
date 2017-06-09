package com.chengdai.ehealthproject.uitls;

import com.alibaba.fastjson.JSON;

/**
 * Created by 李先俊 on 2017/6/9.
 */

public class StringUtils {


    public static String getJsonToString(Object object){

        if(object==null){
            return "";
        }

        String jsonString=JSON.toJSON(object).toString();

        LogUtil.BIGLOG("JSON  转换"+jsonString);

        return jsonString;
    }


}
