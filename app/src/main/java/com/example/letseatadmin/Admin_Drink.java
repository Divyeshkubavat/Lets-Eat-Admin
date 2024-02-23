package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_Drink extends AppCompatActivity {
    FloatingActionButton Admin_Drink_Add_FloatingButton;
    SearchView Admin_Drink_Searchview;
    RecyclerView Admin_Drink_Recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_drink);
        Admin_Drink_Add_FloatingButton=findViewById(R.id.Admin_Drink_ADD_FloatButton);
        Admin_Drink_Searchview=findViewById(R.id.Admin_Drink_Searchbar);
        Admin_Drink_Recyclerview=findViewById(R.id.Admin_Drink_Recyclerview);
        Admin_Drink_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
    }
}