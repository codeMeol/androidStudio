package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    private DatabaseReference mDatabase,userDatabase;
    ArrayList<ContentsCofiguration> contentsCofigList;
    Button btnCreatePost;
    ContentsCofiguration contentsCofiguration;
    int lasnum;
    PostAdapter myPostAdapter;
    String userEmail,userAdmin;
    //   String postUid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        TextView postActivityUserLogoutTv= (TextView)findViewById(R.id.postActivityUserLogout);
        ListView listView = (ListView) findViewById(R.id.post_listView);
        mDatabase = FirebaseDatabase.getInstance().getReference("PostData");
        contentsCofigList = new ArrayList<ContentsCofiguration>();//PostData=> 데이터 클래스


        //DB PostData하위post하위에 몇개나 게시물이 있는지 확인하는 부분

        mDatabase.child("post").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lasnum=(int)snapshot.getChildrenCount();//getChildrencount는 long타입이라서 int형태로 캐스팅 해줬음
                contentsCofigList.clear();
                for(int i=1;i<=lasnum;i++){
                    int finalI = i;
                    mDatabase.child("post").child("lastestTest"+i).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            contentsCofiguration = snapshot.getValue(ContentsCofiguration.class);

                            String content = contentsCofiguration.getContent();
                            String title = contentsCofiguration.getTitle();
                            int suggetion = contentsCofiguration.getSuggestion();
                            int ranUid = contentsCofiguration.getRanUid();

                            contentsCofigList.add(new ContentsCofiguration(title, content, ranUid, suggetion));

                            if(finalI ==lasnum){
                               myPostAdapter = new PostAdapter(PostActivity.this, contentsCofigList);
                                listView.setAdapter(myPostAdapter);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //포스트 들어가기
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(),PostViewActivity.class);
                        String title = myPostAdapter.getItem(position).getTitle();
                        intent.putExtra("userEmail",userEmail);
                        intent.putExtra("title",title);
                        intent.putExtra("position",String.valueOf(position+1));
                        startActivity(intent);

                    }
                });
        //새포스트 만들기
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });

        //우측 상단에 사용자 이름 띄우기
        //일반 사용자
        userDatabase= FirebaseDatabase.getInstance().getReference("Users");
        userAdmin=getIntent().getStringExtra("userAdmin");
        userEmail=getIntent().getStringExtra("userEmail");
        if(!(userEmail.equals("admin"))) {
            Log.e(TAG, "onCreate: " + userEmail);
            userDatabase.child(userEmail).child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String stSnap = snapshot.getValue().toString();
                    TextView activityPostUserNickname = (TextView) findViewById(R.id.postActivityUserNicknameTv);
                    activityPostUserNickname.setText(stSnap + "님 안녕하세요");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        else {
             //admin사용자
            TextView activityPostUserNickname = (TextView) findViewById(R.id.postActivityUserNicknameTv);
            activityPostUserNickname.setText("admin" + "님 안녕하세요");
            }

            //로그아웃 리스너
            postActivityUserLogoutTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });


            }



    }


