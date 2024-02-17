package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class Admin_UID_Verify extends AppCompatActivity {

    LottieAnimationView Admin_Verify_Animation;
    private DatePickerDialog.OnDateSetListener SetDate;
    TextInputEditText Admin_Verify_ID,Admin_Verify_DOB;
    Button Admin_Verify_Button;
    private ProgressDialog Admin_UID_Verify_Progressbar;

    boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_uid_verify);
        Admin_UID_Verify_Progressbar = new ProgressDialog(this);
        Admin_UID_Verify_Progressbar.setTitle("Loading..... ");
        Admin_UID_Verify_Progressbar.setMessage("Login Your Account ... ");
        Admin_UID_Verify_Progressbar.setCanceledOnTouchOutside(false);
        Admin_Verify_ID = findViewById(R.id.Admin_Verify_UID);
        Admin_Verify_Animation = findViewById(R.id.Admin_Verify_Animation);
        Admin_Verify_DOB = findViewById(R.id.Admin_Verify_DOB);
        Admin_Verify_Button=findViewById(R.id.Admin_Verify_Button);

        SharedPreferences Admin_id = getSharedPreferences("Admin_Registration_Check_UID",MODE_PRIVATE);
        String Admin_Check_ID = Admin_id.getString("Admin_check_UID","");
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
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
}