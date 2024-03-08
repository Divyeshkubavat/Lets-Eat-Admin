package com.example.letseatadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.Models.Offer;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Offer_Add extends AppCompatActivity {

    ImageView Admin_Offer_Add_Image;
    RadioButton Admin_offer_Add_Burger,Admin_Offer_Add_Pizza,Admin_Offer_Add_Combo,Admin_Offer_Add_Drink;
    Button Admin_Offer_Add_Button;
    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;
    int Category_Code;
    public FirebaseStorage firebaseStorage;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    private ProgressDialog Admin_Offer_Progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_offer_add);
        Admin_Offer_Progressbar = new ProgressDialog(this);
        Admin_Offer_Progressbar.setTitle("Loading..... ");
        Admin_Offer_Progressbar.setMessage("Image is Upload ... ");
        Admin_Offer_Add_Image = findViewById(R.id.Admin_Offer_Add_Image);
        Admin_Offer_Add_Button =  findViewById(R.id.Admin_Offer_Add_Button);
        Admin_Offer_Add_Pizza = findViewById(R.id.Admin_Offer_Add_Pizza_Radio);
        Admin_Offer_Add_Combo = findViewById(R.id.Admin_Offer_Add_Combo_Radio);
        Admin_Offer_Add_Drink = findViewById(R.id.Admin_Offer_Add_Drink_Radio);
        Admin_offer_Add_Burger = findViewById(R.id.Admin_Offer_Add_Burger_Radio);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        Admin_Offer_Add_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        Admin_Offer_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Admin_Offer_Progressbar.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        offerAdd();
                        Admin_Offer_Progressbar.cancel();
                    }
                },5000);
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
            Admin_Offer_Add_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        String ImageUri = imageuri.toString();
        if(ImageUri.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Admin_offer_Add_Burger.isChecked())
        {
            Category_Code = 201;
        }else if(Admin_Offer_Add_Pizza.isChecked())
        {
            Category_Code =202;
        } else if (Admin_Offer_Add_Combo.isChecked()) {
            Category_Code =203;
        } else if (Admin_Offer_Add_Drink.isChecked()) {
            Category_Code = 204;
        }else
        {
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void offerAdd()
    {
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        isCheck=check();
        if(isCheck)
        {
            reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Offer offer = new Offer();
                            offer.setCategoryId(Category_Code);
                            offer.setImageUrl(String.valueOf(uri));
                            adminApi.saveOffer(offer).enqueue(new Callback<Offer>() {
                                @Override
                                public void onResponse(Call<Offer> call, Response<Offer> response) {
                                    Toast.makeText(Admin_Offer_Add.this, "Offer Added Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Offer> call, Throwable t) {
                                    Toast.makeText(Admin_Offer_Add.this, "Failed", Toast.LENGTH_SHORT).show();
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