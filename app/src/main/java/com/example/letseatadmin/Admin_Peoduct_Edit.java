package com.example.letseatadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Models.Product;
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

public class Admin_Peoduct_Edit extends AppCompatActivity {

    CircleImageView Admin_Product_Edit_Image;
    public Uri imageuri=Uri.EMPTY;
    TextInputEditText Admin_product_Name,Admin_Product_Price,Admin_Product_Description;
    RadioButton Admin_Product_Burger,Admin_Product_Pizza,Admin_Product_Combo,Admin_Product_Drink,Admin_Product_Veg,Admin_Product_Nonveg;
    Button Admin_Product_Button;
    boolean isCheck = false;
    public FirebaseStorage firebaseStorage;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    private int Category_Code;
    String type;
    String name,desc,ptype,image;
    int id,category,rate;
    double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_peoduct_edit);
        Admin_Product_Edit_Image = findViewById(R.id.Admin_Product_Edit_Image);
        Admin_Product_Burger = findViewById(R.id.Admin_Product_Edit_Burger_Radio);
        Admin_Product_Drink = findViewById(R.id.Admin_Product_Edit_Drink_Radio);
        Admin_Product_Combo = findViewById(R.id.Admin_Product_Edit_Combo_Radio);
        Admin_Product_Pizza = findViewById(R.id.Admin_Product_Edit_Pizza_Radio);
        Admin_product_Name =findViewById(R.id.Admin_Product_Edit_Name);
        Admin_Product_Price=findViewById(R.id.Admin_Product_Edit_Price);
        Admin_Product_Description=findViewById(R.id.Admin_Product_Edit_Description);
        Admin_Product_Button=findViewById(R.id.Admin_Product_Edit_Button);
        Admin_Product_Veg=findViewById(R.id.Admin_Product_Edit_Veg_Radio);
        Admin_Product_Nonveg=findViewById(R.id.Admin_Product_Edit_Nonveg_Radio);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        firebaseStorage = FirebaseStorage.getInstance();
        setProduct();
        Admin_Product_Edit_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                image="";
            }
        });
        Admin_Product_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProduct();
            }
        });
    }
    private void setProduct()
    {
        Intent intent= getIntent();
        String name,desc,ptype;
        int category,rate;
        double price;
        name = intent.getStringExtra("pname");
        price=intent.getDoubleExtra("pprice",0);
        desc=intent.getStringExtra("pdesc");
        ptype=intent.getStringExtra("ptype");
        image=intent.getStringExtra("pimage");
        id=intent.getIntExtra("pid",0);
        category=intent.getIntExtra("pcid",0);
        rate=intent.getIntExtra("rate",0);
        Admin_product_Name.setText(name);
        Admin_Product_Price.setText(String.valueOf(price));
        Admin_Product_Description.setText(desc);
        Glide.with(getApplicationContext()).load(image).into(Admin_Product_Edit_Image);
        if(ptype.equals("veg"))
        {
            Admin_Product_Veg.setChecked(true);
        }else {
            Admin_Product_Nonveg.setChecked(true);
        }
        if(category==201){
            Admin_Product_Burger.setChecked(true);
        } else if (category==202) {
            Admin_Product_Pizza.setChecked(true);
        } else if (category==203) {
            Admin_Product_Combo.setChecked(true);
        }else {
            Admin_Product_Drink.setChecked(true);
        }

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
            Admin_Product_Edit_Image.setImageURI(imageuri);
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
    private void updateProduct(){
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        isCheck=check();
        if(isCheck)
        {
            getValue();
            if(!image.equals("")){
                Product p = new Product();
                p.setName(name);
                p.setPrice(price);
                p.setDescription(desc);
                p.setType(type);
                p.setCategoryId(Category_Code);
                p.setImageUrl(image);
                adminApi.updateProduct(id,p).enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        Toast.makeText(Admin_Peoduct_Edit.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Toast.makeText(Admin_Peoduct_Edit.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else {
                reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Product p = new Product();
                                p.setName(name);
                                p.setPrice(price);
                                p.setDescription(desc);
                                p.setType(type);
                                p.setCategoryId(Category_Code);
                                p.setImageUrl(String.valueOf(uri));
                                adminApi.updateProduct(id,p).enqueue(new Callback<Product>() {
                                    @Override
                                    public void onResponse(Call<Product> call, Response<Product> response) {
                                        Toast.makeText(Admin_Peoduct_Edit.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Product> call, Throwable t) {
                                        Toast.makeText(Admin_Peoduct_Edit.this, "failed", Toast.LENGTH_SHORT).show();
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

    }

    private void getValue(){
        name =Admin_product_Name.getText().toString();
        price= Double.parseDouble(Admin_Product_Price.getText().toString());
        desc=Admin_Product_Description.getText().toString();
    }
}