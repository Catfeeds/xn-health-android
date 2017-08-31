package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityTestPageDetailsBinding;
import com.chengdai.ehealthproject.model.healthmanager.model.TestPageListModel;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

/**健康自测页面详情
 * Created by 李先俊 on 2017/6/24.
 */

public class TestPageDetailsActivity extends AbsBaseActivity {

    private ActivityTestPageDetailsBinding mBinding;

    private  TestPageListModel.ListBean mData;
    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, TestPageListModel.ListBean data){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,TestPageDetailsActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_test_page_details, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("健康自测");

        setSubLeftImgState(true);

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        if(mData!=null && !TextUtils.isEmpty(mData.getContent())){
            AppOhterManager.showRichText(this,mBinding.tvContent,mData.getContent());
            mBinding.tvSatrtTest.setOnClickListener(v -> {
                        if (!SPUtilHelpr.isLogin(this)) {
                            return;
                        }
                        HealthDoTestQuesitionActivity.open(this, mData.getCode(), mData.getTitle());
                    }
            );
        }

    }
}
