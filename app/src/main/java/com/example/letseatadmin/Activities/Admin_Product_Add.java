package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.letseatadmin.Models.Product;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Product_Add extends AppCompatActivity {

    CircleImageView Admin_Product_Add_Image;
    public Uri imageuri=Uri.EMPTY;
    TextInputEditText Admin_product_Name,Admin_Product_Price,Admin_Product_Description;
    RadioButton Admin_Product_Burger,Admin_Product_Pizza,Admin_Product_Combo,Admin_Product_Drink,Admin_Product_Veg,Admin_Product_Nonveg;
    Button Admin_Product_Button;
    boolean isCheck = false;
    public FirebaseStorage firebaseStorage;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    String type;
    int Category_Code;
    ProgressDialog pg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_add);
        pg = new ProgressDialog(this);
        Admin_Product_Add_Image = findViewById(R.id.Admin_Product_Add_Image);
        Admin_Product_Burger = findViewById(R.id.Admin_Product_Add_Burger_Radio);
        Admin_Product_Drink = findViewById(R.id.Admin_Product_Add_Drink_Radio);
        Admin_Product_Combo = findViewById(R.id.Admin_Product_Add_Combo_Radio);
        Admin_Product_Pizza = findViewById(R.id.Admin_Product_Add_Pizza_Radio);
        Admin_product_Name =findViewById(R.id.Admin_Product_Add_Name);
        Admin_Product_Price=findViewById(R.id.Admin_Product_Add_Price);
        Admin_Product_Description=findViewById(R.id.Admin_Product_Add_Description);
        Admin_Product_Button=findViewById(R.id.Admin_Product_Add_Button);
        Admin_Product_Veg=findViewById(R.id.Admin_Product_Add_Veg_Radio);
        Admin_Product_Nonveg=findViewById(R.id.Admin_Product_Add_Nonveg_Radio);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        firebaseStorage = FirebaseStorage.getInstance();
        Admin_Product_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setProduct();
            }
        });
        Admin_Product_Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
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
                Admin_Product_Add_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_product_Name.length() == 0)
        {
            Admin_product_Name.setError("*Required");
            return false;
        }
        if(Admin_Product_Price.length() == 0)
        {
            Admin_Product_Price.setError("*Required");
            return false;
        }
        if(Admin_Product_Description.length() == 0)
        {
            Admin_Product_Description.setError("*Required");
            return false;
        }
        String ImageUri = imageuri.toString();
        if(ImageUri.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Admin_Product_Burger.isChecked())
        {
            Category_Code = 201;
        }else if(Admin_Product_Pizza.isChecked())
        {
            Category_Code =202;
        } else if (Admin_Product_Combo.isChecked()) {
            Category_Code =203;
        } else if (Admin_Product_Drink.isChecked()) {
            Category_Code = 204;
        }else
        {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Admin_Product_Veg.isChecked())
        {
            type="veg";
        } else if (Admin_Product_Nonveg.isChecked()) {
            type="Nonveg";
        }else {
            Toast.makeText(this, "Select Product Type", Toast.LENGTH_SHORT).show();
            return  false;
        }
        return true;
    }
    private void setProduct()
    {
        pg.setTitle("Loading..... ");
        pg.setMessage("Adding Product ... ");
        pg.setIcon(R.drawable.logo);
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        String name,des;
        double price;
        name = Admin_product_Name.getText().toString();
        price= Double.parseDouble(Admin_Product_Price.getText().toString());
        des = Admin_Product_Description.getText().toString();
        isCheck=check();
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        if(isCheck)
        {
            reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Product p = new Product();
                            p.setDescription(des);
                            p.setCategoryId(Category_Code);
                            p.setName(name);
                            p.setImageUrl(String.valueOf(uri));
                            p.setPrice(price);
                            p.setType(type);
                            adminApi.addProduct(p).enqueue(new Callback<Product>() {
                                @Override
                                public void onResponse(Call<Product> call, Response<Product> response) {
                                    Toast.makeText(Admin_Product_Add.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Product> call, Throwable t) {
                                    Toast.makeText(Admin_Product_Add.this, "failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    }
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}