package com.example.findword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vipul.hp_hp.library.Layout_to_Image;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;

import static com.example.findword.MainActivity.MSG;

public class ResultFirstActivity extends AppCompatActivity {
    static String msg,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_first);
        getSupportActionBar().setTitle("Share With Friends"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
        Intent i=getIntent();
         msg= i.getStringExtra(MSG);

        TextView t1=findViewById(R.id.t1);
        TextView t2=findViewById(R.id.t2);
        SharedPreferences highest=getSharedPreferences("myref", Context.MODE_PRIVATE);
         name=highest.getString("name","Player 1");


        t1.setText("Player:     "+name);
        t2.setText("Score:      "+msg);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              Intent i=new Intent(ResultFirstActivity.this,ResultActivity.class);
                                                              i.putExtra(MSG,msg);
                                                              startActivity(i);
                                                          }
                                                      }
        );
    }

    public  void Share(View v)
    {

    }








}
