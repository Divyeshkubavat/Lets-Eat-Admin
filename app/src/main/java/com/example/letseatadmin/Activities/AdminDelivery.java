package com.example.letseatadmin.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letseatadmin.Adapter.deliveryBoyAdapter;
import com.example.letseatadmin.Adapter.staffAdapter;
import com.example.letseatadmin.Models.Staff;
import com.example.letseatadmin.Models.deliveryBoy;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDelivery extends Fragment {

    FloatingActionButton Admin_Delivery_Add_FloatButton;

    RecyclerView Admin_Delivery_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<deliveryBoy> deliveryBoysList;
    deliveryBoyAdapter deliveryAdapter;
    ProgressDialog pg;
    SearchView Admin_Delivery_Searchbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_delivery, container, false);
        Admin_Delivery_Add_FloatButton = view.findViewById(R.id.Admin_Delivery_ADD_FloatButton);
        Admin_Delivery_Recyclerview=view.findViewById(R.id.Admin_Delivery_Recyclerview);
        Admin_Delivery_Searchbar=view.findViewById(R.id.Admin_Delivery_Searchbar);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        deliveryBoysList=new ArrayList<>();
        pg = new ProgressDialog(getContext());
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
        },500);
        Admin_Delivery_Add_FloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Admin_delivery_Add_Edit.class));
            }
        });
        Admin_Delivery_Searchbar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        return view;
    }
    private  void setData(){
        adminApi.getAllDeliveryBoy().enqueue(new Callback<List<deliveryBoy>>() {
            @Override
            public void onResponse(Call<List<deliveryBoy>> call, Response<List<deliveryBoy>> response) {
                deliveryBoysList= (ArrayList<deliveryBoy>) response.body();
                deliveryAdapter = new deliveryBoyAdapter(deliveryBoysList,getContext());
                Admin_Delivery_Recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                deliveryAdapter.notifyDataSetChanged();
                Admin_Delivery_Recyclerview.setAdapter(deliveryAdapter);
            }
            @Override
            public void onFailure(Call<List<deliveryBoy>> call, Throwable t) {

            }
        });
    }
    private void search(String s){
        adminApi.searchDelivery(s).enqueue(new Callback<List<deliveryBoy>>() {
            @Override
            public void onResponse(Call<List<deliveryBoy>> call, Response<List<deliveryBoy>> response) {
                deliveryBoysList= (ArrayList<deliveryBoy>) response.body();
                deliveryAdapter = new deliveryBoyAdapter(deliveryBoysList,getContext());
                Admin_Delivery_Recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                deliveryAdapter.notifyDataSetChanged();
                Admin_Delivery_Recyclerview.setAdapter(deliveryAdapter);
            }

            @Override
            public void onFailure(Call<List<deliveryBoy>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }
}