package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseatadmin.Adapter.OrderAdapter;
import com.example.letseatadmin.Adapter.OrderCompleteAdapter;
import com.example.letseatadmin.Models.Order;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Order_Compete extends AppCompatActivity {

    SearchView Admin_Order_Searchview;
    RecyclerView Admin_Order_Complete_Recyclerview;

    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Order> list;

    OrderCompleteAdapter adapter;
    ProgressDialog pg;
    LottieAnimationView Admin_Complete_Lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_compete);
        Admin_Order_Complete_Recyclerview=findViewById(R.id.Admin_Order_Complete_Recyclerview);
        Admin_Complete_Lottie=findViewById(R.id.Admin_Complete_Lottie);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        pg = new ProgressDialog(Admin_Order_Compete.this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Fetching Order ....");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },1500);

    }
    public void setData(){
        adminApi.getOrderByState(5).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list= (ArrayList<Order>) response.body();
                adapter = new OrderCompleteAdapter(list,Admin_Order_Compete.this);
                Admin_Order_Complete_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Order_Compete.this,LinearLayoutManager.VERTICAL,false));
                if(adapter.getItemCount()==0)
                {
                    Admin_Complete_Lottie.setVisibility(View.VISIBLE);
                }else {
                    adapter.notifyDataSetChanged();
                    Admin_Complete_Lottie.setVisibility(View.GONE);
                    Admin_Order_Complete_Recyclerview.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

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