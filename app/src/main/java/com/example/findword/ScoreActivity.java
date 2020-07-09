package com.example.findword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.findword.adapter.RecyclerViewAdapter;
import com.example.findword.data.MyDbHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().setTitle("My Scores"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar
         RecyclerView recyclerView=findViewById(R.id.recycle);
      //  Toast.makeText(ScoreActivity.this,"Hi Syed",Toast.LENGTH_LONG).show();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyDbHandler db=new MyDbHandler(ScoreActivity.this);
        ArrayList<Points> arr=db.getAllPoints();
        ArrayList<Points> p=new ArrayList<>();
        for (int i=0;i<arr.size();i++)
        {
            p.add(arr.get(i));
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\n" +
                    "Points: " + arr.get(i).getPoints() + "\n" +
                    "Time: " + arr.get(i).getTime() + "\n");
        }

         RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, p);
        recyclerView.setAdapter(recyclerViewAdapter);


    }
}
