package com.example.letseatadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.letseatadmin.Models.PromoCode;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.MyViewHolder> {

    ArrayList<PromoCode> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public PromoCodeAdapter(ArrayList<PromoCode> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new PromoCodeAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_promo_code_design,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PromoCode p = list.get(position);
        holder.discount.setText(String.valueOf(p.getDiscount())+" %");
        holder.code.setText(p.getCode());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.deleteCode(p.getId()).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView code,discount;
        ImageView btnDelete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.Admin_Promo_Code_Name_Design);
            discount=itemView.findViewById(R.id.Admin_Promo_Code_Discount_Design);
            btnDelete=itemView.findViewById(R.id.Admin_Promo_Code_Delete_Design);

        }
    }
}
