package com.richer.myhospital.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richer.myhospital.Constant;
import com.richer.myhospital.R;
import com.richer.myhospital.home.account_center_page.AccountCenterFragment;
import com.richer.myhospital.home.homepage.view.HomeFragment;
import com.richer.myhospital.home.historypage.view.HistoryFragment;
import com.richer.myhospital.login.model.User;
import com.richer.myhospital.util.PermissionUtil;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import static com.richer.myhospital.Constant.KEY_BACK;

public class Home extends AppCompatActivity implements View.OnClickListener {

    FrameLayout mFragmentFrameLayout;
    LinearLayout mHomeLayout;
    LinearLayout mPlatformLayout;
    LinearLayout mAccountCenterLayout;
    ImageView mHomeImg;
    ImageView mPlatformImg;
    ImageView mAccountCenterImg;
    TextView mHomeTv;
    TextView mPlatformTv;
    TextView mAccountCenterTv;

    List<ImageView> imageViews;
    List<TextView> textViews;

    FragmentManager fragmentManager;
    List<Fragment> fragments;

    int userId;
    User user;

    Handler handler;
    private static int isExit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = (User) getIntent().getSerializableExtra("user");
        UserManager.setUser(user);
        userId = user.getId();
//        requestLocationPermission();
        initView();
        initFragments();
        initHandler();
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case KEY_BACK:
                        isExit--;
                        break;
                    default:
                        break;
                }
            }
        };
    }

//    private void requestLocationPermission() {
//        List<String> permissions = new ArrayList<>();
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        }
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        }
//        if (permissions.size() == 0) {
//            getLocation();
//        }else {
//            permissionUtil.requestPermission();
//        }
//    }

    private void initView() {
        mFragmentFrameLayout = findViewById(R.id.fragment_main);
        mHomeLayout = findViewById(R.id.layout_home_bottom);
        mPlatformLayout = findViewById(R.id.layout_history_bottom);
        mAccountCenterLayout = findViewById(R.id.layout_account_center_bottom);
        mHomeImg = findViewById(R.id.img_home_bottom);
        mPlatformImg = findViewById(R.id.img_platform_bottom);
        mAccountCenterImg = findViewById(R.id.img_account_center_bottom);
        mHomeTv = findViewById(R.id.tv_home_bottom);
        mPlatformTv = findViewById(R.id.tv_platform_bottom);
        mAccountCenterTv = findViewById(R.id.tv_account_center_bottom);

        mHomeLayout.setOnClickListener(this);
        mPlatformLayout.setOnClickListener(this);
        mAccountCenterLayout.setOnClickListener(this);

        imageViews = new ArrayList<>();
        imageViews.add(mHomeImg);
        imageViews.add(mPlatformImg);
        imageViews.add(mAccountCenterImg);
        textViews = new ArrayList<>();
        textViews.add(mHomeTv);
        textViews.add(mPlatformTv);
        textViews.add(mAccountCenterTv);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        fragments.add(HomeFragment.getInstance());
        fragments.add(HistoryFragment.getInstance());
        fragments.add(AccountCenterFragment.getInstance());
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_main, HomeFragment.getInstance()).commit();
        replaceFragment(Constant.HOME);
    }

    private void replaceFragment(int index) {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragments != null && index < fragments.size()) {
            transaction.replace(R.id.fragment_main, fragments.get(index)).commit();
            refreshBottom(index);
        }
    }

    private void refreshBottom(int index) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (i == index) {
                imageViews.get(i).setSelected(true);
                textViews.get(i).setTextColor(Color.BLACK);
            } else {
                imageViews.get(i).setSelected(false);
                textViews.get(i).setTextColor(Color.parseColor("#808080"));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home_bottom:
                replaceFragment(Constant.HOME);
                break;
            case R.id.layout_history_bottom:
                replaceFragment(Constant.PLATFORM);
                break;
            case R.id.layout_account_center_bottom:
                replaceFragment(Constant.ACCOUNT_CENTER);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSION_REQUEST_CODE_LOCATION:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                }
                break;
            default:
                break;
        }
    }

//    private void getLocation() {
//        Location location = getLastKnownLocation();
//        if (location != null) {
//            double latitude = location.getLatitude();
//            double longitude = location.getLongitude();
//            user.setLocation(new User.Location(latitude, longitude));
//            Log.d("TAG", "latitude: "+latitude + " longitude: " + longitude);
//        }else {
//            ToastUtil.show(this, "位置信息获取失败");
//        }
//    }

//    private Location getLastKnownLocation() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        List<String> providers = locationManager.getProviders(true);
//        Location location = null;
//        for (String provider : providers) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ToastUtil.show(this, "位置权限未授权");
//                break;
//            }
//            Location l = locationManager.getLastKnownLocation(provider);
//            if (l == null) {
//                continue;
//            }
//            if (location == null || l.getAccuracy() < location.getAccuracy()) {
//                location = l;
//            }
//        }
//        return location;
//    }

    @Override
    public void onBackPressed() {
        isExit++;
        exit();
    }

    private void exit() {
        if (isExit < 2) {
            ToastUtil.show(this, "再按一次退出应用");
            Message message = new Message();
            message.what = KEY_BACK;
            handler.sendMessageDelayed(message, 2000);
        }else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }
}