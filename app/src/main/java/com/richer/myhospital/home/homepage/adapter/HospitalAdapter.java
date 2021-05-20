package com.richer.myhospital.home.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.model.Hospital;

import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    private List<Hospital> hospitals;
    private IHospitalClick hospitalClick;

    public HospitalAdapter(List<Hospital> hospitals, IHospitalClick hospitalClick) {
        this.hospitals = hospitals;
        this.hospitalClick = hospitalClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hospital hospital = hospitals.get(position);
        holder.mNameTv.setText(hospital.getName());
        holder.mTypeTv.setText(hospital.getType());
        holder.mRankTv.setText(hospital.getRank());
        holder.mPhoneTv.setText(hospital.getPhone());
        holder.mAddressTv.setText(hospital.getAddress());
        holder.mItemCardView.setOnClickListener(v -> hospitalClick.onClickListener(hospital));
    }

    @Override
    public int getItemCount() {
        return hospitals.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mItemCardView;
        TextView mNameTv;
        TextView mTypeTv;
        TextView mRankTv;
        TextView mPhoneTv;
        TextView mAddressTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemCardView = itemView.findViewById(R.id.cardview_hospital_item);
            mNameTv = itemView.findViewById(R.id.tv_name_hospital_item);
            mTypeTv = itemView.findViewById(R.id.tv_type_hospital_item);
            mRankTv = itemView.findViewById(R.id.tv_rank_hospital_item);
            mPhoneTv = itemView.findViewById(R.id.tv_phone_hospital_item);
            mAddressTv = itemView.findViewById(R.id.tv_address_hospital_item);
        }
    }

    public interface IHospitalClick {
        void onClickListener(Hospital hospital);
    }

}
