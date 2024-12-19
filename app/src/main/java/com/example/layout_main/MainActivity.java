package com.example.layout_main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MapView mapView = findViewById(R.id.mapView);

        MapboxMap mapboxMap = mapView.getMapboxMap();

        mapboxMap.loadStyle("mapbox://styles/kei242017/cm4u0pmti003z01sm7k001a71");

        // ---- ボトムシートの初期設定 ----
        // BottomSheetの参照を取得
        View sheet = findViewById(R.id.sheet);

        // BottomSheetBehaviorを設定
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        // neckHeightに相当する設定
        bottomSheetBehavior.setPeekHeight(60); // peekHeightで初期表示時の高さを指定

        // BottomSheetの状態を設定
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


    }
}