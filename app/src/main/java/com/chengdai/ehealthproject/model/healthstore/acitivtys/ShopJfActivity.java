package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityJfShopBinding;
import com.chengdai.ehealthproject.databinding.ActivitySearchBinding;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopHotJfAdapter;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopJfAdapter;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopTypeListAdapter;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopJfActivity extends AbsBaseActivity {

    private ActivityJfShopBinding mBinding;

    private int mStoreStart=1;

    private List<ShopListModel.ListBean> mHotListData=new ArrayList<>();//热门兑换数据
    private List<ShopListModel.ListBean> mJlistData=new ArrayList<>();//积分兑换数据

    private ShopHotJfAdapter mHotAdapter;
    private ShopJfAdapter mJfAdapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopJfActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_jf_shop, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("积分商城");

        initListView();

        setSubLeftImgState(true);
        getStoreListRequest(this);
    }

    private void initListView() {
        mHotAdapter=new ShopHotJfAdapter(this,mHotListData);
        mJfAdapter=new ShopJfAdapter(this,mJlistData);

        mBinding.listHot.setAdapter(mHotAdapter);
        mBinding.listJf.setAdapter(mJfAdapter);

        mBinding.listJf.setOnItemClickListener((parent, view, position, id) -> {

            ShopJfDetailsActivity.open(this, mJfAdapter.getItem(position));
        });

        mBinding.listHot.setOnItemClickListener((parent, view, position, id) -> {
            ShopJfDetailsActivity.open(this,mHotAdapter.getItem(position));
        });

    }


    /**
     * 获取商城列表
     * @param act
     */
    public void getStoreListRequest(Activity act){
        Map map=new HashMap();

        map.put("kind","2"); //1 标准商城 2 积分商城
        map.put("status","3");//已上架
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().GetShopList("808025", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

                .filter(storeListModel -> storeListModel!=null && storeListModel.getList()!=null)

                .map(shopListModel -> shopListModel.getList())

                .flatMap(listBean -> Observable.fromIterable(listBean))

                .filter(beanData-> beanData!=null)

                .doOnComplete(() -> {

                    mHotAdapter.setData(mHotListData);
                    mJfAdapter.setData(mJlistData);

                }) .subscribe(beanData -> {
                    if(TextUtils.equals(beanData.getLocation(),"1")){
                        mHotListData.add(beanData);
                    }else if(TextUtils.equals(beanData.getLocation(),"0")){
                        mJlistData.add(beanData);
                    }

                },Throwable::printStackTrace));


    }

}
