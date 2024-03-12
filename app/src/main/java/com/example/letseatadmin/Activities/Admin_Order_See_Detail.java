package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.letseatadmin.Adapter.OrderProductAdapter;
import com.example.letseatadmin.Models.Order;
import com.example.letseatadmin.Models.OrderProduct;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Order_See_Detail extends AppCompatActivity {

    TextView Admin_See_Order_Id,Admin_Order_See_Order_Total;
    RecyclerView Admin_See_Order_RecyclerView;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    ArrayList<OrderProduct> list;

    OrderProductAdapter adapter;
    double total;
    ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_see_detail);
        Admin_See_Order_Id=findViewById(R.id.Admin_See_Order_Id);
        Admin_See_Order_RecyclerView=findViewById(R.id.Admin_See_Order_RecyclerView);
        Admin_Order_See_Order_Total=findViewById(R.id.Admin_Order_See_Order_Total);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        pg = new ProgressDialog(this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Deleting Order ....");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },300);
        list=new ArrayList<>();
        setData();
    }
    private void setData(){
        Intent i =getIntent();
        total = i.getDoubleExtra("finaltotal",0);
        Admin_Order_See_Order_Total.setText(String.valueOf(total));
        int id = i.getIntExtra("oid",0);
        Admin_See_Order_Id.setText(String.valueOf(id));
        adminApi.getSingleOrder(id).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                list= (ArrayList<OrderProduct>) response.body().getOrderProducts();
                adapter = new OrderProductAdapter(list,Admin_Order_See_Detail.this);
                Admin_See_Order_RecyclerView.setLayoutManager(new LinearLayoutManager(Admin_Order_See_Detail.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                Admin_See_Order_RecyclerView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}