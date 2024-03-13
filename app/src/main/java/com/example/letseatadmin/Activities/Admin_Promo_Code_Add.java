package com.example.letseatadmin.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.letseatadmin.Models.PromoCode;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Promo_Code_Add extends AppCompatActivity {

    TextInputEditText code,discount;
    Button add;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    boolean isCheck=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_promo_code_add);
        code=findViewById(R.id.Admin_Promo_Code);
        discount=findViewById(R.id.Admin_Add_Discount);
        add=findViewById(R.id.Admin_Add_PromoCode);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCode();
            }
        });

    }

    public boolean check(){
        if(code.getText().toString().length()==0)
        {
            code.setError("* Required");
            return false;
        }
        if(discount.getText().toString().length()==0){
            discount.setError("* Required");
            return false;
        }
        return true;
    }
    private void setCode(){
        isCheck=check();
        if(isCheck){
            PromoCode promo = new PromoCode();
            promo.setCode(code.getText().toString());
            promo.setDiscount(Double.parseDouble(discount.getText().toString()));
            adminApi.setCode(promo).enqueue(new Callback<PromoCode>() {
                @Override
                public void onResponse(Call<PromoCode> call, Response<PromoCode> response) {
                    Toast.makeText(Admin_Promo_Code_Add.this, "Code Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onFailure(Call<PromoCode> call, Throwable t) {

                }
            });
        }
    }
}