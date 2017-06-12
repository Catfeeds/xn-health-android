package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPayBinding;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/12.
 */

public class PayActivity extends AbsBaseActivity {

    private ActivityPayBinding mBinding;

    private float rate;//折扣

    private String  mStoreCode;

    private Double mDiscountMoney=0.0;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,float rate,String storeCode){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,PayActivity.class);
        intent.putExtra("rate",rate);
        intent.putExtra("storeCode",storeCode);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pay, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay));

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mStoreCode=getIntent().getStringExtra("storeCode");
            rate= getIntent().getFloatExtra("rate",0);
        }

        initViews();

        initPayTypeSelectState();

    }

    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {

        ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_,mBinding.imgBalace);
        ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgWeixin);
        ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgZhifubao);

        mBinding.linBalace.setOnClickListener(v -> {
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_,mBinding.imgBalace);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgWeixin);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgZhifubao);
        });
     mBinding.linWeipay.setOnClickListener(v -> {
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgBalace);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_,mBinding.imgWeixin);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgZhifubao);
        });

     mBinding.linZhifubao.setOnClickListener(v -> {
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgBalace);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_un,mBinding.imgWeixin);
            ImgUtils.loadImgId(PayActivity.this,R.mipmap.good_hand_,mBinding.imgZhifubao);
        });


    }



    private void initViews() {
        mBinding.tvRate.setText((int)(rate*10)+"折");

        mBinding.txtPay.setOnClickListener(v -> {
            if(SPUtilHelpr.isLogin(this)){
                payRequest();
            }

        });

        RxTextView.textChanges(mBinding.edtPrice).subscribe(charSequence -> {
            if(!TextUtils.isEmpty(charSequence.toString())){

                mDiscountMoney= (Float.valueOf(charSequence.toString())*0.7);

                mBinding.txtDiscountMoney.setText(mDiscountMoney+"");

                LogUtil.E("钱"+doubleFormatMoney(mDiscountMoney));

            }else{
                mDiscountMoney=0.0;
                mBinding.txtDiscountMoney.setText("0");
            }

        });
    }

    private void payRequest() {
/*// 用户编号（必填）
    private String userId;

    // 商家编号（必填）
    private String storeCode;

    // 消费金额（必填）
    private String amount;

    // 支付类型（必填） 1-余额支付  2-微信APP支付 3-支付宝APP支付
    private String payType;*/

        Map map=new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("storeCode", mStoreCode);
        map.put("amount",mDiscountMoney*1000);
        map.put("payType","1");

        mSubscription.add(RetrofitUtils.getLoaderServer().Pay("808271", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                  if(TextUtils.isEmpty(data)){
                      showToast("支付失败");
                  }else{
                      showToast("支付成功");
                      finish();
                  }

                },Throwable::printStackTrace));

    }
    public static String doubleFormatMoney(double money){
        DecimalFormat df = new DecimalFormat("#######0.000");
        String showMoney = df.format((money/1000));
        return showMoney.substring(0,showMoney.length()-1);
    }

}
