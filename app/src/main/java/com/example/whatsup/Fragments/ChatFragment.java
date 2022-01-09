package com.example.whatsup.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.whatsup.Adapters.useradapter;
import com.example.whatsup.R;
import com.example.whatsup.databinding.FragmentChatBinding;
import com.example.whatsup.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding;
    ArrayList<Users> list=new ArrayList<Users>();
    FirebaseDatabase database;
    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentChatBinding.inflate(inflater, container, false);
        useradapter adapter=new useradapter(list,getContext());
        binding.chatrecyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.chatrecyclerview.setLayoutManager(layoutManager);
        database=FirebaseDatabase.getInstance();

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Users curruser=dataSnapshot.getValue(Users.class);
                    curruser.setUserid(dataSnapshot.getKey());
                    if(!FirebaseAuth.getInstance().getUid().equals(curruser.getUserid())){
                        list.add(curruser);
                    }

//                    Log.d("myTag", curruser.toString());
                }

//                System.out.println(list);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return binding.getRoot();
    }
}