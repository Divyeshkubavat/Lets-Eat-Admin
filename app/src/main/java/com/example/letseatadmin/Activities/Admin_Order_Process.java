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
import com.example.letseatadmin.Adapter.ProcessAdapter;
import com.example.letseatadmin.Models.Order;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Order_Process extends AppCompatActivity {

    SearchView Admin_Order_Process_Searchview;
    RecyclerView Admin_Order_Process_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Order> list;
    ProcessAdapter adapter;
    ProgressDialog pg;
    public static LottieAnimationView Admin_Process_Lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_process);
        Admin_Order_Process_Recyclerview=findViewById(R.id.Admin_Order_Process_Recyclerview);
        Admin_Process_Lottie=findViewById(R.id.Admin_Process_Lottie);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        pg = new ProgressDialog(Admin_Order_Process.this);
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
    private void setData(){
        adminApi.getOrderByState(2).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list= (ArrayList<Order>) response.body();
                adapter = new ProcessAdapter(list,Admin_Order_Process.this);
                Admin_Order_Process_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Order_Process.this,LinearLayoutManager.VERTICAL,false));
                if(adapter.getItemCount()==0){
                    Admin_Process_Lottie.setVisibility(View.VISIBLE);
                }else {
                    adapter.notifyDataSetChanged();
                    Admin_Process_Lottie.setVisibility(View.GONE);
                    Admin_Order_Process_Recyclerview.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        setData();
        super.onResume();
    }
}