package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Activities.Admin_Peoduct_Edit;
import com.example.letseatadmin.Models.Product;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class productAdapter extends RecyclerView.Adapter<productAdapter.MyViewHolder>{
    ArrayList<Product> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    public productAdapter(ArrayList<Product> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new productAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_product_view_design,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Product plist = list.get(position);
        holder.name.setText(plist.getName());
        int price = (int) plist.getPrice();
        holder.price.setText(String.valueOf(price));
        int id = plist.getId();
        Glide.with(context).load(plist.getImageUrl()).into(holder.image);
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.deleteProduct(id).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Toast.makeText(context, "Product Delete Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {

                    }
                });
                list.remove(position);
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Admin_Peoduct_Edit.class);
                intent.putExtra("pname",plist.getName());
                intent.putExtra("pprice",plist.getPrice());
                intent.putExtra("pdesc",plist.getDescription());
                intent.putExtra("ptype",plist.getType());
                intent.putExtra("pid",plist.getId());
                intent.putExtra("pimage",plist.getImageUrl());
                intent.putExtra("pcid",plist.getCategoryId());
                intent.putExtra("prate",plist.getRating());
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
        TextView name,price;
        ImageView btndelete,btnedit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.Admin_Product_Design_Image);
            btndelete=itemView.findViewById(R.id.Admin_Product_Design_Delete);
            name=itemView.findViewById(R.id.Admin_Product_Design_Name);
            price=itemView.findViewById(R.id.Admin_Product_Design_Price);
            btnedit=itemView.findViewById(R.id.Admin_Product_Design_Edit);
        }
    }
}
