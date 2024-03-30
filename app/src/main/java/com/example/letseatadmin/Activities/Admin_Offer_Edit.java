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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Models.Offer;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Offer_Edit extends AppCompatActivity {

    ImageView Admin_Offer_Edit_Img;
    RadioButton Admin_Offer_Edit_Burger,Admin_Offer_Edit_Pizza,Admin_Offer_Edit_Combo,Admin_Offer_Edit_Drink;
    Button Admin_Offer_Edit_Update;
    String imageurl;
    int id;
    int code;
    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;
    int Category_Code;
    public FirebaseStorage firebaseStorage;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer_edit);
        pg=new ProgressDialog(this);
        pg.setMessage("Please wait update your profile ...");
        pg.setTitle("Update ...");
        pg.setIcon(R.drawable.logo);
        Admin_Offer_Edit_Burger = findViewById(R.id.Admin_Offer_Edit_Burger_Radio);
        Admin_Offer_Edit_Combo=findViewById(R.id.Admin_Offer_Edit_Combo_Radio);
        Admin_Offer_Edit_Pizza=findViewById(R.id.Admin_Offer_Edit_Pizza_Radio);
        Admin_Offer_Edit_Drink=findViewById(R.id.Admin_Offer_Edit_Drink_Radio);
        Admin_Offer_Edit_Img=findViewById(R.id.Admin_Offer_Edit_Image);
        Admin_Offer_Edit_Update=findViewById(R.id.Admin_Offer_Update_Button);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        Admin_Offer_Edit_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offerUpdate();
            }
        });
        Admin_Offer_Edit_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });

    }
    private void setData()
    {
        Intent intent = getIntent();
         imageurl = intent.getStringExtra("offerUrl");
         id = intent.getIntExtra("offerId",0);
         code = intent.getIntExtra("offerCode",201);

        Glide.with(getApplicationContext()).load(imageurl).into(Admin_Offer_Edit_Img);
        if(code == 201){
            Admin_Offer_Edit_Burger.setChecked(true);
        } else if (code==202) {
            Admin_Offer_Edit_Pizza.setChecked(true);
        }else if(code==203) {
            Admin_Offer_Edit_Combo.setChecked(true);
        } else if (code==204) {
            Admin_Offer_Edit_Drink.setChecked(true);
        }else {
            Toast.makeText(this, "Product Category Not Found", Toast.LENGTH_SHORT).show();
        }
    }
    public boolean check()
    {
        if(imageurl.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Admin_Offer_Edit_Burger.isChecked())
        {
            Category_Code = 201;
        }else if(Admin_Offer_Edit_Pizza.isChecked())
        {
            Category_Code =202;
        } else if (Admin_Offer_Edit_Combo.isChecked()) {
            Category_Code =203;
        } else if (Admin_Offer_Edit_Drink.isChecked()) {
            Category_Code = 204;
        }else
        {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
            Admin_Offer_Edit_Img.setImageURI(imageuri);
        }
    }
    private void offerUpdate()
    {
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        isCheck=check();
        if(isCheck)
        {
            pg.show();
            if(imageurl.equals(imageurl))
            {
                Offer offer = new Offer();
                offer.setCategoryId(Category_Code);
                offer.setImageUrl(imageurl);
                adminApi.offerUpdate(id,offer).enqueue(new Callback<Offer>() {
                    @Override
                    public void onResponse(Call<Offer> call, Response<Offer> response) {
                        Toast.makeText(Admin_Offer_Edit.this, "Offer Update Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Offer> call, Throwable t) {
                        Toast.makeText(Admin_Offer_Edit.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else
            {
                reference.putFile(Uri.parse(imageurl)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Offer offer = new Offer();
                                offer.setCategoryId(Category_Code);
                                offer.setImageUrl(String.valueOf(uri));
                                adminApi.offerUpdate(id,offer).enqueue(new Callback<Offer>() {
                                    @Override
                                    public void onResponse(Call<Offer> call, Response<Offer> response) {
                                        Toast.makeText(Admin_Offer_Edit.this, "Offer Added Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Offer> call, Throwable t) {
                                        Toast.makeText(Admin_Offer_Edit.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin_Offer_Edit.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
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