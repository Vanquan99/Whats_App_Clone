package com.vanquan.whatsappclone.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.vanquan.whatsappclone.Models.MessageModel;
import com.vanquan.whatsappclone.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{

    ArrayList<MessageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE =1 ;
    int RECEIVER_VIEW_TYPE =2 ;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SENDER_VIEW_TYPE){

            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewVolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_receiver,parent,false);
            return new SenderViewVolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getuId()
                .equals(FirebaseAuth.getInstance().getUid())){

            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }

//        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModel messageModel = messageModels.get(position);


        //delete login user

       holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {

               new AlertDialog.Builder(context)
                       .setTitle("delete")
                       .setMessage("Are you sure you want delete this message")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                               FirebaseDatabase database = FirebaseDatabase.getInstance();
                               String senderRoom = FirebaseAuth.getInstance().getUid() + recId;

                               //delete message
                               database.getReference().child("chats").child(senderRoom)
                                       .child(messageModel.getMessageId())
                                       .setValue(null);

                               //==========
                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               dialogInterface.dismiss();
                           }
                       }).show();
               return false;
           }
       });
        //====================

        if (holder.getClass()==SenderViewVolder.class){
            ((SenderViewVolder)holder).senderMsg.setText(messageModel.getMessage());

        }
        else {
            ((ReceiverViewVolder)holder).receiverMsg.setText(messageModel.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public  class ReceiverViewVolder extends RecyclerView.ViewHolder{

        TextView receiverMsg, receiverTime;
        public ReceiverViewVolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.receiveText);
            receiverTime = itemView.findViewById(R.id.reveiveTime);
        }
    }

    public class SenderViewVolder extends  RecyclerView.ViewHolder{


        TextView senderMsg, senderTime;
        public SenderViewVolder(@NonNull View itemView) {
            super(itemView);

            senderMsg= itemView.findViewById(R.id.senderText);
            senderTime= itemView.findViewById(R.id.senderTime);
        }
    }
}
