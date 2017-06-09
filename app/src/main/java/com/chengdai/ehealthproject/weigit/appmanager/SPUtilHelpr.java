package com.chengdai.ehealthproject.weigit.appmanager;


import android.text.TextUtils;

import com.chengdai.ehealthproject.base.BaseApplication;
import com.chengdai.ehealthproject.uitls.SPUtils;

/**
 * SPUtils 工具辅助类
 */

public class SPUtilHelpr {

	private static final String USERTOKEN="user_toke";
	private static final String USERID="user_id";

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
	return  SPUtils.getString(BaseApplication.getInstance(),USERID,"");
	}


	/**
	 * 判断用户是否登录
	 * @return
	 */
	public static boolean isLogin(){
		return TextUtils.isEmpty(getUserToken());
	}

}
