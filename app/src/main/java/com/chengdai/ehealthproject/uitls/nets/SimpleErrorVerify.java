package com.chengdai.ehealthproject.uitls.nets;


import android.content.Context;

import com.chengdai.ehealthproject.base.BaseActivity;

import java.lang.ref.SoftReference;

/**
 * Created by jiaoyu on 17/4/20.
 */
public class SimpleErrorVerify implements ErrorVerify {
   //软引用
    private SoftReference<Context> mContext;

    public SimpleErrorVerify(Context mContext) {
        this.mContext = new SoftReference<>(mContext);
    }

    @Override
    public void call(String code, String desc) {
        if (mContext!=null && mContext.get()!=null && mContext.get() instanceof BaseActivity) {
            ((BaseActivity) mContext.get()).showToast(desc);
        }
    }
}
