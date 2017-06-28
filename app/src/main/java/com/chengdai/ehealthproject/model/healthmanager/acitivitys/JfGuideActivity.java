package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBmiCalculateBinding;
import com.chengdai.ehealthproject.databinding.ActivityJfGuideBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.activitys.IntroductionActivity;
import com.chengdai.ehealthproject.model.healthmanager.model.JfGuideListModel;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyHelathTaskListActivity;
import com.chengdai.ehealthproject.model.tabmy.activitys.MyJFDetailsActivity;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**积分引导
 * Created by 李先俊 on 2017/6/16.
 */

public class JfGuideActivity extends AbsBaseActivity{

    private ActivityJfGuideBinding mBinding;

    private String mJfaccountNumber;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String jfnum,String jfaccountNumber){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,JfGuideActivity.class);
         intent.putExtra("jfnum",jfnum);
         intent.putExtra("jfaccountNumber",jfaccountNumber);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_jf_guide, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("我的积分");
        setSubLeftImgState(true);
        setSubRightTitleAndClick("积分规则",v -> {
            IntroductionActivity.open(this,"integralRule","积分规则");
        });

        if(getIntent()!=null){
            mBinding.tvJfnum.setText(getIntent().getStringExtra("jfnum"));
            mJfaccountNumber=getIntent().getStringExtra("jfaccountNumber");
        }

        mBinding.tvJfDetails.setOnClickListener(v -> {
            MyJFDetailsActivity.open(this,mBinding.tvJfnum.getText().toString(),mJfaccountNumber);
        });
        mBinding.cycler.addItemDecoration(new MyDividerItemDecoration(this,MyDividerItemDecoration.VERTICAL_LIST));
        mBinding.cycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        getListDate();
    }

    /**
     * 获取积分数据
     */
    public void  getListDate(){

        Map<String,String> map=new HashMap<>();

        map.put("type","1");
        map.put("systemCode", MyConfig.SYSTEMCODE);
        map.put("start", "1");
        map.put("limit", "50");

       mSubscription.add(RetrofitUtils.getLoaderServer().getJfGuideList("621915", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
               .filter(jfGuideListModel -> jfGuideListModel!=null && jfGuideListModel.getList()!=null)
               .map(jfGuideListModel -> {

                   JfGuideListModel.ListBean model=new JfGuideListModel.ListBean();
                   model.setCkey("scgw");
                   model.setNote("商城购物");
                   model.setCvalue("1:1分值");

                   List<JfGuideListModel.ListBean> data=new ArrayList<JfGuideListModel.ListBean>();
                   data.add(model);
                   data.addAll(jfGuideListModel.getList());
                   return data;
               })
                .subscribe(s -> {

                    mBinding.cycler.setAdapter(new CommonAdapter<JfGuideListModel.ListBean>(this,R.layout.item_jf_guide,s) {
                        @Override
                        protected void convert(ViewHolder holder, JfGuideListModel.ListBean listBean, int position) {
                            if(listBean == null){
                                return;
                            }
                            holder.setText(R.id.tv_title,listBean.getNote());
                            holder.setText(R.id.tv_jfnum,"+"+listBean.getCvalue());

                            holder.setOnClickListener(R.id.tv_start_jf,v -> {

                                //点击转赚取积分
                                switch (listBean.getCkey()){
                                    case "jkrw":  //健康任务
                                        MyHelathTaskListActivity.open(mContext);
                                        break;
                                    case "jbfxpg":  //疾病风险评估
                                        HealthTestActivity.open(mContext,HealthTestActivity.TYPE2);
                                        break;
                                    case "jkpfcs":  //健康测试评分
//                                        HealthDoTestQuesitionActivity.open(mContext,mTestCode,mTestTitle);

                                        EventBusModel eventBusMode3=new EventBusModel(); //结束积分商店页
                                        eventBusMode3.setTag("SHopJfActivityFinish");
                                        EventBus.getDefault().post(eventBusMode3);

                                       finish();

                                        break;
                                    case "jkzc":  //健康自测
                                        HealthTestActivity.open(mContext,HealthTestActivity.TYPE1);
                                        break;
                                    case "ft":  //发帖
                                    case "tzbpl":  //帖子被评论
                                    case "tzbdz":  //帖子被点赞
                                    case "pl":  //评论
                                    case "dz":  //点赞

                                        EventBusModel eventBusMode2=new EventBusModel(); //结束积分商店页
                                        eventBusMode2.setTag("SHopJfActivityFinish");
                                        EventBus.getDefault().post(eventBusMode2);

                                        EventBusModel eventBusModel=new EventBusModel();//设置main显示
                                        eventBusModel.setEvInt(2);
                                        eventBusModel.setTag("MainSetIndex");
                                        EventBus.getDefault().post(eventBusModel);

                                        finish();
                                        break;
                                    case "scgw"://商城购物
                                        EventBusModel eventBusMode4=new EventBusModel(); //结束积分商店页
                                        eventBusMode4.setTag("SHopJfActivityFinish");
                                        EventBus.getDefault().post(eventBusMode4);

                                        EventBusModel eventBusModel5=new EventBusModel();//设置main显示
                                        eventBusModel5.setEvInt(4);
                                        eventBusModel5.setTag("MainSetIndex");
                                        EventBus.getDefault().post(eventBusModel5);

                                        finish();
                                        break;
                                }
                            });
                        }
                    });

                },Throwable::printStackTrace));

    }

}
