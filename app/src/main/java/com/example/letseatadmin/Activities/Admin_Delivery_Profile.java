package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.letseatadmin.Adapter.Delivery_History_Adapter;
import com.example.letseatadmin.Models.Delivery_history_Item;
import com.example.letseatadmin.R;

import java.util.ArrayList;

public class Admin_Delivery_Profile extends AppCompatActivity {

    RecyclerView Admin_Delivery_History_recyclerview;

    TextView Admin_Delivery_Profile_History_Button,Admin_Delivery_Profile_History_Button_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_profile);
        Admin_Delivery_Profile_History_Button = findViewById(R.id.Admin_Delivery_Profile_History_Button);
        Admin_Delivery_Profile_History_Button_2=findViewById(R.id.Admin_Delivery_Profile_History_Button_2);
        Admin_Delivery_Profile_History_Button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(Admin_Delivery_History_recyclerview.getVisibility() == View.VISIBLE)
                    {
                        Admin_Delivery_History_recyclerview.setVisibility(View.GONE);
                        Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_downward_24));

                    }
                    else {
                        Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                        Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_upward_24));

                        Admin_Delivery_History_recyclerview.setVisibility(View.VISIBLE);
                    }

            }
        });
        Admin_Delivery_Profile_History_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Admin_Delivery_History_recyclerview.getVisibility() == View.VISIBLE)
                {
                    Admin_Delivery_History_recyclerview.setVisibility(View.GONE);
                    Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_downward_24));

                }
                else {
                    Admin_Delivery_Profile_History_Button_2.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    Admin_Delivery_Profile_History_Button_2.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.baseline_arrow_upward_24));
                    Admin_Delivery_History_recyclerview.setVisibility(View.VISIBLE);

                }
            }
        });
        Admin_Delivery_History_recyclerview = findViewById(R.id.Admin_Delivery_Profile_Recyclerview);
        ArrayList<Delivery_history_Item> deliveryHistoryItems = new ArrayList<Delivery_history_Item>();
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Pending"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Complete"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Pending"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Complete"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Pending"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Complete"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Pending"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Complete"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Pending"));
        deliveryHistoryItems.add(new Delivery_history_Item("#1234567","Complete"));
        Admin_Delivery_History_recyclerview.setNestedScrollingEnabled(false);
        Admin_Delivery_History_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        Admin_Delivery_History_recyclerview.setAdapter(new Delivery_History_Adapter(deliveryHistoryItems,getApplicationContext()));

    }
}