package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthmanager.model.AssistantMenuListModel;
import com.chengdai.ehealthproject.model.healthmanager.model.SexMenuListModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/10.
 */

public class SexMenuListAdapter extends CommonAdapter<SexMenuListModel> {

    public int mSelectPosition=-1;


    public void setDatas(List<SexMenuListModel> datas) {
        if(datas!=null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public int getmSelectPosition() {
        return mSelectPosition;
    }

    public SexMenuListModel getSelectItem(){

        if(mSelectPosition < mDatas.size()){
            return mDatas.get(mSelectPosition);
        }

        return null;

    }

    public void setmSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
        notifyDataSetChanged();
    }

    public SexMenuListAdapter(Context context,int layoutid, List<SexMenuListModel> datas) {
        super(context, layoutid, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, SexMenuListModel model, int position) {
        if(model == null){
            return;
        }

        if(mSelectPosition == position){
            viewHolder.setBackgroundColor(R.id.tv_txt, ContextCompat.getColor(mContext,R.color.white));
        }else{
            viewHolder.setBackgroundColor(R.id.tv_txt, ContextCompat.getColor(mContext,R.color.gray));
        }

       viewHolder.setText(R.id.tv_txt,model.getName());

    }
}
