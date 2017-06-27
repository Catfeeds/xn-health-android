package com.chengdai.ehealthproject.model.healthcircle.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.BaseLazyFragment;
import com.chengdai.ehealthproject.databinding.CommonRecycleerBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.healthcircle.activitys.InfoDetailsActivity;
import com.chengdai.ehealthproject.model.healthcircle.activitys.PersonalLuntanActivity;
import com.chengdai.ehealthproject.model.healthcircle.activitys.PinglunListActivity;
import com.chengdai.ehealthproject.model.healthcircle.models.ArticleModel;
import com.chengdai.ehealthproject.uitls.DateUtil;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.EmptyWrapper;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**健康圈热门帖子
 * Created by 李先俊 on 2017/6/8.
 */

public class HealthCircleHotFragment extends BaseLazyFragment{

    private CommonRecycleerBinding mBinding;

    private boolean isCreate;

    private List<ArticleModel.ListBean> mDatas;

    public static final String HOTTYPE="2";//热门
    public static final String ALLTYPE="1";//全部

    private int mPageStart;

    private String mType;
    private EmptyWrapper mEmptyWrapper;


    /**
     * 获得fragment实例
     * @return
     */
    public static HealthCircleHotFragment getInstanse(String type){
        HealthCircleHotFragment fragment=new HealthCircleHotFragment();

        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(getLayoutInflater(savedInstanceState), R.layout.common_recycleer, null, false);

        isCreate=true;

        mPageStart=1;
        mDatas=new ArrayList<>();

        if(getArguments()!=null) mType=getArguments().getString("type");

        initViews();

        setShowData();

        getDataRequest(mActivity);

        return mBinding.getRoot();

    }

    private void initViews() {

        mBinding.cycler.setLayoutManager(new LinearLayoutManager(mActivity,LinearLayoutManager.VERTICAL,false));
        mBinding.springvew.setType(SpringView.Type.FOLLOW);
        mBinding.springvew.setGive(SpringView.Give.TOP);
        mBinding.springvew.setHeader(new DefaultHeader(mActivity));
        mBinding.springvew.setFooter(new DefaultFooter(mActivity));
        mBinding.springvew.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mPageStart=1;
                getDataRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                mPageStart++;
                getDataRequest(null);
                mBinding.springvew.onFinishFreshAndLoad();
            }
        });
    }


    @Override
    protected void lazyLoad() {

        if (isCreate){

            isCreate=false;
        }

    }

    @Override
    protected void onInvisible() {
    }


    public void getDataRequest(Context context){

        Map<String,String> map=new HashMap<>();

        map.put("userId", SPUtilHelpr.getUserId());
        map.put("location",mType);
        map.put("status","BD");//审核通过并发布
        map.put("start",mPageStart+"");
        map.put("limit","10");

        mSubscription.add(RetrofitUtils.getLoaderServer().GetArticleLisData("621040", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(context))
                .subscribe(s -> {
                    if(mPageStart == 1){
                        if(s==null||s.getList()!=null){
                            mDatas.clear();
                            mDatas.addAll(s.getList());
                            mEmptyWrapper.notifyDataSetChanged();
                        }

                        if(mDatas.size()==0){
                            mEmptyWrapper.setEmptyView(R.layout.empty_view);
                        }

                    }else if(mPageStart >1){
                        if(s==null||s.getList()==null || s.getList().size()==0){
                            mPageStart--;
                            return;
                        }
                        mDatas.addAll(s.getList());
                        mEmptyWrapper.notifyDataSetChanged();
                    }
                },throwable -> {
                    mEmptyWrapper.setEmptyView(R.layout.empty_view);
                }));

    }

    /**
     * 设置显示数据
     */
    private void setShowData() {
        CommonAdapter mAdapter=new CommonAdapter<ArticleModel.ListBean>(mActivity, R.layout.item_luntan,mDatas) {
            @Override
            protected void convert(ViewHolder holder, ArticleModel.ListBean listBean, int position) {

                if(listBean == null){
                    return;
                }

                holder.setOnClickListener(R.id.lin_shear,v -> {
                    ToastUtil.show(mActivity,"分享");
                });

                //个人帖子
                holder.setOnClickListener(R.id.img_user_logo,v -> {

                    if(!SPUtilHelpr.isLogin(mContext)){
                        return;
                    }
                    PersonalLuntanActivity.open(mActivity,listBean.getPublisher());
                });

                //帖子详情
                holder.setOnClickListener(R.id.line_detail,v -> {
                    InfoDetailsActivity.open(mContext,listBean);
                });

               //点赞
                holder.setOnClickListener(R.id.lin_DZ,v -> {
                    DZRequest(listBean,position);
                });

               //评论列表
                setpinlunlist(holder, listBean);

                 //九宫格
                setNineGridView(holder, listBean);

                if(TextUtils.equals(listBean.getIsDZ(),"1")){//已点赞
                    ImgUtils.loadImgId(mActivity,R.mipmap.good_hand_,holder.getView(R.id.img_isDZ));
                }else{
                    ImgUtils.loadImgId(mActivity,R.mipmap.good_hand_un,holder.getView(R.id.img_isDZ));
                }

                /**
                 * 设置数据显示状态
                 */
                setShowState(holder, listBean);
            }

            private void setShowState(ViewHolder holder, ArticleModel.ListBean listBean) {
                ImgUtils.loadImgLogo(mActivity, MyConfig.IMGURL+listBean.getPhoto(),holder.getView(R.id.img_user_logo));
                holder.setText(R.id.tv_name,listBean.getNickname());
                holder.setText(R.id.tv_time, DateUtil.formatStringData(listBean.getPublishDatetime(),DateUtil.DEFAULT_DATE_FMT));

                holder.setText(R.id.tv_content,listBean.getContent());

                if(listBean.getSumLike()>999){
                    holder.setText(R.id.tv_dz_sum,listBean.getSumLike()+"+");
                }else{
                    holder.setText(R.id.tv_dz_sum,listBean.getSumLike()+"");
                }
               if(listBean.getSumComment()>999){
                    holder.setText(R.id.tv_pinlun,listBean.getSumComment()+"+");
                }else{
                    holder.setText(R.id.tv_pinlun,listBean.getSumComment()+"");
                }

                if(TextUtils.isEmpty(listBean.getAddress())){
                   holder.setVisible(R.id.lin_location,false);
                }else{
                    holder.setVisible(R.id.lin_location,true);
                    holder.setText(R.id.tv_location,listBean.getAddress());
                }
            }

            private void setNineGridView(ViewHolder holder, ArticleModel.ListBean listBean) {
                NineGridView b=holder.getView(R.id.nineGrid);
                ArrayList<ImageInfo> imageInfo = new ArrayList<>();

                List<String> pic=new ArrayList<String>();
                pic= StringUtils.splitAsList(listBean.getPic(),"\\|\\|");

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
                b.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
            }

            private void setpinlunlist(ViewHolder holder, ArticleModel.ListBean listBean) {
                ListView lv_pinlun=holder.getView(R.id.lv_pinlun);
                List<ArticleModel.ListBean.CommentListBean> commentListBeanList=new ArrayList<ArticleModel.ListBean.CommentListBean>();
                if(listBean.getCommentList()!=null && listBean.getCommentList().size()>0){

                    holder.setVisible(R.id.lin_pinlun,true);

                    int i=0;
                    for(ArticleModel.ListBean.CommentListBean bean:listBean.getCommentList()){  //最多显示3条评论
                        if(i==3){
                            break;
                        }
                        commentListBeanList.add(bean);
                        i++;
                    }
                     //设置品论数据
                    lv_pinlun.setAdapter(new com.zhy.adapter.abslistview.CommonAdapter<ArticleModel.ListBean.CommentListBean>(mActivity,R.layout.item_pinlun,commentListBeanList) {
                        @Override
                        protected void convert(com.zhy.adapter.abslistview.ViewHolder viewHolder, ArticleModel.ListBean.CommentListBean item, int position) {

                            if(item== null){
                                return;
                            }

                            viewHolder.setText(R.id.tv_name,item.getNickname());
                            viewHolder.setText(R.id.tv_content,": "+item.getContent());
                        }

                    });
                    if(listBean.getCommentList().size()==1){      //只有一条评论时隐藏查看所有
                        holder.setVisible(R.id.lin_pinlun,false);
                    }else{
                        holder.setVisible(R.id.lin_pinlun,true);
                        holder.setOnClickListener(R.id.tv_look_all,v -> {
                            PinglunListActivity.open(mActivity,listBean.getCode());
                        });
                    }

                }else{
                    holder.setVisible(R.id.lin_pinlun,false);
                }
            }
        };

        mEmptyWrapper = new EmptyWrapper(mAdapter);

        mBinding.cycler.setAdapter(mEmptyWrapper);

    }


    /**
     * 点赞
     */
    public void DZRequest(ArticleModel.ListBean item,int position){

        if(!SPUtilHelpr.isLogin(mActivity)){
            return;
        }

        Map map=new HashMap();

        map.put("type","1");        /*1 点赞 2 收藏*/
        map.put("postCode",item.getCode());
        map.put("userId",SPUtilHelpr.getUserId());

        mSubscription.add(RetrofitUtils.getLoaderServer().DZRequest("621015",StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(mActivity))
                .filter(isSuccessModes -> isSuccessModes!=null && isSuccessModes.isSuccess())
                .subscribe(isSuccessModes -> {
                    if(TextUtils.equals("1",item.getIsDZ())){
                        item.setIsDZ("0");
                        int sum=item.getSumLike();
                        if(sum>0){
                            sum--;
                            item.setSumLike(sum);
                        }

                    }else{
                        int sum=item.getSumLike();
                            sum++;
                            item.setSumLike(sum);
                        item.setIsDZ("1");
                    }
                    mEmptyWrapper.notifyItemChanged(position,position);
                },Throwable::printStackTrace));

    }

    @Subscribe
    public void HotFragmentEnent(EventBusModel model){
        if(model== null){
            return;
        }
        if(TextUtils.equals(model.getTag(),"LuntanDzRefeshEnent") || TextUtils.equals(model.getTag(),"LuntanPinglunRefeshEnent")){//点赞刷新
            mPageStart=1;
            getDataRequest(null);

        }
    }

}
