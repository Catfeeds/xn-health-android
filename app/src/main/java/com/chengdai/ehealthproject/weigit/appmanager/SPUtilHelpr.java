package com.chengdai.ehealthproject.weigit.appmanager;


import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengdai.ehealthproject.base.BaseApplication;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.uitls.SPUtils;
import com.chengdai.ehealthproject.uitls.nets.OnOkFailure;

/**
 * SPUtils 工具辅助类
 */

public class SPUtilHelpr {

	private static final String USERTOKEN="user_toke";
	private static final String USERID="user_id";
	private static final String LOCATIONINFO="location_info";

	/**
	 * 设置用户token
	 * @param s
	 */
	public static void saveUserToken(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),USERTOKEN,s);
	}

/**
	 * 设置用户token
	 * @param
	 */
	public static String getUserToken()
	{
	return  SPUtils.getString(BaseApplication.getInstance(),USERTOKEN,"");
	}

	/**
	 * 设置用户token
	 * @param s
	 */
	public static void saveUserId(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),USERID,s);
	}


	/**
	 * 设置用户token
	 * @param
	 */
	public static String getUserId()
	{
	  return SPUtils.getString(BaseApplication.getInstance(),USERID,"");

	}
	/**
	 * 保存定位信息
	 * @param s
	 */
	public static void saveLocationInfo(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),LOCATIONINFO,s);
	}
	/**
	 * 获取定位信息
	 * @param
	 */
	public static LocationModel getLocationInfo()
	{
		LocationModel locationModel = null;

		try {
			locationModel =  JSON.parseObject(	SPUtils.getString(BaseApplication.getInstance(),LOCATIONINFO,""),LocationModel.class);
		}catch (Exception e){

		}

		return locationModel;

	}


	/**
	 * 判断用户是否登录
	 * @return
	 */
	public static boolean isLogin(Context context){
		if(TextUtils.isEmpty(getUserToken())){
			OnOkFailure.StartDoFailure(context,"请先登录");
			return false;
		}

		return true;
	}

}
