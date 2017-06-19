package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelOrderDetailsBinding;
import com.chengdai.ehealthproject.databinding.ActivityShopOrderDetailsBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthstore.acitivtys.ShopPayConfirmActivity;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.model.healthstore.models.ShopOrderDetailBean;
import com.chengdai.ehealthproject.model.tabmy.model.OrderRecordModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.Subscribe;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**订单详情查看
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopOrderDetailsActivity extends AbsBaseActivity{

    private ActivityShopOrderDetailsBinding mBinding;

    private  ShopOrderDetailBean data;

    private  int mType;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, ShopOrderDetailBean data,int type){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopOrderDetailsActivity.class);

        intent.putExtra("data",data);

        intent.putExtra("type",type);

        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_shop_order_details, null, false);

        if(getIntent()!=null){

            data= getIntent().getParcelableExtra("data");
            mType= getIntent().getIntExtra("type",MyConfig.JFORDER);

        }

        setTopTitle("订单详情");

        setSubLeftImgState(true);

        addMainView(mBinding.getRoot());

        setShowDataStae();

//        getShopOrderDetailsReqeust(this);

    }

    private void setShowDataStae() {

        if(data == null){
            return;
        }

        mBinding.txtOrderId.setText(data.getCode());

        mBinding.txtTime.setText(DateUtil.format(new Date(data.getApplyDatetime()),DateUtil.DATE_YYMMddHHmm));

        mBinding.txtStatus.setText(StringUtils.getOrderState(data.getStatus()));


        if(data.getProductOrderList() !=null && data.getProductOrderList().size()>0 && data.getProductOrderList().get(0) !=null
                && data.getProductOrderList().get(0).getProduct()!=null ){
            ImgUtils.loadImgURL(this, MyConfig.IMGURL+data.getProductOrderList().get(0).getProduct().getAdvPic(),mBinding.imgGood);


           if(mType == MyConfig.JFORDER){
               mBinding.txtPrice.setText(StringUtils.showPrice(data.getProductOrderList().get(0).getPrice1())+"  积分");
           }else{
               mBinding.txtPrice.setText(StringUtils.getShowPriceSign(data.getProductOrderList().get(0).getPrice1()));
           }

            mBinding.txtNumber.setText("X" + data.getProductOrderList().get(0).getQuantity());

            mBinding.txtName.setText(data.getProductOrderList().get(0).getProduct().getName());

            mBinding.txtPhone.setText(data.getReceiver()+" "+data.getReMobile());

            mBinding.txtAddress.setText(data.getReAddress());

            mBinding.txtGuige.setText(data.getProductOrderList().get(0).getProductSpecsName());

        }

        if(StringUtils.canDoPay(data.getStatus())){
            mBinding.txtCancel.setVisibility(View.VISIBLE);
            mBinding.txtBtn.setVisibility(View.VISIBLE);

            mBinding.txtBtn.setOnClickListener(v -> {
                ShopListModel.ListBean.ProductSpecsListBean data1=new ShopListModel.ListBean.ProductSpecsListBean();

                if(data.getProductOrderList() !=null && data.getProductOrderList().size()>0 && data.getProductOrderList().get(0) !=null) {
                    data1.setPrice1(data.getProductOrderList().get(0).getPrice1());
                    data1.setmBuyNum(data.getProductOrderList().get(0).getQuantity());
                    ShopPayConfirmActivity.open(this,data1,data.getCode(),false);
                }


            });

           mBinding.txtCancel.setOnClickListener(v -> {

               showDoubleWarnListen("确定取消订单？",view -> {
                   cancelOrderRequest();
               });

            });

        }


    }



    //ShopPayConfirmActivity 支付成功刷新
    @Subscribe
  public void  getShopOrderDetailsReqeustEvent(EventBusModel b){
        if(b ==null || !"getShopOrderDetailsReqeustEvent" .equals(b.getTag())){
            return;
        }

        mBinding.txtBtn.setVisibility(View.GONE);
        mBinding.txtCancel.setVisibility(View.GONE);
        mBinding.txtStatus.setText("已支付");
//        getShopOrderDetailsReqeust(null);
    }

    /**
     * 获取订单详情
     * @param c
     */
    private  void getShopOrderDetailsReqeust(Context c){

        if(data == null){
            return;
        }

        Map object=new HashMap();

        object.put("code", data.getCode());
        object.put("token", SPUtilHelpr.getUserToken());

      mSubscription.add( RetrofitUtils.getLoaderServer().ShopOrderDetails("808066",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(shopOrderModel -> {

                    if(shopOrderModel.getList() != null && shopOrderModel.getList().size()>0){
                        data=shopOrderModel.getList().get(0);

                        setShowDataStae();
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 取消订单
     */
    private void cancelOrderRequest(){
        if(data == null){
            return;
        }
        Map object=new HashMap();
        object.put("code", data.getCode());
        object.put("userId", SPUtilHelpr.getUserId());
        object.put("remark", "用户取消订单");
        object.put("token", SPUtilHelpr.getUserToken());


        mSubscription.add( RetrofitUtils.getLoaderServer().ShopOrderCancel("808053",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(shopOrderModel -> {

                    if(shopOrderModel != null && shopOrderModel.isSuccess()){
                        showToast("取消订单成功");
                        mBinding.txtBtn.setVisibility(View.GONE);
                        mBinding.txtCancel.setVisibility(View.GONE);
                        mBinding.txtStatus.setText("已取消");
                        getShopOrderDetailsReqeust(null);
                    }

                },Throwable::printStackTrace));


    }


}
