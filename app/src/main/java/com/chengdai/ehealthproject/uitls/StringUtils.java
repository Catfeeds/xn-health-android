package com.chengdai.ehealthproject.uitls;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static List<String> splitAsList(String s,String sp){

        List<String> strings=new ArrayList<>();

        if(!TextUtils.isEmpty(s)){
            strings= Arrays.asList( s.split(sp));
        }

        return strings;

    }


    //int前面补零
    public static String frontCompWithZoreString(String sourceDate,int formatLength)
    {
        try {
            Integer i=Integer.parseInt(sourceDate);
            String newString = String.format("%0" + formatLength + "d", sourceDate);
            return  newString;
        }catch (Exception e)
        {
            return sourceDate;
        }
    }

}
