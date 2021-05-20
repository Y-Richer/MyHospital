package com.richer.myhospital.home.homepage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.richer.myhospital.R;
import com.richer.myhospital.home.model.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    List<City> cities;
    ICityClick cityClick;

    public CityAdapter(List<City> cities, ICityClick cityClick) {
        this.cities = cities;
        this.cityClick = cityClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = cities.get(position);
        holder.mNameTv.setText(city.getName());
        holder.mCityLayout.setOnClickListener(v -> cityClick.onClickListener(city));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mCityLayout;
        TextView mNameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCityLayout = itemView.findViewById(R.id.layout_item_city);
            mNameTv = itemView.findViewById(R.id.tv_name_item_city);
        }
    }

    public interface ICityClick {
        void onClickListener(City city);
    }

}
