package com.example.whatsup.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.whatsup.Adapters.useradapter;
import com.example.whatsup.R;
import com.example.whatsup.databinding.FragmentChatBinding;
import com.example.whatsup.databinding.FragmentProfileBinding;
import com.example.whatsup.models.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    ArrayList<Users> list=new ArrayList<Users>();
    FirebaseDatabase database;
//    FirebaseStorage storage;
    public ProfileFragment() {
        // Required empty public constructor
    }

    ProgressDialog progressdialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentProfileBinding.inflate(inflater, container, false);
        database=FirebaseDatabase.getInstance();
        progressdialog=new ProgressDialog(getActivity());
        progressdialog.setTitle("Change Profile Picture");
        progressdialog.setMessage("Changing Your Profile Picture");
        binding.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressdialog.show();
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 32);

            }
        });
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=binding.newname.getText().toString();
                HashMap<String, Object> obj=new HashMap<>();
                obj.put("username",username);
                database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
                Toast.makeText(getActivity(), "User Name Has Been Changed", Toast.LENGTH_SHORT).show();
            }
        });
        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users user=snapshot.getValue(Users.class);
                Picasso.get().load(user.getProfilephoto()).placeholder(R.drawable.img_4).into(binding.profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            Uri file=data.getData();
            binding.profile.setImageURI(file);
            StorageReference ref = FirebaseStorage.getInstance().getReference().child("profile_pictures")
                    .child(FirebaseAuth.getInstance().getUid());

            ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("profilephoto")
                                    .setValue(uri.toString());
                            Toast.makeText(getActivity(), "Profile Photo Updated", Toast.LENGTH_SHORT).show();
                            progressdialog.dismiss();
                        }
                    });
                }
            });

        }
    }
}