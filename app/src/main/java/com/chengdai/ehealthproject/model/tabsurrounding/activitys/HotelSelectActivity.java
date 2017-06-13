package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHotelSelectBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.HotelSelectListAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**房间选择
 * Created by 李先俊 on 2017/6/13.
 */

public class HotelSelectActivity extends AbsBaseActivity{

    private ActivityHotelSelectBinding mBinding;
    private HotelSelectListAdapter hotelAdapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HotelSelectActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_hotel_select, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("酒店选择");

        initViews();

        getHotelDataRequest();
    }

    /**
     *
     */
    private void initViews() {

        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout leftHeadView = (LinearLayout) inflater.inflate(R.layout.layout_hotel_select_head, null);//得到头部的布局
        mBinding.listHotelSelect.addHeaderView(leftHeadView,null,false);

        hotelAdapter = new HotelSelectListAdapter(this,new ArrayList<>());

        mBinding.listHotelSelect.setAdapter(hotelAdapter);

        mBinding.listHotelSelect.setOnItemClickListener((parent, view, position, id) -> {

           if(hotelAdapter !=null){
               hotelAdapter.setSelectPosition(position-mBinding.listHotelSelect.getHeaderViewsCount());
           }

       });



    }

    /**
     * 获取商户详情
     */
    public void getHotelDataRequest(){
        Map map=new HashMap();

//        map.put("category","FL2017061016211611994528");
//        map.put("type","FL2017061219492431865712");
        map.put("start","0");
        map.put("limit","10");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().GetHotelList("808415",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(hotelListModel -> hotelListModel!=null && hotelListModel.getList()!=null)
                .subscribe(hotelListModel -> {
                    if(hotelAdapter !=null ){
                        hotelAdapter.setData(hotelListModel.getList());
                    }
                },Throwable::printStackTrace));

    }


}
