package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Activities.Admin_Staff_Edit;
import com.example.letseatadmin.Models.Staff;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class staffAdapter extends RecyclerView.Adapter<staffAdapter.MyViewHolder>
{
    ArrayList<Staff> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public staffAdapter(ArrayList<Staff> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new staffAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_staff_design,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Staff staff = list.get(position);
        Glide.with(context).load(staff.getImageUrl()).into(holder.image);
        holder.name.setText(staff.getName());
        String mobile = String.valueOf(staff.getMobileNo());
        holder.mobile.setText(mobile);
        holder.email.setText(staff.getEmail());
        String salary = String.valueOf(staff.getSalary());
        holder.salary.setText(salary);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.deleteStaff(staff.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context, "Employee Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                    list.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
            }
        });
        holder.btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mo = String.valueOf(staff.getMobileNo());
                Intent call = new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+mo));
                view.getContext().startActivity(call);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Admin_Staff_Edit.class);
                intent.putExtra("name",staff.getName());
                intent.putExtra("mobile",staff.getMobileNo());
                intent.putExtra("email",staff.getEmail());
                intent.putExtra("salary",staff.getSalary());
                intent.putExtra("image",staff.getImageUrl());
                intent.putExtra("id",staff.getId());
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
        TextView name,salary,email,mobile;
        ImageView btndelete,btncall;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Admin_Staff_Image);
            name=itemView.findViewById(R.id.Admin_Staff_Name);
            salary=itemView.findViewById(R.id.Admin_Staff_Salary);
            email=itemView.findViewById(R.id.Admin_Staff_Email);
            mobile=itemView.findViewById(R.id.Admin_Staff_Mobile);
            btndelete=itemView.findViewById(R.id.Admin_Staff_Delete);
            btncall=itemView.findViewById(R.id.Admin_Staff_Mobile_Call);

        }
    }
}
