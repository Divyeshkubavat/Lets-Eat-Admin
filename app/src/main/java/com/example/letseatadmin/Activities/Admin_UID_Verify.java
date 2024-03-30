package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_UID_Verify extends AppCompatActivity {

    LottieAnimationView Admin_Verify_Animation;
    private DatePickerDialog.OnDateSetListener SetDate;
    TextInputEditText Admin_Verify_ID,Admin_Verify_DOB;
    Button Admin_Verify_Button;
    private ProgressDialog Admin_UID_Verify_Progressbar;
    String Admin_Check_ID;

    boolean isCheck = false;

    RetrofitServices retrofitServices;
    AdminApi adminApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_uid_verify);
        Admin_UID_Verify_Progressbar = new ProgressDialog(this);
        Admin_UID_Verify_Progressbar.setTitle("Loading..... ");
        Admin_UID_Verify_Progressbar.setMessage("Verify UID ... ");
        Admin_UID_Verify_Progressbar.setIcon(R.drawable.logo);
        Admin_UID_Verify_Progressbar.setCanceledOnTouchOutside(false);
        Admin_Verify_ID = findViewById(R.id.Admin_Verify_UID);
        Admin_Verify_Animation = findViewById(R.id.Admin_Verify_Animation);
        Admin_Verify_DOB = findViewById(R.id.Admin_Verify_DOB);
        Admin_Verify_Button=findViewById(R.id.Admin_Verify_Button);

        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);

        SharedPreferences Admin_id = getSharedPreferences("Admin_Registration_Check_UID",MODE_PRIVATE);
        Admin_Check_ID = Admin_id.getString("Admin_check_UID","");

        Admin_Verify_DOB.setFocusable(false);
        Admin_Verify_DOB.setClickable(true);
        Admin_Verify_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Admin_UID_Verify.this, android.R.style.Theme_Holo_Dialog_MinWidth,SetDate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String dob = day+"/"+month+"/"+year;
                Admin_Verify_DOB.setText(dob);
            }
        };
        Admin_Verify_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = check();
                if(isCheck)
                {
                    if(Admin_Verify_ID.getText().toString().equals(Admin_Check_ID))
                    {
                        Admin_UID_Verify_Progressbar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                sendData();
                            }
                        },2000);
                    }
                    else
                    {
                        Toast.makeText(Admin_UID_Verify.this, "Enter valid Admin_ID", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(Admin_UID_Verify.this, "Please enter Admin Id And Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean check(){
        if(Admin_Verify_ID.length() == 0)
        {
            Admin_Verify_ID.setError("This Field Is Required");
            return false;
        } else if (Admin_Verify_ID.length() < 18) {
            Admin_Verify_ID.setError("Admin ID Must Be 18 Character");
            return false;
        }
        if(Admin_Verify_DOB.length() == 0)
        {
            Admin_Verify_DOB.setError("DOB is Required");
        }
        return true;
    }
    public void sendData()
    {
        String Admin_Name,Admin_Pass,Admin_Mobile,Admin_Email,Admin_Uid,Admin_DOB;
        Intent intent = getIntent();
        Admin_Name = intent.getStringExtra("Admin_Name");
        Admin_Email = intent.getStringExtra("Admin_Email");
        Admin_Pass = intent.getStringExtra("Admin_Pass");
        Admin_Mobile = intent.getStringExtra("Admin_Mobile");
        Admin_Uid = Admin_Verify_ID.getText().toString();
        Admin_DOB = Admin_Verify_DOB.getText().toString();

        isCheck = check();
        if(isCheck)
        {
            Admin admin = new Admin();
            admin.setAdminUId(Admin_Uid);
            admin.setEmail(Admin_Email);
            admin.setName(Admin_Name);
            admin.setPassword(Admin_Pass);
            admin.setMobileNo(Long.parseLong(Admin_Mobile));
            admin.setDateOfBirth(Admin_DOB);
            adminApi.save(admin).enqueue(new Callback<Admin>() {
                @Override
                public void onResponse(Call<Admin> call, Response<Admin> response) {
                    Toast.makeText(Admin_UID_Verify.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Admin_Login.class));
                }
                @Override
                public void onFailure(Call<Admin> call, Throwable t) {
                    Toast.makeText(Admin_UID_Verify.this, "Sorry ", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Admin_UID_Verify.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });

        }else
        {
            Toast.makeText(this, "Fill All The Field", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }

}