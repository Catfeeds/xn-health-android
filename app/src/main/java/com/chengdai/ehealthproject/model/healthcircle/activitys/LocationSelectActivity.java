package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityLocationSelectBinding;
import com.chengdai.ehealthproject.databinding.ActivityModifyTradeBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

/**定位选择
 * Created by 李先俊 on 2017/6/29.
 */

public class LocationSelectActivity extends AbsBaseActivity{

    private ActivityLocationSelectBinding mBinding;

    /**
     *
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,LocationSelectActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_location_select, null, false);
        addMainView(mBinding.getRoot());

        setSubLeftImgState(true);

        setTopTitle("选择位置");

        LocationModel locationModel = SPUtilHelpr.getLocationInfo();
        if(locationModel !=null){
            mBinding.tvLocationName.setText(locationModel.getProvinceName()+"."+locationModel.getCityName()+"."+locationModel.getAreaName());

        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            mBinding.tvLocationName.setText( SPUtilHelpr.getResetLocationInfo().getCityName());
        }else{
            mBinding.tvLocationName.setHint("位置信息获取失败");
        }

        mBinding.tvLocationName.setOnClickListener(v -> {
            if(TextUtils.isEmpty(mBinding.tvLocationName.getText().toString())){
                return;
            }

            EventBusModel event=new EventBusModel();
            event.setTag("LOCATIONSELECT");
            event.setEvInfo(mBinding.tvLocationName.getText().toString());
            EventBus.getDefault().post(event);
            finish();
        });

    }
}
