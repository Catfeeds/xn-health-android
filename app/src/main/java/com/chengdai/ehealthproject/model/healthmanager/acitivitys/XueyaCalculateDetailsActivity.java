package com.chengdai.ehealthproject.model.healthmanager.acitivitys;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityXueyaTestBinding;
import com.chengdai.ehealthproject.databinding.ActivityXueyaTestDetailsBinding;

/**血压计算结果详情
 * Created by 李先俊 on 2017/6/29.
 */

public class XueyaCalculateDetailsActivity extends AbsBaseActivity{

    private ActivityXueyaTestDetailsBinding mBinding;

    private int mHeight;
    private int mLow;


    /**
     *
     * @param context
     */
    public static void open(Context context,int height,int low){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,XueyaCalculateDetailsActivity.class);
        intent.putExtra("height",height);
        intent.putExtra("low",low);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_xueya_test_details, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle("血压测试结果");

        setSubLeftImgState(true);
/*低血压(收缩压≤90mmHg 或舒张压≤60mmHg)
您的血压测量结果属于低血压，建议均衡营养，坚持锻炼，改善体质，祝您生活愉快！

正常血压(90mmHg<收缩压<140mmHg、60mmHg<舒张压<90mmHg)
您的血压测量结果属于正常血压；恭喜您，您的体检结果是正常的，但是也要进行预防哦！

理想血压 (收缩压<120 mmHg、舒张压<80mmHg)
您的血压测量结果属于理想血压；恭喜您，您的体检结果是正常的，但是也要进行预防哦！

高血压(收缩压≥140mmHg 或舒张压≥90mmHg)
您的血压测量结果属于（轻高度）高血压，您的身体开始亮红灯了，请重视！建议您采取健康的生活方式，戒烟戒酒，限制钠盐的摄入，加强锻炼，密切关注血压。祝您生活愉快！*/
        if(getIntent()!=null){
            mHeight=getIntent().getIntExtra("height",50);
            mLow=getIntent().getIntExtra("low",30);
        }

        mBinding.tvNumber.setText(mHeight+"/"+mLow);

        if(mLow<=90 || mHeight<=60){
            mBinding.tvXueyaTitle.setText("低血压");
            mBinding.tvDetails.setText("您的血压测量结果属于低血压，建议均衡营养，坚持锻炼，改善体质，祝您生活愉快！");
        }else if(mLow<90 && mLow<140 &&  mHeight>60 && mHeight<90){
           mBinding.tvXueyaTitle.setText("正常压");
            mBinding.tvDetails.setText("您的血压测量结果属于正常血压；恭喜您，您的体检结果是正常的，但是也要进行预防哦！");
        }else if( mLow<120 &&  mHeight<80){
           mBinding.tvXueyaTitle.setText("理想血压");
            mBinding.tvDetails.setText("您的血压测量结果属于理想血压；恭喜您，您的体检结果是正常的，但是也要进行预防哦！");
        }else  if( mLow>=140 &&  mHeight>=90){
           mBinding.tvXueyaTitle.setText("高血压");
           mBinding.tvDetails.setText("您的血压测量结果属于（轻高度）高血压，您的身体开始亮红灯了，请重视！建议您采取健康的生活方式，戒烟戒酒，限制钠盐的摄入，加强锻炼，密切关注血压。祝您生活愉快！");
        }

    }
}
