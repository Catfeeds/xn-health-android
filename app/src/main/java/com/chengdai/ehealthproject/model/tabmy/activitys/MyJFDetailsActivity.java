package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityJfDetailsListBinding;
import com.chengdai.ehealthproject.databinding.ActivitySettingBinding;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.tabmy.model.JfDetailsListModel;
import com.chengdai.ehealthproject.model.user.LoginActivity;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**j积分流水
 * Created by 李先俊 on 2017/6/16.
 */

public class MyJFDetailsActivity extends AbsBaseActivity{

    private ActivityJfDetailsListBinding mBinding;

    private String accountNumber;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String jfsum,String accountNumber){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,MyJFDetailsActivity.class);
        intent.putExtra("jfsum",jfsum);//积分
        intent.putExtra("accountNumber",accountNumber);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_jf_details_list, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("积分记录");
        setSubLeftImgState(true);

        if(getIntent()!=null){
            mBinding.tvJfnum.setText(getIntent().getStringExtra("jfsum"));
            accountNumber=getIntent().getStringExtra("accountNumber");
        }

        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        getJfDetailsList();
    }

    public void getJfDetailsList() {

        Map<String,String> map=new HashMap<>();
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("token", SPUtilHelpr.getUserToken());
        map.put("accountNumber", accountNumber);
        map.put("accountType", "C");
        map.put("currency","JF");
        map.put("start","1");
        map.put("limit","10");

       mSubscription.add( RetrofitUtils.getLoaderServer().getJFDetailsList("802520", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(s -> {

                    mBinding.cycler.setAdapter(new CommonAdapter<JfDetailsListModel.ListBean>(this,R.layout.item_jf_deails,s.getList()) {
                        @Override
                        protected void convert(ViewHolder holder, JfDetailsListModel.ListBean listBean, int position) {
                            if(listBean==null){
                                return;
                            }
                            holder.setText(R.id.tv_name,listBean.getBizNote());
                            holder.setText(R.id.tv_sum,StringUtils.showPrice(listBean.getTransAmount()));
                            holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getCreateDatetime(),DateUtil.DEFAULT_DATE_FMT));

                        }
                    });

                },Throwable::printStackTrace));

    }
}
