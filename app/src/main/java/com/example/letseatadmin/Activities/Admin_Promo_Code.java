package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.letseatadmin.Adapter.PromoCodeAdapter;
import com.example.letseatadmin.Adapter.staffAdapter;
import com.example.letseatadmin.Models.PromoCode;
import com.example.letseatadmin.Models.Staff;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Promo_Code extends AppCompatActivity {
    FloatingActionButton Admin_Code_Add_FlotingButton;
    RecyclerView Admin_Promo_Display;

    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<PromoCode> codes;

    ProgressDialog pg;
    PromoCodeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_promo_code);
        Admin_Code_Add_FlotingButton=findViewById(R.id.Admin_Code_Add_FlotingButton);
        Admin_Promo_Display=findViewById(R.id.Admin_Promo_Display);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        codes=new ArrayList<>();
        pg = new ProgressDialog(this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Fetching Codes ....");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },500);

        Admin_Code_Add_FlotingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Promo_Code_Add.class));
            }
        });

    }
    private void setData(){
        adminApi.getAllCodes().enqueue(new Callback<List<PromoCode>>() {
            @Override
            public void onResponse(Call<List<PromoCode>> call, Response<List<PromoCode>> response) {
                codes= (ArrayList<PromoCode>) response.body();
                adapter=new PromoCodeAdapter(codes,Admin_Promo_Code.this);
                Admin_Promo_Display.setLayoutManager(new LinearLayoutManager(Admin_Promo_Code.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                Admin_Promo_Display.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<PromoCode>> call, Throwable t) {

            }
        });
    }
}