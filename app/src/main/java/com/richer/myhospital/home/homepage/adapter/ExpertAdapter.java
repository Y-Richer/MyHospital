package com.richer.myhospital.home.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.model.Expert;

import java.util.List;

public class ExpertAdapter extends RecyclerView.Adapter<ExpertAdapter.ViewHolder> {

    List<Expert> expertList;
    IExpertClick click;

    public ExpertAdapter(List<Expert> expertList, IExpertClick click) {
        this.expertList = expertList;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expert, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expert expert = expertList.get(position);
        holder.mNameTv.setText(expert.getName());
        holder.mSexTv.setText(expert.getSex());
        holder.mPositionTv.setText(expert.getPosition());
        holder.mDepartmentTv.setText(expert.getDepartment());
        holder.mReserveBtn.setOnClickListener(v -> click.onClickListener(expert));
    }

    @Override
    public int getItemCount() {
        return expertList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mNameTv;
        TextView mSexTv;
        TextView mPositionTv;
        TextView mDepartmentTv;
        Button mReserveBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTv = itemView.findViewById(R.id.tv_name_expert);
            mSexTv = itemView.findViewById(R.id.tv_sex_expert);
            mPositionTv = itemView.findViewById(R.id.tv_position_expert);
            mDepartmentTv = itemView.findViewById(R.id.tv_department_expert);
            mReserveBtn = itemView.findViewById(R.id.btn_reserve_expert);
        }
    }

    public interface IExpertClick {
        void onClickListener(Expert expert);
    }

}
