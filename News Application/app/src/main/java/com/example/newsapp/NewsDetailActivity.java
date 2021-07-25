package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    //Store all variables coming through intent
    String title,desc,image,url,content;
    private TextView TVTitle,TVSubTitle,TVContent;
    private ImageView IVNews;
    private Button BtnRead;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        image=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");
        //Initialize all
        content=getIntent().getStringExtra("content");
        TVTitle=findViewById(R.id.TVTitle);
        TVSubTitle=findViewById(R.id.TVSubTitle);
        TVContent=findViewById(R.id.TVContent);
        IVNews=findViewById(R.id.IVNews);
        BtnRead=findViewById(R.id.BtnRead);
        //Set content in all
        TVTitle.setText(title);
        TVSubTitle.setText(desc);
        TVContent.setText(content);
        Picasso.get().load(image).into(IVNews);
        //Add event listner
        BtnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}