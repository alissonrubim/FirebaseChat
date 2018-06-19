package com.alissonrubim.odeioandroidstudio.Helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alissonrubim.odeioandroidstudio.ChatActivity;
import com.alissonrubim.odeioandroidstudio.R;

public class SentMessageViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewMessage = null;
    public View view = null;


    SentMessageViewHolder(View view) {
        super(view);
        textViewMessage = (TextView) view.findViewById(R.id.text_message_body);
    }

    void bind(BaseMessage message) {
        textViewMessage.setText(message.getMessage());
    }
}