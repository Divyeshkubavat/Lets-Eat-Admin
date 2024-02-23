package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_Offer extends AppCompatActivity {

    FloatingActionButton Admin_Offer_Add_FloatingButton;
    RecyclerView Admin_Offer_RecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer);
        Admin_Offer_Add_FloatingButton = findViewById(R.id.Admin_Offer_ADD_FloatButton);
        Admin_Offer_RecyclerView = findViewById(R.id.Admin_Offer_Recyclerview);
        Admin_Offer_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Offer_Add.class));
            }
        });
    }
}