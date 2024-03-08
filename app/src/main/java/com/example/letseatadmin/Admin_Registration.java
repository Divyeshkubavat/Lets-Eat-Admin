package com.example.letseatadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCas;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.util.Property;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Admin_Registration extends AppCompatActivity {

    LottieAnimationView Admin_registration_Animation;
    TextInputEditText Admin_Registration_Mobile,Admin_Registration_Pass,Admin_Registration_Email,Admin_Registration_Fullname;
    Button Admin_Reistration_Button;
    TextView Admin_Registration_Login;
    String Admin_New_UID;
    SharedPreferences Admin_Registration_Check_UID;

    private ProgressDialog Admin_Registration_Progressbar;

    boolean ischeck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);
        Admin_Registration_Progressbar = new ProgressDialog(this);
        Admin_Registration_Progressbar.setTitle("Loading..... ");
        Admin_Registration_Progressbar.setMessage("UID will send to your email .... ");
        Admin_Registration_Progressbar.setCanceledOnTouchOutside(false);
        Admin_registration_Animation = findViewById(R.id.Admin_Registration_Animation);
        Admin_Registration_Fullname = findViewById(R.id.Admin_Registration_Fullname);
        Admin_Registration_Login = findViewById(R.id.Admin_Registration_Login);
        Admin_Registration_Email = findViewById(R.id.Admin_Registration_Email);
        Admin_Registration_Mobile=findViewById(R.id.Admin_Registration_Mobile);
        Admin_Registration_Pass=findViewById(R.id.Admin_Registration_Pass);
        Admin_Reistration_Button = findViewById(R.id.Admin_Registration_Button);
        Admin_Registration_Check_UID = getApplicationContext().getSharedPreferences("Admin_Registration_Check_UID",MODE_PRIVATE);
        Admin_Reistration_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck = check();
                if(ischeck)
                {
                    Admin_New_UID=Admin_UID_Generattor();
                    String Admin_Email = Admin_Email_Format();
                    Admin_Registration_Progressbar.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if(sendPlainTextEmail("smtp.gmail.com","587","letseatpdm2024@gmail.com","rfwbmpzughmufojq",Admin_Registration_Email.getText().toString(),"Admin Unique User ID",Admin_Email))
                                        {
                                            SharedPreferences.Editor editor = Admin_Registration_Check_UID.edit();
                                            editor.putString("Admin_check_UID",Admin_New_UID);
                                            editor.apply();
                                            editor.commit();
                                            Intent intent = new Intent(getApplicationContext(), Admin_UID_Verify.class);
                                            intent.putExtra("Admin_Name",Admin_Registration_Fullname.getText().toString());
                                            intent.putExtra("Admin_Pass",Admin_Registration_Pass.getText().toString());
                                            intent.putExtra("Admin_Email",Admin_Registration_Email.getText().toString());
                                            intent.putExtra("Admin_Mobile",Admin_Registration_Mobile.getText().toString());
                                            startActivity(intent);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            thread.start();
                        }
                    },3000);
                }
                else
                {
                    Toast.makeText(Admin_Registration.this, "Please Fill The Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Admin_Registration_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin_Login.class));
            }
        });
    }
    private boolean check() {
        if(Admin_Registration_Fullname.length() == 0)
        {
            Admin_Registration_Fullname.setError("Username Is Requird Field");
            return false;
        }
        if(Admin_Registration_Mobile.length() < 0)
        {
            Admin_Registration_Mobile.setError("Mobile number is Requird");
            return  false;
        } else if (Admin_Registration_Mobile.length() < 10) {
            Admin_Registration_Mobile.setError("Mobile Number Must Be 10 Digits");
            return false;
        }
        if(Admin_Registration_Email.length() == 0)
        {
            Admin_Registration_Email.setError("Email Is Requird Field");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Admin_Registration_Email.getText().toString()).matches()) {
            Admin_Registration_Email.setError("Email Must Be In Email Formate");
            return false;
        }
        if(Admin_Registration_Pass.length() == 0)
        {
            Admin_Registration_Pass.setError("Password Is Requird Field");
            return false;
        } else if (Admin_Registration_Pass.length() < 8) {
            Admin_Registration_Pass.setError("Password Must Be * Character");
            return false;
        }
        return  true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public boolean sendPlainTextEmail(String host, String port,
                                   final String userName, final String password, String toAddress,
                                   String subject, String message) throws RuntimeException, MessagingException {

        // sets SMTP server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
// *** BEGIN CHANGE
        properties.put("mail.smtp.user", userName);

        // creates a new session, no Authenticator (will connect() later)
        Session session = Session.getDefaultInstance(properties);
// *** END CHANGE

        // creates a new e-mail message
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // set plain text message
        msg.setText(message);

// *** BEGIN CHANGE
        // sends the e-mail
        Transport t = session.getTransport("smtp");
        t.connect(userName, password);
        t.sendMessage(msg, msg.getAllRecipients());
        t.close();
// *** END CHANGE
    return  true;
    }
    public String Admin_UID_Generattor() {
            // choose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(12);

            for (int i = 0; i < 18; i++) {

                // generate a random number between
                // 0 to AlphaNumericString variable length
                int index
                        = (int)(AlphaNumericString.length()
                        * Math.random());

                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            return sb.toString();
    }
    public String Admin_Email_Format() {
        String Admin_Email_Format = "Subject: Welcome to Let's Eat - Divyeshkubavat2003\n" +
                "\n" +
                "Dear " + Admin_Registration_Fullname.getText().toString()+"\n"+
                "\n" +
                "Welcome aboard! We are thrilled to have you join our community at Let's eat. As a new member, we want to ensure that you have all the necessary information to navigate our platform seamlessly.\n" +
                "\n" +
                "Your Unique User ID  is an essential piece of information that will grant you access to various features and functionalities within our system. Please find your UID below:\n" +
                "\n" +
                "UID : "+Admin_New_UID+"\n" +
                "\n" +
                "With this UID, you'll be able to log in and enjoy the full range of benefits that Let's Eat has to offer. Should you have any questions or encounter any issues during the onboarding process, please don't hesitate to reach out to our support team at letseatpdm2024@gmail.com.\n" +
                "\n" +
                "Once again, welcome to the Let's Eat community. We're excited to have you with us and look forward to seeing you thrive within our platform.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Divyesh Kubavat\n" +
                "Managing director\n" +
                "Let's Eat\n" +
                "dkubavat0@gmail.com";
        return Admin_Email_Format;
    }
}