package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

public class OrderCompleteAdapter extends RecyclerView.Adapter<OrderCompleteAdapter.MyViewHolder> {

    ArrayList<Order> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public OrderCompleteAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new OrderCompleteAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_order_complete_design,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Order o = list.get(position);
        holder.OrderId.setText(String.valueOf(o.getId()));
        holder.Total.setText(String.valueOf(o.getFinalPayment()));
        holder.Address.setText(o.getAddress());

        holder.Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Admin_Order_See_Detail.class);
                i.putExtra("oid",o.getId());
                i.putExtra("finaltotal",o.getFinalPayment());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView OrderId,Total,Address;
        TextView Detail;
        Date d;
        int count=1000;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           OrderId=itemView.findViewById(R.id.Admin_Order_Complete_Design_Order_Id);
           Total=itemView.findViewById(R.id.Admin_Order_Complete_Design_Total);
           Address=itemView.findViewById(R.id.Admin_Order_Complete_Design_Address);
           Detail=itemView.findViewById(R.id.Admin_Order_Complete_Design_Detail);
        }
    }
}
