package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthmanager.model.AssistantMenuListModel;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/10.
 */

public class AssistantMenuListAdapter extends CommonAdapter<AssistantMenuListModel> {

    public int mSelectPosition=-1;


    public void setDatas(List<AssistantMenuListModel> datas) {
        if(datas!=null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }

    public int getmSelectPosition() {
        return mSelectPosition;
    }

    public AssistantMenuListModel getSelectItem(){

        if(mSelectPosition < mDatas.size()){
            return mDatas.get(mSelectPosition);
        }

        return null;

    }

    public void setmSelectPosition(int mSelectPosition) {
        this.mSelectPosition = mSelectPosition;
        notifyDataSetChanged();
    }

    public AssistantMenuListAdapter(Context context, List<AssistantMenuListModel> datas) {
        super(context, R.layout.item_textview_16sp_left, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, AssistantMenuListModel model, int position) {
        if(model == null){
            return;
        }

        if(mSelectPosition == position){
            viewHolder.setBackgroundColor(R.id.tv_txt, ContextCompat.getColor(mContext,R.color.white));
        }else{
            viewHolder.setBackgroundColor(R.id.tv_txt, ContextCompat.getColor(mContext,R.color.gray));
        }

       viewHolder.setText(R.id.tv_txt,model.getDvalue());

    }
}
