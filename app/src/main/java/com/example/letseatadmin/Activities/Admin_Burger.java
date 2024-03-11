package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class Admin_Burger extends AppCompatActivity {
    FloatingActionButton Admin_Burger_Add_FloatingButton;
    SearchView Admin_Burger_Searchview;
    RecyclerView Admin_Burger_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Product> list;
    productAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_burger);
        Admin_Burger_Add_FloatingButton = findViewById(R.id.Admin_Burger_ADD_FloatButton);
        Admin_Burger_Searchview=findViewById(R.id.Admin_Burger_Searchbar);
        Admin_Burger_Recyclerview=findViewById(R.id.Admin_Burger_Recyclerview);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        Admin_Burger_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
        Admin_Burger_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    private void search(String key)
    {
        adminApi.searchProduct(key,201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Burger.this);
                Admin_Burger_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Burger.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Burger_Recyclerview.setAdapter(productAdapter);
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void setData(){
        adminApi.getSingleProduct(201).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Burger.this);
                Admin_Burger_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Burger.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Burger_Recyclerview.setAdapter(productAdapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }
}