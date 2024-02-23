package com.example.letseatadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_delivery_Add_Edit extends AppCompatActivity {

    CircleImageView Admin_Delivery_Image;
    TextInputEditText Admin_Delivery_Name,Admin_Delivery_Mobile,Admin_Delivery_Email,Admin_Delivery_Salary;
    Button Admin_Delivery_Add_Button;

    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_add_edit);
        Admin_Delivery_Add_Button = findViewById(R.id.Admin_Delivery_Add_Button);
        Admin_Delivery_Email=findViewById(R.id.Admin_Delivery_Add_Email);
        Admin_Delivery_Image = findViewById(R.id.Admin_Delivery_Add_Image);
        Admin_Delivery_Name=findViewById(R.id.Admin_Delivery_Add_Name);
        Admin_Delivery_Salary=findViewById(R.id.Admin_Delivery_Add_Salary);
        Admin_Delivery_Mobile=findViewById(R.id.Admin_Delivery_Add_Mobile);
        Admin_Delivery_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        Admin_Delivery_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck=check();
                if(isCheck)
                {
                    Toast.makeText(Admin_delivery_Add_Edit.this, "Delivery Boy Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void UploadImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK)
        {
            imageuri = data.getData();
            Admin_Delivery_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_Delivery_Name.length() == 0)
        {
            Admin_Delivery_Name.setError("*Required");
            return false;
        }
        if(Admin_Delivery_Email.length() == 0)
        {
            Admin_Delivery_Email.setError("*Required");
            return false;
        }
        if(Admin_Delivery_Mobile.length() == 0)
        {
            Admin_Delivery_Mobile.setError("*Required");
            return false;
        } else if (Admin_Delivery_Mobile.length() < 10) {
            Admin_Delivery_Mobile.setError("Mobile Must be 10 Digit");
        }
        if(Admin_Delivery_Salary.length() > 8)
        {
            Admin_Delivery_Salary.setError("Maximum 8 Digit");
        }
        String ImageUri = imageuri.toString();
        if(ImageUri.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}