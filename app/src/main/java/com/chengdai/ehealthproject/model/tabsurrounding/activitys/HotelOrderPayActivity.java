package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelOrderPayBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelOrderCreateModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.HotelOrderPayModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.Map;

/**酒店订单支付
 * Created by 李先俊 on 2017/6/14.
 */

public class HotelOrderPayActivity extends AbsBaseActivity {

    private ActivityHotelOrderPayBinding mBinding;
    private int mPayType=1;

    private HotelOrderPayModel mPayModel;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, HotelOrderPayModel payModel){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelOrderPayActivity.class);
        intent.putExtra("payModel",payModel);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_order_pay, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_pay));

        setSubLeftImgState(true);

        if(getIntent()!=null) mPayModel=getIntent().getParcelableExtra("payModel");

        initViews();

        initPayTypeSelectState();

        setShowData();

    }

    /**
     * 设置显示数据
     */
    private void setShowData() {

        if(mPayModel == null) return;

        mBinding.tvPay.setText(getString(R.string.txt_pay));

        mBinding.tvOrdercode.setText(mPayModel.getOrderCode());

        mBinding.tvUseInfo.setText(mPayModel.getUsePhoee()+"  "+mPayModel.getUseName());

        mBinding.tvHotelData.setText("入住 : "+mPayModel.getStartShowDate() +"  离店 : "+mPayModel.getEndShowDate() +"  "+ mPayModel.getDays()+"晚");

        mBinding.tvPs.setText(mPayModel.getUsePs());


        if(mPayModel.getmHotelModel() != null){

            mBinding.tvHotelSize.setText(mPayModel.getmHotelModel().getName());
            mBinding.tvHotelInfo.setText(mPayModel.getmHotelModel().getSlogan());
            ImgUtils.loadImgURL(this,mPayModel.getmHotelModel().getAdvPic(),mBinding.imgHotelInfo);

            mBinding.tvPrice.setText(getString(R.string.price_sing)+mPayModel.getmHotelModel().getPrice());
        }


    }

    private void initViews() {
        mBinding.tvPay.setOnClickListener(v -> {
            if(SPUtilHelpr.isLogin(this)){

                if(mPayType != 1){
                    showToast("暂未开通此支付功能");
                    return;
                }

                Map<String,String> map=new HashMap();
                map.put("code",mPayModel.getOrderCode());
                map.put("payType",mPayType+"");

                mSubscription.add(RetrofitUtils.getLoaderServer().HotelOrderPay("808451", StringUtils.getJsonToString(map))

                        .compose(RxTransformerHelper.applySchedulerResult(this))

                        .subscribe(payState -> {

                            if(payState!=null && payState.isSuccess()){
                                showToast("支付成功");
                            }else{
                                showToast("支付失败");
                            }


                        },Throwable::printStackTrace));


            }
        });



    }


    /**
     * 初始化支付方式选择状态
     */
    private void initPayTypeSelectState() {

        ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.pay_select,mBinding.imgBalace);
        ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
        ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);

        mBinding.linBalace.setOnClickListener(v -> {
            mPayType=1;
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.pay_select,mBinding.imgBalace);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);
        });
        mBinding.linWeipay.setOnClickListener(v -> {
            mPayType=2;
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgBalace);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.pay_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgZhifubao);
        });

        mBinding.linZhifubao.setOnClickListener(v -> {
            mPayType=3;
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgBalace);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.un_select,mBinding.imgWeixin);
            ImgUtils.loadImgId(HotelOrderPayActivity.this,R.mipmap.pay_select,mBinding.imgZhifubao);
        });


    }



}