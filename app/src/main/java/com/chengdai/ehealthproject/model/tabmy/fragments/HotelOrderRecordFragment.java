package com.chengdai.ehealthproject.model.tabmy.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseFragment;
import com.chengdai.ehealthproject.databinding.CommonListRefreshBinding;
import com.chengdai.ehealthproject.model.tabmy.activitys.HotelOrderDetailsActivity;
import com.chengdai.ehealthproject.model.tabmy.adapters.HotelOrderRecordAdapter;
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

public class HotelOrderRecordFragment extends BaseFragment {

    private CommonListRefreshBinding mBinding;
    private int mPageStart=1;
    private HotelOrderRecordAdapter mAdapter;

    /**
     * 获得fragment实例
     * @return
     */
    public static HotelOrderRecordFragment getInstanse(){
        HotelOrderRecordFragment fragment=new HotelOrderRecordFragment();
        Bundle bundle=new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.common_list_refresh, null, false);


        orderRecordRequest(mActivity);

        initListView();

        initSpringView();

        return mBinding.getRoot();
    }

    private void initListView() {
        mAdapter = new HotelOrderRecordAdapter(mActivity,new ArrayList<>());
        mBinding.listview.setAdapter(mAdapter);
        mBinding.listview.setOnItemClickListener((parent, view, position, id) -> {
            HotelOrderDetailsActivity.open(mActivity,mAdapter.getItem(position));
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

    public void orderRecordRequest(Context context){

        Map<String,String> map=new HashMap();

        map.put("applyUser",SPUtilHelpr.getUserId());
        map.put("start",mPageStart+"");
        map.put("limit","10");
        map.put("status","1");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().HotelOrderRecordRequest("808468", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(r -> {
                    if(mPageStart==1){
                        if(r==null || r.getList()==null || r.getList().size()==0){
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


    }

}
