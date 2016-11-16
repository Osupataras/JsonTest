package com.osypchuk.taras.jsontest;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import info.androidhive.jsonparsing.R;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.MatchViewHolder>{

    List<Match> match;
    RVAdapter(List<Match> match){
        this.match = match;
    }


    @Override
    public MatchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        MatchViewHolder matchViewHolder = new MatchViewHolder(view);
        return matchViewHolder;
    }

    @Override
    public void onBindViewHolder(MatchViewHolder holder, int position) {
        holder.hero_icon.setImageResource(match.get(position).hero_icon_id);
        holder.hero_name.setText(match.get(position).hero_name);
        holder.lobby_type.setText(match.get(position).lobby_type);
        holder.start_time.setText(match.get(position).start_time);
        holder.win.setText(match.get(position).win);
        if(match.get(position).win.equals("Won")){
            holder.win.setTextColor(Color.GREEN);
        }
        else {holder.win.setTextColor(Color.RED);}
        holder.cv.setCardBackgroundColor(Color.GRAY);


    }

    @Override
    public int getItemCount() {
        return match == null ? 0 : match.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class MatchViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView hero_icon;
        TextView hero_name;
        TextView win;
        TextView lobby_type;
        TextView start_time;

        MatchViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            hero_icon = (ImageView) itemView.findViewById(R.id.hero_icon);
            hero_name = (TextView) itemView.findViewById(R.id.hero_name);
            win = (TextView) itemView.findViewById(R.id.win);
            lobby_type = (TextView) itemView.findViewById(R.id.lobby_type);
            start_time = (TextView) itemView.findViewById(R.id.start_time);

        }
    }

}