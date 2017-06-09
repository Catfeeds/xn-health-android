package com.chengdai.ehealthproject.model.api;


import com.chengdai.ehealthproject.model.user.model.PhoneCodeSendState;
import com.chengdai.ehealthproject.model.user.model.UserRegisterState;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public interface ApiServer {

    /**
     * 发送验证码
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<PhoneCodeSendState>> PhoneCodeSend(@Field("code") String code,@Field("json") String json);

    /**
     * //注册
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<UserRegisterState>> UserRegister( @Field("code") String code,@Field("json") String  json);
    /**
     * 登录
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<UserRegisterState>> UserLogin( @Field("code") String code,@Field("json") String  json);


}
