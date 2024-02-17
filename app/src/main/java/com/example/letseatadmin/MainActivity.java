package com.example.letseatadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    AdminHome adminHome = new AdminHome();
    AdminOrder adminOrder = new AdminOrder();
    AdminProfile adminProfile = new AdminProfile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // getSupportActionBar().hide();
        navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.Admin_Home);
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#281818"));
        actionBar.setBackgroundDrawable(drawable);
        getSupportFragmentManager().beginTransaction().replace(R.id.container2,new AdminHome()).commit();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId())
                {
                    case  R.id.Admin_Home:
                        fragment = new AdminHome();
                        break;
                    case  R.id.Admin_Order:
                        fragment = new AdminOrder();
                        break;
                    case  R.id.Admin_Profile:
                        fragment = new AdminProfile();
                        break;
                    case R.id.Admin_Staff:
                        fragment = new AdminStaff();
                        break;
                    case R.id.Admin_Delivery:
                        fragment=new AdminDelivery();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container2,fragment).commit();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit 🥺🥺🥺 ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}