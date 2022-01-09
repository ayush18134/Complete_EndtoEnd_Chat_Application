package com.example.whatsup.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.R;
import com.example.whatsup.chatdetailactivity;
import com.example.whatsup.models.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class useradapter extends RecyclerView.Adapter<useradapter.ViewHolder>{
    ArrayList<Users> list;
    Context context;

    public useradapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users curruser=list.get(position);
        Picasso.get().load(curruser.getProfilephoto()).placeholder(R.drawable.img_4).into(holder.image);
        holder.username.setText(curruser.getUsername());
//        holder.image.setImageResource(R.drawable.img_4);
        FirebaseDatabase.getInstance().getReference().child("chats")
                .child(FirebaseAuth.getInstance().getUid()+curruser.getUserid())
        .orderByChild("timestamp").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for(DataSnapshot shot:snapshot.getChildren()){
                        holder.messege.setText(shot.child("text").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, chatdetailactivity.class);
                intent.putExtra("userid",curruser.getUserid());
                intent.putExtra("profile",curruser.getProfilephoto());
                intent.putExtra("username",curruser.getUsername());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView username,messege;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profile);
            username=itemView.findViewById(R.id.username);
            messege=itemView.findViewById(R.id.last_message);
        }
    }
}
