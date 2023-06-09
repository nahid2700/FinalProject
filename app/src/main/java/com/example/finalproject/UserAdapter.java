package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Viewholder> {
    Context chatMain;
    ArrayList<Users> usersArrayList;
    public UserAdapter(ChatMain chatMain, ArrayList<Users> usersArrayList) {
        this.chatMain= chatMain;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(chatMain).inflate(R.layout.item_user_row, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Users users = usersArrayList.get(position);
        holder.user_name.setText(users.name);
        holder.user_status.setText(users.status);
        Picasso.get().load(users.imgUri).into(holder.user_profile);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(chatMain, ChatActivity.class);
                i.putExtra("name", users.getName());
                i.putExtra("ReceiverImage", users.getImgUri());
                i.putExtra("uid", users.getUid());
                chatMain.startActivity(i);
            }
        });
    }



    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }
    class Viewholder extends RecyclerView.ViewHolder {
CircleImageView user_profile;
TextView user_name, user_status;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            user_profile =itemView.findViewById(R.id.user_image);
            user_status = itemView.findViewById(R.id.user_status);
            user_name = itemView.findViewById(R.id.user_name);
        }


    }
}
