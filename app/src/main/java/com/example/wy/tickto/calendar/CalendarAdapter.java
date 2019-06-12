package com.example.wy.tickto.calendar;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.wy.tickto.R;
import com.example.wy.tickto.entity.ThingInfo;

import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private List<ThingInfo> calendarV;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView calendar_thing;
        TextView calendar_date;
        TextView calendar_rank;
        CheckBox calendar_check;

        public ViewHolder(View view){
            super(view);
            calendar_thing = (TextView) view.findViewById(R.id.calendar_thing);
            calendar_date = (TextView) view.findViewById(R.id.calendar_date);
            calendar_rank = (TextView) view.findViewById(R.id.calendar_rank);
            calendar_check = (CheckBox) view.findViewById(R.id.calendar_check);
        }

    }

    public CalendarAdapter(List<ThingInfo> thingInfoList){

        calendarV = thingInfoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_context,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ThingInfo things = calendarV.get(position);
        holder.calendar_thing.setText(things.getWhatthing());
        holder.calendar_date.setText(things.getCal_date());

        int rank = things.getHowimportant();
        String imp = null;
        if(rank==1){
            imp = new String("重要");
            holder.calendar_rank.setTextColor(Color.RED);
        }else if(rank==2){
            imp = new String("中等");
            holder.calendar_rank.setTextColor(Color.BLUE);
        }else{
            imp = new String("悠闲");
            holder.calendar_rank.setTextColor(Color.GREEN);
        }
        holder.calendar_rank.setText(imp);
        holder.calendar_check.setChecked(things.isDone());
    }

    @Override
    public int getItemCount() {
        return calendarV.size();
    }

}
