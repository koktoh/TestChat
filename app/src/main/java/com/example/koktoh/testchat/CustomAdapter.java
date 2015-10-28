package com.example.koktoh.testchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kuroda on 2015/10/27.
 */
public class CustomAdapter extends ArrayAdapter<ChatContent> {
    private LayoutInflater _li;

    public CustomAdapter(Context context, int textViewResourceId, List<ChatContent> objects){
        super(context, textViewResourceId, objects);

        _li = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ChatContent item = (ChatContent)getItem(position);

        convertView = _li.inflate(item.getLayout(), null);

        ImageView iv;
        iv = (ImageView)convertView.findViewById(R.id.image);
        iv.setImageBitmap(item.getIcon());

        TextView tv;
        tv = (TextView)convertView.findViewById(R.id.text);
        tv.setText(item.getText());

        return convertView;
    }
}
