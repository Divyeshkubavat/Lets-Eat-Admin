package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Edit extends AppCompatActivity {
    TextInputEditText Admin_Edit_Name,Admin_Edit_Mobile,Admin_Edit_Id,Admin_Edit_Pass,Admin_Edit_DOB,Admin_Edit_Email;
    Button Admin_Edit_Update_Button;
    boolean isCheck=false;
    private DatePickerDialog.OnDateSetListener SetDate;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit);
        Admin_Edit_Name=findViewById(R.id.Admin_Edit_Name);
        Admin_Edit_Email=findViewById(R.id.Admin_Edit_Email);
        Admin_Edit_Id=findViewById(R.id.Admin_Edit_ID);
        Admin_Edit_Pass=findViewById(R.id.Admin_Edit_Pass);
        Admin_Edit_DOB=findViewById(R.id.Admin_Edit_DOB);
        Admin_Edit_Mobile=findViewById(R.id.Admin_Edit_Mobile);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        Admin_Edit_Update_Button=findViewById(R.id.Admin_Edit_Update_Button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Admin_Edit_Id.setFocusable(View.NOT_FOCUSABLE);
        }
        Admin_Edit_DOB.setFocusable(false);
        Admin_Edit_DOB.setClickable(false);
        getAdminData();
        Admin_Edit_Id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(Admin_Edit_Id,"Not Changeable",Snackbar.LENGTH_SHORT).show();
            }
        });
        Admin_Edit_Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck=check();
                if(isCheck)
                {
                    updateAdmin();
                }
            }
        });
        Admin_Edit_DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Admin_Edit.this, android.R.style.Theme_Holo_Dialog_MinWidth,SetDate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        SetDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String dob = day+"/"+month+"/"+year;
                Admin_Edit_DOB.setText(dob);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private boolean check() {
        if(Admin_Edit_Name.length() == 0)
        {
            Admin_Edit_Name.setError("Username Is Required Field");
            return false;
        }
        if(Admin_Edit_Mobile.length() < 0)
        {
            Admin_Edit_Mobile.setError("Mobile number is Required");
            return  false;
        } else if (Admin_Edit_Mobile.length() < 10) {
            Admin_Edit_Mobile.setError("Mobile Number Must Be 10 Digits");
            return false;
        }
        if(Admin_Edit_Email.length() == 0)
        {
            Admin_Edit_Email.setError("Email Is Required Field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Admin_Edit_Email.getText().toString()).matches()) {
            Admin_Edit_Email.setError("Email Must Be In Email Format");
            return false;
        }
        if(Admin_Edit_Pass.length() == 0)
        {
            Admin_Edit_Pass.setError("Password Is Required Field");
            return false;
        } else if (Admin_Edit_Pass.length() < 8) {
            Admin_Edit_Pass.setError("Password Must Be * Character");
            return false;
        }
        if(Admin_Edit_Id.length() == 0)
        {
            Admin_Edit_Id.setError("Admin ID Is Required Field");
            return false;
        } else if (Admin_Edit_Id.length() < 18) {
            Admin_Edit_Id.setError("Admin Id Must be 18 character");
        }
        if(Admin_Edit_DOB.length() == 0)
        {
            Admin_Edit_DOB.setError("Date Of Birth is Required");
            return  false;
        }
        return  true;
    }
    private void getAdminData()
    {
        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        String uid = preferences.getString("Login_UID","");
        adminApi.getSingleUser(uid).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                String id = response.body().getAdminUId();
                String dob = response.body().getDateOfBirth();
                String email = response.body().getEmail();
                String mobile = String.valueOf(response.body().getMobileNo());
                String name = response.body().getName();
                String pass = response.body().getPassword();

                Admin_Edit_Id.setText(id);
                Admin_Edit_Email.setText(email);
                Admin_Edit_Mobile.setText(mobile);
                Admin_Edit_Name.setText(name);
                Admin_Edit_DOB.setText(dob);
                Admin_Edit_Pass.setText(pass);
            }
            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
            }
        });
    }
    private void updateAdmin()
    {
        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        String uid = preferences.getString("Login_UID","");
        String name,pass,mobile,email,date;
        email=Admin_Edit_Email.getText().toString();
        mobile=Admin_Edit_Mobile.getText().toString();
        name=Admin_Edit_Name.getText().toString();
        date=Admin_Edit_DOB.getText().toString();
        pass=Admin_Edit_Pass.getText().toString();

        Admin admin = new Admin();
        admin.setDateOfBirth(date);
        admin.setPassword(pass);
        admin.setName(name);
        admin.setMobileNo(Long.parseLong(mobile));
        admin.setEmail(email);

        adminApi.updateAdmin(uid,admin).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Toast.makeText(Admin_Edit.this, "Profile Updated Successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {

            }
        });


    }
}