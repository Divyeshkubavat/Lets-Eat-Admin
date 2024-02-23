package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_Burger extends AppCompatActivity {
    FloatingActionButton Admin_Burger_Add_FloatingButton;
    SearchView Admin_Burger_Searchview;
    RecyclerView Admin_Burger_Recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_burger);
        Admin_Burger_Add_FloatingButton = findViewById(R.id.Admin_Burger_ADD_FloatButton);
        Admin_Burger_Searchview=findViewById(R.id.Admin_Burger_Searchbar);
        Admin_Burger_Recyclerview=findViewById(R.id.Admin_Burger_Recyclerview);
        Admin_Burger_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
    }
}