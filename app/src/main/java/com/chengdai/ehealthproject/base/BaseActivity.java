package com.chengdai.ehealthproject.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chengdai.ehealthproject.uitls.ToastUtil;
import com.chengdai.ehealthproject.uitls.nets.NetUtils;
import com.chengdai.ehealthproject.weigit.dialog.CommonDialog;
import com.chengdai.ehealthproject.weigit.dialog.LoadingDialog;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;



/**
 * Actvity 基类
 *
 */
public abstract class BaseActivity extends AppCompatActivity {

    private CommonDialog commonDialog;
    protected LoadingDialog loadingDialog;
    protected CompositeDisposable mSubscription;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeDisposable();
        loadingDialog = new LoadingDialog(this);
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        EventBus.getDefault().register(this);
    }



    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();

        if (commonDialog != null) {
            commonDialog.closeDialog();
            commonDialog = null;
        }
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        if (mSubscription != null && !mSubscription.isDisposed()) {
            mSubscription.dispose();
            mSubscription.clear();
        }
    }


    /**
     * 获取请求参数
     *
     * @param args 参数数组 (key,value,key,value...) 一定要为偶数对应的
     * @return
     */
    public Map<String, String> getRequestMap(String... args) {
        Map<String, String> requestMap = new HashMap<>();
        if (args.length % 2 == 1) {
            return requestMap;
        }
        for (int i = 0; i < args.length - 1; i++) {
            if (i % 2 == 0) {
                requestMap.put(args[i], args[i + 1]);
            }
        }
        return requestMap;
    }


    /**
     * 隐藏Dialog
     */
    public void disMissLoading() {
        if (loadingDialog != null) {
            loadingDialog.closeDialog();
        }
    }

    /**
     * 显示dialog
     */
    public void showLoadingDialog() {
        if (loadingDialog != null ) {
            loadingDialog.showDialog();
        }
    }


    /**
     * 返回键的处理，返回值为是否关闭当前页
     *
     * @return
     */
    public boolean getBackIsFinish() {
        return true;
    }

    public void goBack(boolean isfinish) {
        if (isFinishing()) {
            return;
        }

        finish();
    }


    /**
     * Dialog
     *
     * @param str
     */
    public void showSimpleWran(String str) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", null);

        commonDialog.show();
    }

    public void showToast(String str){
        ToastUtil.show(this,str);
    }

    /**
     * Dialog
     *
     * @param str
     */
    protected void showRedSimpleWran(String str) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", null, true);

        commonDialog.show();
    }
    /**
     * @param str
     * @param onNegativeListener
     */
    public void showWarnListen(String str, CommonDialog.OnNegativeListener onNegativeListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", onNegativeListener, false);

        commonDialog.show();
    }

    /**
     * @param str
     * @param onNegativeListener
     */
    protected void showRedWarnListen(String str, CommonDialog.OnNegativeListener onNegativeListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setNegativeBtn("确定", onNegativeListener, true);

        commonDialog.show();
    }

    protected void showDoubleWarnListen(String str, CommonDialog.OnPositiveListener onPositiveListener) {

        if (isFinishing()) {
            return;
        }

        commonDialog = new CommonDialog(this).builder()
                .setTitle("提示").setContentMsg(str)
                .setPositiveBtn("确定", onPositiveListener)
                .setNegativeBtn("取消", null, false);

        commonDialog.show();
    }

    /**
     * 判断是否有网络
     *
     * @return
     */
    protected boolean isNetworkConnected() {
        return NetUtils.isNetworkConnected();
    }

    protected boolean isNoNetwork() {
        if (!isNetworkConnected()) {
            showSimpleWran("暂无网络");
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */

    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 隐藏键盘
     */
    public void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public void hideKeyboard(Activity activity) {
        if (activity == null || activity.getWindow() == null) {
            return;
        }
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            hideKeyboard(view);
        }
    }

    @Override
    public void finish() {
        hideKeyboard(this);
        super.finish();
    }


    /**
     * 设置状态栏颜色（5.0以上系统）
     *
     * @param stateTitleColor
     */
    public void setStateTitleColor(int stateTitleColor) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0以上系统实现状态栏颜色改变
            try {
                Window window = getWindow();
                //取消设置透明状态栏,使 ContentView 内容不再沉浸到状态栏下
                if (window != null) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    //设置状态栏颜色
                    window.setStatusBarColor(stateTitleColor);
                }
            } catch (Exception e) {

            }
        }
    }
}
