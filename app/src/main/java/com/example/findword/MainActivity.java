package com.example.findword;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.findword.data.MyDbHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import ticker.views.com.ticker.widgets.circular.timer.callbacks.CircularViewCallback;
import ticker.views.com.ticker.widgets.circular.timer.view.CircularView;


public class MainActivity extends AppCompatActivity {
     TextView point;
      TextView word;
    MyDbHandler db;
    SharedPreferences.Editor editor;
    CircularView.OptionsBuilder builderWithTimer,builderWithTimer1;
    SharedPreferences highest;
CircularView circularViewWithTimer;
    static int points=0;
 static ArrayList<Integer> grid;
   static ArrayList<Integer> id;
   static  ArrayList<String> words;

    static Random random;

    static HashMap<Integer,String> hashMap;
  //  private long ;


    ArrayList<String> make(ArrayList<Integer> grid)
    {
        ArrayList<Integer> result=new ArrayList<>();
        result.addAll(grid);
        result.addAll(grid);
        for(int i=0;i<36;i++)
        {
            int r1=random.nextInt(36);
            int r2=random.nextInt(36);
            if(r1==r2)r2=(r2+1)%72;
            int t=result.get(r1); result.set(r1,result.get(r2)); result.set(r2,t);
        }
        grid.clear();

        for(int i=20;i<56;i++)grid.add(result.get(i));
        words = findWord(grid);
        Log.d("myapp",Integer.toString(grid.size()));

        for(int i=0;i<36;i++)
        {
            ImageView img=findViewById(id.get(i));
            img.setImageResource(grid.get(i));
        }

        int r=random.nextInt(2);
        Log.d("myapp",Integer.toString(r));
        if(r==1)
            word.setText(giveWord(hashMap));

        else
            word.setText(  words.get(  random.nextInt(words.size())  )  );

        circularViewWithTimer.startTimer();

                findViewById(R.id.yes).setAlpha(1);
                findViewById(R.id.yes).setEnabled(true);

                findViewById(R.id.no).setAlpha(1);
                findViewById(R.id.no).setEnabled(true);

                findViewById(R.id.score).setAlpha(0);
        findViewById(R.id.score).setEnabled(false);



        return words;
    }

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        point = findViewById(R.id.point1);
        word = findViewById(R.id.word);
        hashMap = new HashMap<>();
        random = new Random();
        highest = getSharedPreferences("myref", Context.MODE_PRIVATE);
        findViewById(R.id.yes).setAlpha(0);
        findViewById(R.id.no).setAlpha(0);

       // deleteDatabase("points_db");

         editor = highest.edit();

        if(highest==null){ editor.putInt("points", 0);
            editor.putString("name", "Player 1");
            editor.commit();
        }
        if(highest.getString("name","Player 1").equals("Player 1")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Enter Player Name");

// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   String m_Text = input.getText().toString();
                   editor.putString("name",m_Text);
                   editor.commit();
                }
            });
            builder.show();

        }
        //Toast.makeText(MainActivity.this,Integer.toString(highest.getInt("points",0)),Toast.LENGTH_SHORT).show();

        grid = new ArrayList<>(Arrays.asList(R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f,
                R.drawable.g, R.drawable.h, R.drawable.i, R.drawable.j, R.drawable.k, R.drawable.l,
                R.drawable.m, R.drawable.n, R.drawable.o, R.drawable.p, R.drawable.q, R.drawable.r,
                R.drawable.s, R.drawable.t, R.drawable.u, R.drawable.vi, R.drawable.w, R.drawable.x,
                R.drawable.y, R.drawable.z, R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four,
                R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.hash));

        id = new ArrayList<>(Arrays.asList(R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6,
                R.id.img7, R.id.img8, R.id.img9, R.id.img10, R.id.img11, R.id.img12,
                R.id.img13, R.id.img14, R.id.img15, R.id.img16, R.id.img17, R.id.img18,
                R.id.img19, R.id.img20, R.id.img21, R.id.img22, R.id.img23, R.id.img24,
                R.id.img25, R.id.img26, R.id.img27, R.id.img28, R.id.img29, R.id.img30,
                R.id.img31, R.id.img32, R.id.img33, R.id.img34, R.id.img35, R.id.img36));

        points = 0;
        point.setText("Points 0");


        for (int i = 0; i < 26; i++) {
            hashMap.put(grid.get(i), (char) (i + 65) + "");
        }
        for (int i = 1; i <= 9; i++) {
            hashMap.put(grid.get(25 + i), i + "");
        }
        hashMap.put(grid.get(35), "#");


        db=new MyDbHandler(MainActivity.this);

        ArrayList<Points> allPoints = db.getAllPoints();
        for (int i=0;i<allPoints.size();i++)
        {
            Log.d("myapp", "\nId: " + allPoints.get(i).getId() + "\n" +
                    "Points: " + allPoints.get(i).getPoints() + "\n" +
                    "Time: " + allPoints.get(i).getTime() + "\n");
        }

        circularViewWithTimer  = findViewById(R.id.circular_view);
        builderWithTimer =
                new CircularView.OptionsBuilder()
                        .shouldDisplayText(true)
                        .setCounterInSeconds(30)
                        .setCircularViewCallback(new CircularViewCallback() {

                            @Override
                            public void onTimerFinish() {
                                MediaPlayer player=MediaPlayer.create(MainActivity.this,R.raw.wrong);
                                player.start();

                                // Will be called if times up of countdown timer
                            //   Toast.makeText(MainActivity.this, "CircularCallback: Timer Finished ", Toast.LENGTH_SHORT).show();
                              boolean b=false;
                                FloatingActionButton fab = findViewById(R.id.fab);
                                fab.show();
                                circularViewWithTimer.stopTimer();
                                findViewById(R.id.yes).setAlpha(0);
                                findViewById(R.id.yes).setEnabled(false);

                                findViewById(R.id.no).setAlpha(0);
                                findViewById(R.id.no).setEnabled(false);

                                findViewById(R.id.score).setAlpha(1);
                                findViewById(R.id.score).setEnabled(true);
                                if(points>0) {

                                    Date d = new Date();
                                    Points p = new Points();
                                    p.setPoints(points);
                                    p.setTime(d.toString());
                                    db = new MyDbHandler(MainActivity.this);
                                    db.addPoints(p);
                                    Log.d("myapp", "Point was saved");
                                    b=congratulate();
                                    //   Toast.makeText(MainActivity.this,"Your point was saved",Toast.LENGTH_SHORT).show();
                                }

                                 if(!b)result();

                            }

                            @Override
                            public void onTimerCancelled() {


                                // Will be called if stopTimer is called
                               // Toast.makeText(MainActivity.this, "CircularCallback: Timer Cancelled ", Toast.LENGTH_SHORT).show();
                            }
                        });

        circularViewWithTimer.setOptions(builderWithTimer);


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Started", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                 //   words = make(grid);
                    circularViewWithTimer.setOptions(builderWithTimer);

                    words = make(grid);
                    FloatingActionButton fab = findViewById(R.id.fab);
                    fab.hide();

                  //  points = 0;

                }
            });
        }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings)
            {
                circularViewWithTimer.pauseTimer();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("1. Find whether the given Pattern exists in the Table or not.\n" +
                        "2. You can traverse left,right,up,down from any cell.\n" +
                        "3. You can start from any cell.\n" +
                        "4. You have 30 secs to find every pattern.\n" +
                        "5. Best of luck and enjoy the game.");
                alertDialogBuilder.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                circularViewWithTimer.resumeTimer();
                                //      Toast.makeText(MainActivity.this,"You clicked yes  button", Toast.LENGTH_LONG).show();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


                return true;
            }
            else if(id==R.id.name)
            {
                circularViewWithTimer.pauseTimer();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter Player Name");

// Set up the input
                final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String m_Text = input.getText().toString();
                       if(m_Text==null || m_Text.length()==0){Toast.makeText(MainActivity.this,"Invalid Name",Toast.LENGTH_SHORT).show();}
                      else {
                           editor.putString("name", m_Text);
                           editor.commit();
                           Toast.makeText(MainActivity.this,"Player Name is set",Toast.LENGTH_SHORT).show();
                       }
                        circularViewWithTimer.resumeTimer();

                    }
                });


                builder.show();
            }

            return super.onOptionsItemSelected(item);
        }

    static String giveWord(HashMap<Integer,String> hashMap)
    {
        String res="";
        ArrayList<String> s=new ArrayList<>();
        for(int i=0;i<36;i++)s.add(hashMap.get(grid.get(i)));

        for(int i=0;i<20;i++){
            int r1=random.nextInt(36);
            int r2=random.nextInt(36);
            if(r1==r2)r2=(r2+1)%36 ;
            String t=s.get(r1); s.set(r1,s.get(r2)); s.set(r2,t);
        }
        for(int i=0;i<8;i++)res+=s.get(i);
        return res;
    }

    static ArrayList<String> findWord(ArrayList<Integer> grid)
    {
        String d[][]=new String[6][6];
          for(int i=0;i<36;i++)
          {
                  int x=i/6;
                  int y=i%6;
                  d[x][y]=hashMap.get(grid.get(i));
          }


        ArrayList<String> res=new ArrayList<>();
        String s;
        int vis[][]={{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};

        for(int i=0;i<6;i++)
        {
            for(int j=0;j<6;j++){
                                    for(int x=0;x<6;x++){for(int y=0;y<6;y++)vis[x][y]=0;}
                                    s="";
                dfs(d,i,j,vis,s,res);
            }
        }
       // for(int i=0;i<res.size();i++) Log.d("myapp",Integer.toString(res.size()));
        return res;

    }

static void dfs(String d[][],int i,int j,int vis[][],String s,ArrayList<String> res)
{
    if(i<0 || j<0 || i>=6 || j>=6)return;
    if(vis[i][j]==1)return;
    vis[i][j]=1;
    s+=d[i][j];
    if(s.length()==8){res.add(s);return;}
    dfs(d,i+1,j,vis,s,res);
    dfs(d,i-1,j,vis,s,res);
    dfs(d,i,j+1,vis,s,res);
    dfs(d,i,j-1,vis,s,res);
    vis[i][j]=0;
}


public  void yes(View v)
{

    TextView point=findViewById(R.id.point1);
   TextView word=findViewById(R.id.word);
    String w=word.getText().toString();
    if(words.indexOf(w)!=-1) {
        MediaPlayer player=MediaPlayer.create(MainActivity.this,R.raw.correct);
        player.start();
        points++;
        point.setText("Points " + points);
        circularViewWithTimer.stopTimer();
        circularViewWithTimer.setOptions(builderWithTimer);
        words=make(grid);


    }
    else
    {
        MediaPlayer player=MediaPlayer.create(MainActivity.this,R.raw.wrong);
        player.start();
        boolean b=false;

        findViewById(R.id.yes).setAlpha(0);
        findViewById(R.id.yes).setEnabled(false);

        findViewById(R.id.no).setAlpha(0);
        findViewById(R.id.no).setEnabled(false);

        findViewById(R.id.score).setAlpha(1);
        findViewById(R.id.score).setEnabled(true);

        circularViewWithTimer.stopTimer();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.show();
        if(points>0) {
            Date d = new Date();
            Points p = new Points();
            p.setPoints(points);
            p.setTime(d.toString());
            db = new MyDbHandler(MainActivity.this);
            db.addPoints(p);
            Log.d("myapp", "Point was saved");
            Toast.makeText(MainActivity.this,"Your Score was saved",Toast.LENGTH_SHORT).show();
            b=congratulate();
        }

        if(!b)result();

        //words=make(grid);
      //  circularViewWithTimer.setOptions(builderWithTimer);


    }
}
    public  void no(View v)
    {
        TextView point=findViewById(R.id.point1);
        TextView word=findViewById(R.id.word);
        String w=word.getText().toString();
        if(words.indexOf(w)==-1) {
            MediaPlayer player=MediaPlayer.create(MainActivity.this,R.raw.correct);
            player.start();
            points++;
            point.setText("Points " + points);
            circularViewWithTimer.stopTimer();
            circularViewWithTimer.setOptions(builderWithTimer);
            words=make(grid);
        }
        else
        {
            MediaPlayer player=MediaPlayer.create(MainActivity.this,R.raw.wrong);
            player.start();
            boolean b=false;
            findViewById(R.id.yes).setAlpha(0);
            findViewById(R.id.yes).setEnabled(false);

            findViewById(R.id.no).setAlpha(0);
            findViewById(R.id.no).setEnabled(false);

            findViewById(R.id.score).setAlpha(1);
            findViewById(R.id.score).setEnabled(true);
            circularViewWithTimer.stopTimer();
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.show();
            if(points>0) {
                Date d = new Date();
                Points p = new Points();
                p.setPoints(points);
                p.setTime(d.toString());
                db = new MyDbHandler(MainActivity.this);
                db.addPoints(p);
                Log.d("myapp", "Point was saved");
                Toast.makeText(MainActivity.this,"Your Score was saved",Toast.LENGTH_LONG).show();
                b= congratulate();

            }

           if(!b)result();


        }
    }


    public void  onStop() {
        super.onStop();
        if (points > 0) {
//            congratulate();
            Date d = new Date();
            Points p = new Points();
            p.setPoints(points);
            p.setTime(d.toString());
            db = new MyDbHandler(MainActivity.this);
            db.addPoints(p);
            Log.d("myapp", "Point was saved");
            Toast.makeText(MainActivity.this, "Your Score was saved", Toast.LENGTH_LONG).show();


        }
    }
public  void onDestroy()
{
    super.onDestroy();
    db.close();

}



    public boolean congratulate(){
        if(highest.getInt("points",0)<points) {
            Toast.makeText(MainActivity.this,Integer.toString(highest.getInt("points",0)),Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = highest.edit();
            editor.putInt("points", points);
            editor.commit();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Congratulations!!! This is your highest score so far");
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            result();
                            //      Toast.makeText(MainActivity.this,"You clicked yes  button", Toast.LENGTH_LONG).show();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return true;

        }
        return false;
}

public static String MSG="com.example.findword.RESULT";
public void result()
{
    Intent i=new Intent(MainActivity.this,ResultFirstActivity.class);
    i.putExtra(MSG,Integer.toString(points));
    points=0;
    point.setText("Points 0");
    startActivity(i);
}

    public void score(View v)
    {
        Intent i=new Intent(MainActivity.this,ScoreActivity.class);
        startActivity(i);
    }


}


