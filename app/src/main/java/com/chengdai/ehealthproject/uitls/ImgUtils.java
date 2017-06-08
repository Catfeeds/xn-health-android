package com.chengdai.ehealthproject.uitls;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chengdai.ehealthproject.uitls.glidetransforms.GlideRoundTransform;


/**
 * 图片加载工具类
 * Created by Administrator on 2016-09-14.
 */
public class ImgUtils {

    public static void  loadImgId(Context context,int imgid,ImageView img){

        if(context==null || img==null)
        {
            return;
        }

        Glide.with(context).load(imgid).into(img);
    }
    public static void  loadImgId(Activity context,int imgid,ImageView img){
        if(context==null || img==null) {
            return;
        }
        Glide.with(context).load(imgid).into(img);
    }
    public static void  loadImgURL(Context context,String URL,ImageView img){
        if(context==null || img==null || TextUtils.isEmpty(URL))
        {
            return;
        }
        Glide.with(context).load(URL).into(img);
    }

    public static void  loadImgURL(Activity activity,String URL,ImageView img){
        if(activity==null || img==null || TextUtils.isEmpty(URL))
        {
            return;
        }
        Glide.with(activity).load(URL).into(img);
    }
    public static void  loadImgIdforRound(Context activity,int URL,ImageView img){
        if(activity==null || img==null)
        {
            return;
        }
        Glide.with(activity).load(URL) .transform(new GlideRoundTransform(activity)).into(img);
    }

}
