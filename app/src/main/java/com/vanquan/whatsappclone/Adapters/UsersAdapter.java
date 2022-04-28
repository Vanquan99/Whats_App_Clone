package com.vanquan.whatsappclone.Adapters;

import android.content.Context;
import android.graphics.Picture;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vanquan.whatsappclone.Models.Users;
import com.vanquan.whatsappclone.R;

import java.util.ArrayList;

public class UsersAdapter  extends RecyclerView.Adapter<UsersAdapter.viewHolder>{

    ArrayAdapter<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context){
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_users,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Users users = list.getItem(position);

        Picasso.get().load(users.getProfilepic()).placeholder(R.drawable.avatarman).into(holder.image);

        holder.userName.setText(users.getUserName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class viewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView userName, lastMessage;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
        }
    }
}
