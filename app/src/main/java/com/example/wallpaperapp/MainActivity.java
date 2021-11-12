package com.example.wallpaperapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private ArrayList<ImageModel> modelClassList;
    private RecyclerView recyclerView;
    Adapter adapter;
    CardView mnature, mbus, mcar, mtrain, mtrending;
    EditText editText;
    ImageButton search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        recyclerView=findViewById(R.id.recyclerView);
        mnature=findViewById(R.id.nature);
        mcar=findViewById(R.id.car);
        mbus=findViewById(R.id.bus);
        mtrain=findViewById(R.id.train);
        mtrending=findViewById(R.id.trending);

        editText=findViewById(R.id.edittext);
        search=findViewById(R.id.search);



        modelClassList=new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        adapter=new Adapter(getApplicationContext(), modelClassList);
        recyclerView.setAdapter(adapter);
        findPhotos();


        mnature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="nature";
                getSearchImage(query);
            }
        });

        mcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="car";
                getSearchImage(query);
            }
        });

        mtrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="train";
                getSearchImage(query);
            }
        });

        mbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query="bus";
                getSearchImage(query);
            }
        });

        mtrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhotos();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId)
                {
                    case EditorInfo.IME_ACTION_GO:
                        String query=editText.getText().toString().trim().toLowerCase();
                        if(query.isEmpty())
                        {
                            Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            getSearchImage(query);
                        }

                        break;
                }
                return true;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query=editText.getText().toString().trim().toLowerCase();
                if(query.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Something", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getSearchImage(query);
                }
            }
        });


    }


    private void getSearchImage(String query) {

        ApiUtilities.getApiInterface().getSearchImage(query, 1, 80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                modelClassList.clear();
                if(response.isSuccessful())
                {
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not able to get images", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });
    }

    private void findPhotos() {

        ApiUtilities.getApiInterface().getImage(1,80).enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {

                modelClassList.clear();
                if(response.isSuccessful())
                {
                    modelClassList.addAll(response.body().getPhotos());
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Not able to get images", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

            }
        });

    }
}