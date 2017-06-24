package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingMenuselectBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.AssistantMenuListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.adapters.AssistantMenuRightListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.adapters.SexMenuListAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**菜单类型选择
 * Created by 李先俊 on 2017/6/10.
 */

public class SexMenuActivity extends AbsBaseActivity {

    private ActivitySurroundingMenuselectBinding mBinding;

    private SexMenuListAdapter mAdapterLeftMenuList;
    private SexMenuListAdapter mAdapterRightMenuList;



    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,SexMenuActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_menuselect, null, false);


        addMainView(mBinding.getRoot());

        setTopTitle("形体");

        setSubLeftImgState(true);
        initListView();
        leftMenuRequest();

    }

    /**
     * 一级菜单请求
     */
    private void leftMenuRequest() {


        Map<String,String> map=new HashMap();

        map.put("type","2");
        map.put("status","1");
        map.put("parentCode","0");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().getBeautyMenu("621507",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(s -> {

                    mAdapterLeftMenuList.setmSelectPosition(0);

                    mAdapterLeftMenuList.setDatas(s);

                    if(mAdapterLeftMenuList.getSelectItem() !=null){
                        rightMenuRequest(mAdapterLeftMenuList.getSelectItem().getCode());
                    }

                },Throwable::printStackTrace));


    }

    private void initListView() {

        mAdapterLeftMenuList=new SexMenuListAdapter(this,R.layout.item_textview_16sp,new ArrayList<>());
        mBinding.listMenuLeft.setAdapter(mAdapterLeftMenuList);

        mAdapterRightMenuList=new SexMenuListAdapter(this,R.layout.item_textview_16sp_left,new ArrayList<>());
        mBinding.listMenuRight.setAdapter(mAdapterRightMenuList);

        mBinding.listMenuLeft.setOnItemClickListener((parent, view, position, id) -> {
            mAdapterLeftMenuList.setmSelectPosition(position);
            if(mAdapterLeftMenuList.getSelectItem() !=null){
                rightMenuRequest(mAdapterLeftMenuList.getSelectItem().getCode());
            }

        });

        mBinding.listMenuRight.setOnItemClickListener((parent, view, position, id) -> {
            HealthSexInfoTypeListActivity.open(this,mAdapterRightMenuList.getItem(position).getCode());
        });



    }


    /**
     * 二级菜单数据请求
     */
    private void rightMenuRequest(String dkey) {

        Map<String,String> map=new HashMap();

        map.put("type","2");
        map.put("status","1");
        map.put("parentCode",dkey);
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);


        mSubscription.add(RetrofitUtils.getLoaderServer().getBeautyMenu("621507",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(s -> {
                    mAdapterRightMenuList.setDatas(s);
                },Throwable::printStackTrace));


    }
}
