package com.alissonrubim.odeioandroidstudio.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alissonrubim.odeioandroidstudio.ChatActivity;
import com.alissonrubim.odeioandroidstudio.R;

public class ReceiveMessageViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewMessage = null;
    public TextView textViewUserName = null;
    public View view = null;


    ReceiveMessageViewHolder(View view) {
        super(view);
        textViewMessage = (TextView) view.findViewById(R.id.text_message_body);
        textViewUserName = (TextView) view.findViewById(R.id.text_message_name);
    }

    void bind(BaseMessage message) {
        textViewMessage.setText(message.getMessage());
        textViewUserName.setText(message.getFrom());
    }
}