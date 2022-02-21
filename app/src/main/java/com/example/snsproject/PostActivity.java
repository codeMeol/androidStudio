package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class PostActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    ArrayList<ContentsCofiguration> contentsCofigList;
    Button btnCreatePost;
    ContentsCofiguration contentsCofiguration;


    //   String postUid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        mDatabase = FirebaseDatabase.getInstance().getReference("PostData");
        contentsCofigList = new ArrayList<ContentsCofiguration>();//PostData=> 데이터 클래스
        mDatabase.child("post").child("lastestTest").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange:" + snapshot.getValue());

                    contentsCofiguration = snapshot.getValue(ContentsCofiguration.class);

                    String content=contentsCofiguration.getContent();
                    String title  =contentsCofiguration.getTitle();
                    int suggetion =contentsCofiguration.getSuggestion();
                    int ranUid    =contentsCofiguration.getRanUid();
                Log.e(TAG, "onDataChange: "+content+title+suggetion+ranUid );
                   contentsCofigList.add(new ContentsCofiguration(title,content,ranUid,suggetion));

                ListView listView = (ListView) findViewById(R.id.post_listView); //리스트뷰 연결


                //adapter 새로 생성
                final PostAdapter myPostAdapter = new PostAdapter(PostActivity.this, contentsCofigList);

                //리스트뷰에 어댑터 장착
                listView.setAdapter(myPostAdapter);

                //리스트뷰중에 한개 클릭했을 때 그거에 리스너를 달아서 토스트 메시지 실행
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Toast.makeText(getApplicationContext(),
                                myPostAdapter.getItem(position).getTitle(),
                                Toast.LENGTH_LONG).show();


                    }
                });
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
        //리스트뷰 추가한거 구현하는 메소드 생성 및 실행

    }

}
