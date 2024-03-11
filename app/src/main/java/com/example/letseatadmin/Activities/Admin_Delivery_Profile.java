package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Adapter.Delivery_History_Adapter;
import com.example.letseatadmin.Models.Delivery_history_Item;
import com.example.letseatadmin.Models.deliveryBoy;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Delivery_Profile extends AppCompatActivity {

    RecyclerView Admin_Delivery_History_recyclerview;
    CircleImageView Admin_Delivery_Profile_Image;

    Button Admin_Delivery_Profile_Edit_Button;

    TextView Admin_Delivery_Profile_History_Button,Admin_Delivery_Profile_History_Button_2;
    TextView Admin_Delivery_Profile_Name,Admin_Delivery_Profile_Email,Admin_Delivery_Profile_Mobile,Admin_Delivery_Profile_Salary;
    int id;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    String image;
    String name,email,mobile,salary,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_profile);
        Admin_Delivery_Profile_History_Button = findViewById(R.id.Admin_Delivery_Profile_History_Button);
        Admin_Delivery_Profile_History_Button_2=findViewById(R.id.Admin_Delivery_Profile_History_Button_2);
        Admin_Delivery_Profile_Email=findViewById(R.id.Admin_Delivery_Profile_Email);
        Admin_Delivery_Profile_Name=findViewById(R.id.Admin_Delivery_Profile_Name);
        Admin_Delivery_Profile_Mobile=findViewById(R.id.Admin_Delivery_Profile_Mobile);
        Admin_Delivery_Profile_Salary=findViewById(R.id.Admin_Delivery_Profile_Salary);
        Admin_Delivery_Profile_Edit_Button=findViewById(R.id.Admin_Delivery_Profile_Edit_Button);
        Admin_Delivery_Profile_Image=findViewById(R.id.Admin_Delivery_Profile_Image);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        set();
        Admin_Delivery_Profile_Edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_Delivery_Profile.this,Admin_Delivery_Edit.class);
                intent.putExtra("name",name);
                intent.putExtra("mobile",Long.valueOf(mobile));
                intent.putExtra("email",email);
                intent.putExtra("salary",Double.valueOf(salary));
                intent.putExtra("image",image);
                intent.putExtra("id",id);
                intent.putExtra("pass",pass);
                startActivity(intent);
            }
        });

        Admin_Delivery_Profile_History_Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(Admin_Delivery_History_recyclerview.getVisibility() == View.VISIBLE)
                    {
                        Admin_Delivery_History_recyclerview.setVisibility(View.GONE);
                        Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_downward_24));

                    }
                    else {
                        Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_upward_24));

                        Admin_Delivery_History_recyclerview.setVisibility(View.VISIBLE);
                    }

            }
        });
        Admin_Delivery_Profile_History_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Admin_Delivery_History_recyclerview.getVisibility() == View.VISIBLE)
                {
                    Admin_Delivery_History_recyclerview.setVisibility(View.GONE);
                    Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_downward_24));

                }
                else {
                    Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_upward_24));
                    Admin_Delivery_History_recyclerview.setVisibility(View.VISIBLE);

                }
            }
        });
    }
    private void setData(){
        Intent i = getIntent();
        name=i.getStringExtra("name");
        email=i.getStringExtra("email");
        mobile= String.valueOf(i.getLongExtra("mobile",0));
        salary= String.valueOf(i.getDoubleExtra("salary",0));
        image=i.getStringExtra("image");
        id=i.getIntExtra("id",0);
        pass=i.getStringExtra("pass");
    }
    private void set(){
        adminApi.getSingleDeliveryBoy(id).enqueue(new Callback<deliveryBoy>() {
            @Override
            public void onResponse(Call<deliveryBoy> call, Response<deliveryBoy> response) {
                String name = response.body().getName();
                String email = response.body().getEmail();
                long mobile = response.body().getMobileNo();
                double salary = response.body().getSalary();
                String image= response.body().getImageUrl();
                Glide.with(getApplicationContext()).load(image).into(Admin_Delivery_Profile_Image);
                Admin_Delivery_Profile_Email.setText(email);
                Admin_Delivery_Profile_Name.setText(name);
                Admin_Delivery_Profile_Mobile.setText(String.valueOf(mobile));
                Admin_Delivery_Profile_Salary.setText(String.valueOf(salary));
            }
            @Override
            public void onFailure(Call<deliveryBoy> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        set();
    }

    @Override
    protected void onStart() {
        super.onStart();
        set();
    }
}