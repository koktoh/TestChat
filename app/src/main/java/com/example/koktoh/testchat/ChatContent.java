package com.example.koktoh.testchat;

import android.graphics.Bitmap;

/**
 * Created by kuroda on 2015/10/27.
 */
public class ChatContent {
    private Bitmap _icon;
    private String _text;

    private int _layout;

    private String _id;

    public void setIcon(Bitmap image) {
        _icon = image;
    }

    public Bitmap getIcon(){
        return _icon;
    }

    public void setText(String text){
        _text = text;
    }

    public String getText(){
        return _text;
    }

    public void setLayout(int layout) { _layout = layout; }

    public int getLayout() { return _layout; }

    public String getId() { return _id; }

    public ChatContent(String id) {
        _id = id;
    }
}
