package com.chengdai.ehealthproject.model.api;


import com.chengdai.ehealthproject.model.common.model.AmountModel;
import com.chengdai.ehealthproject.model.common.model.CodeModel;
import com.chengdai.ehealthproject.model.common.model.IsSuccessModes;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.model.tabmy.model.HotelOrderRecordModel;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.BannerModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreDetailsModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
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
    /**
     * 获取banner
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<BannerModel>> GetBanner(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取banner
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<StoreTypeModel>> GetStoreType(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取商户列表
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<StoreListModel>> GetStoreList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取商户详情
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<StoreDetailsModel>> GetStoreDetails(@Field("code") String code, @Field("json") String  json);

    /**
     * 支付
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<String>> Pay(@Field("code") String code, @Field("json") String  json);

    /**
     * 点赞请求
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> DZRequest(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取酒店list数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<HotelListModel>> GetHotelList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取酒店list数据
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> HotelOrderCreate(@Field("code") String code, @Field("json") String  json);

 /**
     * 酒店订单支付
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> HotelOrderPay(@Field("code") String code, @Field("json") String  json);

/**
     * 酒店订单记录
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<OrderRecordModel>> OrderRecordRequest(@Field("code") String code, @Field("json") String  json);
/**
     * 酒店订单记录
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<HotelOrderRecordModel>> HotelOrderRecordRequest(@Field("code") String code, @Field("json") String  json);

/**
     * 获取账户余额
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<AmountModel>> getAmount(@Field("code") String code, @Field("json") String  json);

/**
     * 获取账户余额
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<UserInfoModel>> GetUserInfo(@Field("code") String code, @Field("json") String  json);

/**
     * 找回密码
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> FindPassWord(@Field("code") String code, @Field("json") String  json);

/**
     * 获取商城列表
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ShopListModel>> GetShopList(@Field("code") String code, @Field("json") String  json);


/**
     * 获取商城列表
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<getOrderAddressModel>> GetAddress(@Field("code") String code, @Field("json") String  json);

/**
     * 设置默认地址
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> SetDefultAddress(@Field("code") String code, @Field("json") String  json);

    /**
     * 添加
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> AddAddress(@Field("code") String code, @Field("json") String  json);
}
