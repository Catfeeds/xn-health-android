package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.healthcircle.adapters.PinglunListAdapter;
import com.chengdai.ehealthproject.model.healthcircle.models.PinglunListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**评论列表
 * Created by 李先俊 on 2017/6/21.
 */

public class PinglunListActivity extends AbsBaseActivity {

    private CommonRecycleerBinding mBinding;

    private String mCode;

    private CommonAdapter mAdapter;

    private List<PinglunListModel.ListBean> mDatas;

    private int mStartPage=1;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,PinglunListActivity.class);
        intent.putExtra("code",code);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_recycleer, null, false);

        if(getIntent()!=null){
            mCode=getIntent().getStringExtra("code");
        }

        addMainView(mBinding.getRoot());

        setTopTitle("评论");

        setSubLeftImgState(true);

        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {

                mStartPage=1;
                getDataList(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStartPage++;
                getDataList(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));

        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        mDatas=new ArrayList<>();

        mAdapter=new PinglunListAdapter(this,mDatas);

        mBinding.cycler.setAdapter(mAdapter);

        getDataList(PinglunListActivity.this);

    }

    private  void getDataList(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("postCode",mCode);
        map.put("start",mStartPage+"");
        map.put("limit","10");
        map.put("status","BD");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetPinglunLisData("621062", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mStartPage == 1){
                        if(s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mAdapter.notifyDataSetChanged();
                        }

                    }else if(mStartPage >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mStartPage--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mAdapter.notifyDataSetChanged();
                    }


                },Throwable::printStackTrace));

    }


}
