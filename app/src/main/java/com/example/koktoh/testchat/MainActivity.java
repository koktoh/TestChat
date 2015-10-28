package com.example.koktoh.testchat;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Bitmap userIcon;
    private Bitmap comIcon;

    private int userLayout;
    private int comLayout;

    private List<ChatContent> objects;
    CustomAdapter ca;

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userIcon = BitmapFactory.decodeResource(getResources(),R.drawable.user);
        comIcon = BitmapFactory.decodeResource(getResources(),R.drawable.com);

        userLayout = R.layout.user_layout;
        comLayout = R.layout.com_layout;

        objects = new ArrayList<ChatContent>();
        ca = new CustomAdapter(this, 0, objects);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        textView = (TextView)findViewById(R.id.message);

        ListView lv = (ListView)findViewById(R.id.list);
        lv.setAdapter(ca);

        try {
            FileInputStream fis = openFileInput("log.txt");
            byte buffer[] = new byte[100];
            fis.read(buffer);
            String str = new String(buffer);
            String[] strings = str.split(System.getProperty("line.separator"));
            for(String s : strings){
                String[] ss = s.split(":");
                if(ss.length < 2) continue;
                if(ss[1].isEmpty()) continue;
                if(ss[0].equals("user")){
                    sendUserMessage(ss[1]);
                } else {
                    sendComMessage(ss[1]);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        try{
            FileOutputStream fos = openFileOutput("log.txt", MODE_PRIVATE);
            String str = "";
            for(ChatContent item : objects){
                if(item.getId().equals("user")){
                    str += "user:" + item.getText() + System.getProperty("line.separator");
                }
                else{
                    str += "com:" + item.getText() + System.getProperty("line.separator");
                }
            }
            Log.v("onPause", str);
            fos.write(str.getBytes());
            fos.close();
        }catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }


    public void onClick(View v){
        String text = textView.getText().toString();

        if(text.isEmpty()) return;

        sendUserMessage(text);

        sendComMessage(text);

        textView.setText("");
    }

    private void sendUserMessage(String text){
        ChatContent item = new ChatContent("user");
        item.setIcon(userIcon);
        item.setText(text);
        item.setLayout(userLayout);

        objects.add(item);

        ca.notifyDataSetChanged();
    }

    private void sendComMessage(String text){
        ChatContent item = new ChatContent("com");
        item.setIcon(comIcon);
        item.setText(text);
        item.setLayout(comLayout);

        objects.add(item);

        ca.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
