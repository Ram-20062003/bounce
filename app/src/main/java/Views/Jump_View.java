package Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.jump.Leaderboard;
import com.example.jump.R;
import com.example.jump.TableRoomDatabase;
import com.example.jump.Table_Score;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Jump_View extends View {
    private static final String TAG = "Jump_View";
    Rect rect,rect_wall1,rect_wall2,rect_box,b_scores,b_exit;
    Paint paint,paint_wall1,paint_wall2,paint_ball,paint_text,paint_box,paint_score;
    int seconds,present=0,minutes,twice_check,cx=100,cy=100,rad=50,s_wall1=0,s_wall2=0,start=0,h1,h2,l1,l2,ran1,ran2;
    Timer timer_wall,timer_up,timer_down,timer_clock;
    String time;
    Bitmap b_restart,b_home;
    Typeface typeface,typeface1;
   public MediaPlayer mediaPlayer,mediaPlayer_lose;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Jump_View(Context context) {
        super(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Jump_View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Jump_View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Jump_View(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        rect=new Rect();
        rect_wall1=new Rect();
        rect_wall2=new Rect();
        paint=new Paint();
        paint_box=new Paint();
        paint_score=new Paint();
        typeface1=Typeface.create(typeface1,Typeface.BOLD);
        paint_score.setTypeface(typeface1);
        paint_score.setColor(Color.parseColor("#034eff"));
        paint_score.setTextSize(75);
        paint_box.setColor(Color.parseColor("#f7b0b0"));
        rect_box=new Rect();
        b_scores=new Rect();
        b_exit=new Rect();
        paint_wall1=new Paint();
        paint_wall2=new Paint();
        paint_ball=new Paint();
        paint_text=new Paint();
        paint_text.setColor(Color.parseColor("#ff4db8"));
        paint_text.setTextSize(100);
        paint_ball.setColor(Color.parseColor("#faff70"));
        paint.setColor(Color.parseColor("#70deff"));
        paint.setTextSize(100);
        typeface=getResources().getFont(R.font.press_start_2p);
        paint_text.setTypeface(typeface);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        Bitmap b_back=BitmapFactory.decodeResource(getResources(),R.drawable.rainbow);
        canvas.drawBitmap(b_back,0,0,null);
        b_home= BitmapFactory.decodeResource(getResources(),R.drawable.home);
        b_restart=BitmapFactory.decodeResource(getResources(),R.drawable.restart);
        rect.left=0;
        rect.top=2*getHeight()/3;
        rect.right=rect.left+getWidth();
        rect.bottom=rect.top+50;
        canvas.drawRect(rect,paint);
        time=String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        Log.d(TAG, "Time:"+time);
        if(start!=0)
            canvas.drawText(time,40*getWidth()/100,2*getHeight()/10,paint_text);

        if(start==0){
            cy=rect.top-rad;
            canvas.drawText("Tap above line to Play",30*getWidth()/100,9*getHeight()/10,paint_text);

        }
        if(start==2)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(getWidth()/6,getHeight()/5,5*getWidth()/6,60*getHeight()/100,15,15,paint_box);
            }
            else
            {
                canvas.drawRect(getWidth()/6,getHeight()/4,5*getWidth()/6,90*getHeight()/100,paint_box);

            }
            canvas.drawText("Score="+time,40*getWidth()/100,30*(getHeight()/6+5*getHeight()/6)/100,paint_score);
            canvas.drawRect(getWidth()/4,(getHeight()/6+5*getHeight()/6)/3,3*getWidth()/4,(getHeight()/6+5*getHeight()/6)/3+150,paint);
            canvas.drawText("VIEW LEADERBOARD",getWidth()/3,(getHeight()/6+5*getHeight()/6)/2-50,paint_score);
        }
        canvas.drawCircle(cx,cy,rad,paint_ball);
        if(s_wall1==0)
        {
            setcolor();
            Random random_check=new Random();
            twice_check=random_check.nextInt(10);
            l1=getWidth();
            l2=getWidth();
            Random random=new Random();
            ran1=(random.nextInt(3)+1)*100;
            s_wall1=1;
        }
        if(s_wall2==0)
        {
            setcolor();
            Random random=new Random();
            ran2=(random.nextInt(3)+1)*100;
            l2=getWidth()+ran2;
            s_wall2=1;
        }
        rect_wall1.left=l1;
        rect_wall1.right=rect_wall1.left+100;
        rect_wall1.bottom=2*getHeight()/3;
        rect_wall1.top=rect_wall1.bottom-ran1;
        canvas.drawRect(rect_wall1,paint_wall1);
        if(rect_wall1.right<0&&twice_check%3!=0)
            s_wall1=0;
        if(twice_check%3==0)
        {
            rect_wall2.left=l2;
            rect_wall2.right=rect_wall2.left+100;
            rect_wall2.bottom=2*getHeight()/3;
            rect_wall2.top=rect_wall2.bottom-ran2;
            canvas.drawRect(rect_wall2,paint_wall2);
            if(rect_wall2.right<0)
                s_wall2=0;
        }
        if(twice_check%3==0)
        {
            if(rect_wall1.right<0&&rect_wall2.right<0){
                s_wall2=0;
                s_wall1=0;
            }
        }
        canvas.drawBitmap(b_home,90*getWidth()/100,10,null);
    }

    private void setcolor() {
        int a;
        Random random=new Random();
        a=random.nextInt(4);
        switch (a)
        {
            case 0:
                paint_wall1.setColor(Color.parseColor("#a705f2"));
                paint_wall2.setColor(Color.parseColor("#d1d0c9"));
                break;
            case 1:
                paint_wall1.setColor(Color.parseColor("#ff0000"));
                paint_wall2.setColor(Color.parseColor("#fcca26"));

                break;
            case 2:
                paint_wall1.setColor(Color.parseColor("#f5dd7d"));
                paint_wall2.setColor(Color.parseColor("#fc7a56"));

                break;
            case 3:
                paint_wall1.setColor(Color.parseColor("#7aff99"));
                paint_wall2.setColor(Color.parseColor("#5b23f7"));

                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                float s_x=event.getX();
                float s_y=event.getY();
                if(s_x>=0&&s_x<=getWidth()&&s_y>=0&&s_y<=rect.bottom) {
                    if (start == 0) {
                        timer_wall = new Timer();
                        timer_wall.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                l1--;
                                l2--;
                                collisoncheck();
                                postInvalidate();
                            }
                        }, 0, 1);

                        timer_clock = new Timer();
                        timer_clock.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                seconds = seconds + 1;
                                if (seconds == 60) {
                                    minutes = minutes + 1;
                                    seconds = 0;
                                }
                                postInvalidate();

                            }
                        }, 0, 1000);
                        start = 1;
                    }
                    if (start == 1) {
                            soundplay();
                        if (cy == rect.top - rad) {
                            timer_up = new Timer();
                            timer_up.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    cy--;
                                    if (cy == 0) {
                                        if (timer_up != null)
                                            timer_up.cancel();
                                        timer_down = new Timer();
                                        timer_down.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                cy++;
                                                if (cy == rect.top - rad) {
                                                    timer_down.cancel();
                                                    timer_up.cancel();
                                                    postInvalidate();
                                                }
                                            }
                                        }, 0, 1);
                                    }
                                    postInvalidate();
                                }
                            }, 0, 1);
                        }
                    }
                }


                    float rx=event.getX();
                    float ry=event.getY();
                        if (rx >= 90*getWidth()/100 && rx <= getWidth() && ry >=10&&ry<=100)
                            System.exit(0);
                        postInvalidate();
                    if(start==2){
                        if(rx>=getWidth()/4&&rx<=3*getWidth()/4&&ry>=(getHeight()/6+5*getHeight()/6)/3&&ry<=(getHeight()/6+5*getHeight()/6)/3+150){
                            Intent intent=new Intent(getContext(), Leaderboard.class);
                            getContext().startActivity(intent);
                        }
                    }

        }

        return super.onTouchEvent(event);
    }

    private void soundplay() {

        mediaPlayer=MediaPlayer.create(getContext(),R.raw.jump);
            mediaPlayer.start();
    }

    private void collisoncheck() {
        if(cx+rad>=rect_wall1.left+5&&cx-rad<=rect_wall1.right-5)
        {
            if( cy-rad>=rect_wall1.top &&cy+rad<=rect_wall1.bottom) {
                Log.d(TAG, "collide: yes wall 1");
                timer_clock.cancel();
                timer_wall.cancel();
                timer_up.cancel();
                timer_down.cancel();
                start=2;

            }
            if(cy+rad==rect_wall1.top){
                Log.d(TAG, "collide: no wall 1");
                timer_clock.cancel();
                timer_wall.cancel();
                timer_up.cancel();
                timer_down.cancel();
                start=2;

            }
        }
        if(cx+rad>=rect_wall2.left+5&&cx-rad<=rect_wall2.right-5)
        {
            if( cy-rad>=rect_wall2.top &&cy+rad<=rect_wall2.bottom) {
                Log.d(TAG, "collide: yes wall 2");
                timer_clock.cancel();
                timer_wall.cancel();
                timer_up.cancel();
                timer_down.cancel();
                start=2;
            }
            if(cy+rad==rect_wall2.top){
                Log.d(TAG, "collide: no wall 2");
                timer_clock.cancel();
                timer_wall.cancel();
                timer_up.cancel();
                timer_down.cancel();
                start=2;
            }
        }
        if(start==2)
        {
            soundplay_lose();

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Table_Score> list=TableRoomDatabase.getInstance(getContext()).table_score_dao().get_row();
                    Log.d(TAG, "run: "+list.toString());
                    for(int i=0;i<list.size();i++)
                        if(time.equals(list.get(i).getScore()))
                            present=1;
                    if(present==0)
                    {
                        Table_Score table_score=new Table_Score(time);
                        insert_score insert_score=new insert_score();
                        insert_score.execute();
                    }

                }
            });
            thread.start();


        }
    }

    private void soundplay_lose() {
        mediaPlayer_lose=MediaPlayer.create(getContext(),R.raw.lose);
        mediaPlayer_lose.start();
    }

    class insert_score extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            Table_Score table_score=new Table_Score(time);
            TableRoomDatabase.getInstance(getContext()).table_score_dao().insert_row(table_score);
            return null;
        }
    }
}
