package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPayBinding;
import com.chengdai.ehealthproject.databinding.ActivityShopPayConfirmBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.pay.PaySucceedInfo;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.other.MainActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.payutils.PayUtil;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.chengdai.ehealthproject.uitls.StringUtils.doubleFormatMoney2;

/**
 * Created by 李先俊 on 2017/6/12.
 */

public class ShopPayConfirmActivity extends AbsBaseActivity {

    private ActivityShopPayConfirmBinding mBinding;

    private String  mStoreCode;

    private int mPayType=1;

    private boolean isStartMain=true;

    private  ShopListModel.ListBean.ProductSpecsListBean mData;

    private  static final String CALLPAYTAG="ShopPayConfirmAcitivty";



    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,ShopListModel.ListBean.ProductSpecsListBean data,String orderCode,boolean isStartMain){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopPayConfirmActivity.class);
        intent.putExtra("orderCode",orderCode);
        intent.putExtra("data",data);
        intent.putExtra("isStartMain",isStartMain);//是否打开主页
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_pay_confirm, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay));

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mStoreCode=getIntent().getStringExtra("orderCode");
            mData=getIntent().getParcelableExtra("data");
            isStartMain=getIntent().getBooleanExtra("isStartMain",true);
        }

        if(mData!=null){
            mBinding.tvPrice.setText(StringUtils.getShowPriceSign(mData.getPrice1(),mData.getmBuyNum()));
            mBinding.txtDiscountMoney.setText(StringUtils.getShowPriceSign(mData.getPrice1(),mData.getmBuyNum()));
        }

        initViews();

        initPayTypeSelectState();

    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {

        ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.pay_select,mBinding.imgBalace);
        ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
        ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);

        mBinding.linBalace.setOnClickListener(v -> {
            mPayType=1;
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.pay_select,mBinding.imgBalace);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);
        });
     mBinding.linWeipay.setOnClickListener(v -> {
            mPayType=2;
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgBalace);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.pay_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);
        });

     mBinding.linZhifubao.setOnClickListener(v -> {
           mPayType=3;
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgBalace);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(ShopPayConfirmActivity.this,R.mipmap.pay_select,mBinding.imgZhifubao);
        });


    }



    private void initViews() {

        mBinding.txtPay.setOnClickListener(v -> {
            if(SPUtilHelpr.isLogin(this)){
              payRequest(mPayType);
            }
        });

    }

    private void payRequest(int PayType) {
/*// 用户编号（必填）
    private String userId;

    // 商家编号（必填）
    private String storeCode;

    // 消费金额（必填）
    private String amount;

    // 支付类型（必填） 1-余额支付  2-微信APP支付 3-支付宝APP支付
    private String payType;*/

    if(!SPUtilHelpr.isLogin(this)){
      return;
  }

        Map object=new HashMap();

        ArrayList codeList=new ArrayList();
        codeList.add(mStoreCode);

        object.put("codeList", codeList);
        object.put("payType", PayType);
        object.put("token", SPUtilHelpr.getUserToken());


        switch (PayType){
            case 1:
                yuPay(object);//余额支付
                break;
            case 3://支付宝支付
                aliPay(object);
                break;
        }


    }

    /**
     * z支付宝支付
     * @param object
     */
    private void aliPay(Map object) {
        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderAliPay("808052", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(data-> data!=null)
                .subscribe(data -> {
                    PayUtil.callAlipay(this,data.getSignOrder(),CALLPAYTAG);

                },Throwable::printStackTrace));
    }

    /**
     * 余额支付
     * @param object
     */
    private void yuPay(Map object) {
        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderPay("808052", StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                        if(data != null && data.isSuccess()){
                            payState();
                        }else{
                            showToast("支付失败");
                        }

                },Throwable::printStackTrace));
    }

    /**
     * 支付成功状态
     */
    private void payState() {
        showToast("支付成功");
        if (isStartMain) MainActivity.open(this);

        EventBusModel eventBusModel=new EventBusModel();
        eventBusModel.setTag("getShopOrderDetailsReqeustEvent"); //刷新订单详情页面

        EventBus.getDefault().post(eventBusModel);

        finish();
    }


    /**
     * 支付回调
     * @param mo
     */
    @Subscribe
    public void AliPayState(PaySucceedInfo mo){
        if(mo == null || !TextUtils.equals(mo.getTag(),CALLPAYTAG)){
            return;
        }

        if(mo.getCallType() == PayUtil.ALIPAY && mo.isPaySucceed()){
            payState();
        }
    }

}
