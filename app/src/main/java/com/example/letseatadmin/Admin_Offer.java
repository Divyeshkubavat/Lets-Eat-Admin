package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.letseatadmin.Adapter.OfferAdapter;
import com.example.letseatadmin.Models.Offer;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Offer extends AppCompatActivity {

    FloatingActionButton Admin_Offer_Add_FloatingButton;
    RecyclerView Admin_Offer_RecyclerView;
    OfferAdapter offerAdapter;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Offer> offerArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer);
        Admin_Offer_Add_FloatingButton = findViewById(R.id.Admin_Offer_ADD_FloatButton);
        Admin_Offer_RecyclerView = findViewById(R.id.Admin_Offer_Recyclerview);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        offerArrayList = new ArrayList<>();
        loadData();
        Admin_Offer_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Offer_Add.class));
            }
        });
    }
    private void loadData()
    {
        adminApi.getAllOffer().enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                offerArrayList = (ArrayList<Offer>) response.body();
                offerAdapter = new OfferAdapter(offerArrayList,Admin_Offer.this);
                Admin_Offer_RecyclerView.setLayoutManager(new LinearLayoutManager(Admin_Offer.this,LinearLayoutManager.VERTICAL,false));
                offerAdapter.notifyDataSetChanged();
                Admin_Offer_RecyclerView.setAdapter(offerAdapter);
            }
            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                Toast.makeText(Admin_Offer.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }


}