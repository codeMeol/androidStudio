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
    String userEmail,userUid;
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

                //리스트뷰중에 한개 클릭했을 때 그거에 리스너를 달아서 토스트 메시지 실행
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(),PostViewActivity.class);
                        String title = myPostAdapter.getItem(position).getTitle();
                        intent.putExtra("title",title);
                        intent.putExtra("position",myPostAdapter.getItem(position).toString());
                        startActivity(intent);

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


        userDatabase= FirebaseDatabase.getInstance().getReference("Users");

        userEmail=getIntent().getStringExtra("userEmail");

        userDatabase.child(userEmail).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String stSnap=snapshot.getValue().toString();
                TextView activityPostUserNickname = (TextView)findViewById(R.id.postActivityUserNicknameTv);
                activityPostUserNickname.setText(stSnap+"님 안녕하세요");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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


