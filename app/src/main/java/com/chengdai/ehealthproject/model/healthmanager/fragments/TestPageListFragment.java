package com.chengdai.ehealthproject.model.healthmanager.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.CommonListRefreshNodriverBinding;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.HealthTestActivity;
import com.chengdai.ehealthproject.model.healthmanager.adapters.TestPageAdapter;
import com.chengdai.ehealthproject.model.healthmanager.model.TestPageListModel;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/24.
 */

public class TestPageListFragment extends BaseLazyFragment {

    private CommonRecycleerBinding mBinding;

    private int mIndex;//在viewPager中的位置
    private String mType;//请求问卷类型
    private int mOpenType;//打开类型

    private boolean isCreate;

    private int mStartPage=1;
    private EmptyWrapper mEmptyWrapper;

    private List<TestPageListModel.ListBean> mDatas;


    /**
     * 获得fragment实例
     * @return
     */
    public static TestPageListFragment getInstanse(int index,String type,int openType){
        TestPageListFragment fragment=new TestPageListFragment();

        Bundle bundle=new Bundle();
        bundle.putInt("index",index);
        bundle.putString("type",type);
        bundle.putInt("openType",openType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.common_recycleer, null, false);

        if(getArguments() !=null){
            mIndex=getArguments().getInt("index",0);
            mType=getArguments().getString("type");
            mOpenType=getArguments().getInt("openType");

        }

        isCreate=true;

        mDatas=new ArrayList<>();
        TestPageAdapter mAdapter=new TestPageAdapter(mActivity,mDatas);
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(R.layout.empty_view);
        mBinding.cycler.setAdapter(mEmptyWrapper);

        initViews();

        return mBinding.getRoot();
    }

    private void initViews() {
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(mActivity,MyDividerItemDecoration.VERTICAL_LIST));

        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(mActivity));
        mBinding.springvew.setFooter(new DefaultHeader(mActivity));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStartPage=1;
                getTestPageListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStartPage++;
                getTestPageListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mIndex == 0){
            getTestPageListRequest(mActivity);
        }

    }

    @Override
    protected void lazyLoad() {
        if(isCreate && mIndex != 0){
            getTestPageListRequest(mActivity);
            isCreate=false;
        }
    }

    @Override
    protected void onInvisible() {

    }


    public void getTestPageListRequest(Context context){

        Map<String,String> map=new HashMap<>();
        if(mOpenType ==HealthTestActivity.TYPE1){
            map.put("kind","questionare_kind_2");
        }else{
            map.put("kind","questionare_kind_3");
        }
        map.put("type",mType);
        map.put("start",mStartPage+"");
        map.put("limit","10");

       mSubscription.add( RetrofitUtils.getLoaderServer().getTestPageList("621205", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(context))

                .subscribe(s -> {

                    if(mStartPage == 1){
                        if(s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mEmptyWrapper.notifyDataSetChanged();
                        }

                        if(mDatas.size()==0){
                            mEmptyWrapper.setEmptyView(R.layout.empty_view);
                        }

                    }else if(mStartPage >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mStartPage--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mEmptyWrapper.notifyDataSetChanged();
                    }


                }));

    }


}
