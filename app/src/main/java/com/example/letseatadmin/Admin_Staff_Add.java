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

import dalvik.annotation.optimization.CriticalNative;
import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Staff_Add extends AppCompatActivity {

    CircleImageView Admin_Staff_Image;
    TextInputEditText Admin_Staff_Name,Admin_Staff_Mobile,Admin_Staff_Email,Admin_Staff_Salary;
    Button Admin_Staff_Button;
    public Uri imageuri=Uri.EMPTY;
    boolean isCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_staff_add);
        Admin_Staff_Button = findViewById(R.id.Admin_Staff_Add_Button);
        Admin_Staff_Email =  findViewById(R.id.Admin_Staff_Add_Email);
        Admin_Staff_Name=findViewById(R.id.Admin_Staff_Add_Name);
        Admin_Staff_Salary=findViewById(R.id.Admin_Staff_Add_Salary);
        Admin_Staff_Mobile=findViewById(R.id.Admin_Staff_Add_Mobile);
        Admin_Staff_Image=findViewById(R.id.Admin_Staff_Add_Image);

        Admin_Staff_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    isCheck =check();
                    if(isCheck)
                    {
                        Toast.makeText(Admin_Staff_Add.this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();;
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Admin_Staff_Add.this, "Fill All Field", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        Admin_Staff_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
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
            Admin_Staff_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_Staff_Name.length() == 0)
        {
            Admin_Staff_Name.setError("*Required");
            return false;
        }
        if(Admin_Staff_Email.length() == 0)
        {
            Admin_Staff_Email.setError("*Required");
            return false;
        }
        if(Admin_Staff_Mobile.length() == 0)
        {
            Admin_Staff_Mobile.setError("*Required");
            return false;
        } else if (Admin_Staff_Mobile.length() < 10) {
            Admin_Staff_Mobile.setError("Mobile Must be 10 Digit");
        }
        if(Admin_Staff_Salary.length() > 8)
        {
            Admin_Staff_Salary.setError("Maximum 8 Digit");
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