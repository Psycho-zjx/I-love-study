package com.example.networktest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import bean.Javabean;

/**
 * Created by 。 on 2017/12/30.
 */

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    private List<Javabean.ObjectBean.AaDataBean> Scores_List;
    public Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View ScoreView;
        TextView mkcm;
        TextView mxf;
        TextView mcj;
        TextView mdj;
        TextView mjd;

        public ViewHolder(View view) {
            super(view);
            ScoreView = view;
            mkcm = (TextView) view.findViewById(R.id.kcm);
            mxf = (TextView) view.findViewById(R.id.xf);
            mcj = (TextView) view.findViewById(R.id.cj);
            mdj = (TextView) view.findViewById(R.id.dj);
            mjd = (TextView) view.findViewById(R.id.jd);

        }
    }

    public ScoresAdapter(List<Javabean.ObjectBean.AaDataBean>  ScoresList) {
        Scores_List =  ScoresList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scores, parent, false);
        context = parent.getContext();
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Javabean.ObjectBean.AaDataBean scores = Scores_List.get(position);
        holder.mkcm.setText(scores.getKcm());
        holder.mxf.setText(Double.toString(scores.getXf()));
        holder.mcj.setText(Double.toString(scores.getKscj()));
        holder.mdj.setText(scores.getWfzdj());
        holder.mjd.setText(scores.getWfzjd());
    }

    @Override
    public int getItemCount() {
        return Scores_List.size();
    }

}
