package com.chengdai.ehealthproject.model.tabsurrounding.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityStoreTypeBinding;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.StoreTypeListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreListModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.HashMap;
import java.util.Map;

/**商户类型列表
 * Created by 李先俊 on 2017/6/12.
 */

public class StoreTypeActivity extends AbsBaseActivity {

    private ActivityStoreTypeBinding mBinding;
    private StoreTypeListAdapter mAdapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,StoreTypeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_store_type, null, false);


        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.store_type_list));


        initViews();

        setSubLeftImgState(true);

        getStoreListRequest();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout leftHeadView = (LinearLayout) inflater.inflate(R.layout.layout_search, null);//得到头部的布局

        mBinding.lvStoreType.addHeaderView(leftHeadView,null,false);

        mBinding.lvStoreType.setOnItemClickListener((parent, view, position, id) -> {

            StoreListModel.ListBean model= (StoreListModel.ListBean) mAdapter.getItem(position-mBinding.lvStoreType.getHeaderViewsCount());
            StoredetailsActivity.open(this,model.getCode());

        });
    }


    /**
     * 获取商户列表
     */
    public void getStoreListRequest(){
        Map map=new HashMap();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("city","杭州");
        map.put("area","余杭区");
        map.put("start","0");
        map.put("limit","10");
        map.put("limit","10");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);

       mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreList("808217", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(this))

                .subscribe(storeListModel -> {
                    mAdapter = new StoreTypeListAdapter(this,storeListModel.getList(),true);
                    mBinding.lvStoreType.setAdapter(mAdapter);

                },Throwable::printStackTrace));


    }



}
