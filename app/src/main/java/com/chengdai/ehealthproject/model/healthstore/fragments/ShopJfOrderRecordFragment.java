package com.chengdai.ehealthproject.model.healthstore.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.CommonListRefreshBinding;
import com.chengdai.ehealthproject.model.tabmy.activitys.ShopOrderDetailsActivity;
import com.chengdai.ehealthproject.model.tabmy.adapters.ShopOrderListAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/15.
 */

public class ShopJfOrderRecordFragment extends BaseLazyFragment {

    private CommonListRefreshBinding mBinding;
    private boolean isCreate;

    private int mPageStart=1;
    private ShopOrderListAdapter mAdapter;

    private String mState;

    /**
     * 获得fragment实例
     * @return
     */
    public static ShopJfOrderRecordFragment getInstanse(String state){
        ShopJfOrderRecordFragment fragment=new ShopJfOrderRecordFragment();
        Bundle bundle=new Bundle();
        bundle.putString("state",state);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.common_list_refresh, null, false);

        isCreate=true;

        if(getArguments()!=null){
            mState=getArguments().getString("state","");
        }

        initListView();

        initSpringView();


        return mBinding.getRoot();
    }

    private void initListView() {


        mAdapter = new ShopOrderListAdapter(mActivity,new ArrayList<>(),MyConfig.JFORDER);
        mBinding.listview.setAdapter(mAdapter);

        mBinding.listview.setOnItemClickListener((parent, view, position, id) -> {

            if(mAdapter !=null){
                ShopOrderDetailsActivity.open(mActivity,mAdapter.getItem(position),MyConfig.JFORDER);
            }

        });

    }

    private void initSpringView() {
        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(getActivity()));
        mBinding.springvew.setFooter(new DefaultFooter(getActivity()));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                orderRecordRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                orderRecordRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

        if(getUserVisibleHint()){
            mPageStart=1;
            orderRecordRequest(mActivity);
        }
    }

    @Override
    protected void lazyLoad() {

        if(isCreate){
            mPageStart=1;
            orderRecordRequest(mActivity);
        }

    }

    @Override
    protected void onInvisible() {

    }


    public void orderRecordRequest(Context context){

        Map<String,String> object=new HashMap();

        object.put("applyUser", SPUtilHelpr.getUserId());
        object.put("start", mPageStart+"");
        object.put("limit", "10");
        object.put("status", mState+"");
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("systemCode", MyConfig.SYSTEMCODE);
        object.put("type","2");//积分商城


        mSubscription.add(RetrofitUtils.getLoaderServer().ShopOrderList("808068",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(r->{
                    if(mPageStart==1){
                        if(r==null || r.getList()==null){
                            return;
                        }
                        mAdapter.setData(r.getList());
                        return;
                    }
                    if(mPageStart>1){
                        if(r==null || r.getList()==null || r.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mAdapter.addData(r.getList());
                    }

                },Throwable::printStackTrace));


/*        mSubscription.add(RetrofitUtils.getLoaderServer().OrderRecordRequest("808245", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(r -> {



                },Throwable::printStackTrace));*/

    }

}
