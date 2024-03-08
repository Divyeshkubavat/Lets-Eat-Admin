package com.example.letseatadmin;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminHome extends Fragment {

    ImageButton Admin_Home_Burger,Admin_Home_Pizza,Admin_Home_Combo,Admin_Home_drink;
    Button Admin_Home_Offers;
    CircleImageView Admin_Home_Image;
    TextView Admin_Home_Name,Admin_Home_Greeting;

    int Greeting_Hour;
    String Login_Uid;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_home, container, false);
        Admin_Home_Name = view.findViewById(R.id.Admin_Home_Name);
        Admin_Home_Burger=view.findViewById(R.id.Admin_Home_Burger);
        Admin_Home_Combo=view.findViewById(R.id.Admin_Home_Combo);
        Admin_Home_drink=view.findViewById(R.id.Admin_Home_Drink);
        Admin_Home_Greeting=view.findViewById(R.id.Admin_Home_Greeting);
        Admin_Home_Pizza=view.findViewById(R.id.Admin_Home_Pizza);
        Admin_Home_Offers=view.findViewById(R.id.Admin_Home_Offers);
        Admin_Home_Image=view.findViewById(R.id.Admin_Home_Image);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);

        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Login_Uid = preferences.getString("Login_UID","");
        adminApi.getSingleUser(Login_Uid).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                String name = response.body().getName();
                Admin_Home_Name.setText("Hi "+name);
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

            }
        });
        Calendar calendar = Calendar.getInstance();
        Greeting_Hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(Greeting_Hour >= 12 && Greeting_Hour < 17)
        {
            Admin_Home_Greeting.setText("Good Afternoon");
        } else if (Greeting_Hour >= 17 && Greeting_Hour <21) {
            Admin_Home_Greeting.setText("Good Evening");
        } else if (Greeting_Hour >= 21 && Greeting_Hour<24) {
            Admin_Home_Greeting.setText("Good Night");
        }else {
            Admin_Home_Greeting.setText("Good Morning");
        }

        Admin_Home_Burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Burger.class));
            }
        });
        Admin_Home_Pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Pizza.class));
            }
        });
        Admin_Home_Combo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Combo.class));
            }
        });
        Admin_Home_drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Drink.class));
            }
        });
        Admin_Home_Offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Admin_Offer.class));
            }
        });
        return view;
    }

}