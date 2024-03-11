package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import com.example.letseatadmin.Adapter.OrderAdapter;
import com.example.letseatadmin.Models.Order;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Order_Display extends AppCompatActivity {
    SearchView Admin_Order_Searchview;
    RecyclerView Admin_Order_Recyclerview;

    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Order> list;

    OrderAdapter adapter;
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_display);
        Admin_Order_Recyclerview=findViewById(R.id.Admin_Order_Recyclerview);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        pg = new ProgressDialog(Admin_Order_Display.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Fetching Order ....");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },3000);
    }
    public void setData(){
        adminApi.getOrderByState(1).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list= (ArrayList<Order>) response.body();
                adapter = new OrderAdapter(list,Admin_Order_Display.this);
                Admin_Order_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Order_Display.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                Admin_Order_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
}