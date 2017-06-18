package com.chengdai.ehealthproject.uitls;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.math.BigDecimal;
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

    /**
     * 切割获取广告图片
     * @param s
     * @return
     */
   public static List<String> splitBannerList(String s){
        return splitAsList(s,"\\|\\|");
    }

    public static String subString(String s, int start, int end) {

        try {
            if (s == null || s.length() <= 0 || end < start || end < 0 || start < 0) {

                return "";
            }
            return s.substring(start, end);
        } catch (Exception ex) {
            return "";
        }
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

    public static String showPrice(BigDecimal big){

        if(big !=null){
            return (big.doubleValue()/1000)+"";
        }
        return "0";
    }

    /**
     * 显示金钱乘规格
     * @param big
     * @param size
     * @return
     */
        public static String showPrice(BigDecimal big,int size){

        if(big !=null){
            BigDecimal bigDecimal=new BigDecimal(size);
            return (big.multiply(bigDecimal).doubleValue()/1000)+"";
        }

        return "0";

    }

    public static String getOrderState(String state){

        if(TextUtils.isEmpty(state)){
            return "";
        }

        if (state.equals(MyConfig.ORDERTYPEWAITPAY)) { // 待支付
            return"待支付";
        } else if (state.equals(MyConfig.ORDERTYPEWAITFAHUO)) { // 已支付
            return"待发货";
        } else if (state.equals(MyConfig.ORDERTYPEWAITSHOUHUO)) { // 已发货
            return"去收货";
        } else if (state.equals(MyConfig.ORDERTYPEYISHOUHUO)) { // 已收货
            return"已收货";
        } else if (state.equals(MyConfig.ORDERTYPECANCELSHOP)) { // 用户取消
            return"已取消";
        } else if (state.equals(MyConfig.ORDERTYPECANCELUSER)) { // 商户取消
            return"已取消";
        } else if (state.equals(MyConfig.ORDERTYPEERROR)) {
            return"快递异常";
        }
        return "";
    }

    /**
     * 检查是否时待支付状态
     * @param state
     * @return
     */
    public static boolean canDoPay(String state){

        return MyConfig.ORDERTYPEWAITPAY.equals(state);
    }

    public static String getShowPriceSign(BigDecimal bigDecimal){
        return "￥"+showPrice(bigDecimal);
    }

   public static String getShowPriceSign(BigDecimal bigDecimal,int size){
        return "￥"+showPrice(bigDecimal,size);
    }

}
