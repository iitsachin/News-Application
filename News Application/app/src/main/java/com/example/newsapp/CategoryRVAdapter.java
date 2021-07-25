package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryRVModel> categoryRVModels;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(Context context, ArrayList<CategoryRVModel> categoryRVModels, CategoryClickInterface categoryClickInterface) {
        this.context = context;
        this.categoryRVModels = categoryRVModels;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
            CategoryRVModel categoryRVModel=categoryRVModels.get(position);
            holder.categoryTV.setText(categoryRVModel.getCategory());
        Picasso.get().load(categoryRVModel.getCategoryImageUrl()).into(holder.categoryIV);
        //Now set up click event listner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModels.size();
    }
    public interface CategoryClickInterface{
        void onCategoryClick(int position) ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize image and category here
        private ImageView categoryIV;
        private TextView categoryTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryIV=itemView.findViewById(R.id.idIVCategory);
            categoryTV=itemView.findViewById(R.id.idTVcatogaries);
        }
    }
}
