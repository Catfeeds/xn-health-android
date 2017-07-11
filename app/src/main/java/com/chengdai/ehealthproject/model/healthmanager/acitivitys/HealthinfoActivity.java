package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityHealthInfoBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthInfoListAdapter;
import com.chengdai.ehealthproject.model.tabmy.activitys.SettingActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingStoreTypeAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**健康资讯
 * Created by 李先俊 on 2017/6/23.
 */

public class HealthinfoActivity extends AbsBaseActivity {

    private ActivityHealthInfoBinding mBinding;

    private SurroundingStoreTypeAdapter mMenuAdapter;
    private HealthInfoListAdapter mListAdapter;

    private int mPageStart=1;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthinfoActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_health_info, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("健康资讯");

        setSubLeftImgState(true);

        initViews();

        getMenuRequest(null);
        getListRequest(this);
    }

    private void initViews() {

        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));

        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getMenuRequest(null);
                getListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getListRequest(null);
                getMenuRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });

        mMenuAdapter = new SurroundingStoreTypeAdapter(this,new ArrayList<>());
        mBinding.gridmenu.setAdapter(mMenuAdapter);


        mBinding.gridmenu.setOnItemClickListener((parent, view, position, id) -> {
            StoreTypeModel model= (StoreTypeModel) mMenuAdapter.getItem(position);
            if(model!=null){
                HealthinfoTypeListActivity.open(this,model.getType(),model.getCode());
            }

        });

        mListAdapter = new HealthInfoListAdapter(this,new ArrayList<>());
        mBinding.lvInfo.setAdapter(mListAdapter);

        mBinding.lvInfo.setOnItemClickListener((parent, view, position, id) -> {

            HealthinfoDetailsActivity.open(this, mListAdapter.getItem(position));

        });

    }


    private void getListRequest(Context c){

        Map<String,String> map=new HashMap<>();

        map.put("start",mPageStart+"");
        map.put("limit","10");
//        map.put("type","2");
        map.put("location","2");//1普通 2热门
        map.put("status","1");
        map.put("kind","1");//健康资讯

       mSubscription.add( RetrofitUtils.getLoaderServer().getHealthInfoList("621105",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s==null || s.getList()==null){
                            return;
                        }
                        mListAdapter.setData(s.getList());
                    }else{
                        if(s==null || s.getList()==null|| s.getList().size()==0){
                            if(mPageStart>1){
                                mPageStart--;
                            }
                            return;
                        }
                        mListAdapter.addData(s.getList());
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 获取菜单分类接口
     * @param context
     */
    private void getMenuRequest(Context context) {


        Map<String,String> map=new HashMap();

        map.put("parentCode","0");
        map.put("type","1");            //1 -健康资讯
        map.put("status","1");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);


        mSubscription.add( RetrofitUtils.getLoaderServer().GetStoreType("621507", StringUtils.getJsonToString(map))

                .compose(RxTransformerListHelper.applySchedulerResult(context))

                .filter(storeTypeModels -> storeTypeModels!=null)
                .subscribe(r -> {
                    mMenuAdapter.setDatas(r);
                },Throwable::printStackTrace));

    }


}
