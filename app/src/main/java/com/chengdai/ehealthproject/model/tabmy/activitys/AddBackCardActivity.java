package com.chengdai.ehealthproject.model.tabmy.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;

import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.base.AbsBaseActivity;
import com.chengdai.ehealthproject.databinding.ActivityBindBankCardBinding;
import com.chengdai.ehealthproject.model.tabmy.model.BankCardModel;
import com.chengdai.ehealthproject.model.tabmy.model.BankModel;
import com.chengdai.ehealthproject.uitls.StringUtils;
import com.chengdai.ehealthproject.uitls.nets.RetrofitUtils;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerHelper;
import com.chengdai.ehealthproject.uitls.nets.RxTransformerListHelper;
import com.chengdai.ehealthproject.weigit.appmanager.MyConfig;
import com.chengdai.ehealthproject.weigit.appmanager.SPUtilHelpr;
import com.chengdai.ehealthproject.weigit.dialog.InputDialog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**添加 、删除、修改银行卡
 * Created by 李先俊 on 2017/6/29.
 */

public class AddBackCardActivity extends AbsBaseActivity{

    private ActivityBindBankCardBinding mBinding;

    private String[] mBankNames;
    private String[] mBankCodes;

    private String mSelectCardId;//选择的银行卡ID

    private InputDialog inputDialog;


    /**
     *
     * @param context
     */
    public static void open(Context context){
        if(context==null){
            return;
        }
        Intent intent=new Intent(context,AddBackCardActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_bind_bank_card, null, false);

        addMainView(mBinding.getRoot());

        setTopTitle("绑定银行卡");

        setSubLeftImgState(true);

        //添加银行类型
        mBinding.txtBankName.setOnClickListener(v -> {
            getBankBrand();
        });

        //添加银行卡
        mBinding.txtConfirm.setOnClickListener(v -> {

            if(TextUtils.isEmpty(mBinding.editName.getText().toString())){
                showToast("请输入姓名");
                return;
            }
            if(TextUtils.isEmpty(mSelectCardId)){
                showToast("请选择银行");
                return;
            }
            if(TextUtils.isEmpty(mBinding.edtCardId.getText().toString())){
                showToast("请输入卡号");
                return;
            }
            if (mBinding.edtCardId.getText().toString().length() < 16 || mBinding.edtCardId.getText().toString().length() > 19) {
                showToast("请输入正确的卡号");
                return;
            }
            bindCard();
//            inputPayDialog();

        });
    }


    //绑定银行卡
    public void bindCard(){

        Map<String,String> object=new HashMap<>();


        object.put("realName", mBinding.editName.getText().toString().trim());
        object.put("bankcardNumber", mBinding.edtCardId.getText().toString().trim());
        object.put("bankName", mBinding.txtBankName.getText().toString().trim());
        object.put("bankCode", mSelectCardId);
        object.put("currency", "CNY");
        object.put("type", "C");
        object.put("token", SPUtilHelpr.getUserToken());
        object.put("userId",SPUtilHelpr.getUserId());
        object.put("systemCode", MyConfig.SYSTEMCODE);

       mSubscription.add( RetrofitUtils.getLoaderServer().bindBankCard("802010",StringUtils.getJsonToString(object))
                .compose(RxTransformerHelper.applySchedulerAndAllFilter(this))
                .subscribe(isSuccessModes -> {
                    showToast("绑定成功");
                    finish();
                },Throwable::printStackTrace));

    }


    public void inputPayDialog(){

        if(inputDialog == null){
            inputDialog = new InputDialog(this).builder().setTitle("支付密码")
                    .setPositiveBtn("确定", (view, inputMsg) -> {
                        if (TextUtils.isEmpty(inputMsg)) {
                            showToast("请输入支付密码");
                            return;
                        }

                        inputDialog.dismiss();
                    })
                    .setNegativeBtn("取消", null)
                    .setContentMsg("");
            inputDialog.getContentView().setHint("请输入支付密码");
//            inputDialog.getContentView().setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            inputDialog.getContentView().setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        }
        inputDialog.show();
    }



    /**
     * 获取银行卡渠道
     */
    private void getBankBrand() {
        Map object = new HashMap();
            object.put("token", SPUtilHelpr.getUserToken());
            object.put("payType", "WAP");


      mSubscription.add( RetrofitUtils.getLoaderServer().getBackModel("802116", StringUtils.getJsonToString(object))
                .compose(RxTransformerListHelper.applySchedulerResult(this))
                .subscribe(r -> {

                    mBankNames=new String[r.size()];
                    mBankCodes=new String[r.size()];

                    int i=0;

                    for(BankModel b:r){
                        mBankNames[i]=b.getBankName();
                        mBankCodes[i]=b.getBankCode();
                        i++;
                    }
                   if(mBankNames.length !=0 && mBankNames.length == mBankCodes.length){
                       chooseBankCard();
                   }

                },Throwable::printStackTrace));

    }



    private void chooseBankCard() {
        new AlertDialog.Builder(this).setTitle("请选择银行").setSingleChoiceItems(
                mBankNames, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        txtBankCard.setText(list.get(which).getBankName());
                        mBinding.txtBankName.setText(mBankNames[which]);
                        mSelectCardId=mBankCodes[which];
                        dialog.dismiss();
                    }
                }).setNegativeButton("取消", null).show();
    }

}
