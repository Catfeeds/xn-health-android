package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityAddHealthTaskBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthTaskListMdoel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.popwindows.SelectMinutesTimePopup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**添加健康任务
 * Created by 李先俊 on 2017/6/26.
 */
public class AddHelathTaskActivity extends AbsBaseActivity{

    private ActivityAddHealthTaskBinding mBinding;

    private HealthTaskListMdoel mData;

    private List<String> mSelectWeekList=new ArrayList<>();

    private String mSelectTime;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,HealthTaskListMdoel data){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,AddHelathTaskActivity.class);
        intent.putExtra("data",data);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_add_health_task, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("任务设置");

        if(getIntent()!=null){
            mData=getIntent().getParcelableExtra("data");
            if(mData!=null){
                ImgUtils.loadImgURL(this, MyConfig.IMGURL+mData.getLogo(),mBinding.imgTitle);
                mBinding.tvName.setText(mData.getName());
            }
        }

        setSubLeftImgState(true);

        setSubRightTitleAndClick("确定",v -> {
            if(TextUtils.isEmpty(mSelectTime)){
                showToast("请添加提醒时间");
                return;
            }
            if(mSelectWeekList.size() == 0){
                showToast("请添加提醒周期");
                return;
            }
            addTaskRequest();
        });


        mBinding.tvSelectTime.setOnClickListener(v -> {

            new SelectMinutesTimePopup(this).setSureOnClick(value -> {
                mSelectTime=value;
                mBinding.tvSelectTime.setText(value);
            }).showPopupWindow();

        });

        setWeekListener();

    }


    //添加任务请求
    public void addTaskRequest(){
        if(mData == null){
            return;
        }
        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());

        map.put("hTaskCode", mData.getCode());

        map.put("txDatetime", mSelectTime+"");

        map.put("txWeek", StringUtils.ListToString(mSelectWeekList,","));

        map.put("status", "1");//1 开启 0 关闭

      mSubscription.add( RetrofitUtils.getLoaderServer().addTask("621160",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
              .filter(codeModel -> codeModel!=null &&!TextUtils.isEmpty( codeModel.getCode()))
                .subscribe(codeModel -> {
                    showToast("添加任务成功");

                    EventBusModel eventBusModel=new EventBusModel();
                    eventBusModel.setTag("HealthManagerFragmentRefhsh_Task");
                    EventBus.getDefault().post(eventBusModel);

                    finish();
                },Throwable::printStackTrace));

    }

    //星期选择
    private void setWeekListener() {
        mBinding.ckWeek1.setOnClickListener(v -> {
            if(mBinding.ckWeek1.isChecked()){
                mSelectWeekList.add("2");
            }else{
                mSelectWeekList.remove("2");
            }

        });
      mBinding.ckWeek2.setOnClickListener(v -> {
            if(mBinding.ckWeek2.isChecked()){
                mSelectWeekList.add("3");
            }else{
                mSelectWeekList.remove("3");
            }

        });
      mBinding.ckWeek3.setOnClickListener(v -> {
            if(mBinding.ckWeek3.isChecked()){
                mSelectWeekList.add("4");
            }else{
                mSelectWeekList.remove("4");
            }

        });
      mBinding.ckWeek4.setOnClickListener(v -> {
            if(mBinding.ckWeek4.isChecked()){
                mSelectWeekList.add("5");
            }else{
                mSelectWeekList.remove("5");
            }

        });

      mBinding.ckWeek5.setOnClickListener(v -> {
            if(mBinding.ckWeek5.isChecked()){
                mSelectWeekList.add("6");
            }else{
                mSelectWeekList.remove("6");
            }

        });
      mBinding.ckWeek6.setOnClickListener(v -> {
            if(mBinding.ckWeek6.isChecked()){
                mSelectWeekList.add("7");
            }else{
                mSelectWeekList.remove("7");
            }

        });

      mBinding.ckWeek7.setOnClickListener(v -> {
            if(mBinding.ckWeek7.isChecked()){
                mSelectWeekList.add("1");
            }else{
                mSelectWeekList.remove("1");
            }
        });

    }


}
