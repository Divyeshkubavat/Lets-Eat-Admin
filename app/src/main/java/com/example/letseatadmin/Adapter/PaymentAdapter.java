package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseatadmin.Models.Payment;
import com.example.letseatadmin.Models.Product;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.MyViewHolder> {

    ArrayList<Payment> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public PaymentAdapter(ArrayList<Payment> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new PaymentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_payment_design,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Payment p = list.get(position);
        holder.amount.setText(String.valueOf(p.getFinalTotal()));
        holder.status.setText(p.getStatus());
        holder.orderId.setText(String.valueOf(p.getOrderId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView orderId,status,amount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           orderId=itemView.findViewById(R.id.Admin_Payment_Design_OrderID);
           status=itemView.findViewById(R.id.Admin_Payment_Design_Status);
           amount=itemView.findViewById(R.id.Admin_Payment_Design_Amount);
        }
    }
}
