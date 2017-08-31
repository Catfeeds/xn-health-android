package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.base.ImagePrviewActivity2;
import com.chengdai.ehealthproject.databinding.ActivityHealthinfoDetailsBinding;
import com.chengdai.ehealthproject.model.healthmanager.model.HealthInfoModel;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingStoreTypeAdapter;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;

import java.util.ArrayList;
import java.util.List;

/**健康资讯详情
 * Created by 李先俊 on 2017/6/23.
 */

public class HealthinfoDetailsActivity extends AbsBaseActivity {

    private ActivityHealthinfoDetailsBinding mBinding;

    private SurroundingStoreTypeAdapter mMenuAdapter;

    private HealthInfoModel.ListBean mData;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, HealthInfoModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthinfoDetailsActivity.class);
        intent.putExtra("data",data);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_healthinfo_details, null, false);

        addMainView(mBinding.getRoot());

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setTopTitle("资讯详情");

        setSubLeftImgState(true);

        setShowData();


    }

    private void setShowData() {

        if(mData == null){
            return;
        }

        mBinding.tvTitle.setText(mData.getTitle());

        if(!TextUtils.isEmpty(mData.getContent())){
            AppOhterManager.showRichText(this,mBinding.tvContent,mData.getContent());
        }

    }


}
