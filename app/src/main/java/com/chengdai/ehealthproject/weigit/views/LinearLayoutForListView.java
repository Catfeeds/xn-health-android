package com.chengdai.ehealthproject.weigit.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

/**
 * linearLayout替代listView
 * Created by 李先俊 on 2017/6/10.
 */

public class LinearLayoutForListView extends LinearLayout {
    private Adapter mAdapter;
    //  private AdapterDataSetObserver mDataSetObserver;
    public LinearLayoutForListView(Context context) {
        super(context);
    }
    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public LinearLayoutForListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void  bindView(){
        setOrientation(LinearLayout.VERTICAL);
        final int count = mAdapter.getCount();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < count; i++){
            View v = mAdapter.getView(i,null,null);
            addView(v, layoutParams);
        }
        requestLayout();
        invalidate();
    }
    public void setAdapter(Adapter adapter){
        if(this.getChildCount()>0)
        {
            this.removeAllViews();
        }
        mAdapter = adapter;
        bindView();
    }
    public int getCount(){
        return this.getChildCount();
    }
    public Adapter getAdapter(){
        return mAdapter;
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    /*    if(mAdapter != null && mDataSetObserver == null){
            mDataSetObserver = new AdapterDataSetObserver();
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }*/
    }
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
   /*     if(mAdapter != null && mDataSetObserver != null){
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }*/
    }
/*    private class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            final int count = mAdapter.getCount();
            for (int i = 0; i < count; i++){
                mAdapter.getView(i,mViewHolders.get(i,null),null);
            }
            requestLayout();
        }
    }*/
}