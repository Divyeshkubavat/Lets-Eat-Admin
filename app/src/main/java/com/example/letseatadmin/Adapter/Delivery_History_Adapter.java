package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseatadmin.Models.Delivery_history_Item;
import com.example.letseatadmin.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Delivery_History_Adapter extends RecyclerView.Adapter<MyViewHolder>
{
    ArrayList<Delivery_history_Item> list;
    Context context;
    public Delivery_History_Adapter(ArrayList<Delivery_history_Item> list, Context context) {
        this.list = list;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_delivery_history_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Order_ID.setText(list.get(position).getOrder_ID());
        holder.Status.setText(list.get(position).getStatus());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder
{
    TextView Order_ID,Status;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        Order_ID = itemView.findViewById(R.id.Admin_Delivery_History_OrderID);
        Status = itemView.findViewById(R.id.Admin_Delivery_History_Status);
    }
}
