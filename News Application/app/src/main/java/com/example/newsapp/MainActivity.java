package com.example.newsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

//d6c11541b3c44a679348d866775f6600
public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {
     private RecyclerView newsRV,categoryRV;
     private ProgressBar loadingPB;
     private ArrayList<Articles> articlesArrayList;
     private ArrayList<CategoryRVModel> categoryRVModelArrayList;
     private NewsRVAdapter newsRVAdapter;
     private CategoryRVAdapter categoryRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV=findViewById(R.id.idRVNews);
        categoryRV=findViewById(R.id.idRvCatogaries);
        loadingPB=findViewById(R.id.idPBLoading);
        articlesArrayList=new ArrayList<>();
        categoryRVModelArrayList= new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(this, articlesArrayList);
        categoryRVAdapter=new CategoryRVAdapter(this, categoryRVModelArrayList,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        //Set adapter
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);

        //Now set the data in category and news rv's
        getCategories();
        //By default show all news
        getnews("All");
        newsRVAdapter.notifyDataSetChanged();
    }
    private void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("All","https://images.unsplash.com/photo-1556117153-0f1cad722b5a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1yZWxhdGVkfDEyfHx8ZW58MHx8fHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1461749280684-dccba630e2f6?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzh8fHRlY2hub2xvZ3l8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science","https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8c2NpZW5jZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports","https://images.unsplash.com/photo-1599586120429-48281b6f0ece?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        categoryRVModelArrayList.add(new CategoryRVModel("General","https://images.unsplash.com/photo-1512314889357-e157c22f938d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nzh8fGdlbmVyYWx8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business","https://images.unsplash.com/photo-1607863680198-23d4b2565df0?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjR8fGZpbmFuY2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health","https://images.unsplash.com/photo-1526256262350-7da7584cf5eb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzF8fGhlYWx0aHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment","https://images.unsplash.com/photo-1603739903239-8b6e64c3b185?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZW50ZXJ0YWlubWVudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
    }
    private void getnews(String category){
        //once this call show progress bar
        loadingPB.setVisibility(View.VISIBLE);
        //Set new data so first clear old data
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apikey=d6c11541b3c44a679348d866775f6600";
        String url="https://newsapi.org/v2/top-headlines?country=in&sortBy=publishedAt&language=en&apiKey=d6c11541b3c44a679348d866775f6600";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();
        //This comes from the class we have made in starting
        RetrofitAPI retrofitAPI= retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All"))
            call=retrofitAPI.getAllNews(url);
        else{
            call=retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel=response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles=newsModel.getArticles();
                for(int i=0;i<articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrl(),
                            articles.get(i).getUrlToImage(),articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Not able to load news for you",
                        Toast.LENGTH_LONG)
                        .show();
            }
        });


    }
    @Override
    public void onCategoryClick(int position) {
      //Now call get_news on a click to load the news
        String category=categoryRVModelArrayList.get(position).getCategory();
        getnews(category);
    }
}