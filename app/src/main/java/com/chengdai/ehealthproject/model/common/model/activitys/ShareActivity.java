package com.chengdai.ehealthproject.model.common.model.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.ActivityShareBinding;
import com.chengdai.ehealthproject.uitls.WxUtil;

/**
 * Created by 李先俊 on 2017/8/1.
 */

public class ShareActivity extends Activity {

    private ActivityShareBinding mbinding;

    private String mShareUrl;//需要分享的URL

    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context,String shareUrl){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,ShareActivity.class);
        intent.putExtra("shareUrl",shareUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding= DataBindingUtil.setContentView(this, R.layout.activity_share);

        if(getIntent()!=null){
            mShareUrl=getIntent().getStringExtra("shareUrl");
        }

        initListener();

    }

    /**
     * 初始化事件
     */
    private void initListener() {

        mbinding.txtCancel.setOnClickListener(v -> {
            finish();
        });

        mbinding.imgPyq.setOnClickListener(v -> {
            WxUtil.shareToPYQ(ShareActivity.this, mShareUrl,
                    "加入健康E购大平台","邀请好友送积分。");
            finish();
        });

        mbinding.imgWx.setOnClickListener(v -> {
            WxUtil.shareToWX(ShareActivity.this, mShareUrl,
                    "加入健康E购大平台","邀请好友送积分。");
            finish();
        });

    }
}
