package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthTaskListMdoel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import java.util.HashMap;
import java.util.Map;

/**添加健康任务列表
 * Created by 李先俊 on 2017/6/26.
 */
public class AddHelathTaskLisActivity extends AbsBaseActivity{

    private CommonRecycleerBinding mBinding;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,AddHelathTaskLisActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_recycleer, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("任务列表");

        setSubLeftImgState(true);

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        getListDataRequest();
    }

    public void getListDataRequest(){
        Map<String,String> map=new HashMap<>();
        map.put("userId", SPUtilHelpr.getUserId());

       mSubscription.add( RetrofitUtils.getLoaderServer().getTaskList("621168", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(healthTaskListMdoels -> {

                    EmptyWrapper mAdapter=new EmptyWrapper(new CommonAdapter<HealthTaskListMdoel>(this,R.layout.item_add_health_task,healthTaskListMdoels) {
                        @Override
                        protected void convert(ViewHolder holder, HealthTaskListMdoel model, int position) {
                            if(model == null){
                                return;
                            }

                            ImgUtils.loadImgURL(mContext, MyConfig.IMGURL+model.getLogo(),holder.getView(R.id.img_addhealth_title));
                            holder.setText(R.id.tv_health_name,model.getName());
                            holder.setText(R.id.tv_health_task_info,"已有"+model.getCjNum()+"位参与者");

                            holder.setOnClickListener(R.id.tv_add_health_task,v -> {
                                AddHelathTaskActivity.open(mContext,model);
                            });
                        }
                    });

                    mAdapter.setEmptyView(R.layout.empty_view);

                    mBinding.cycler.setAdapter(mAdapter);

                },Throwable::printStackTrace));
    }


}
