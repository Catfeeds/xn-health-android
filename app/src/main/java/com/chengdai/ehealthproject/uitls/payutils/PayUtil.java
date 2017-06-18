package com.chengdai.ehealthproject.uitls.payutils;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.chengdai.ehealthproject.model.common.model.pay.PayResult;
import com.chengdai.ehealthproject.model.common.model.pay.PaySucceedInfo;
import com.chengdai.ehealthproject.weigit.dialog.CommonDialog;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/4/13.
 */

public class PayUtil {

    //支付工具类型
    public static final int ALIPAY = 1;         //支付宝
    public static final int WEIXINPAY = 2;      //微信支付
    //支付类型

    public static int WXPAYTYPE = -1; //调用微信支付类型

    /**
     * 调用微信支付
     */
/*    public static void callWXPay(Context mContext, WXPayInfoModel model, int PayType) {

        if (mContext == null || model==null) {
            CommonDialog commonDialog = new CommonDialog(mContext).builder()
                    .setTitle("提示").setContentMsg("微信支付出现未知错误")
                    .setNegativeBtn("确定", null);

            commonDialog.show();
            return;
        }

        NewPayUtil.WXPAYTYPE = PayType;

        WXUtils.SpId=model.getAppid();

        IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);

        if (!api.isWXAppInstalled()) {
            CommonDialog commonDialog = new CommonDialog(mContext).builder()
                    .setTitle("提示").setContentMsg("亲，您要先安装微信才能使用微信支付哦！")
                    .setNegativeBtn("确定", null);

            commonDialog.show();
            return;

        } else if (!api.isWXAppSupportAPI()) {
            CommonDialog commonDialog = new CommonDialog(mContext).builder()
                    .setTitle("提示").setContentMsg("亲，您的微信版本过低，请先更新微信！")
                    .setNegativeBtn("确定", null);

            commonDialog.show();
            return;
        }

        // 将该app注册到微信
        api.registerApp(model.getAppid());

        PayReq request = new PayReq();

        Map<String, String> map = new HashMap<>();

        request.appId = model.getAppid();  //应用ID

        request.partnerId = model.getPartnerid();  //商户号

        request.prepayId = model.getPrepayid();     //预支付ID

        request.packageValue = model.getPackage_(); //扩展字段

        request.nonceStr = model.getNoncestr();      //随机字符串

        request.timeStamp = model.getTimestamp();    //签名

        map.put("appid", model.getAppid());
        map.put("partnerid",model.getPartnerid() );
        map.put("prepayid",model.getPrepayid() );
        map.put("package", model.getPackage_());
        map.put("noncestr",model.getNoncestr() );
        map.put("timestamp", model.getTimestamp());

        request.sign = WXUtils.getSing(WXUtils.formatQueryParaMap(map, false));
        api.sendReq(request);
    }*/

 //支付宝支付
    public  static void callAlipay(final Activity mContext,String payInfo,String callTag) {

        if (mContext == null || TextUtils.isEmpty(payInfo)) {
            CommonDialog commonDialog = new CommonDialog(mContext).builder()
                    .setTitle("提示").setContentMsg("支付宝调用失败")
                    .setNegativeBtn("确定",null);

            commonDialog.show();
        }

        Observable.just(payInfo).map(v -> {
            // 构造PayTask 对象
            PayTask alipay = new PayTask(mContext);
            // 调用支付接口，获取支付结果
            return alipay.pay(payInfo, true);

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    PayResult payResult = new PayResult(result);

                    String resultStatus = payResult.getResultStatus();

                    PaySucceedInfo aLiPayModel=new PaySucceedInfo();
                    aLiPayModel.setCallType(ALIPAY);
                    aLiPayModel.setTag(callTag);
                    //支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        aLiPayModel.setPaySucceed(true);
                        EventBus.getDefault().post(aLiPayModel);

                    } else {
                        //支付失败
                        aLiPayModel.setPaySucceed(false);
                        EventBus.getDefault().post(aLiPayModel);

                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//                            ToastUtils.showToast(mContext, "支付结果确认中");
                        } else {
//                            ToastUtils.showToast(mContext, "未支付");
                        }
                    }
                });
    }


}
