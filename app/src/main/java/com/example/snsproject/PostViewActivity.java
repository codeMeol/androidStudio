package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostViewActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ContentsCofiguration contentsCofiguration;
    String title;
    String position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        TextView tvPostTitle = (TextView)findViewById(R.id.tvPostTitle);
        TextView tvPostContens = (TextView)findViewById(R.id.tvPostContents);
        mDatabase = FirebaseDatabase.getInstance().getReference("PostData");
        title = getIntent().getStringExtra("title");
        position = getIntent().getStringExtra("position");
        Log.e("확인", "onCreate: "+position);//PostActivity에서 클릭한 게시글의 번호? 를 가져오려고 노력중... 그 외엔 딱히 로직없음

//        mDatabase.child("post").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                lasnum=(int)snapshot.getChildrenCount();
//                for(int i=1;i<=lasnum;i++){
//                    if(title == snapshot.getValue())
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}
