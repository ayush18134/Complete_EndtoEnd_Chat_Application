package com.example.whatsup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.whatsup.Adapters.chatadapter;
import com.example.whatsup.databinding.ActivityChatdetailactivityBinding;
import com.example.whatsup.models.message;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class chatdetailactivity extends AppCompatActivity {
    ActivityChatdetailactivityBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent=new Intent(chatdetailactivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatdetailactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        String senderid=auth.getUid();
        String receiverid=getIntent().getStringExtra("userid");
        String username=getIntent().getStringExtra("username");
        String profile=getIntent().getStringExtra("profile");
        binding.nameofuser.setText(username);
        getSupportActionBar().hide();

//        binding.chatr
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chatdetailactivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Picasso.get().load(profile).placeholder(R.drawable.img_4).into(binding.profile);

        final ArrayList<message> messages=new ArrayList<>();
        final chatadapter adapter=new chatadapter(messages,this);
        binding.chatrecyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatrecyclerview.setLayoutManager(layoutManager);
        final String senderroom=senderid+receiverid;
        final String receiverroom=receiverid+senderid;


// this might be helpful

        database.getReference().child("chats").child(senderroom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot shot:snapshot.getChildren()){
                    message currm=shot.getValue(message.class);
                    messages.add(currm);
                }
                adapter.notifyDataSetChanged();
                binding.chatrecyclerview.scrollToPosition(messages.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currmess=binding.entermessege.getText().toString();
                final message currm=new message(senderid,currmess);
                currm.setTimestanp(new Date().getTime());
                binding.entermessege.setText("");

                database.getReference().child("chats").child(senderroom).push().setValue(currm)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats").child(receiverroom).push().setValue(currm)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });

    }
}