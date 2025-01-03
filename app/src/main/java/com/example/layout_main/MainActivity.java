package com.example.layout_main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.Manifest;

import com.example.layout_main.activity.SettingActivity;
import com.example.layout_main.activity.TimelineActivity;
import com.example.layout_main.activity.HomeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    // フラグメントマネージャー各メソッドで使いまわ焦るようにする
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初期画面をhomeに設定する
        Log.i("transition","home");

        // フラグメントマネージャーを初期化
        fragmentManager = getSupportFragmentManager();


        // -----初期画面をHomeに設定する-----
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // activity_mainのframeLayout: fragment_containerにHomeActivityを設定
        fragmentTransaction.replace(R.id.fragment_container,new HomeActivity());
        // 変更を反映
        fragmentTransaction.commit();

        initBottomNavBar();

    }
    // ボトムナビゲーションバーを押したときの動作を設定
    public void initBottomNavBar() {
        BottomNavigationView bottomNavView = findViewById(R.id.bottom_navigation_bar);

        bottomNavView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // itemIDはメニューのとこで設定したアイコンごとのid
                int itemID = item.getItemId();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // home
                if (itemID == R.id.nav_home) {
                    Log.i("transition","home");
                    fragmentTransaction.replace(R.id.fragment_container,new HomeActivity());
                    fragmentTransaction.commit();

                    return true;
                }
                // timeline
                if (itemID == R.id.nav_timeline) {
                    Log.i("transition","timeline");
                    fragmentTransaction.replace(R.id.fragment_container,new TimelineActivity());
                    fragmentTransaction.commit();

                    return true;
                }
                // 設定
                if (itemID == R.id.nav_setting) {
                    Log.i("transition","setting");
                    fragmentTransaction.replace(R.id.fragment_container,new SettingActivity());
                    fragmentTransaction.commit();

                    return true;
                }

                // 失敗した場合
                Log.e("transition-ERR","id is not found. id: " + itemID);
                return false;
            }
        });

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
    }
}