package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityModifyTradeBinding;
import com.chengdai.ehealthproject.databinding.ActivityXueyaTestBinding;
import com.chengdai.ehealthproject.model.common.model.UserInfoModel;

/**血压计算
 * Created by 李先俊 on 2017/6/29.
 */

public class XueyaCalculateActivity extends AbsBaseActivity{

    private ActivityXueyaTestBinding mBinding;

    /**
     *
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,XueyaCalculateActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_xueya_test, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle("血压测试");

        setSubLeftImgState(true);

     mBinding.tvSure.setOnClickListener(v -> {
         if(TextUtils.isEmpty(mBinding.editHeight.getText().toString())){
             showToast("请输入高压值");
             return;
         }
         if(TextUtils.isEmpty(mBinding.editLow.getText().toString())){
             showToast("请输入低压值");
             return;
         }

         int height=Integer.valueOf(mBinding.editHeight.getText().toString());
         int low=Integer.valueOf(mBinding.editLow.getText().toString());

         if(height<50 || height>250){
             showToast("高压值在50-250之间");
             return;
         }

         if(low<30 || low>220){
             showToast("低压值在30-220之间");
             return;
         }

         XueyaCalculateDetailsActivity.open(this,height,low);


     });

    }
}
