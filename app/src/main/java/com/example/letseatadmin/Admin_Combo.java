package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Admin_Combo extends AppCompatActivity {
    FloatingActionButton Admin_Combo_Add_FloatingButton;
    SearchView Admin_Combo_Searchview;
    RecyclerView Admin_Combo_Recyclerview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_combo);
        Admin_Combo_Add_FloatingButton=findViewById(R.id.Admin_Combo_ADD_FloatButton);
        Admin_Combo_Searchview=findViewById(R.id.Admin_Combo_Searchbar);
        Admin_Combo_Recyclerview=findViewById(R.id.Admin_Combo_Recyclerview);
        Admin_Combo_Add_FloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Admin_Product_Add.class));
            }
        });
    }
}