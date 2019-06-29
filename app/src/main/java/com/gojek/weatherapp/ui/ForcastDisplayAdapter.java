package com.gojek.weatherapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gojek.weatherapp.R;
import com.gojek.weatherapp.model.ModifiedWeatherResponse;

import java.util.List;

public class ForcastDisplayAdapter extends RecyclerView.Adapter<ForcastDisplayAdapter.ApplicationViewHolder> {

    private List<ModifiedWeatherResponse> modifiedWeatherResponseList;
    private Context mContext;

    public ForcastDisplayAdapter(Context context, List<ModifiedWeatherResponse> modifiedWeatherResponses) {
        this.modifiedWeatherResponseList = modifiedWeatherResponses;
        mContext = context;
    }


    @Override
    public ApplicationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ApplicationViewHolder(parent.getContext());
    }

    @Override
    public void onBindViewHolder(ApplicationViewHolder holder, int position) {
        ModifiedWeatherResponse modifiedWeatherResponse = modifiedWeatherResponseList.get(position);
        holder.dataTwo.setText(modifiedWeatherResponse.getTemperature());
        holder.dataOne.setText(modifiedWeatherResponse.getDay());

    }

    @Override
    public int getItemCount() {
        return modifiedWeatherResponseList == null ? 0 : modifiedWeatherResponseList.size();
    }

    static class ApplicationViewHolder extends RecyclerView.ViewHolder {

        TextView dataOne;
        TextView dataTwo;

        public ApplicationViewHolder(Context context) {
            this(LayoutInflater.from(context).inflate(R.layout.forcast_item, null));
        }

        private ApplicationViewHolder(View itemView) {
            super(itemView);
            dataOne = (TextView) itemView.findViewById(R.id.data_one);
            dataTwo = (TextView) itemView.findViewById(R.id.data_two);
        }
    }
}
