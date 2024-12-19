package com.example.layout_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import android.Manifest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 位置情報の権限を求める
        int LOCATION_PERMISSION_REQUEST_CODE = 100;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // 権限がない場合、リクエストを送信
            Log.d("debug","Request ACCESS_FINE_LOCATION");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // 権限がすでに付与されている場合
            Log.d("debug", "ACCESS_FINE_LOCATION: Granted");
        }


        // ---- MapBoxの初期設定 ----
        MapView mapView = findViewById(R.id.mapView);

        MapboxMap mapboxMap = mapView.getMapboxMap();

        mapboxMap.loadStyle("mapbox://styles/kei242017/cm4u0pmti003z01sm7k001a71", style -> {
            // Mapのスタイルがロードされた後の処理
            Log.d("debug", "Map style loaded");
        });

//        mapboxMap.setCamera(
//                new CameraOptions.Builder()
//                        .center(Point.fromLngLat(136.881537,35.170915 ))
//                        .pitch(0.0)
//                        .zoom(8.0)
//                        .bearing(0.0)
//                        .build()
//
//        );



        // ---- ボトムシートの初期設定 ----
        // BottomSheetの参照を取得
        View sheet = findViewById(R.id.sheet);

        // BottomSheetBehaviorを設定
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        // neckHeightに相当する設定
        bottomSheetBehavior.setPeekHeight(100); // peekHeightで初期表示時の高さを指定

        // BottomSheetの状態を設定
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


    }
}