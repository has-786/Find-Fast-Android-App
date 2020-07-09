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

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getSupportActionBar().setTitle("Results"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action bar

        Intent i=getIntent();
        String msg= i.getStringExtra(MSG);
        TextView t1=findViewById(R.id.t1);
        TextView t2=findViewById(R.id.t2);
        SharedPreferences highest=getSharedPreferences("myref", Context.MODE_PRIVATE);
        String name=highest.getString("name","Player 1");


        t1.setText("Player:     "+name);
        t2.setText("Score:      "+msg);

        share();


    }

    public  void share()
    {
       // findViewById(R.id.textView).getLayoutParams().height=20;
        //findViewById(R.id.textView).getLayoutParams().width=30;

      //  ImageView img=findViewById(R.id.imageView);
      //  img.getLayoutParams().height = 600;
       // img.getLayoutParams().width = 300;
       // img.requestLayout();
       // findViewById(R.id.t1).getLayoutParams().height=20;
       // findViewById(R.id.t1).getLayoutParams().width=30;

       // findViewById(R.id.t2).getLayoutParams().height=20;
       // findViewById(R.id.t2).getLayoutParams().width=30;

        //findViewById(R.id.t1).requestLayout();
        //findViewById(R.id.t2).requestLayout();
        //findViewById(R.id.textView).requestLayout();


        //Bitmap for holding Image of layout

        //provide layout with its id in Xml

        LinearLayout linearLayout=findViewById(R.id.l1);

        //initialise layout_to_image object with its parent class and pass parameters as (<Current Activity>,<layout object>)


        //now call the main working function ;) and hold the returned image in bitmap
        linearLayout.setDrawingCacheEnabled(true);
        // this is the important code :)
        // Without it the view will have a dimension of 0,0 and the bitmap will be null
        Log.d("myapp",Integer.toString(linearLayout.getWidth()));

        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        linearLayout.layout(0, 0, linearLayout.getMeasuredWidth(), linearLayout.getMeasuredHeight());

        linearLayout.buildDrawingCache(true);
        Bitmap bitmap=null;
        bitmap= Bitmap.createBitmap(linearLayout.getDrawingCache());
        linearLayout.setDrawingCacheEnabled(false); // clear drawing cache


      //  Bitmap bitmap=layout_to_image.convert_layout();

        try {
            //File file= new File("C:\\Users\\SYED MD HASNAIN JAH\\Register\\app\\src\\main\\res\\drawable\\african_parrot.jpg");
            File file=new File(getApplicationContext().getExternalCacheDir(), File.separator + "score.jpg");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");

            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            e.printStackTrace();
        }



    }








}
