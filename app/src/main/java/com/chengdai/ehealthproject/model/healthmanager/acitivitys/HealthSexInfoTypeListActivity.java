package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.CommonListRefreshNodriverBinding;
import com.chengdai.ehealthproject.model.healthmanager.adapters.HealthInfoListAdapter;
import com.chengdai.ehealthproject.model.healthmanager.adapters.SexHealthInfoListAdapter;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**健康资讯列表分类查询
 * Created by 李先俊 on 2017/6/23.
 */

public class HealthSexInfoTypeListActivity extends AbsBaseActivity {

    private CommonListRefreshNodriverBinding mBinding;

    private SexHealthInfoListAdapter mListAdapter;

    private int mPageStart=1;

    private String mTypeCode;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String typecode){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,HealthSexInfoTypeListActivity.class);

        intent.putExtra("typecode",typecode);//小类

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.common_list_refresh_nodriver, null, false);

        addMainView(mBinding.getRoot());

        if(getIntent() !=null){
            mTypeCode=getIntent().getStringExtra("typecode");
        }


        setTopTitle("资讯");

        setSubLeftImgState(true);

        initViews();

        getListRequest(this);
    }

    private void initViews() {

        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });


        mListAdapter = new SexHealthInfoListAdapter(this,new ArrayList<>());

/*            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout emptyView = (LinearLayout) inflater.inflate(R.layout.empty_view, null);//得到头部的布局

         mBinding.listview.setEmptyView(emptyView);*/
        mBinding.listview.setAdapter(mListAdapter);

        mBinding.listview.setOnItemClickListener((parent, view, position, id) -> {

            SexHealthinfoDetailsActivity.open(this, mListAdapter.getItem(position));

        });

    }


    private void getListRequest(Context c){


        Map<String,String> map=new HashMap<>();
        map.put("start",mPageStart+"");
        map.put("limit","10");
        map.put("kind","2");
        map.put("status","1");
        map.put("type",mTypeCode);

       mSubscription.add( RetrofitUtils.getLoaderServer().getSexHealthInfoList("621105",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(c))
                .subscribe(s -> {
                    if(mPageStart == 1 ){
                        if(s==null || s.getList()==null ){
                            return;
                        }
                        mListAdapter.setData(s.getList());


                    }else{
                        if(s==null || s.getList()==null ||  s.getList().size()==0){
                            if(mPageStart>1){
                                mPageStart--;
                            }
                            return;
                        }
                        mListAdapter.addData(s.getList());
                    }

                },Throwable::printStackTrace));

    }


}
