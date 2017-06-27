package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityDoHealthTaskBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthmanager.model.TaskNowModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**确认执行任务
 * Created by 李先俊 on 2017/6/26.
 */

public class DoHealthTaskActivity extends AbsBaseActivity {

    public ActivityDoHealthTaskBinding mBinding;

    public TaskNowModel mData;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, TaskNowModel code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,DoHealthTaskActivity.class);
        intent.putExtra("data",code);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_do_health_task, null, false);

        addMainView(mBinding.getRoot());


        setSubLeftImgState(true);

        setTopTitle("");

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
        }

        if(TextUtils.equals(mData.getIsZX(),"0")){

            mBinding.tvIsDo.setText("未完成任务");

            ImgUtils.loadImgId(this,R.mipmap.task_un_do,mBinding.imgDo);

        }else{
            mBinding.tvIsDo.setText("已完成任务");
            ImgUtils.loadImgId(this,R.mipmap.task_do,mBinding.imgDo);

            showWeek(mData.getTxWeek());

        }

        mBinding.tvDays.setText("已坚持"+mData.getZxNum()+"天");

        mBinding.tvData.setText(DateUtil.format(new Date(),"M月dd日"));


        mBinding.imgDo.setOnClickListener(v -> {
            doTaskRequest();
        });

    }

    private void doTaskRequest() {

        if(mData == null){
            return;
        }

        Map<String,String> map=new HashMap<>();
        map.put("uTaskCode",mData.getCode());

        RetrofitUtils.getLoaderServer().sureDoThealthTask("621170",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(codemodel -> codemodel!=null && !TextUtils.isEmpty(codemodel.getCode()))
                .subscribe(codemodel -> {

                    EventBusModel model=new EventBusModel();
                    model.setTag("HealthManagerFragmentRefhsh_Task");
                    EventBus.getDefault().post(model);

                mBinding.tvIsDo.setText("已完成任务");
               ImgUtils.loadImgId(this,R.mipmap.task_do,mBinding.imgDo);

                mBinding.tvDays.setText("已坚持"+(mData.getZxNum()+1)+"天");

                    showWeek(mData.getTxWeek());

                },Throwable::printStackTrace);

    }

    private void showWeek(String week) {
        switch (week){
            case "2":
                mBinding.ckWeek1.setChecked(true);
                break;
            case "3":
                mBinding.ckWeek2.setChecked(true);
                break;
            case "4":
                mBinding.ckWeek3.setChecked(true);
                break;
            case "5":
                mBinding.ckWeek4.setChecked(true);
                break;
            case "6":
                mBinding.ckWeek5.setChecked(true);
                break;
            case "7":
                mBinding.ckWeek6.setChecked(true);
                break;
            case "1":
                mBinding.ckWeek7.setChecked(true);
                break;
        }
    }


}
