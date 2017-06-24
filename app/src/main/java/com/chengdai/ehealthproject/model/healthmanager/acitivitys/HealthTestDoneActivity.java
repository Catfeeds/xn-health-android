package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingOrderStateBinding;
import com.chengdai.ehealthproject.databinding.ActivityTestDoneBinding;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.healthmanager.fragments.TestPageListFragment;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**测试结果查看
 * Created by 李先俊 on 2017/6/15.
 */

public class HealthTestDoneActivity extends AbsBaseActivity {

    private ActivityTestDoneBinding mBinding;

    private ArrayList<String> mCodeList;

    private String mCode;//用于再测一次

    private String mTitle;//

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, ArrayList<String> codeList,String quesitionCode){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthTestDoneActivity.class);
        intent.putStringArrayListExtra("codelist",codeList);
        intent.putExtra("quesitionCode",quesitionCode);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_test_done, null, false);

        if(getIntent()!=null){
            mCodeList=getIntent().getStringArrayListExtra("codelist");
            mCode=getIntent().getStringExtra("quesitionCode");
        }

        setTopTitle("测试结果");

        setSubLeftImgState(true);

        addMainView(mBinding.getRoot());
        getDataRequest();

        mBinding.tvISee.setOnClickListener(v -> {
            finish();
        });

        mBinding.tvTryTest.setOnClickListener(v -> {
            HealthDoTestQuesitionActivity.open(this,mCode,mTitle);
            finish();
        });

    }


    public void getDataRequest(){

        Map map=new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("codeList",mCodeList);

        mSubscription.add(RetrofitUtils.getLoaderServer().getDoneTestData("621240",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(s->s!=null)
                .subscribe(s -> {
                    mBinding.tvContent.setText(s.getContent());
                    mTitle=s.getTitle();
                },Throwable::printStackTrace));

    }

}
