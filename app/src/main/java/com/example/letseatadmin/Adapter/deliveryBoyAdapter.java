package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Activities.Admin_Delivery_Edit;
import com.example.letseatadmin.Activities.Admin_Delivery_Profile;
import com.example.letseatadmin.Models.deliveryBoy;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class deliveryBoyAdapter extends RecyclerView.Adapter<deliveryBoyAdapter.MyViewHolder> {


    ArrayList<deliveryBoy> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    public deliveryBoyAdapter(ArrayList<deliveryBoy> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new deliveryBoyAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_delivery_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final deliveryBoy boy = list.get(position);
        holder.name.setText(boy.getName());
        String mo = String.valueOf(boy.getMobileNo());
        holder.mobile.setText(mo);
        String salary = String.valueOf(boy.getSalary());
        holder.salary.setText(salary);
        holder.email.setText(boy.getEmail());
        Glide.with(context).load(boy.getImageUrl()).into(holder.image);
        holder.btnphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mo = String.valueOf(boy.getMobileNo());
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+mo));
                view.getContext().startActivity(call);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Admin_Delivery_Profile.class);
                intent.putExtra("name",boy.getName());
                intent.putExtra("mobile",boy.getMobileNo());
                intent.putExtra("email",boy.getEmail());
                intent.putExtra("salary",boy.getSalary());
                intent.putExtra("image",boy.getImageUrl());
                intent.putExtra("id",boy.getId());
                intent.putExtra("pass",boy.getPassword());
                view.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name,mobile,email,salary;
        ImageView btndelete,btnphone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Admin_Delivery_Image);
            name=itemView.findViewById(R.id.Admin_Delivery_Name);
            mobile=itemView.findViewById(R.id.Admin_Delivery_Mobile);
            email=itemView.findViewById(R.id.Admin_Delivery_Email);
            salary=itemView.findViewById(R.id.Admin_Delivery_Salary);
            btnphone=itemView.findViewById(R.id.Admin_Delivery_Mobile_call);
        }
    }
}
