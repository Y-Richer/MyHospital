package com.richer.myhospital.home.historypage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.historypage.model.ReserveRecord;
import java.util.List;

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ViewHolder> {

    List<ReserveRecord> recordList;
    IReserveClick click;

    public ReserveAdapter(List<ReserveRecord> recordList, IReserveClick click) {
        this.recordList = recordList;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reserve_record, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReserveRecord record = recordList.get(position);
        holder.mHospitalTv.setText(record.getHospitalName());
        holder.mDepartmentTv.setText(record.getDepartmentName());
        holder.mExpertTv.setText(record.getExpertName());
        holder.mDateTv.setText(record.getDate());
        holder.mTimeTv.setText(record.getTime());
        holder.mCardView.setOnLongClickListener(v -> {
            click.onLongClickListener(record);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView mCardView;
        TextView mHospitalTv;
        TextView mDepartmentTv;
        TextView mExpertTv;
        TextView mDateTv;
        TextView mTimeTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.cardview_reserve_record);
            mHospitalTv = itemView.findViewById(R.id.tv_hospital_reserve_record);
            mDepartmentTv = itemView.findViewById(R.id.tv_department_reserve_record);
            mExpertTv = itemView.findViewById(R.id.tv_expert_reserve_record);
            mDateTv = itemView.findViewById(R.id.tv_date_reserve_record);
            mTimeTv = itemView.findViewById(R.id.tv_time_reserve_record);
        }
    }

    public interface IReserveClick {
        void onLongClickListener(ReserveRecord record);
    }

}
