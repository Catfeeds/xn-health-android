package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPersonalLuntanBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthcircle.adapters.LuntanListAdapter;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李先俊 on 2017/6/21.
 */

public class PersonalLuntanActivity extends AbsBaseActivity {

    private ActivityPersonalLuntanBinding mBinding;

    private LuntanListAdapter mAdapter;

    private String mUserId;


    private int mPageStart=1;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String userid){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,PersonalLuntanActivity.class);
        intent.putExtra("userid",userid);
        context.startActivity(intent);
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_personal_luntan, null, false);

        addMainView(mBinding.getRoot());

        mAdapter=new LuntanListAdapter(this,new ArrayList<>());

        if(getIntent()!=null){
            mUserId=getIntent().getStringExtra("userid");
        }

        setTopTitle("个人帖子");

        setSubLeftImgState(true);

        initSpringView();

        initListView();

        getDataRequest(this);

        getUserInfoRequest();

    }

    private void initListView() {

        mAdapter=new LuntanListAdapter(this,new ArrayList<>());
        mBinding.listView.setAdapter(mAdapter);
    }

    private void initSpringView() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));
        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getDataRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

    }

    @Subscribe
    public void PersonalLuntanActivityEnent(EventBusModel model){
        if(model !=null && TextUtils.equals(model.getTag(),"LuntanDzRefeshEnent")){
            mPageStart=1;
            getDataRequest(this);
        }
    }

    /**
     * 获取帖子数据
     * @param context
     */
    public void getDataRequest(Context context){
        Map<String ,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("status","BD");//审核通过并发布
        map.put("start",mPageStart+"");
        map.put("publisher",mUserId);
        map.put("limit","10");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetArticleLisData("621040", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s.getList()!=null){
                            mAdapter.setData(s.getList());
                        }

                    }else if(mPageStart >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mAdapter.addData(s.getList());
                    }

                },throwable -> {

                }));
    }


    /**
     * 获取用户信息请求
     */
    private  void getUserInfoRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("userId",mUserId);
        map.put("token", SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))

                .filter(r -> r!=null)

                .subscribe(r -> {
                    mBinding.tvName.setText(r.getLoginName());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgLogo(this, MyConfig.IMGURL+r.getUserExt().getPhoto(),mBinding.imgLogo);
                    if("0".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(this,R.mipmap.man,mBinding.imgSex);
                    }else if ("1".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(this,R.mipmap.woman,mBinding.imgSex);
                    }

                },Throwable::printStackTrace));


    }

}
