package com.example.layout_main.activity;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.layout_main.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.bindgen.Expected;
import com.mapbox.common.Cancelable;
import com.mapbox.common.location.DeviceLocationProvider;
import com.mapbox.common.location.GetLocationCallback;
import com.mapbox.common.location.Location;
import com.mapbox.common.location.LocationError;
import com.mapbox.common.location.LocationService;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.plugin.animation.CameraAnimationsPlugin;
import com.mapbox.maps.plugin.animation.CameraAnimationsUtils;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.common.location.LocationProvider;
import com.mapbox.common.location.LocationServiceFactory;

public class HomeActivity extends Fragment {
    View rootView;

    MapView mapView;
    MapboxMap mapboxMap;

    public HomeActivity(){
        // require a empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();  // onViewCreatedで初期化を呼び出す
    }


    public void setCameraToCurrentLocation() {
        // mapViewがnullの場合は初期化
        if (mapboxMap == null) {
            MapView mapView = rootView.findViewById(R.id.mapView);
        }

        LocationService locationService = LocationServiceFactory.getOrCreate();

        LocationProvider locationProvider;
        Expected<LocationError, DeviceLocationProvider> result = locationService.getDeviceLocationProvider(null);

        if (result.isValue()) {
            locationProvider = result.getValue();
            locationProvider.getLastLocation(new GetLocationCallback() {
                @Override
                public void run(@Nullable Location location) {
                    if (location != null) {
                        CameraAnimationsPlugin camera = CameraAnimationsUtils.getCamera(mapView);
                        Cancelable cancelable = camera.flyTo(
                                new CameraOptions.Builder()
                                        .center(Point.fromLngLat(location.getLongitude(), location.getLatitude()))
                                        .build(),
                                new MapAnimationOptions.Builder()
                                        .duration(1000)
                                        .build(),
                                null
                        );
                    }
                }
            });
        }
    }

    private void initialize() {
        // ---- MapBoxの初期設定 ----
        mapView = rootView.findViewById(R.id.mapView);

        mapboxMap = mapView.getMapboxMap();

        mapboxMap.loadStyle("mapbox://styles/kei242017/cm4u0pmti003z01sm7k001a71", style -> {
            // Mapのスタイルがロードされた後の処理
            Log.d("debug", "Map style loaded");
        });


        mapboxMap.setCamera(
                new CameraOptions.Builder()
                        .center(Point.fromLngLat(136.881537,35.170915 ))
                        .pitch(0.0)
                        .zoom(8.0)
                        .bearing(0.0)
                        .build()

        );

        // ---- ボトムシートの初期設定 ----
        // BottomSheetの参照を取得
        View sheet = rootView.findViewById(R.id.sheet);

        // BottomSheetBehaviorを設定
        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(sheet);

        // neckHeightに相当する設定
        bottomSheetBehavior.setPeekHeight(100); // peekHeightで初期表示時の高さを指定

        // BottomSheetの状態を設定
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        FloatingActionButton fab_current_location = rootView.findViewById(R.id.fab_current_location);
        fab_current_location.setOnClickListener(v -> {
            setCameraToCurrentLocation();
        });
    }
}