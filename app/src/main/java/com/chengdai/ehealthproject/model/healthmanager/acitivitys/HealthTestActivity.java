package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingOrderStateBinding;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.healthmanager.fragments.TestPageListFragment;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.HashMap;
import java.util.Map;

/**医学美容
 * Created by 李先俊 on 2017/6/15.
 */

public class HealthTestActivity extends AbsBaseActivity {

    private ActivitySurroundingOrderStateBinding mBinding;

    /*Tablayout 适配器*/
    private TablayoutAdapter tablayoutAdapter;


    public static final int TYPE1=1;//健康自测
    public static final int TYPE2=2;//健康风险评估

    private int mType;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,int type){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthTestActivity.class);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_order_state, null, false);

        if(getIntent()!=null){
            mType=getIntent().getIntExtra("type",1);
        }

        if(mType == TYPE1){
            setTopTitle("健康自测");
        }else{
            setTopTitle("疾病风险评估");
        }


        setSubLeftImgState(true);

        initViewPager();

        addMainView(mBinding.getRoot());
        getTestMenuRequest();
    }

    private void initViewPager() {
        tablayoutAdapter=new TablayoutAdapter(getSupportFragmentManager());
    }

    public void getTestMenuRequest(){

        Map<String,String> map=new HashMap<>();

        map.put("type","1");
        if(mType == TYPE1){
            map.put("parentKey","questionare_kind_2");
        }else{
            map.put("parentKey","questionare_kind_3");
        }
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

      mSubscription.add(RetrofitUtils.getLoaderServer().getTestMenu("621906", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(s -> {
                    for(int i=0;i<s.size();i++){
                        if(s.get(i) == null){
                            continue;
                        }
                        tablayoutAdapter.addFrag(TestPageListFragment.getInstanse(i,s.get(i).getDkey(),mType),s.get(i).getDvalue());
                    }

                    mBinding.viewpagerOrder.setAdapter(tablayoutAdapter);
                    mBinding.tablayout.setupWithViewPager(mBinding.viewpagerOrder);        //viewpager和tablayout关联
                    mBinding.viewpagerOrder.setOffscreenPageLimit(tablayoutAdapter.getCount());
                    if(tablayoutAdapter.getCount()>4){
                        mBinding.tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//                       mBinding.tablayout.setTabGravity(GRAVITY_FILL);
                    }

                },Throwable::printStackTrace));

    }

}
