package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
String receiverImage, receiveruid, receivername, SenderUID;
CircleImageView profileimage;
TextView receiver_name;
FirebaseDatabase database;
FirebaseAuth auth;
CardView sendBtn;
EditText etmessage;
String senderRoom, receiverRoom;
RecyclerView messageAdapter;
ArrayList<Messages> messagesArrayList;
MessagesAdapter messagesAdapter;
public static String sImage, rImage;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        profileimage = findViewById(R.id.profile_image);
        receivername = getIntent().getStringExtra("name");
        receiverImage = getIntent().getStringExtra("ReceiverImage");
        receiveruid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();
        messageAdapter = findViewById(R.id.messageAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(ChatActivity.this, messagesArrayList);
        messageAdapter.setAdapter(messagesAdapter);
        receiver_name = findViewById(R.id.receiver);
        sendBtn = findViewById(R.id.sendbtn);
        etmessage = findViewById(R.id.etmessage);


        Picasso.get().load(receiverImage).into(profileimage);
        receiver_name.setText(receivername);
        SenderUID = auth.getUid();
        senderRoom = SenderUID + receiveruid;
        receiverRoom = receiveruid + SenderUID;
       DatabaseReference reference = database.getReference().child("user").child(auth.getUid());
        DatabaseReference chatreference = database.getReference().child("chats").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        messagesArrayList.clear();
        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
            Messages messages = dataSnapshot.getValue(Messages.class);
            messagesArrayList.add(messages);
        }

        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sImage =snapshot.child("imgUri").getValue().toString();
                rImage = receiverImage;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = etmessage.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Write Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                etmessage.setText("");
                Date date= new Date();
                Messages messages = new Messages(message,SenderUID, date.getTime());
                database.getReference().child("chats").child(senderRoom).child("messages")
                        .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                database.getReference().child("chats").child(receiverRoom).child("messages")
                                        .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }
                        });

            }
        });
    }
}