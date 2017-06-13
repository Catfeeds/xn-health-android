package com.chengdai.ehealthproject.uitls;

import com.alibaba.fastjson.JSON;

import java.text.DecimalFormat;

/**
 * Created by 李先俊 on 2017/6/9.
 */

public class StringUtils {


    public static String getJsonToString(Object object){

        if(object==null){
            return "";
        }

        String jsonString=JSON.toJSONString(object);

        LogUtil.BIGLOG("JSON 转换__:        "+jsonString);

        return jsonString;
    }

    public static String doubleFormatMoney(double money){
        DecimalFormat df = new DecimalFormat("#######0.000");
        String showMoney = df.format((money));
        return showMoney.substring(0,showMoney.length()-1);
    }

    public static Double doubleFormatMoney2(double money){
        DecimalFormat df = new DecimalFormat("#######0.000");
        String showMoney = df.format((money));
        return Double.valueOf(showMoney.substring(0,showMoney.length()-1));
    }


}
