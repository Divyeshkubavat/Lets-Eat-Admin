package com.example.letseatadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Product_Add extends AppCompatActivity {

    CircleImageView Admin_Product_Add_Image;
    public Uri imageuri=Uri.EMPTY;
    TextInputEditText Admin_product_Name,Admin_Product_Price,Admin_Product_Description;
    RadioButton Admin_Product_Burger,Admin_Product_Pizza,Admin_Product_Combo,Admin_Product_Drink;
    Button Admin_Product_Button;
    boolean isCheck = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_add);

        Admin_Product_Add_Image = findViewById(R.id.Admin_Product_Add_Image);
        Admin_Product_Burger = findViewById(R.id.Admin_Product_Add_Burger_Radio);
        Admin_Product_Drink = findViewById(R.id.Admin_Product_Add_Drink_Radio);
        Admin_Product_Combo = findViewById(R.id.Admin_Product_Add_Combo_Radio);
        Admin_Product_Pizza = findViewById(R.id.Admin_Product_Add_Pizza_Radio);
        Admin_product_Name =findViewById(R.id.Admin_Product_Add_Name);
        Admin_Product_Price=findViewById(R.id.Admin_Product_Add_Price);
        Admin_Product_Description=findViewById(R.id.Admin_Product_Add_Description);
        Admin_Product_Button=findViewById(R.id.Admin_Product_Add_Button);
        Admin_Product_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = check();
                if(isCheck)
                {
                    Toast.makeText(Admin_Product_Add.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(Admin_Product_Add.this, " Fill The Detail ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Admin_Product_Add_Image.setOnClickListener(new View.OnClickListener() {
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
                Admin_Product_Add_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_product_Name.length() == 0)
        {
            Admin_product_Name.setError("*Required");
            return false;
        }
        if(Admin_Product_Price.length() == 0)
        {
            Admin_Product_Price.setError("*Required");
            return false;
        }
        if(Admin_Product_Description.length() == 0)
        {
            Admin_Product_Description.setError("*Required");
            return false;
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