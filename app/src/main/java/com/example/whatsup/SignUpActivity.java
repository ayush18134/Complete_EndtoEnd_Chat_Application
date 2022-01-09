package com.example.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whatsup.databinding.ActivitySignUpBinding;
import com.example.whatsup.models.Users;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        progressdialog=new ProgressDialog(this);
        progressdialog.setTitle("Creating The Account");
        progressdialog.setMessage("We are creating your account");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();
        binding.tvAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etName.getText().toString().isEmpty()){
                    binding.etName.setError("Enter Your Email");
                    return;
                }
                if(binding.etEmail.getText().toString().isEmpty()){
                    binding.etEmail.setError("Enter Your Email");
                    return;
                }
                if(binding.etPassword.getText().toString().isEmpty()){
                    binding.etPassword.setError("Enter Your Email");
                    return;
                }

                progressdialog.show();
                auth.createUserWithEmailAndPassword
                        (binding.etEmail.getText().toString(),binding.etPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressdialog.dismiss();
                                if(task.isSuccessful()){
                                    Users user=new Users(binding.etName.getText().toString(),binding.etEmail.getText().toString(),binding.etPassword.getText().toString());
                                    String id=task.getResult().getUser().getUid();
                                    database.getReference().child("users").child(id).setValue(user);
                                    Toast.makeText(SignUpActivity.this, "User Created Successfully " ,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage() ,Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}