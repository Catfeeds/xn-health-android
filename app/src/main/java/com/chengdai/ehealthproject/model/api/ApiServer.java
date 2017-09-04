package com.chengdai.ehealthproject.model.api;


import com.chengdai.ehealthproject.model.common.model.AmountModel;
import com.chengdai.ehealthproject.model.common.model.CodeModel;
import com.chengdai.ehealthproject.model.common.model.IntroductionInfoModel;
import com.chengdai.ehealthproject.model.common.model.IsSuccessModes;
import com.chengdai.ehealthproject.model.common.model.UpdateModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.common.model.pay.AliPayRequestMode;
import com.chengdai.ehealthproject.model.common.model.pay.WxPayRequestModel;
import com.chengdai.ehealthproject.model.common.model.qiniu.QiniuGetTokenModel;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleDetailsModel;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleModel;
import com.chengdai.ehealthproject.model.healthcircle.models.PinglunListModel;
import com.chengdai.ehealthproject.model.healthmanager.adapters.FoodHotListData;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthTaskListMdoel;
import com.chengdai.ehealthproject.model.healthmanager.model.AssistantMenuListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.DoTestQusetionModel;
import com.chengdai.ehealthproject.model.healthmanager.model.HealthInfoListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.HealthInfoModel;
import com.chengdai.ehealthproject.model.healthmanager.model.JfGuideListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.SexHealthInfoListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.SexMenuListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.TaskNowModel;
import com.chengdai.ehealthproject.model.healthmanager.model.TestDoneModel;
import com.chengdai.ehealthproject.model.healthmanager.model.TestPageListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.TestScoreModel;
import com.chengdai.ehealthproject.model.healthmanager.model.WeatherCityModel;
import com.chengdai.ehealthproject.model.healthmanager.model.WeatherModel;
import com.chengdai.ehealthproject.model.healthstore.models.JfPicModel;
import com.chengdai.ehealthproject.model.healthstore.models.PayCarListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopEvaluateModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel2;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderModel;
import com.chengdai.ehealthproject.model.healthstore.models.getOrderAddressModel;
import com.chengdai.ehealthproject.model.tabmy.model.BankModel;
import com.chengdai.ehealthproject.model.tabmy.model.HotelOrderRecordModel;
import com.chengdai.ehealthproject.model.tabmy.model.JfDetailsListModel;
import com.chengdai.ehealthproject.model.tabmy.model.MyBankCardListMode;
import com.chengdai.ehealthproject.model.tabmy.model.MyTaskListModel;
import com.chengdai.ehealthproject.model.tabmy.model.MyTestHistoryListModel;
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
     * 周边余额支付
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<String>> SurroundingPay(@Field("code") String code, @Field("json") String  json);

   /**
     * 周边支付宝支付
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<AliPayRequestMode>> SurroundingAliPay(@Field("code") String code, @Field("json") String  json);

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
     * 获取商城列表 (分页)
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ShopListModel>> GetShopList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取商城列表 （不分页）
     * @param code
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<ShopListModel2>> GetShopList2(@Field("code") String code, @Field("json") String  json);


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
    Observable<BaseResponseModel<Boolean>> SetDefultAddress(@Field("code") String code, @Field("json") String  json);

    /**
     * 添加收货地址
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> AddAddress(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城订单下单
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<String>> ShopOrderCerate(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城支付 (余额)
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> ShopOrderPay(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城支付(支付宝)
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<AliPayRequestMode>> ShopOrderAliPay(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城订单列表
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ShopOrderModel>> ShopOrderList(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城订单详情
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ShopOrderModel>> ShopOrderDetails(@Field("code") String code, @Field("json") String  json);

    /**
     * 商城订取消
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> ShopOrderCancel(@Field("code") String code, @Field("json") String  json);

    /**
     * 收货地址删除
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> AddressDelete(@Field("code") String code, @Field("json") String  json);

    /**
     * 编辑收货地址
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> editAddress(@Field("code") String code, @Field("json") String  json);

    /**
     * 添加到购物车
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> ShopAddPayCar(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取购物车列表数据
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<PayCarListModel>> GetPayCarList(@Field("code") String code, @Field("json") String  json);

    /**
     * 删除购物车列表数据
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> DeletePayCar(@Field("code") String code, @Field("json") String  json);

    /**
     * 积分商城图片
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<JfPicModel>> GetJfPic(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取商品评价
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<ShopEvaluateModel>> GgetEvaluate(@Field("code") String code, @Field("json") String  json);
    /**
     * 获取商品评价
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<QiniuGetTokenModel>> GetQiniuTOken(@Field("code") String code, @Field("json") String  json);

    /**
     * 发布帖子
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> SendArticleInfo(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取帖子列表数据
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ArticleModel>> GetArticleLisData(@Field("code") String code, @Field("json") String  json);
    /**
     * 获取评论列表数据
     * @param code
     * @param json
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<PinglunListModel>> GetPinglunLisData(@Field("code") String code, @Field("json") String  json);
    /**
     * 获取行政区域编码
     * @return
             */
    @FormUrlEncoded
    @POST("http://restapi.amap.com/v3/config/district")
    Observable<WeatherCityModel> getCityCode(@Field("keywords") String keywords, @Field("key") String  key, @Field("subdistrict") String  subdistrict);

    /**
     * 根据区域码获取天气
     * @return
             */
    /*http://restapi.amap.com/v3/weather/weatherInfo?city=330100&key=ad51a85d0046c4e083a9f263ae96868e*/
    @FormUrlEncoded
    @POST("http://restapi.amap.com/v3/weather/weatherInfo")
    Observable<WeatherModel> getCityWeather(@Field("city") String city, @Field("key") String  key, @Field("extensions") String  extensions);

    /**
     * 资讯文章列表
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<HealthInfoModel>> getHealthInfoList(@Field("code") String code, @Field("json") String  json);
    /**
     * 形体资讯文章列表
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<SexHealthInfoListModel>> getSexHealthInfoList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取食物分类
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<AssistantMenuListModel>> getHealthAssistantMenu(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取食物热量数据
     * @return
             */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<FoodHotListData>> getFoodHotData(@Field("code") String code, @Field("json") String  json);


    /**
     * 获取美容菜单
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<SexMenuListModel>> getBeautyMenu(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康自测菜单
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<AssistantMenuListModel>> getTestMenu(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康自测题目列表
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<TestPageListModel>> getTestPageList(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康自测题目列表(做题)
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<DoTestQusetionModel>> getDoTestList(@Field("code") String code, @Field("json") String  json);
    /**
     * 获取健康自测结果
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<TestDoneModel>> getDoneTestData(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康积分结果
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<TestScoreModel>> getTestScoreData(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<HealthTaskListMdoel>> getTaskList(@Field("code") String code, @Field("json") String  json);
    /**
     * 添加健康任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> addTask(@Field("code") String code, @Field("json") String  json);
    /**
     * 更新健康任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> updateHealthTask(@Field("code") String code, @Field("json") String  json);

    /**
     * 删除健康任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> removeHealthTask(@Field("code") String code, @Field("json") String  json);


    /**
     * 获取今日健康任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<TaskNowModel>> getNowHealthTask(@Field("code") String code, @Field("json") String  json);


    /**
     * 确认执行任务
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> sureDoThealthTask(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取健康积分结果
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<MyTaskListModel>> myTaskList(@Field("code") String code, @Field("json") String  json);

    /**
     * 更新用户头像
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> updateUserLogo(@Field("code") String code, @Field("json") String  json);


    /**
     * 自测历史
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<MyTestHistoryListModel>> getTestHistoryList(@Field("code") String code, @Field("json") String  json);
  /**
     * 积分流水
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<JfDetailsListModel>> getJFDetailsList(@Field("code") String code, @Field("json") String  json);

  /**
     * 删除帖子
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> deleteTiezi(@Field("code") String code, @Field("json") String  json);

  /**
     * 获取积分引导页面数据
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<JfGuideListModel>> getJfGuideList(@Field("code") String code, @Field("json") String  json);

    /**
     * 根据ckey查询系统参数
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IntroductionInfoModel>> getInfoByKey(@Field("code") String code, @Field("json") String  json);


    /**
     * 根据ckey查询系统参数
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<UpdateModel>> getInfoByUpdate(@Field("code") String code, @Field("json") String  json);

    /**
     * 确认收货
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> confirmGetOrder(@Field("code") String code, @Field("json") String  json);
    /**
     * 更换手机号
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> updatePhone(@Field("code") String code, @Field("json") String  json);

    /**
     * 更换手机号
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<MyBankCardListMode>> getCardListData(@Field("code") String code, @Field("json") String  json);

    /**
     * 获取银行卡类型
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseListModel<BankModel>> getBackModel(@Field("code") String code, @Field("json") String  json);

    /**
     * 绑定银行卡
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<String>> bindBankCard(@Field("code") String code, @Field("json") String  json);
    /**
     * 绑定银行卡
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IsSuccessModes>> updateBankCard(@Field("code") String code, @Field("json") String  json);

    /**
     * 充值（支付宝）
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<AliPayRequestMode>> rechargeRequest(@Field("code") String code, @Field("json") String  json);

    /**
     * 微信支付
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<WxPayRequestModel>> wxPayRequest(@Field("code") String code, @Field("json") String  json);
    /**
     * 提现
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<String>> withdrawalRequest(@Field("code") String code, @Field("json") String  json);
    /**
     * 获取帖子数据详情
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<ArticleDetailsModel>> getTieziDetails(@Field("code") String code, @Field("json") String  json);

   /**
     * 线下充值
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<CodeModel>> rechargeIn(@Field("code") String code, @Field("json") String  json);


    /**
     * 获取线下充值提醒
     * @return
     */
    @FormUrlEncoded
    @POST("api")
    Observable<BaseResponseModel<IntroductionInfoModel>> getRechargeNum(@Field("code") String code, @Field("json") String  json);




}
