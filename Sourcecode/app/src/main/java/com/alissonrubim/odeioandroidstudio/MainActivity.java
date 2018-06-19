package com.alissonrubim.odeioandroidstudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText editTextNickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChat();
            }
        });
    }

    private void bind(){
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextNickname = findViewById(R.id.editTextNickname);
    }

    public void openChat(){
        ChatActivity.MyNickname = editTextNickname.getText().toString();
        if(ChatActivity.MyNickname.isEmpty()){
            Toast.makeText(getApplicationContext(), "Informe seu nickname antes de entrar na sala!", Toast.LENGTH_LONG);
        }else {
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            finish();
        }
    }
}
