package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.letseatadmin.Adapter.productAdapter;
import com.example.letseatadmin.Models.Product;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Drink extends AppCompatActivity {
    FloatingActionButton Admin_Drink_Add_FloatingButton;
    SearchView Admin_Drink_Searchview;
    RecyclerView Admin_Drink_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Product> list;
    com.example.letseatadmin.Adapter.productAdapter productAdapter;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drink);
        Admin_Drink_Add_FloatingButton=findViewById(R.id.Admin_Drink_ADD_FloatButton);
        Admin_Drink_Searchview=findViewById(R.id.Admin_Drink_Searchbar);
        Admin_Drink_Recyclerview=findViewById(R.id.Admin_Drink_Recyclerview);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        pg = new ProgressDialog(this);
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait Deleting Order ....");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        setProduct();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pg.dismiss();
            }
        },500);
        Admin_Drink_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
        Admin_Drink_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });
    }

    private void setProduct()
    {
        adminApi.getSingleProduct(204).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Drink.this);
                Admin_Drink_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Drink.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Drink_Recyclerview.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void search(String key)
    {
        adminApi.searchProduct(key,204).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Drink.this);
                Admin_Drink_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Drink.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Drink_Recyclerview.setAdapter(productAdapter);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        setProduct();
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