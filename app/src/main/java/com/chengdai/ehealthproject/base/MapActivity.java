package com.chengdai.ehealthproject.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.chengdai.ehealthproject.R;
import com.chengdai.ehealthproject.databinding.ActivityMapBinding;
import com.chengdai.ehealthproject.uitls.LogUtil;

/**
 * 地图Activity
 * Created by 李先俊 on 2017/8/28.
 */

public class MapActivity extends AbsBaseActivity {


    private ActivityMapBinding mBinding;
    private AMap aMap;

    private Double mLat;//纬度
    private Double mLon;//经度
    private String mAddress;//经度


    /**
     * 打开当前页面
     *
     * @param context
     */
    public static void open(Context context, String address, String mLat, String mLon) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra("address", address);
        intent.putExtra("mLat", mLat);
        intent.putExtra("mLon", mLon);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.activity_map, null, false);
        addMainView(mBinding.getRoot());
        setTopTitle("地图");
        setSubLeftImgState(true);

        if (getIntent() != null) {
            mAddress = getIntent().getStringExtra("address");
            mLat = Double.parseDouble(getIntent().getStringExtra("mLat"));
            mLon = Double.parseDouble(getIntent().getStringExtra("mLon"));

            LogUtil.E("地图 经度 "+mLat);
            LogUtil.E("地图 纬度 "+mLon);
        }

        mBinding.tvAddress.setText(mAddress);

        mBinding.map.onCreate(savedInstanceState);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mBinding.map.getMap();
            aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(mLat, mLon), 17, 0, 0)));

            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(mLat,mLon));
//            markerOption.title("西安市").snippet("xxxxxxxxxx");
            markerOption.title(mAddress);
            markerOption.perspective(true);
            markerOption.draggable(true);
//            markerOption.icon(
//                    BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker))
//            );
            markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
            //将Marker设置为贴地显示，可以双指下拉看效果
            markerOption.setFlat(true);
            aMap.addMarker(markerOption);

            Marker marker = aMap.addMarker(markerOption);
//            marker.showInfoWindow();// 设置默认显示一个infowinfow
        }

        mBinding.imgDaohang.setOnClickListener(v -> {
            SelectMapActivity.lunch(this, mLat+"",mLon+"");
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mBinding.map.onDestroy()，销毁地图
        mBinding.map.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mBinding.map.onResume ()，重新绘制加载地图
        mBinding.map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mBinding.map.onPause ()，暂停地图的绘制
        mBinding.map.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mBinding.map.onSaveInstanceState (outState)，保存地图当前的状态
        mBinding.map.onSaveInstanceState(outState);
    }


}
