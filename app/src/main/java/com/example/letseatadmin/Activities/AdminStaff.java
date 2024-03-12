package com.example.letseatadmin.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.letseatadmin.Adapter.staffAdapter;
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


public class AdminStaff extends Fragment {
    FloatingActionButton Admin_Staff_Add_FloatButton;
    RecyclerView Admin_Staff_Recyclerview;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ArrayList<Staff> staff;

    ProgressDialog pg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_staff, container, false);
        Admin_Staff_Add_FloatButton = view.findViewById(R.id.Admin_Staff_ADD_FloatButton);
        Admin_Staff_Recyclerview=view.findViewById(R.id.Admin_Staff_Recyclerview);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        staff = new ArrayList<>();
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
        Admin_Staff_Add_FloatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Staff_Add.class));
            }
        });
    return view;
    }
    private void setData(){
        adminApi.getAllStaff().enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                staff = (ArrayList<Staff>) response.body();
                staffAdapter adapter = new staffAdapter(staff,getContext());
                Admin_Staff_Recyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                Admin_Staff_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        setData();
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }
}