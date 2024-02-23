package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_Pizza extends AppCompatActivity {
    FloatingActionButton Admin_Pizza_Add_FloatingButton;
    SearchView Admin_Pizza_Searchview;
    RecyclerView Admin_Pizza_Recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pizza);
        Admin_Pizza_Add_FloatingButton=findViewById(R.id.Admin_Pizza_ADD_FloatButton);
        Admin_Pizza_Searchview=findViewById(R.id.Admin_Pizza_Searchbar);
        Admin_Pizza_Recyclerview=findViewById(R.id.Admin_Pizza_Recyclerview);
        Admin_Pizza_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
    }
}