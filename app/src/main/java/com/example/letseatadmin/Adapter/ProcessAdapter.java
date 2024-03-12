package com.example.letseatadmin.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseatadmin.Activities.Admin_Order_See_Detail;
import com.example.letseatadmin.Models.Order;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProcessAdapter extends RecyclerView.Adapter<ProcessAdapter.MyViewHolder> {

    ArrayList<Order> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public ProcessAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new ProcessAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_order_process_design,parent,false));

    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Order o = list.get(position);
        holder.orderId.setText(String.valueOf(o.getId()));
        holder.total.setText(String.valueOf(o.getFinalPayment()));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Admin_Order_See_Detail.class);
                i.putExtra("oid",o.getId());
                i.putExtra("finaltotal",o.getFinalPayment());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });
        holder.process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order o1 = new Order();
                o1.setState(3);
                ProgressDialog pg;
                pg = new ProgressDialog(view.getContext());
                pg.setTitle("Loading..... ");
                pg.setMessage("Please wait Accept Oder  ....");
                pg.setCanceledOnTouchOutside(false);
                pg.show();
                adminApi.updateOrder(o.getId(),o1).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {

                        Toast.makeText(context, "Processed Successfully", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pg.dismiss();
                    }
                },2000);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderId,total;
        TextView detail;
        MaterialButton process;
        Date d;
        int count=1000;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId=itemView.findViewById(R.id.Admin_Order_Process_Design_Order_Id);
            total=itemView.findViewById(R.id.Admin_Order_Process_Design_Total);
            detail=itemView.findViewById(R.id.Admin_Order_Design_Process_SeeDetail);
            process=itemView.findViewById(R.id.Admin_Order_Design_Processbtn);
        }
    }

}
