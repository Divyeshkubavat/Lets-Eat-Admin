package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.letseatadmin.Adapter.Delivery_History_Adapter;
import com.example.letseatadmin.Adapter.PaymentAdapter;
import com.example.letseatadmin.Adapter.productAdapter;
import com.example.letseatadmin.Models.Delivery_history_Item;
import com.example.letseatadmin.Models.Payment;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Payment extends AppCompatActivity {

    RecyclerView Admin_Payment_Recyclerview;
    RadioButton Admin_Payment_All_Radio, Admin_Payment_Paid_Radio, Admin_Payment_Unpaid_Radio;
    TextView Admin_Payment_Total_Income,Admin_Payment_Total_Paid_Income,Admin_Payment_Total_Unpaid_Income;
    RetrofitServices retrofitServices;
    AdminApi adminApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment);
        Admin_Payment_Recyclerview = findViewById(R.id.Admin_Payment_Recyclerview);
        Admin_Payment_Total_Income = findViewById(R.id.Admin_Payment_Total_Income);
        Admin_Payment_Total_Unpaid_Income = findViewById(R.id.Admin_Payment_Total_Unpaid_Income);
        Admin_Payment_All_Radio = findViewById(R.id.Admin_Payment_All_Radio);
        Admin_Payment_Paid_Radio = findViewById(R.id.Admin_Payment_Paid_Radio);
        Admin_Payment_Unpaid_Radio = findViewById(R.id.Admin_Payment_Unpaid_Radio);
        Admin_Payment_Total_Paid_Income = findViewById(R.id.Admin_Payment_Total_Paid_Income);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        adminApi.getAllPayment().enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                PaymentAdapter adapter;
                ArrayList<Payment> list=new ArrayList<>();
                list= (ArrayList<Payment>) response.body();
                adapter = new PaymentAdapter(list,Admin_Payment.this);
                Admin_Payment_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Payment.this,LinearLayoutManager.VERTICAL,false));
                adapter.notifyDataSetChanged();
                Admin_Payment_Recyclerview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {

            }
        });
        Admin_Payment_All_Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.getAllPayment().enqueue(new Callback<List<Payment>>() {
                    @Override
                    public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                        PaymentAdapter adapter;
                        ArrayList<Payment> list=new ArrayList<>();
                        list= (ArrayList<Payment>) response.body();
                        adapter = new PaymentAdapter(list,Admin_Payment.this);
                        Admin_Payment_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Payment.this,LinearLayoutManager.VERTICAL,false));
                        adapter.notifyDataSetChanged();
                        Admin_Payment_Recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Payment>> call, Throwable t) {

                    }
                });
            }
        });
        Admin_Payment_Paid_Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.getPaymentByStatus("done").enqueue(new Callback<List<Payment>>() {
                    @Override
                    public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                        PaymentAdapter adapter;
                        ArrayList<Payment> list=new ArrayList<>();
                        list= (ArrayList<Payment>) response.body();
                        adapter = new PaymentAdapter(list,Admin_Payment.this);
                        Admin_Payment_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Payment.this,LinearLayoutManager.VERTICAL,false));
                        adapter.notifyDataSetChanged();
                        Admin_Payment_Recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Payment>> call, Throwable t) {

                    }
                });
            }
        });
        Admin_Payment_Unpaid_Radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.getPaymentByStatus("pending").enqueue(new Callback<List<Payment>>() {
                    @Override
                    public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                        PaymentAdapter adapter;
                        ArrayList<Payment> list = new ArrayList<>();
                        list= (ArrayList<Payment>) response.body();
                        adapter = new PaymentAdapter(list,Admin_Payment.this);
                        Admin_Payment_Recyclerview.setLayoutManager(new LinearLayoutManager(Admin_Payment.this,LinearLayoutManager.VERTICAL,false));
                        adapter.notifyDataSetChanged();
                        Admin_Payment_Recyclerview.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Payment>> call, Throwable t) {

                    }
                });
            }
        });

    }
    private void setData(){
        adminApi.getTotalPayment().enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                double all=response.body();
                Admin_Payment_Total_Income.setText(String.valueOf(all));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
        adminApi.getTotalPaymentByStatus("Done").enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Admin_Payment_Total_Paid_Income.setText(String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });
        adminApi.getTotalPaymentByStatus("pending").enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                Admin_Payment_Total_Unpaid_Income.setText(String.valueOf(response.body()));
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {

            }
        });

    }
}