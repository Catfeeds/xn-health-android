package com.chengdai.ehealthproject.model.user;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityRegisterBinding;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.TextReadUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**注册
 * Created by 李先俊 on 2017/6/8.
 */

public class RegisterActivity extends AbsBaseActivity {

    private ActivityRegisterBinding mBinding;


    /**
     * 打开当前页面
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        context.startActivity(new Intent(context,RegisterActivity.class));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_register, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_register));

        ImgUtils.loadImgIdforRound(this,R.mipmap.icon,mBinding.imgLoginIcon);

         initViews();

//        initTvReadView();

    }

    private void initTvReadView() {
        TextReadUtils t=new TextReadUtils();
        t.setPos(9,13).setPos2(14,18).setOnSpanTextClickListener(type -> {
            if(type==1){
                showToast("法律申明");
            }else{
                showToast("隐私条款");
            }
        }).setSpanTextColor(ContextCompat.getColor(this, R.color.red))
          .setShowData(mBinding.tvIRead,getString(R.string.txt_register_read));

    }

    private void initViews() {


        mBinding.btnSendCode.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editUsername.getText().toString())){
                showToast("请输入手机号");
                return;
            }

            sendCodeRequest();

        });

        mBinding.btnRegister.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editUsername.getText().toString())){
                showToast("请输入手机号");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editPhoneCode.getText().toString())){
                showToast("请输入验证码");
                return;
            }

          if(TextUtils.isEmpty(mBinding.editPassword.getText().toString())){
                showToast("请输入密码");
                return;
            }

        /*    if(!mBinding.checkboxRegi.isChecked()){
                showToast("请阅读法律申明和隐私条款");
                return;
            }*/

            registerRequest();

        });

        mBinding.tvGologin.setOnClickListener(v -> {
            LoginActivity.open(this,true);
            finish();
        });
    }

    /**
     * 发送验证码
     */
    private void sendCodeRequest() {
        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("systemCode", MyConfig.SYSTEMCODE);
        hashMap.put("mobile",mBinding.editUsername.getText().toString());
        hashMap.put("bizType","805041");
        hashMap.put("kind","f1");

        mSubscription.add(RetrofitUtils.getLoaderServer().PhoneCodeSend("805904", StringUtils.getJsonToString(hashMap))                 //发送验证码
                  .compose(RxTransformerHelper.applySchedulerResult(this))
                  .subscribe(data -> {
                      if (data !=null && data.isSuccess()){
                          showToast("验证码已经发送请注意查收");
                          mSubscription.add(AppUtils.startCodeDown(60,mBinding.btnSendCode));//启动倒计时
                      }else{
                          showToast("验证码发送失败");
                      }
                   },Throwable::printStackTrace));
    }

    /**
     * 用户注册请求
     */
    private void registerRequest() {

        HashMap<String,String> hashMap=new LinkedHashMap<String, String>();

        hashMap.put("mobile",mBinding.editUsername.getText().toString());
        hashMap.put("loginPwd",mBinding.editPassword.getText().toString());
        hashMap.put("loginPwdStrength","2");
        hashMap.put("smsCaptcha",mBinding.editPhoneCode.getText().toString());
        hashMap.put("kind","f1");
        hashMap.put("isRegHx","0");
        hashMap.put("systemCode",MyConfig.SYSTEMCODE);


        mSubscription.add(RetrofitUtils.getLoaderServer().UserRegister("805041",StringUtils.getJsonToString(hashMap) )
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                        if(data!=null && !TextUtils.isEmpty(data.getToken()) && !TextUtils.isEmpty(data.getUserId())){ //token 和 UserId不为空时
                            showWarnListen("注册成功",view -> {
                                LoginActivity.open(this,true);
                                finish();
                            });
                        }else{
                            showToast("注册失败");
                        }
                },Throwable::printStackTrace));



    }
}
