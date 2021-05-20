package com.richer.myhospital.home.historypage.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.historypage.adapter.ReserveAdapter;
import com.richer.myhospital.home.historypage.model.ReserveRecord;
import com.richer.myhospital.home.homepage.adapter.HospitalAdapter;
import com.richer.myhospital.net.HttpRequest;
import com.richer.myhospital.net.ResponseBody;
import com.richer.myhospital.util.ToastUtil;
import com.richer.myhospital.util.UserManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment implements ReserveAdapter.IReserveClick {

    private static HistoryFragment historyFragment;
    private HistoryFragment() {
    }
    public static HistoryFragment getInstance() {
        if (historyFragment == null) {
            historyFragment = new HistoryFragment();
        }
        return historyFragment;
    }

    RecyclerView mRecordRecyclerView;
    List<ReserveRecord> recordList;
    LinearLayoutManager manager;
    ReserveAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        mRecordRecyclerView = view.findViewById(R.id.recyclerview_reserve_record);

        initView();

        return view;
    }

    private void initView() {

        getReserveRecord(UserManager.getUser().getId());

    }

    private void getReserveRecord(int userId) {
        HttpRequest.getReserveRecord(userId, new Callback<ResponseBody<List<ReserveRecord>>>() {
            @Override
            public void onResponse(Call<ResponseBody<List<ReserveRecord>>> call, Response<ResponseBody<List<ReserveRecord>>> response) {
                ResponseBody<List<ReserveRecord>> body = response.body();
                if (body != null) {
                    if (body.getStatus() == 200 && body.getData() != null) {
                        recordList = body.getData();
                        manager = new LinearLayoutManager(getContext());
                        mRecordRecyclerView.setLayoutManager(manager);
                        adapter = new ReserveAdapter(recordList, HistoryFragment.this);
                        mRecordRecyclerView.setAdapter(adapter);
                    }else {
                        ToastUtil.show(getContext(), body.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<List<ReserveRecord>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onLongClickListener(ReserveRecord record) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("取消预约")
                .setMessage("确定取消预约记录吗?(确定后无法撤回)")
                .setPositiveButton("确定", (dialog1, which) -> {
                    unReserve(record);
                    initView();
                })
                .setNegativeButton("取消",null)
                .create();
        dialog.show();
    }

    private void unReserve(ReserveRecord record) {
        HttpRequest.unReserve(record, new Callback<ResponseBody<Integer>>() {
            @Override
            public void onResponse(Call<ResponseBody<Integer>> call, Response<ResponseBody<Integer>> response) {
                ResponseBody<Integer> body = response.body();
                if (body != null) {
                    ToastUtil.show(getContext(), body.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody<Integer>> call, Throwable t) {

            }
        });
    }
}
