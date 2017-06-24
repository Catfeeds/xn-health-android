package com.chengdai.ehealthproject.model.healthmanager.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.healthmanager.model.AssistantMenuListModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * Created by 李先俊 on 2017/6/10.
 */

public class AssistantMenuRightListAdapter extends CommonAdapter<FoodHotListData> {



    public void setDatas(List<FoodHotListData> datas) {
        if(datas!=null){
            this.mDatas = datas;
            notifyDataSetChanged();
        }
    }


    public AssistantMenuRightListAdapter(Context context, List<FoodHotListData> datas) {
        super(context, R.layout.item_textview_16sp_left, datas);
    }


    @Override
    protected void convert(ViewHolder viewHolder, FoodHotListData model, int position) {
        if(model == null){
            return;
        }


       viewHolder.setText(R.id.tv_txt,model.getName());

    }
}
