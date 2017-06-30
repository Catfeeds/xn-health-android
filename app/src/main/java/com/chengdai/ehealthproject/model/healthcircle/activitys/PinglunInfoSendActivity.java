package com.chengdai.ehealthproject.model.healthcircle.activitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityPinglunSendBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.LocationModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**帖子评论
 * Created by 李先俊 on 2017/6/21.
 */

public class PinglunInfoSendActivity extends AbsBaseActivity {

    private ActivityPinglunSendBinding mBinding;

    private String mCode;

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String code){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,PinglunInfoSendActivity.class);

        intent.putExtra("code",code);

        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_pinglun_send, null, false);
        addMainView(mBinding.getRoot());

        if(getIntent()!=null){
            mCode=getIntent().getStringExtra("code");
        }

        setTopTitle("");

        setSubRightTitleAndClick("评论",v -> {
            if(TextUtils.isEmpty(mBinding.editInfo.getText().toString())){
                showToast("请输入要品论的内容");
                return;
            }

            sendArticleInfoRequest();

        });


        setSubLeftTitle("取消");

    }


    /**
     * 发布帖子
     */
    private void sendArticleInfoRequest() {

        if(!SPUtilHelpr.isLogin(this))
        {
            return;
        }
        Map<String,String> map=new HashMap<>();

/*"type": "1",
"content": "这帖好有深意啊",
"parentCode": "TZ20170616310173480",
"commer": "U201706081717446351925"*/

         map.put("type","1");
         map.put("content",mBinding.editInfo.getText().toString());
         map.put("parentCode",mCode);
         map.put("commer",SPUtilHelpr.getUserId());

        mSubscription .add( RetrofitUtils.getLoaderServer().SendArticleInfo("621011", StringUtils.getJsonToString(map))
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(codeModel -> {

                    String s=";filter:true";//是否包含敏感词汇
                    if(codeModel!=null && !TextUtils.isEmpty(codeModel.getCode()) && -1== codeModel.getCode().indexOf(s,0)){
                        showToast("评论成功");
                        EventBusModel model=new EventBusModel();
                        model.setTag("LuntanPinglunRefeshEnent");
                        EventBus.getDefault().post(model);
                        finish();
                    }else if(-1!= codeModel.getCode().indexOf(s,0)){
                        showSimpleWran("您的评论存在敏感字符，我们将进行审核。");
                    }else{
                        showToast("评论失败");
                    }


                },Throwable::printStackTrace));
    }

}
