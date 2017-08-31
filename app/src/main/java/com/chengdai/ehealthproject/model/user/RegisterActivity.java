package com.chengdai.ehealthproject.model.user;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.base.AbsBaseLocationActivity;
import com.chengdai.ehealthproject.base.BaseLocationActivity;
import com.chengdai.ehealthproject.databinding.ActivityRegisterBinding;
import com.chengdai.ehealthproject.model.common.model.EventBusModel;
import com.chengdai.ehealthproject.model.common.model.activitys.AddAddressActivity;
import com.chengdai.ehealthproject.model.common.model.activitys.IntroductionActivity;
import com.chengdai.ehealthproject.model.other.MainActivity;
import com.chengdai.ehealthproject.uitls.AppUtils;
import com.chengdai.ehealthproject.uitls.ImgUtils;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.TextReadUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**注册
 * Created by 李先俊 on 2017/6/8.
 */

public class RegisterActivity extends AbsBaseLocationActivity {

    private ActivityRegisterBinding mBinding;
    private String mProvince="";
    private String mCity="";
    private String mDistrict="";
    private String[] mTypeNames;//推荐人类型
    private String[] mTypeCodes;//推荐人类型标识
    private String mSelectTypeCode="";//选中的推荐人
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
    protected void locationSuccessful(AMapLocation aMapLocation) {

        mProvince=aMapLocation.getProvince();
        mCity=aMapLocation.getCity();
        mDistrict=aMapLocation.getDistrict();

        mBinding.txtAddress.setText(aMapLocation.getProvince()+" "+aMapLocation.getCity()+" "+aMapLocation.getDistrict());

    }

    @Override
    protected void locationFailure(AMapLocation aMapLocation) {

    }

    @Override
    protected void onNegativeButton() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_register, null, false);
        addMainView(mBinding.getRoot());

        setTopTitle(getString(R.string.txt_register));

        mTypeNames=getResources().getStringArray(R.array.tjtype);
        mTypeCodes=getResources().getStringArray(R.array.tj_type_code);

        initViews();

        initTvReadView();

        startLocation();//开始定位

    }

    private void initTvReadView() {
        TextReadUtils t=new TextReadUtils();
//        t.setPos(9,13).setPos2(14,18).setOnSpanTextClickListener(type -> {
        t.setPos(9,13).setPos2(14,18).setOnSpanTextClickListener(type -> {
            IntroductionActivity.open(this,"reg_protocol","注册协议");

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

            if(TextUtils.isEmpty(mBinding.tvTjType.getText().toString())){
                showToast("请选择推荐人类型");
                return;
            }

            if(TextUtils.isEmpty(mBinding.editTj.getText().toString()) ){
                showToast("请输入推荐人");
                return;
            }

            if(TextUtils.isEmpty(mProvince) || TextUtils.isEmpty(mCity) || TextUtils.isEmpty(mDistrict) || TextUtils.isEmpty(mBinding.txtAddress.getText().toString())){
                showToast("请选择正确地区");
                return;
            }

            if(!mBinding.checkboxRegi.isChecked()){
                showToast("请阅读并勾选法律注册协议");
                return;
            }

            registerRequest();

        });

        mBinding.tvGologin.setOnClickListener(v -> {
            LoginActivity.open(this,true);
            finish();
        });

        mBinding.txtAddress.setOnClickListener(v -> {
            cityPicker();
        });

        mSubscription.add(RxTextView.textChanges(mBinding.editTj).subscribe(charSequence -> {
            if(TextUtils.isEmpty(charSequence.toString())){
//                mBinding.linTj.setVisibility(View.GONE);
//                mBinding.viewTj.setVisibility(View.GONE);
                mSelectTypeCode="";
                mBinding.tvTjType.setText("");
            }else{
//               mBinding.linTj.setVisibility(View.VISIBLE);
//               mBinding.viewTj.setVisibility(View.VISIBLE);
            }
        }));

        mBinding.linTj.setOnClickListener(v -> {
            chooseTjType();
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

        hashMap.put("userReferee",mBinding.editTj.getText().toString());//推荐人
        hashMap.put("userRefereeKind",mSelectTypeCode);//推荐人类型

        hashMap.put("province",mProvince);//省
        hashMap.put("city",mCity);//市
        hashMap.put("area",mDistrict);//区



        mSubscription.add(RetrofitUtils.getLoaderServer().UserRegister("805154",StringUtils.getJsonToString(hashMap) )
                .compose(RxTransformerHelper.applySchedulerResult(this))
                .subscribe(data -> {
                    if(data!=null && ( !TextUtils.isEmpty(data.getToken()) || !TextUtils.isEmpty(data.getUserId()))){ //token 和 UsrId不为空时

                        SPUtilHelpr.saveUserToken(data.getToken());
                        SPUtilHelpr.saveUserId(data.getUserId());

                        EventBusModel eventBusModel=new EventBusModel();
                        eventBusModel.setTag("AllFINISH");
                        EventBus.getDefault().post(eventBusModel); //结束掉所有界面
                        MainActivity.open(this,1);

                        showToast("注册成功,已自动登录");
                        finish();
                    }else{
                        showToast("注册失败");
                    }
                },Throwable::printStackTrace));
    }

    private void cityPicker(){
        String province;
        String city;
        String district;
        if(!TextUtils.isEmpty(mProvince) && !TextUtils.isEmpty(mCity) && !TextUtils.isEmpty(mDistrict)){
            province=mProvince;
            city=mCity;
            district=mDistrict;
        }else{
            province="北京市";
            city="北京市";
            district="昌平区";
        }

        CityPicker cityPicker = new CityPicker.Builder(RegisterActivity.this)
                .textSize(18)
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#FE4332")
                .cancelTextColor("#FE4332")
                .province(province)
                .city(city)
                .district(district)
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                mProvince = citySelected[0];
                //城市
                mCity = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                mDistrict = citySelected[2];
    /*            //邮编
                String code = citySelected[3];*/

                mBinding.txtAddress.setText(mProvince +" "+ mCity +" "+ mDistrict);
            }
        });
    }

    private void chooseTjType() {
        new AlertDialog.Builder(this).setTitle("请选择推荐人类型").setSingleChoiceItems(
                mTypeNames, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        txtBankCard.setText(list.get(which).getBankName());
                        mBinding.tvTjType.setText(mTypeNames[which]);
                        mSelectTypeCode=mTypeCodes[which];
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

}