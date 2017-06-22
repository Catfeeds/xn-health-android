package com.chengdai.ehealthproject.weigit.appmanager;

import android.content.Context;

import com.chengdai.ehealthproject.uitls.qiniu.QiNiuUtil;

/**第三方管理
 * Created by 李先俊 on 2017/6/20.
 */

public class AppOhterManager {

    public static  void  getQiniuURL(Context cotext, String data, QiNiuUtil.QiNiuCallBack callBack){
        new QiNiuUtil(cotext).getQiniuURL(callBack,data);
    }
}
