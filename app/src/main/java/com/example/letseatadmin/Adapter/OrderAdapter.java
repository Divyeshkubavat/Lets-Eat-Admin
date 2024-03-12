package com.example.letseatadmin.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    ArrayList<Order> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    public OrderAdapter(ArrayList<Order> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new OrderAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_order_design,parent,false));

    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        final Order o = list.get(position);
        holder.Admin_Order_Design_Order_Id.setText(String.valueOf(o.getId()));
        holder.Admin_Order_Design_Total.setText(String.valueOf(o.getFinalPayment()));
        holder.Admin_Order_Design_Address.setText(o.getAddress());

        holder.Admin_Order_Design_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Admin_Order_See_Detail.class);
                i.putExtra("oid",o.getId());
                i.putExtra("finaltotal",o.getFinalPayment());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }
        });
        holder.Admin_Order_Design_Approvebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order o1 = new Order();
                o1.setState(2);
                ProgressDialog pg;
                pg = new ProgressDialog(view.getContext());
                pg.setTitle("Loading..... ");
                pg.setMessage("Please wait ....");
                pg.setCanceledOnTouchOutside(false);
                pg.show();
                adminApi.updateOrder(o.getId(),o1).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {

                        Toast.makeText(context, "Order Accepted Successfully", Toast.LENGTH_SHORT).show();

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
        TextView Admin_Order_Design_Order_Id,Admin_Order_Design_Total,Admin_Order_Design_Address;
        TextView Admin_Order_Design_Detail;
        MaterialButton Admin_Order_Design_Approvebutton;
        Date d;
        int count=1000;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Admin_Order_Design_Order_Id=itemView.findViewById(R.id.Admin_Order_Design_Order_Id);
            Admin_Order_Design_Total=itemView.findViewById(R.id.Admin_Order_Design_Total);
            Admin_Order_Design_Address=itemView.findViewById(R.id.Admin_Order_Design_Address);
            Admin_Order_Design_Detail=itemView.findViewById(R.id.Admin_Order_Design_Detail);
            Admin_Order_Design_Approvebutton=itemView.findViewById(R.id.Admin_Order_Design_Approvebutton);
        }
    }

}
