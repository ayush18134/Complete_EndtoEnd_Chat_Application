package com.example.whatsup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsup.R;
import com.example.whatsup.models.message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatadapter extends RecyclerView.Adapter{
    ArrayList<message> messages;
    Context context;

    public chatadapter(ArrayList<message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }
    int SenderViewType=1;
    int ReceiverViewType=2;
    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUid().equals(FirebaseAuth.getInstance().getUid())){
            return SenderViewType;
        }
        return ReceiverViewType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SenderViewType){
            View view= LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
        View view= LayoutInflater.from(context).inflate(R.layout.sample_receive,parent,false);
        return new ReceiverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        message currmessage=messages.get(position);
        if(holder.getClass()==SenderViewHolder.class){
            ((SenderViewHolder)holder).sendermessage.setText(currmessage.getText());
        }
        else{
            ((ReceiverViewHolder)holder).receivemessage.setText(currmessage.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder{
        TextView receivemessage, receivetime;
        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receivemessage=itemView.findViewById(R.id.receivermessage);
            receivetime=itemView.findViewById(R.id.receiver_time);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder{
        TextView sendermessage, sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sendermessage=itemView.findViewById(R.id.sendmessage);
            sendertime=itemView.findViewById(R.id.send_time);
        }
    }
}
