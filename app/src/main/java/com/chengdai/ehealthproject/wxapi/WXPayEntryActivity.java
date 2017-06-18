/*
package com.chengdai.ehealthproject.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yitu8.client.application.R;
import com.yitu8.client.application.activities.common.BaseActivity;
import com.yitu8.client.application.modles.pay.PaySucceedInfo;
import com.yitu8.client.application.others.NewPayUtil;
import com.yitu8.client.application.utils.LogUtil;
import com.yitu8.client.application.utils.RxBus;
import com.yitu8.client.application.utils.WXUtils;


*/
/**
 * Created by Administrator on 2016-06-01.
 *//*

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private IWXAPI api;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixinpay);
        api = WXAPIFactory.createWXAPI(WXPayEntryActivity.this, WXUtils.SpId, false);
        // 将该app注册到微信
        api.registerApp(WXUtils.SpId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        WXUtils.SpId=null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
        LogUtil.E("支付回调onReq___");
    }

    @Override
    public void onResp(BaseResp resp) {

        */
/*
0  展示成功页面
-1 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
-2 用户取消
*//*


        int code=resp.errCode;
        PaySucceedInfo paySucceedInfo=new PaySucceedInfo();

        paySucceedInfo.setCallType(NewPayUtil.WEIXINPAY);

        paySucceedInfo.setPayType(NewPayUtil.WXPAYTYPE);

        if(code==0)
        {   paySucceedInfo.setPaySucceed(true);
            RxBus.getDefault().post(paySucceedInfo);
        }else if(code==-1)
        {
            paySucceedInfo.setPaySucceed(false);
            RxBus.getDefault().post(paySucceedInfo);
        }

        finish();


    }



}
*/
