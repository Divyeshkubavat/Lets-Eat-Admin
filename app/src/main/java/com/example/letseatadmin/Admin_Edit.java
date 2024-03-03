package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class Admin_Edit extends AppCompatActivity {
    TextInputEditText Admin_Edit_Name,Admin_Edit_Mobile,Admin_Edit_Id,Admin_Edit_Pass,Admin_Edit_DOB,Admin_Edit_Email;
    Button Admin_Edit_Update_Button;
    boolean isCheck=false;
    private DatePickerDialog.OnDateSetListener SetDate;
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
        Admin_Edit_Update_Button=findViewById(R.id.Admin_Edit_Update_Button);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Admin_Edit_Id.setFocusable(View.NOT_FOCUSABLE);
        }
        Admin_Edit_Update_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck=check();
                if(isCheck)
                {
                    Toast.makeText(Admin_Edit.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
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
                DatePickerDialog dialog = new DatePickerDialog(getApplicationContext(), android.R.style.Theme_Holo_Dialog_MinWidth,SetDate,year,month,day);
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
}