package com.chengdai.ehealthproject.uitls;

import android.widget.Button;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by 李先俊 on 2017/6/8.
 */

public class AppUtils {
    /*new Observer<Long>() {
                    @Override
                    public void onCompleted() {

                        RxTextView.text(btn).accept();

                        RxTextView.text(btn).call("重发验证码");
                        RxView.enabled(btn).call(true);
                    }
                    @Override
                    public void onError(Throwable e) {
                        RxTextView.text(btn).call("重发验证码");
                        RxView.enabled(btn).call(true);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        btn.setEnabled(false);
                        RxTextView.text(btn).call((count - aLong) + "秒后重发");
                    }
                }*/

    /**
     * 验证码倒计时
     * @param count  秒数
     * @param btn   按钮
     * @return
     */
    public static Disposable startCodeDown(int count, Button btn){
        return Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())    // 创建一个按照给定的时间间隔发射从0开始的整数序列
                .take(count)//只发射开始的N项数据或者一定时间内的数据
                .subscribe(aLong ->{
                    RxView.enabled(btn).accept(false);
                    RxTextView.text(btn).accept((count - aLong) + "秒后重发");
                } ,throwable -> {
                    RxView.enabled(btn).accept(true);
                    RxTextView.text(btn).accept("重发验证码");
                },() -> {
                    RxView.enabled(btn).accept(true);
                    RxTextView.text(btn).accept("重发验证码");
                });
    }
}
