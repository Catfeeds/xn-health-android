package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityTestStartBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthmanager.model.DoTestQusetionModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.jakewharton.rxbinding2.view.RxView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**做测试
 * Created by 李先俊 on 2017/6/15.
 */

public class HealthDoTestQuesitionActivity extends AbsBaseActivity {

    private ActivityTestStartBinding mBinding;

    private String mCode;
    private String mTitle;

    private CommonAdapter mAdapter;

    private List<DoTestQusetionModel.OptionsListBean> mQuesitionData;//要回答的题目 详细数据

    private List<DoTestQusetionModel> mQueRequestData;//请求的问题数据

    private int mQuesitionIndex=0;//要回答的问题下标

    private  DoTestQusetionModel model;

    private ArrayList<String> mSelectCodeList;//回答问题codelist
    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String code,String title){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthDoTestQuesitionActivity.class);
        intent.putExtra("code",code);
        intent.putExtra("title",title);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_test_start, null, false);

        if(getIntent() !=null ){

            mCode=getIntent().getStringExtra("code");
            mTitle=getIntent().getStringExtra("title");

        }


        setTopTitle("自测题");

        setSubLeftImgState(true);

        addMainView(mBinding.getRoot());

        initEvent();

        getDataRequest();
    }

    /**
     * 初始化数据事件
     */
    private void initEvent() {

        mSelectCodeList=new ArrayList<>();

        mBinding.tvTitle.setText(mTitle);

        mQuesitionData=new ArrayList<>();
        mAdapter=new CommonAdapter<DoTestQusetionModel.OptionsListBean>(this, R.layout.item_test_question,mQuesitionData) {

            @Override
            protected void convert(ViewHolder holder, DoTestQusetionModel.OptionsListBean optionsListBean, int position) {

                if(optionsListBean == null){
                    return;
                }

                CheckBox checkBox=holder.getView(R.id.check_quesition);
                checkBox.setChecked(false);
                checkBox.setText(optionsListBean.getContent());

               mSubscription.add( RxView.clicks(checkBox)
                        .delay(120,TimeUnit.MILLISECONDS)
                       .subscribeOn(AndroidSchedulers.mainThread())
                       .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(o -> {
                    if(mSelectCodeList.size()<mQueRequestData.size()){
                        mSelectCodeList.add(optionsListBean.getCode());
                    }

                    dataStateChange();
                }));

            }
        };


        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mBinding.recycler.setAdapter(mAdapter);

    }


    /**
     * 数据变化
     */
    private void dataStateChange() {
        if(mQueRequestData == null || mQueRequestData.size()<=0) return;

        if(mQuesitionIndex<=mQueRequestData.size()-1){
            model=mQueRequestData.get(mQuesitionIndex);
        }

        mQuesitionData.clear();
        mQuesitionData.addAll(model.getOptionsList());

        mBinding.tvTitleNum.setText(model.getOrderNo()+". "+ model.getContent());

        float i=(int)(getValue(mQuesitionIndex,mQueRequestData.size())*100);

        mBinding.tvProgressInfo.setText(mQuesitionIndex+"/"+mQueRequestData.size()+"已完成"+i+"%");
        mAdapter.notifyDataSetChanged();
        mBinding.pb.setProgress(mQuesitionIndex);
        mQuesitionIndex++;
        if(mQuesitionIndex -1 >= mQueRequestData.size()){

            EventBusModel eventBusModel=new EventBusModel();
            eventBusModel.setTag("HealthManagerFragmentRefhsh"); //健康管理首页健康测评时刷新
            EventBus.getDefault().post(eventBusModel);

            HealthTestDoneActivity.open(this,mSelectCodeList,mCode);
            finish();
        }
    }


    public float getValue(int f1,int f2){
        return  Float.valueOf(f1)/  Float.valueOf(f2);
    }


    public void getDataRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("wjCode",mCode);
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

       mSubscription.add( RetrofitUtils.getLoaderServer().getDoTestList("621227",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .filter(s->s!=null)
                .subscribe(s -> {
                    mQueRequestData=s;
                    mBinding.pb.setMax(mQueRequestData.size());
                    dataStateChange();
                },Throwable::printStackTrace));

    }

}
