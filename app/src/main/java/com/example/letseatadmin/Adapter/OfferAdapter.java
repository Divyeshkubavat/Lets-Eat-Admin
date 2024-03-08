package com.example.letseatadmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Activities.Admin_Offer_Edit;
import com.example.letseatadmin.Models.Offer;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
    ArrayList<Offer> list;
    Context context;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    public OfferAdapter(ArrayList<Offer> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_admin_offers_view_design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Offer offer = list.get(position);
        Glide.with(context).load(offer.getImageUrl()).into(holder.image);
        int id = list.get(position).getId();
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminApi.offerDelete(id).enqueue(new Callback<String>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context, "Offer Delete Successful", Toast.LENGTH_SHORT).show();

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
        holder.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), Admin_Offer_Edit.class);
                intent.putExtra("offerId",offer.getId());
                intent.putExtra("offerCode",offer.getCategoryId());
                intent.putExtra("offerUrl",offer.getImageUrl());
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
        Button btndelete,btnedit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.Admin_Offer_Image);
            btndelete=itemView.findViewById(R.id.Admin_Offer_Delete_Button);
            btnedit=itemView.findViewById(R.id.Admin_Offer_Edit_Button);

        }

    }
}
