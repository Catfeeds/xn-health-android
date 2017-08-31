package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHealthinfoDetailsBinding;
import com.chengdai.ehealthproject.model.healthmanager.model.HealthInfoListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.SexHealthInfoListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingStoreTypeAdapter;
import com.chengdai.ehealthproject.weigit.appmanager.AppOhterManager;

/**形体健康资讯详情
 * Created by 李先俊 on 2017/6/23.
 */

public class SexHealthinfoDetailsActivity extends AbsBaseActivity {

    private ActivityHealthinfoDetailsBinding mBinding;

    private SurroundingStoreTypeAdapter mMenuAdapter;

    private SexHealthInfoListModel.ListBean mData;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, SexHealthInfoListModel.ListBean data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,SexHealthinfoDetailsActivity.class);
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
