package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingMenuselectBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.AssistantMenuListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.adapters.AssistantMenuRightListAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.activitys.StoreTypeActivity;
import com.chengdai.ehealthproject.model.tabsurrounding.adapters.SurroundingMenuLeftAdapter;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

/**菜单类型选择
 * Created by 李先俊 on 2017/6/10.
 */

public class AssistantMenuActivity extends AbsBaseActivity {

    private ActivitySurroundingMenuselectBinding mBinding;

    private AssistantMenuListAdapter mAdapterLeftMenuList;
    private AssistantMenuRightListAdapter mAdapterRightMenuList;



    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,AssistantMenuActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_menuselect, null, false);


        addMainView(mBinding.getRoot());

        setTopTitle("食物计算器");

        setSubLeftImgState(true);
        initListView();
        leftMenuRequest();

    }

    /**
     * 一级菜单请求
     */
    private void leftMenuRequest() {


        Map<String,String> map=new HashMap();

        map.put("type","1");
        map.put("parentKey","calorie_kind");
        map.put("companyCode",MyConfig.COMPANYCODE);
        map.put("systemCode", MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().getHealthAssistantMenu("621906",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(s -> {

                    mAdapterLeftMenuList.setmSelectPosition(0);

                    mAdapterLeftMenuList.setDatas(s);

                    if(mAdapterLeftMenuList.getSelectItem() !=null){
                        rightMenuRequest(mAdapterLeftMenuList.getSelectItem().getDkey());
                    }

                },Throwable::printStackTrace));


    }

    private void initListView() {

        mAdapterLeftMenuList=new AssistantMenuListAdapter(this,new ArrayList<>());
        mBinding.listMenuLeft.setAdapter(mAdapterLeftMenuList);

        mAdapterRightMenuList=new AssistantMenuRightListAdapter(this,new ArrayList<>());
        mBinding.listMenuRight.setAdapter(mAdapterRightMenuList);

        mBinding.listMenuLeft.setOnItemClickListener((parent, view, position, id) -> {
            mAdapterLeftMenuList.setmSelectPosition(position);
            if(mAdapterLeftMenuList.getSelectItem() !=null){
                rightMenuRequest(mAdapterLeftMenuList.getSelectItem().getDkey());
            }

        });

        mBinding.listMenuRight.setOnItemClickListener((parent, view, position, id) -> {
            FoodCalculateActivity.open(this,mAdapterRightMenuList.getItem(position));
        });



    }


    /**
     * 二级菜单数据请求
     */
    private void rightMenuRequest(String dkey) {

        Map<String,String> map=new HashMap();

        map.put("type",dkey);

        mSubscription.add(RetrofitUtils.getLoaderServer().getFoodHotData("621117",StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(s -> {
                    mAdapterRightMenuList.setDatas(s);
                },Throwable::printStackTrace));


    }
}
