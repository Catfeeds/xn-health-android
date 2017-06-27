package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivitySendInfoBinding;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.LogUtil;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.qiniu.QiNiuUtil;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.dialog.LoadingDialog;
import com.qiniu.android.http.ResponseInfo;
import com.yanzhenjie.album.Album;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**发帖
 * Created by 李先俊 on 2017/6/20.
 */

public class SendEditInfoActivity extends AbsBaseActivity{

    private ActivitySendInfoBinding mBinding;

    private final int mStartPoToFlag=105;

    private ArrayList<String> mSelectPhotoUrls=new ArrayList<>();//我选择的图片地址
    private ArrayList<String> mQiniuSelectPhotoUrls=new ArrayList<>();//七牛图片地址

    private CommonAdapter commonAdapter;
    private LoadingDialog loadingDialog;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,SendEditInfoActivity.class);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_send_info, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("");

        setSubLeftTitle("取消");
        mBinding.grid.post(new TimerTask() {
            @Override
            public void run() {
               int width=mBinding.grid.getWidth()-2*5/3;
                mBinding.grid.setItemHeight(width);
                mBinding.grid.setItemWidth(width);
                mBinding.grid.invalidate();

            }
        });

        initViews();
    }


    private void initViews() {
        mSelectPhotoUrls.add("");//添加默认图片
        setAdapter();

        setSubRightTitleAndClick("发布",v -> {

            if(!SPUtilHelpr.isLogin(this)){
                return;
            }

            if(TextUtils.isEmpty(mBinding.editInfo.getText().toString())){
                showToast("请输入要发布的内容");
                return;
            }

            if(mSelectPhotoUrls .size() == 0 || mSelectPhotoUrls.size()==1){
                sendArticleInfoRequest();
                return;
            }

            loadingDialog = new LoadingDialog(this);
            loadingDialog.show();


            if(mSelectPhotoUrls.size()>0){ //删除最后一个
                mSelectPhotoUrls.remove(mSelectPhotoUrls.size()-1);
            }

            QiNiuUtil qiNiuUtil = new QiNiuUtil(this);

            qiNiuUtil .getQiniuToeknRequest().subscribe(qiniuGetTokenModel -> {
                qiNiuUtil.updataeImage(mSelectPhotoUrls, qiniuGetTokenModel.getUploadToken(), new QiNiuUtil.QiNiuCallBack() {
                    @Override
                    public void onSuccess(String key, ResponseInfo info, JSONObject res) {
                        mQiniuSelectPhotoUrls.add(key);
                        if(mQiniuSelectPhotoUrls.size() == mSelectPhotoUrls.size()){
                            loadingDialog.dismiss();
                            sendArticleInfoRequest();
                        }
                    }
                    @Override
                    public void onFal(String info) {
                        showToast("发布失败");
                           loadingDialog.dismiss();
                    }
                });

            });

        });


    }

    private void startPotoSelect() {
        Album.album(this)
                .requestCode(mStartPoToFlag) // 请求码，返回时onActivityResult()的第一个参数。
                .toolBarColor(ContextCompat.getColor(this, R.color.activity_title_bg)) // Toolbar 颜色，默认蓝色。
                .statusBarColor(ContextCompat.getColor(this,R.color.activity_title_bg)) // StatusBar 颜色，默认蓝色。
//                    .navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                .title("选择图片") // 配置title。
                .selectCount(9) // 最多选择几张图片。
                .columnCount(3) // 相册展示列数，默认是2列。
                .camera(true) // 是否有拍照功能。
                .checkedList(mSelectPhotoUrls) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                .start();

    }

    /**
     * 发布帖子
     */
    private void sendArticleInfoRequest() {

        Map<String,String> map=new HashMap<>();

        StringBuffer sb=new StringBuffer();

        for(int i=0;i<mQiniuSelectPhotoUrls.size();i++){
            sb.append(mQiniuSelectPhotoUrls.get(i));
            if(i != mQiniuSelectPhotoUrls.size()-1){
                sb.append("||");
            }
        }

        map.put("title","健康圈");
        map.put("content",mBinding.editInfo.getText().toString());
        map.put("pic",sb.toString());
        map.put("publisher", SPUtilHelpr.getUserId());

        LocationModel locationModel =SPUtilHelpr.getLocationInfo();
        if(locationModel !=null){
            map.put("address", locationModel.getProvinceName()+"."+locationModel.getCityName()+"."+locationModel.getAreaName());
        }else if(!TextUtils.isEmpty(SPUtilHelpr.getResetLocationInfo().getCityName())){
            map.put("address", SPUtilHelpr.getResetLocationInfo().getCityName());
        }else{
            map.put("address","");
        }

      mSubscription .add( RetrofitUtils.getLoaderServer().SendArticleInfo("621010", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(codeModel -> {

                    if(codeModel!=null && !TextUtils.isEmpty(codeModel.getCode())){
                        showToast("发布成功");



                        finish();
                    }else{
                        showToast("发布失败");
                    }

                },throwable -> {

                }));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == mStartPoToFlag) {
            if (resultCode == RESULT_OK) { // Successfully.

                ArrayList<String> pathList = Album.parseResult(data);

                    if(pathList != null && pathList.size()>0){
                        mSelectPhotoUrls.clear();
                        mSelectPhotoUrls.addAll(pathList);
                        if(mSelectPhotoUrls.size()<10){
                            mSelectPhotoUrls.add("");
                        }
                    }


                setAdapter();

            } else if (resultCode == RESULT_CANCELED) { // User canceled.

            }
        }


/*        AppOhterManager.getQiniuURL(this, path, new QiNiuUtil.QiNiuCallBack() {
            @Override
            public void onSuccess(String key, ResponseInfo info, JSONObject res) {
                showToast("成功");
                mSelectPhotoUrls.add(MyConfig.IMGURL+key);

                myNineGridAdapter = new MyNineGridAdapter(SendEditInfoActivity.this,mSelectPhotoUrls);
                mBinding.ivNgridLayout.setAdapter(myNineGridAdapter);
                mBinding.ivNgridLayout.setGap(10);

            }

            @Override
            public void onFal(String info) {
                showToast("失败");
            }
        });*/


    }

    private void setAdapter() {
        commonAdapter = new CommonAdapter<String>(this, R.layout.badge_layout,mSelectPhotoUrls) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

                if(( mDatas.size()==1 || position == mDatas.size()-1) && TextUtils.isEmpty(mSelectPhotoUrls.get(position))   ){
                    viewHolder.getView(R.id.img_delete).setVisibility(View.GONE);
                    viewHolder.setOnClickListener(R.id.img_delete,null);
                    ImgUtils.loadImgId(SendEditInfoActivity.this,R.mipmap.add,viewHolder.getView(R.id.img_bg));
                }else{
                    ImgUtils.loadDeful(SendEditInfoActivity.this,item,viewHolder.getView(R.id.img_bg));
                    viewHolder.getView(R.id.img_delete).setVisibility(View.VISIBLE);
                    viewHolder.setOnClickListener(R.id.img_delete,v1 -> {

                        mSelectPhotoUrls.remove(position);

                        if(mSelectPhotoUrls.size()>0){
                            mSelectPhotoUrls.remove(mSelectPhotoUrls.size()-1);
                        }
                        mSelectPhotoUrls.add("");
                        setAdapter();
                    });
                }

                if(position==mDatas.size()-1 && TextUtils.isEmpty(mDatas.get(position))){

                    viewHolder.setOnClickListener(R.id.img_bg,v1 -> {

                            if(mSelectPhotoUrls.size()>0){
                                mSelectPhotoUrls.remove(mSelectPhotoUrls.size()-1);
                            }
                            startPotoSelect();
                    });

                }else{
                    viewHolder.setOnClickListener(R.id.img_bg,null);
                }
            }
        };

        mBinding.grid.bindView(commonAdapter);
    }


}
