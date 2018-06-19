package com.alissonrubim.odeioandroidstudio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alissonrubim.odeioandroidstudio.Helper.BaseMessage;
import com.alissonrubim.odeioandroidstudio.Helper.MessageListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    public static String MyNickname;

    private RecyclerView recyclerView;
    private Button buttonSend;
    private List<BaseMessage> messageList = new ArrayList<BaseMessage>();
    private EditText edittext_chatbox;
    private MessageListAdapter messageListAdapter;
    private ArrayList<String> loadedMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bind();
        Firebase_setup();
        
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        messageListAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageListAdapter);
    }

    private void bind(){
        edittext_chatbox = findViewById(R.id.edittext_chatbox);
        buttonSend = findViewById(R.id.buttonSend);
        recyclerView = (RecyclerView) findViewById(R.id.reyclerview_message_list);
    }

    private void sendMessage(){
        String text = edittext_chatbox.getText().toString();
        if(!text.isEmpty()) {
            BaseMessage message = new BaseMessage(MyNickname, text, true);
            Firebase_sendMessage(message);
            edittext_chatbox.setText("");
            showMessage(message);
        }
    }

    private void showMessage(BaseMessage message){
        messageList.add(message);
        messageListAdapter.notifyItemInserted(messageList.size() - 1);
        messageListAdapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(messageList.size() - 1);
    }

    private boolean Firebase_isFirstLoad = false;
    private DatabaseReference Firebase_reference = null;
    private void Firebase_setup(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Firebase_reference = FirebaseDatabase.getInstance().getReference("Messages");

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    System.out.println("Firebase is connected");
                } else {
                    System.out.println("Firebase is not connected");
                    Toast.makeText(getApplicationContext(), "Sem conexão com o Firebase", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!Firebase_isFirstLoad) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        loadedMessages.add(childSnapshot.getKey());
                    }
                    Firebase_isFirstLoad = true;
                }else{
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        if(!loadedMessages.contains(key)){
                            BaseMessage message = BaseMessage.parseData(childSnapshot);
                            message.setSentByMe(message.getFrom().equals(MyNickname));
                            showMessage(message);
                            loadedMessages.add(key);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Firebase_reference.addValueEventListener(postListener);
    }

    private void Firebase_sendMessage(BaseMessage message){
        String key = Firebase_reference.push().getKey();
        loadedMessages.add(key);
        Firebase_reference.child(key).setValue(message.toHashMap());
    }
}
