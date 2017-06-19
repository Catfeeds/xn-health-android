package com.chengdai.ehealthproject.model.healthstore.acitivtys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityJfShopBinding;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopHotJfAdapter;
import com.chengdai.ehealthproject.model.healthstore.adapters.ShopJfAdapter;
import com.chengdai.ehealthproject.model.healthstore.models.ShopListModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**积分商城列表
 * Created by 李先俊 on 2017/6/19.
 */

public class ShopJfActivity extends AbsBaseActivity {

    private ActivityJfShopBinding mBinding;

    private int mStoreStart=1;

    private List<ShopListModel.ListBean> mHotListData=new ArrayList<>();//热门兑换数据
    private List<ShopListModel.ListBean> mJlistData=new ArrayList<>();//积分兑换数据

    private ShopHotJfAdapter mHotAdapter;
    private ShopJfAdapter mJfAdapter;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShopJfActivity.class);

        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_jf_shop, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("积分商城");

        initListView();

        setSubLeftImgState(true);

        getUserInfoRequest(null);
        getJifenRequest(null);
        getStoreListRequest(this);

    }

    private void initListView() {



        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(this));
        mBinding.springvew.setFooter(new DefaultFooter(this));

        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStoreStart=1;
                getUserInfoRequest(null);
                getJifenRequest(null);
                getStoreListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStoreStart++;
                getStoreListRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });

        mHotAdapter=new ShopHotJfAdapter(this,new ArrayList<>());
        mJfAdapter=new ShopJfAdapter(this,new ArrayList<>());

        mBinding.listHot.setAdapter(mHotAdapter);
        mBinding.listJf.setAdapter(mJfAdapter);

        mBinding.listJf.setOnItemClickListener((parent, view, position, id) -> {

            ShopJfDetailsActivity.open(this, mJfAdapter.getItem(position));
        });

        mBinding.listHot.setOnItemClickListener((parent, view, position, id) -> {
            ShopJfDetailsActivity.open(this,mHotAdapter.getItem(position));
        });

    }


    /**
     * 获取商城列表
     * @param act
     */
    public void getStoreListRequest(Activity act){
        Map map=new HashMap();

        map.put("kind","2"); //1 标准商城 2 积分商城
        map.put("status","3");//已上架
        map.put("start",mStoreStart+"");
        map.put("limit","10");
        map.put("companyCode", MyConfig.COMPANYCODE);
        map.put("systemCode",MyConfig.SYSTEMCODE);

        mSubscription.add(RetrofitUtils.getLoaderServer().GetShopList("808025", StringUtils.getJsonToString(map))

                .compose(RxTransformerHelper.applySchedulerResult(act))

                .filter(storeListModel -> storeListModel!=null && storeListModel.getList()!=null)

                .map(shopListModel -> shopListModel.getList())

                .flatMap(listBean -> {

                    if(mStoreStart==1){
                        mHotListData.clear();
                        mJlistData.clear();
                    }

                    if(listBean.size() == 0){
                        if(mStoreStart>1){
                            mStoreStart--;
                        }
                    }

                 return    Observable.fromIterable(listBean);
                } ) .filter(beanData-> beanData!=null)

                .doOnComplete(() -> {

                    if(mStoreStart == 1){
                        mHotAdapter.setData(mHotListData);
                        mJfAdapter.setData(mJlistData);
                    }else {
                        mHotAdapter.addData(mHotListData);
                        mJfAdapter.addData(mJlistData);
                    }

                    mBinding.tvJfchange.setVisibility(View.VISIBLE);
                    mBinding.tvHotChange.setVisibility(View.VISIBLE);

                }) .subscribe(beanData -> {
                    if(TextUtils.equals(beanData.getLocation(),"1")){
                        mHotListData.add(beanData);
                    }else if(TextUtils.equals(beanData.getLocation(),"0")){
                        mJlistData.add(beanData);
                    }

                },Throwable::printStackTrace));

    }

    /**
     * 获取积分请求
     */
    private  void getJifenRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("currency","JF");
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().getAmount("802503", StringUtils.getJsonToString(map))
                .compose(RxTransformerListHelper.applySchedulerResult(context))
                .filter(r -> r !=null && r.size() >0  && r.get(0)!=null)
                .subscribe(r -> {
                    mBinding.tvJf.setText(StringUtils.showPrice(r.get(0).getAmount()));

                },Throwable::printStackTrace));

    }


    /**
     * 获取用户信息请求
     */
    private  void getUserInfoRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("token",SPUtilHelpr.getUserToken());

        mSubscription.add( RetrofitUtils.getLoaderServer().GetUserInfo("805056", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))

                .filter(r -> r!=null)

                .subscribe(r -> {

                    mBinding.linlayoutHead.setVisibility(View.VISIBLE);

                    mBinding.txtName.setText(r.getLoginName());

                    if(r.getUserExt() == null) return;

                    ImgUtils.loadImgURL(context, MyConfig.IMGURL+r.getUserExt().getPhoto(),mBinding.imgUserLogo);
                    if("0".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(context,R.mipmap.man,mBinding.imgSex);
                    }else if ("1".equals(r.getUserExt().getGender())){
                        ImgUtils.loadImgId(context,R.mipmap.woman,mBinding.imgSex);
                    }

                },Throwable::printStackTrace));


    }

}
