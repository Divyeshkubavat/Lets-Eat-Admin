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

public class Admin_Pizza extends AppCompatActivity {
    FloatingActionButton Admin_Pizza_Add_FloatingButton;
    SearchView Admin_Pizza_Searchview;
    RecyclerView Admin_Pizza_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Product> list;
    com.example.letseatadmin.Adapter.productAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pizza);
        Admin_Pizza_Add_FloatingButton=findViewById(R.id.Admin_Pizza_ADD_FloatButton);
        Admin_Pizza_Searchview=findViewById(R.id.Admin_Pizza_Searchbar);
        Admin_Pizza_Recyclerview=findViewById(R.id.Admin_Pizza_Recyclerview);
        retrofitServices = new RetrofitServices();
        list = new ArrayList<>();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setProduct();
        Admin_Pizza_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
        Admin_Pizza_Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        adminApi.getSingleProduct(202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Pizza.this);
                Admin_Pizza_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Pizza.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Pizza_Recyclerview.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }
    private void search(String key)
    {
        adminApi.searchProduct(key,202).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list = (ArrayList<Product>) response.body();
                productAdapter = new productAdapter(list,Admin_Pizza.this);
                Admin_Pizza_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Pizza.this,LinearLayoutManager.VERTICAL,false));
                productAdapter.notifyDataSetChanged();
                Admin_Pizza_Recyclerview.setAdapter(productAdapter);
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
}