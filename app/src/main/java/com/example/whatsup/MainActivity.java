package com.example.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.whatsup.Adapters.fragmentadapter;
import com.example.whatsup.databinding.ActivityMainBinding;
import com.example.whatsup.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private FirebaseAuth auth;
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"White\">" + getString(R.string.app_name) + "</font>"));
        getSupportActionBar().setTitle("");
        binding.pager.setAdapter(new fragmentadapter(getSupportFragmentManager()));
        binding.tabs.setupWithViewPager(binding.pager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            auth.signOut();
            Intent intent=new Intent(MainActivity.this,SignInActivity.class);
            startActivity(intent);
        }
        return true;
    }
}