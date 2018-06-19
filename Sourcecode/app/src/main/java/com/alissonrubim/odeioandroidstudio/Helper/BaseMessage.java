package com.alissonrubim.odeioandroidstudio.Helper;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class BaseMessage {
    public String Message;
    public String From;
    public boolean SentByMe;

    public BaseMessage(String from, String message, boolean sentByMe){
        this.Message = message;
        this.From = from;
        this.SentByMe = sentByMe;
    }

    public BaseMessage(String message){
        this.Message = message;
    }

    public boolean IsMine(){
        return this.SentByMe;
    }

    public String getMessage(){
        return Message;
    }

    public String getFrom(){
        return From;
    }

    public void setSentByMe(boolean v){
        SentByMe = v;
    }

    public Map<String, String> toHashMap(){
        Map<String, String> data = new HashMap<>();
        data.put("Message", getMessage());
        data.put("From", getFrom());
        return data;
    }

    public static BaseMessage parseData(DataSnapshot data){
        return new BaseMessage((String)data.child("From").getValue(), (String)data.child("Message").getValue(), false);
    }
}
