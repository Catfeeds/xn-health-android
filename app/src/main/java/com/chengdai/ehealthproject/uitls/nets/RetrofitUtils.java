package com.chengdai.ehealthproject.uitls.nets;


import com.chengdai.ehealthproject.model.api.ApiServer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


import retrofit2.Retrofit;




/**
 * 目标：简单易用，高效，稳定
 * 服务器api
 * Created by Administrator on 2016/9/1.
 */
public class RetrofitUtils {

    public static RetrofitUtils retrofitUtils;

    private ApiServer apiServer;

    public RetrofitUtils() {
        apiServer = new Retrofit.Builder()
                .baseUrl(getBaseURL())
                .client(OkHttpUtils.getInstance())
                .addConverterFactory(RsaGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(ApiServer.class);
    }

    /**
     * 服务接口单例
     *
     * @return Retrofit
     */
    public static RetrofitUtils bulid() {
        if (retrofitUtils == null) {
            retrofitUtils = new RetrofitUtils();
        }
        return retrofitUtils;
    }


    public ApiServer getApiServer() {
        return apiServer;
    }

    public static ApiServer getLoaderServer() {
        return bulid().getApiServer();
    }

    /**
     * 获取URL  根据版本切换不同版本
     *
     * @return
     */
    public static String getBaseURL() {
        return "http://devapi2017.yitu8.cn/driver/";//正式版服务器的url
    }

}
