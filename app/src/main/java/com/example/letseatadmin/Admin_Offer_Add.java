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

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_Offer_Add extends AppCompatActivity {

    CircleImageView Admin_Offer_Add_Image;
    RadioButton Admin_offer_Add_Burger,Admin_Offer_Add_Pizza,Admin_Offer_Add_Combo,Admin_Offer_Add_Drink;
    Button Admin_Offer_Add_Button;
    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer_add);

        Admin_Offer_Add_Image = findViewById(R.id.Admin_Offer_Add_Image);
        Admin_Offer_Add_Button =  findViewById(R.id.Admin_Offer_Add_Button);
        Admin_Offer_Add_Pizza = findViewById(R.id.Admin_Offer_Add_Pizza_Radio);
        Admin_Offer_Add_Combo = findViewById(R.id.Admin_Offer_Add_Combo_Radio);
        Admin_Offer_Add_Drink = findViewById(R.id.Admin_Offer_Add_Drink_Radio);
        Admin_offer_Add_Burger = findViewById(R.id.Admin_Offer_Add_Burger_Radio);

        Admin_Offer_Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        Admin_Offer_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCheck = check();
                if(isCheck)
                {
                    Toast.makeText(Admin_Offer_Add.this, "Offer Added Successfully", Toast.LENGTH_SHORT).show();
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
            Admin_Offer_Add_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        String ImageUri = imageuri.toString();
        if(ImageUri.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}