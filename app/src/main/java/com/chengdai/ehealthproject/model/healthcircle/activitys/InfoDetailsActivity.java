package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.LayoutHeadInfoDetailsBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleDetailsModel;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleModel;
import com.chengdai.ehealthproject.model.healthcircle.models.PinglunListModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.views.MyDividerItemDecoration;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**帖子详情
 * Created by 李先俊 on 2017/6/21.
 */

public class InfoDetailsActivity extends AbsBaseActivity {


    private List<PinglunListModel.ListBean> mDatas;

    private int mStartPage=1;

    private LayoutHeadInfoDetailsBinding mBinding;//头布局

    private ArticleDetailsModel mData2;

    private CommonAdapter mAdapter;

    private String mCode;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context, String code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,InfoDetailsActivity.class);
        intent.putExtra("code",code);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_head_info_details, null, false);

        if(getIntent()!=null){
            mCode=getIntent().getStringExtra("code");
        }

        addMainView(mBinding.getRoot());

        setTopTitle("帖子详情");

        setSubLeftImgState(true);

        initViews();

        mBinding.linPinlun.setOnClickListener(v -> {
            PinglunInfoSendActivity.open(this,mCode);
        });

        getTieziDetails(mCode);

    }


    public void getTieziDetails(String code){
        Map<String,String> map=new HashMap<>();
        map.put("code",code);
        map.put("userId", SPUtilHelpr.getUserId());
        map.put("commStatus", "BD");

       mSubscription.add( RetrofitUtils.getLoaderServer().getTieziDetails("621041",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {

                    mData2=data;
                    mBinding.tvDzinfo.setVisibility(View.VISIBLE);
                    setHeadView();
                },throwable -> {
                    mBinding.tvDzinfo.setVisibility(View.VISIBLE);
                }));

    }


    private void initViews() {
        mBinding.springview.setType(SpringView.Type.FOLLOW);
        mBinding.springview.setGive(SpringView.Give.TOP);
        mBinding.springview.setHeader(new DefaultHeader(this));
        mBinding.springview.setFooter(new DefaultFooter(this));

        mBinding.springview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mStartPage=1;
                getPinglunListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mStartPage++;
                getPinglunListRequest(null);
                mBinding.springview.onFinishFreshAndLoad();
            }
        });


        mDatas=new ArrayList<>();

        mAdapter = new CommonAdapter<PinglunListModel.ListBean>(this, R.layout.item_pinlun_activity,mDatas) {

            @Override
            protected void convert(com.zhy.adapter.abslistview.ViewHolder holder, PinglunListModel.ListBean listBean, int position) {
                if(listBean == null) return;

                ImgUtils.loadImgLogo(mContext, MyConfig.IMGURL+listBean.getPhoto(),holder.getView(R.id.img_user_logo));

                holder.setText(R.id.tv_content,listBean.getContent());
                holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getCommDatetime(),DateUtil.DEFAULT_DATE_FMT));
                holder.setText(R.id.tv_name,listBean.getNickname());
            }

        };

        mBinding.listview.setAdapter(mAdapter);

        getPinglunListRequest(this);

    }

    /**
     * 设置头部View布局数据
     */
    private void setHeadView() {

        if(mData2==null){
            return;
        }
        mBinding.tvContent.setText(mData2.getContent());
        mBinding.tvName.setText(mData2.getNickname());
        mBinding.tvTime.setText(DateUtil.formatStringData(mData2.getPublishDatetime(),DateUtil.DEFAULT_DATE_FMT));
        ImgUtils.loadImgLogo(this, MyConfig.IMGURL+mData2.getPhoto(),mBinding.imgUserLogo);
        setNineGridView();

        if(TextUtils.isEmpty(mData2.getAddress())){
            mBinding.linLocation.setVisibility(View.GONE);
        }else{
            mBinding.linLocation.setVisibility(View.VISIBLE);
            mBinding.tvLocation.setText(mData2.getAddress());
        }

        if(mData2.getSumLike()>999){
            mBinding.tvDzNum.setText(mData2.getSumLike()+"+)");
        }else{
            mBinding.tvDzNum.setText("("+mData2.getSumLike()+")");
        }

        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        mBinding.recycler.setAdapter(new com.zhy.adapter.recyclerview.CommonAdapter<ArticleDetailsModel.LikeListBean>(this,R.layout.item_img,mData2.getLikeList()) {
            @Override
            protected void convert(ViewHolder holder, ArticleDetailsModel.LikeListBean likeListBean, int position) {
                if(likeListBean == null){
                    return;
                }

                ImgUtils.loadImgLogo(InfoDetailsActivity.this,MyConfig.IMGURL+likeListBean.getPhoto(),holder.getView(R.id.img));

            }

        });

    }


    private void setNineGridView() {

        if(mData2==null){
            return;
        }

        ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        List<String> pic=new ArrayList<String>();
        pic= StringUtils.splitAsList(mData2.getPic(),"\\|\\|");

        if (pic != null) {
            for (String imageDetail : pic) {
                if(TextUtils.isEmpty(imageDetail)){
                    continue;
                }
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(MyConfig.IMGURL+imageDetail);
                info.setBigImageUrl(MyConfig.IMGURL+imageDetail);
                imageInfo.add(info);
            }
        }
        mBinding.nineGrid.setAdapter(new NineGridViewClickAdapter(this, imageInfo));
    }

    /**
     * 获取评论
     * @param context
     */
    private  void getPinglunListRequest(Context context){
        Map<String,String> map=new HashMap<>();

        if(TextUtils.isEmpty(mCode)){
            return;
        }
        map.put("postCode",mCode);
        map.put("start",mStartPage+"");
        map.put("limit","10");
        map.put("status","BD");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetPinglunLisData("621062", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mStartPage == 1){
                        if(s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mAdapter.notifyDataSetChanged();
                        }
                        if(mAdapter.getCount()==0){
                            mBinding.tvNodata.setVisibility(View.VISIBLE);
                        }else{
                            mBinding.tvNodata.setVisibility(View.GONE);
                        }

                    }else if(mStartPage >1){
                        if(s.getList()==null || s.getList().size()==0){
                            mStartPage--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mAdapter.notifyDataSetChanged();
                    }

                },Throwable::printStackTrace));

    }

    @Subscribe
    public void InfoDetailsActivityRefesh(EventBusModel e){

        if(e==null || !TextUtils.equals(e.getTag(),"LuntanPinglunRefeshEnent")){//刷新评论
            return;
        }
        mStartPage=1;
        getPinglunListRequest(null);
    }


}
