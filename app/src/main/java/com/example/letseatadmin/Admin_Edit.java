package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class Admin_Edit extends AppCompatActivity {
    TextInputEditText Admin_Edit_Name,Admin_Edit_Mobile,Admin_Edit_Id,Admin_Edit_Pass,Admin_Edit_DOB,Admin_Edit_Email;
    Button Admin_Edit_Udate_Button;
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
        Admin_Edit_Udate_Button=findViewById(R.id.Admin_Edit_Update_Button);
        Admin_Edit_Udate_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}