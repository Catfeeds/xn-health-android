package com.chengdai.ehealthproject.weigit.appmanager;


import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengdai.ehealthproject.base.BaseApplication;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.uitls.OnLoginFailure;
import com.chengdai.ehealthproject.uitls.SPUtils;

/**
 * SPUtils 工具辅助类
 */

public class SPUtilHelpr {

	private static final String USERTOKEN="user_toke";
	private static final String USERID="user_id";
	private static final String LOCATIONINFO="location_info";
	private static final String ISFIRSTADDADDRESS="isfirstaddaddress";//是否第一次添加地址

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

/*
	*/
/**
	 * 设置用户token
	 * @param
	 *//*

	public static void changeIsFirstAddressState(boolean isfirst)
	{
		SPUtils.put(BaseApplication.getInstance(),ISFIRSTADDADDRESS,isfirst);
	}

*/
/**
	 * 设置用户token
	 * @param
	 *//*

	public static boolean isFirstAddressState()
	{
     	return  SPUtils.getBoolean(BaseApplication.getInstance(),ISFIRSTADDADDRESS,true);
	}
*/

	/**
	 * 设置用户token
	 * @param s
	 */
	public static void saveUserId(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),USERID,s);
	}
	/**
	 * 设置用户手机号码
	 * @param s
	 */
	public static void saveUserPhoneNum(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),"user_phone",s);
	}
	/**
	 * 获取用户手机号
	 */
	public static String getUserPhoneNum()
	{
	return 	SPUtils.getString(BaseApplication.getInstance(),"user_phone","");
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
	 * 获取选择定位信息
	 * @param
	 */
	public static LocationModel getResetLocationInfo()
	{
		LocationModel locationModel = new LocationModel();

		locationModel.setCityName(SPUtils.getString(BaseApplication.getInstance(),"LOCATIONINFRESET",""));

		return locationModel;

	}

	/**
	 * 保存选择定位信息
	 * @param s
	 */
	public static void saveRestLocationInfo(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),"LOCATIONINFRESET",s);
	}
	/**
	 * 保存流水账户信息
	 * @param s
	 */
	public static void saveAmountaccountNumber(String s)
	{
		SPUtils.put(BaseApplication.getInstance(),"mAmountaccountNumber",s);
	}
	/**
	 * 保存流水账户信息
	 * @param
	 */
	public static String getAmountaccountNumber()
	{
		return  SPUtils.getString(BaseApplication.getInstance(),"mAmountaccountNumber","");
	}


	/**
	 * 判断用户是否登录
	 * @return
	 */
	public static boolean isLogin(Context context){
		if(TextUtils.isEmpty(getUserToken())){
			OnLoginFailure.StartDoFailure(context,"请先登录");
			return false;
		}

		return true;
	}

	/**
	 * 判断用户是否登录
	 * @return
	 */
	public static boolean isLoginNoStart(){
		if(TextUtils.isEmpty(getUserToken())){

			return false;
		}

		return true;
	}

}
