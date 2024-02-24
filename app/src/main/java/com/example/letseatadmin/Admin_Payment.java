package com.example.letseatadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.letseatadmin.Adapter.Delivery_History_Adapter;
import com.example.letseatadmin.Models.Delivery_history_Item;

import java.util.ArrayList;

public class Admin_Payment extends AppCompatActivity {

    RecyclerView Admin_Payment_Recyclerview;
    RadioButton Admin_Payment_All_Radio, Admin_Payment_Paid_Radio, Admin_Payment_Unpaid_Radio;
    TextView Admin_Payment_Total_Income,Admin_Payment_Total_Paid_Income,Admin_Payment_Total_Unpaid_Income;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_payment);
        Admin_Payment_Recyclerview = findViewById(R.id.Admin_Payment_Recyclerview);
        Admin_Payment_Total_Income = findViewById(R.id.Admin_Payment_Total_Income);
        Admin_Payment_Total_Unpaid_Income = findViewById(R.id.Admin_Payment_Total_Unpaid_Income);
        Admin_Payment_All_Radio = findViewById(R.id.Admin_Payment_All_Radio);
        Admin_Payment_Paid_Radio = findViewById(R.id.Admin_Payment_Paid_Radio);
        Admin_Payment_Unpaid_Radio = findViewById(R.id.Admin_Payment_Unpaid_Radio);
        Admin_Payment_Total_Paid_Income = findViewById(R.id.Admin_Payment_Total_Paid_Income);
        if(Admin_Payment_Paid_Radio.isChecked())
        {

        } else if (Admin_Payment_Unpaid_Radio.isChecked()) {

        }else {

        }
    }
}