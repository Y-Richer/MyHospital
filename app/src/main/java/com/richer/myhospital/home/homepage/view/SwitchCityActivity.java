package com.richer.myhospital.home.homepage.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;

import com.richer.myhospital.R;
import com.richer.myhospital.home.homepage.adapter.CityAdapter;
import com.richer.myhospital.home.homepage.presenter.CityPresenter;
import com.richer.myhospital.home.model.City;
import com.richer.myhospital.util.BmobUtil;
import com.richer.myhospital.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import static com.richer.myhospital.Constant.REFRESH;

public class SwitchCityActivity extends AppCompatActivity implements CityAdapter.ICityClick {

    List<City> cityList;
    CityPresenter presenter;
    Handler handler;

    RecyclerView mCityRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_city);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("切换城市");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        presenter = new CityPresenter(this);
        presenter.getCities();
        cityList = new ArrayList<>();
        mCityRecyclerView = findViewById(R.id.recyclerview_city_list_switch_city);

        initHandler();
        initView();

    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mCityRecyclerView.setLayoutManager(manager);
        CityAdapter adapter = new CityAdapter(cityList, this);
        mCityRecyclerView.setAdapter(adapter);
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case REFRESH:
                        initView();
                        break;
                }
            }
        };
    }

    private void refresh() {
        Message message = new Message();
        message.what = REFRESH;
        handler.sendMessage(message);
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
        refresh();
    }

    @Override
    public void onClickListener(City city) {
        HomeFragment.getInstance().setCity(city.getName());
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}