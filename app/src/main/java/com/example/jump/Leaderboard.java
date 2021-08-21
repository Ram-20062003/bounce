package com.example.jump;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard extends AppCompatActivity {
    private static final String TAG = "Leaderboard";
    List<String> leader_list;
    TextView t_1,t_2,t_3,t_4,t_5,t_6,t_7,t_8,t_9,t_10;
    Button b_play,b_exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        t_1=(TextView)findViewById(R.id.text1) ;
        t_2=(TextView)findViewById(R.id.text2);
        t_3=(TextView)findViewById(R.id.text3);
        t_4=(TextView)findViewById(R.id.text4);
        t_5=(TextView)findViewById(R.id.text5);
        t_6=(TextView)findViewById(R.id.text6);
        t_7=(TextView)findViewById(R.id.text7);
        t_8=(TextView)findViewById(R.id.text8);
        t_9=(TextView)findViewById(R.id.text9);
        t_10=(TextView)findViewById(R.id.text10);
        b_play=(Button)findViewById(R.id.button);
        b_exit=(Button) findViewById(R.id.button2);
        b_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Leaderboard.this,MainActivity.class);
                startActivity(intent);
            }
        });
        b_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        TextView[] textView=new TextView[]{t_1,t_2,t_3,t_4,t_5,t_6,t_7,t_8,t_9,t_10};
        leader_list=new ArrayList<>();
        Thread thread1=new Thread(new Runnable() {
            String t;
            int i;
            @Override
            public void run() {
                List<Table_Score> list=TableRoomDatabase.getInstance(Leaderboard.this).table_score_dao().get_row();
                Log.d(TAG, "run: "+list.toString());
                int t=list.size();
                if(t>=10)
                    t=10;
                for( i=0;i<t;i++)
                {
                    textView[i].setText(list.get(i).getScore());

                }
                if(t<10)
                {
                    for(int j=i;j<10;j++)
                        textView[j].setText("-");

                }
            }
        });
        thread1.start();

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Leaderboard.this,Home_activity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
