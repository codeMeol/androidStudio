package kr.ac.poly.smart_coin_saver;


import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;

import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static kr.ac.poly.smart_coin_saver.R.layout.activity_main;

public class MainActivity extends ActionBarista  {
    Glide t=null;

    String StrGoal=null;
    Animation translate;
    CreateText CTgoal= new CreateText(this);
    TextView tv ;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        translate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
            tv= (TextView) findViewById(R.id.txtView1);
             tv.startAnimation(translate);
        final ImageView ivSetOpt = (ImageView)findViewById(R.id.imgBtnSetOption);
        final ImageView ivGoal = (ImageView)findViewById(R.id.buttonMini);


        //iv.setImageResource(R.drawable.img);

        ivSetOpt.setImageResource(R.drawable.gear);

// BluetoothService 클래스 생성

        //블루투스 부분



        //목표 확인 부분


        //톱니바퀴 부분
        ivSetOpt.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view,MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {//눌렀을 때
                    GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivSetOpt);
                    Glide.with(getApplicationContext()).load(R.drawable.gear).into(imageViewTarget);
                }
                else if(motionEvent.getAction()==MotionEvent.ACTION_UP){//눌렀다가 뗐을 때

                }
                return false; }
        });
        //목표 설정
        ivGoal.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivGoal);
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {//눌렀을 때
                    Log.e("눌린건", "" + MotionEvent.ACTION_DOWN);

                    t.with(getApplicationContext()).load(R.raw.gold).into(imageViewTarget);

                    Intent btnGoalIntent = new Intent(MainActivity.this, AllMoney.class);
                    MainActivity.this.startActivity(btnGoalIntent);
                }
                return false;
            }
        });


        taro();
        final VideoView video=(VideoView)findViewById(R.id.videoView);
        double t=1.3;
        video.setScaleX((float) t);
        Uri videoURi = Uri.parse("android.resource://" + getPackageName() + "/raw/ggari");
        video.setVideoURI(videoURi);
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            public void onCompletion(MediaPlayer mp){
                video.start();
            }
        });
        video.start();


    }

    public void editButton(View v) {
        ImageButton imb = (ImageButton) findViewById(R.id.imageButton2);
        EditText et = (EditText) findViewById(R.id.Edittxt);
        et.setBackgroundColor(Color.TRANSPARENT);
        onStart();


        if (et.isEnabled()) {
            et.setEnabled(false);


            //에니메이션 동작
            tv.setText(et.getText().toString());
            tv.startAnimation(translate);
        } else
            et.setEnabled(true);
    }

    public String getCalender(int num){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -num);  // 오늘 날짜에서 하루를 뺌.

        String date = sdf.format(calendar.getTime());

        return date;
    }
    public void sunOOn(){
        TextView TextView1 = (TextView) findViewById(R.id.txtView1);
        EditText EditText1 = (EditText) findViewById(R.id.Edittxt);

        TextView dayLater0=(TextView)findViewById(R.id.dayLater0);
        TextView dayLater1=(TextView)findViewById(R.id.dayLater1);
        TextView dayLater2=(TextView)findViewById(R.id.dayLater2);
        TextView dayLater3=(TextView)findViewById(R.id.dayLater3);

        /////////////오늘날짜 구하기//////////////////
        dayLater0.setText(getCalender(0));
        dayLater1.setText(getCalender(1));
        dayLater2.setText(getCalender(2));
        dayLater3.setText(getCalender(3));
    }

    public void taro(){
        TextView txtSetTextView = (TextView)findViewById(R.id.txtSetViewMoneyToday);
        TextView TextRight = (TextView) findViewById(R.id.txtRight);
        Intent intent=getIntent();
        String t=new String("TextView");
        String name=intent.getStringExtra("injavalue");
        TextRight.setText(name);
        if(name!=null) {
            goal = Integer.parseInt(name);
            ProgressBar PB = (ProgressBar) findViewById(R.id.progressBar);
            PB.setMax(goal);
            PB.setProgress(5000);
            sunOOn();
        }



    }




}