package com.example.capitalink.loginSignup;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import com.example.capitalink.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPw extends AppCompatActivity {

    private Toolbar mTopToolbar;
    TextInputLayout email;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pw);

        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);


        Window window=getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.status_bar));

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);


        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#eeeeee"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>Forgot Password</font>"));

        email=findViewById(R.id.email_forget_pw);
        AppCompatButton sendBtn=(AppCompatButton)findViewById(R.id.sendBtn);
        firebaseAuth = FirebaseAuth.getInstance(FirebaseApp.getInstance());



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String forgetEmail=email.getEditText().getText().toString().trim();


                if (TextUtils.isEmpty(forgetEmail)) {
                    email.setError("Email is Required.");

                    return;
                }

                email.setError(null);

                firebaseAuth.sendPasswordResetEmail(forgetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ForgetPw.this,"Link sent to your email",Toast.LENGTH_LONG).show();
                        }

                        else {
                            Toast.makeText(ForgetPw.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
