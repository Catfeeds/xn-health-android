package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.healthmanager.acitivitys.AddHelathTaskLisActivity;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthTaskListMdoel;
import com.chengdai.ehealthproject.model.tabmy.model.MyTaskListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.dialog.CommonDialog;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.HashMap;
import java.util.Map;

/**我的健康任务列表
 * Created by 李先俊 on 2017/6/26.
 */
public class MyHelathTaskListActivity extends AbsBaseActivity{

    private CommonRecycleerBinding mBinding;
    private EmptyWrapper mAdapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,MyHelathTaskListActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_recycleer, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("健康任务");

        setSubLeftImgState(true);

        setSubRightTitleAndClick("添加",v -> {
            AddHelathTaskLisActivity.open(this);
        });

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    protected void onResume() {
        super.onResume();
        getListDataRequest();
    }

    public void getListDataRequest(){
        Map<String,String> map=new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("status","1");

        mSubscription.add( RetrofitUtils.getLoaderServer().myTaskList("621167", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(healthTaskListMdoels -> {

                    mAdapter = new EmptyWrapper(new CommonAdapter<MyTaskListModel>(this, R.layout.item_my_health_task,healthTaskListMdoels) {
                        @Override
                        protected void convert(ViewHolder holder, MyTaskListModel model, int position) {
                            if(model == null){
                                return;
                            }

                            if(model.getHealthTask()!=null){
                                ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+model.getHealthTask().getLogo(),holder.getView(R.id.img_addhealth_title));
                                holder.setText(R.id.tv_health_name,model.getHealthTask().getName());
                            }
                            holder.setText(R.id.tv_day_num,"已坚持"+model.getZxNum()+"天");

                            holder.setOnClickListener(R.id.frame_task,v -> {
                                UpdateMyHelathTaskActivity.open(mContext,model);

                            });

                            holder.setOnClickListener(R.id.lin_delete,v -> {

                                showDoubleWarnListen("确认删除这条任务？", view -> {
                                    removeHealthTaskRequest(model.getCode());
                                });
                            });

                        }
                    });

                    mAdapter.setEmptyView(R.layout.empty_view);

                    mBinding.cycler.setAdapter(mAdapter);

                },Throwable::printStackTrace));
    }

    /**
     * 删除任务
     * @param code
     */
    public void removeHealthTaskRequest(String code){

        Map<String,String> map=new HashMap<>();
        map.put("code",code);

     mSubscription.add( RetrofitUtils.getLoaderServer().removeHealthTask("621161",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .filter(isSuccessModes -> isSuccessModes!=null & isSuccessModes.isSuccess())
                .subscribe(codeModel -> {
                    getListDataRequest();
                },Throwable::printStackTrace));

    }

}
