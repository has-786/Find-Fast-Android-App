package com.example.findword.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findword.Points;
import com.example.findword.R;
import com.example.findword.ScoreActivity;
import com.example.findword.data.MyDbHandler;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Points> arr;

    public RecyclerViewAdapter(Context context,ArrayList<Points> arr) {
        this.context = context;
        this.arr=arr;
        for (int i=0;i<arr.size();i++)
        {
            Log.d("myapp", "\nId: " + arr.get(i).getId() + "\ns " +
                    "Points: " + arr.get(i).getPoints() + "\nd " +
                    "Time: " + arr.get(i).getTime() + "\n");
        }
       //Toast.makeText(context,arr.size(),Toast.LENGTH_LONG).show();

    }

    // Where to get the single card as viewholder Object
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

    // What will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

//MyDbHandler db=new MyDbHandler(context);
  //      arr=db.getAllPoints();

        //  Toast.makeText(context,arr.get(0).getId(),Toast.LENGTH_LONG).show();

           holder.points.setText(Integer.toString(arr.get(position).getPoints()));
            holder.time.setText(arr.get(position).getTime());

            //holder.img.setLayerType(View.LAYER_TYPE_SOFTWARE, null);



    }


    // How many items?
    @Override
    public int getItemCount() {
        return arr.size();
    }


   public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView id;
        public TextView points;
        public TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            points = itemView.findViewById(R.id.point1);
            time = itemView.findViewById(R.id.time1);
            MyDbHandler db=new MyDbHandler(context);

            ArrayList<Points> arr=db.getAllPoints();
            for (int i=0;i<arr.size();i++)
            {
                Log.d("myapp", "\nId: " + arr.get(i).getId() + "\nss " +
                        "Points: " + arr.get(i).getPoints() + "\ndd " +
                        "Time: " + arr.get(i).getTime() + "\n");
            }
        }

        @Override
        public void onClick(View view) {
            Log.d("ClickFromViewHolder", "Clicked");

        }
    }





}


