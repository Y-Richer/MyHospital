package com.richer.myhospital.home.homepage.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.homepage.adapter.HospitalAdapter;
import com.richer.myhospital.home.homepage.presenter.HomePagePresenter;
import com.richer.myhospital.home.model.Hospital;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import java.util.ArrayList;
import java.util.List;

import static com.richer.myhospital.Constant.REFRESH;

/**
 * 2021/04/17 Richer
 *
 */
public class HomeFragment extends Fragment implements IHomeView, View.OnClickListener, HospitalAdapter.IHospitalClick {

    private static HomeFragment homeFragment;
    private HomeFragment() {
    }
    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    HomePagePresenter homePagePresenter;
    TextView mCityTv;
    TextView mNearByTv;
    TextView mCollectionTv;
    ImageView mSelectCityImg;
    Button mSearchBtn;
    String city = "福州";

    RecyclerView mHospitalRecyclerView;
    List<Hospital> nearbyHospitals = new ArrayList<>();
    List<Hospital> collectHospitals = new ArrayList<>();
    List<Hospital> hospitals = new ArrayList<>();
    Handler handler;

    LinearLayoutManager manager;
    HospitalAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initController(view);
        initHandler();
        initData();
        initView();
        return view;
    }

    private void initController(View view) {
        mCityTv = view.findViewById(R.id.tv_city_home);
        mSelectCityImg = view.findViewById(R.id.img_select_city_home);
        mCityTv.setOnClickListener(this);
        mSelectCityImg.setOnClickListener(this);
        mHospitalRecyclerView = view.findViewById(R.id.recyclerview_hospital_list_home);
        mNearByTv = view.findViewById(R.id.tv_nearby_home);
        mNearByTv.setOnClickListener(this);
        mCollectionTv = view.findViewById(R.id.tv_collection_home);
        mCollectionTv.setOnClickListener(this);
        mSearchBtn = view.findViewById(R.id.btn_search_home);
        mSearchBtn.setOnClickListener(this);
    }

    private void initView() {
        mCityTv.setText(city);
        initList(hospitals);
    }

    private void initList(List<Hospital> hospitals) {
        this.hospitals = hospitals;
        manager = new LinearLayoutManager(getContext());
        mHospitalRecyclerView.setLayoutManager(manager);
        adapter = new HospitalAdapter(hospitals, this);
        mHospitalRecyclerView.setAdapter(adapter);
    }
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case REFRESH:
                        initView();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    private void initData() {
        homePagePresenter = new HomePagePresenter(this);
//        if (User.getInstance().getLocation() != null) {
//            homePagePresenter.getLocation(String.valueOf(User.getInstance().getLocation().getLongitude()),
//                    String.valueOf(User.getInstance().getLocation().getLatitude()));
//        }
        homePagePresenter.getHospital(city);
    }

    private void refresh() {
        Message message = new Message();
        message.what = REFRESH;
        handler.sendMessage(message);
    }

    @Override
    public void showMessage(String message) {
        ToastUtil.show(getActivity(), message);
    }

    @Override
    public void setCity(String city) {
        this.city = city;
        homePagePresenter.getHospital(city);
        refresh();
    }

    @Override
    public void setNearByHospitals(List<Hospital> hospitals) {
        //被导航栏挡住内容问题

        this.nearbyHospitals = hospitals;
        initList(nearbyHospitals);
        refresh();
    }

    @Override
    public void setCollectionHospitals(List<Hospital> hospitals) {
        this.collectHospitals = hospitals;
        initList(collectHospitals);
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city_home:
            case R.id.img_select_city_home:
                Intent intent = new Intent(getContext(), SwitchCityActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_nearby_home:
                mNearByTv.setTextColor(Color.BLACK);
                mCollectionTv.setTextColor(Color.GRAY);
                homePagePresenter.getHospital(city);
                break;
            case R.id.tv_collection_home:
                mNearByTv.setTextColor(Color.GRAY);
                mCollectionTv.setTextColor(Color.BLACK);
                homePagePresenter.getCollectedHospital(UserManager.getUser().getId());
                break;
            case R.id.btn_search_home:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            default:break;
        }
    }

    @Override
    public void onClickListener(Hospital hospital) {
        Intent intent = new Intent(getContext(), HospitalDetailActivity.class);
        intent.putExtra("hospitalId", hospital.getId());
        startActivity(intent);
    }
}
