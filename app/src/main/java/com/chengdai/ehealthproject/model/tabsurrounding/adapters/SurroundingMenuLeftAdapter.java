package com.chengdai.ehealthproject.model.tabsurrounding.adapters;

import android.content.Context;
import android.widget.TextView;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.model.tabsurrounding.model.StoreTypeModel;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**周边商户分类选择左
 * Created by 李先俊 on 2017/6/10.
 */

public class SurroundingMenuLeftAdapter extends CommonAdapter {

    private String typeName;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
        notifyDataSetChanged();
    }

    public SurroundingMenuLeftAdapter(Context context, int layoutId, List<StoreTypeModel> datas, String typeName) {
        super(context, layoutId, datas);
        this.typeName=typeName;
    }

    @Override
    protected void convert(ViewHolder viewHolder, Object item, int position) {

        TextView tv=viewHolder.getView(R.id.tv_txt);
        StoreTypeModel model= (StoreTypeModel) item;

        if(model!=null ){
            if(typeName.equals(model.getName())){
                tv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }else{
                tv.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
            }
            tv.setText(model.getName());
        }

    }
}
