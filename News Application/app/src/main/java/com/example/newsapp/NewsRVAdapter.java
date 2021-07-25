package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Articles> articlesArrayList;

    public NewsRVAdapter(Context context, ArrayList<Articles> articlesArrayList) {
        this.context = context;
        this.articlesArrayList = articlesArrayList;
    }

    @NonNull
    @Override
    public NewsRVAdapter.ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        //We have told that inflate the news_rv layout
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.new_rv_item,parent,false);
        //And return it
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRVAdapter.ViewHolder holder, int position) {
        //All data stored in articles and then we will send it to all the view holders
      Articles articles=articlesArrayList.get(position);
      holder.titleTV.setText(articles.getTitle());
      holder.subTitleTV.setText(articles.getDescription());
      //NNow to get image from url we will use picasso
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsIV);
        //Now add on click event so that when we click on image we can get more new
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,NewsDetailActivity.class);
                i.putExtra("title",articles.getTitle());
                i.putExtra("desc",articles.getDescription());
                i.putExtra("content",articles.getContent());
                i.putExtra("url",articles.getUrl());
                i.putExtra("image",articles.getUrlToImage());
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Create Instance of all the member in new_rv_view
        private TextView titleTV,subTitleTV;
        private ImageView newsIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV=itemView.findViewById(R.id.idTVNewHeading);
            subTitleTV=itemView.findViewById(R.id.idTVSubTitle);
            newsIV=itemView.findViewById(R.id.idIVNews);
        }
    }
}
