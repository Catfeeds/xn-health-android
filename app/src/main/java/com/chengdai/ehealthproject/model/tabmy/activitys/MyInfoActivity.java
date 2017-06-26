package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityMyInfoBinding;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

/**我的信息
 * Created by 李先俊 on 2017/6/16.
 */

public class MyInfoActivity extends AbsBaseActivity{

    private ActivityMyInfoBinding mBinding;

    private UserInfoModel mData;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, UserInfoModel data){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyInfoActivity.class);

        intent.putExtra("data",data);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_my_info, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("编辑资料");
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        setSubRightTitleAndClick("保存",v -> {

        });


        setShowData();


    }

    private void setShowData() {
        if(mData == null){
            return;
        }

        ImgUtils.loadImgLogo(this, MyConfig.IMGURL+mData.getUserExt().getPhoto(),mBinding.imgLogo);
        mBinding.tvName.setText(mData.getNickname());
        mBinding.tvBirthday.setText(mData.getBirthday());
        if("0".equals(mData.getUserExt().getGender())){
         mBinding.tvGender.setText("男");
        }else if ("1".equals(mData.getUserExt().getGender())){
            mBinding.tvGender.setText("nv");
        }
         mBinding.tvEmail.setText(mData.getEmail());

    }
}
