package com.alissonrubim.odeioandroidstudio.Helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alissonrubim.odeioandroidstudio.ChatActivity;
import com.alissonrubim.odeioandroidstudio.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    public Context context = null;
    public List<BaseMessage> messageList = null;

    public MessageListAdapter(Context context, List<BaseMessage> messageList){
        super();
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        BaseMessage message = (BaseMessage)this.messageList.get(position);
        return message.IsMine() ? 1 : 2; //1 == SENT, 2 == RECEIVED
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        if(viewType == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageViewHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceiveMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseMessage message = (BaseMessage)this.messageList.get(position);

        int viewType = holder.getItemViewType();
        if(viewType == 1)
            ((SentMessageViewHolder)holder).bind(message);
        else
            ((ReceiveMessageViewHolder)holder).bind(message);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}