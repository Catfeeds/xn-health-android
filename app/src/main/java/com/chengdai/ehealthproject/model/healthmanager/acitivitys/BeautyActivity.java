package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySurroundingOrderStateBinding;
import com.chengdai.ehealthproject.model.dataadapters.TablayoutAdapter;
import com.chengdai.ehealthproject.model.healthmanager.fragments.BeautySexFragment;
import com.chengdai.ehealthproject.model.tabmy.fragments.HotelOrderRecordFragment;
import com.chengdai.ehealthproject.model.tabmy.fragments.OrderRecordFragment;

/**医学美容
 * Created by 李先俊 on 2017/6/15.
 */

public class BeautyActivity extends AbsBaseActivity {

    private ActivitySurroundingOrderStateBinding mBinding;

    /*Tablayout 适配器*/
    private TablayoutAdapter tablayoutAdapter;



    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }

        Intent intent=new Intent(context,BeautyActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_surrounding_order_state, null, false);

        setTopTitle("形体");

        setSubLeftImgState(true);

        initViewPager();

        addMainView(mBinding.getRoot());

    }

    private void initViewPager() {
        tablayoutAdapter=new TablayoutAdapter(getSupportFragmentManager());
        tablayoutAdapter.addFrag(BeautySexFragment.getInstanse(0), "女生");
        tablayoutAdapter.addFrag(BeautySexFragment.getInstanse(1), "男生");
        mBinding.viewpagerOrder.setAdapter(tablayoutAdapter);
        mBinding.tablayout.setupWithViewPager(mBinding.viewpagerOrder);        //viewpager和tablayout关联
        mBinding.viewpagerOrder.setOffscreenPageLimit(tablayoutAdapter.getCount());
    }
}
