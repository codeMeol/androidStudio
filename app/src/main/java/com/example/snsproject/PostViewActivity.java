package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PostViewActivity extends AppCompatActivity {

    private DatabaseReference mDatabase,userDatabase;
    ContentsCofiguration contentsCofiguration;
    String title,position,contents,userEmail,lastestpost,suggestPlusMinusStr;
    final String TAG="PostViewActivity";
    int suggestion,ranUid;
    boolean suggestBool ;
    Button postViewApproveBtn, postViewDisapproveBtn;
    TextView postViewSuggestionTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        TextView tvPostTitle = (TextView)findViewById(R.id.tvPostTitle);
        TextView tvPostContens = (TextView)findViewById(R.id.tvPostContents);
        postViewApproveBtn = (Button)findViewById(R.id.postViewApproveBtn);
        postViewDisapproveBtn = (Button)findViewById(R.id.postViewDisapproveBtn);
        postViewSuggestionTv = (TextView)findViewById(R.id.postViewSuggestionTv);


        mDatabase = FirebaseDatabase.getInstance().getReference("PostData");//db 활성화
        //인텐트 값 받기
        userEmail = getIntent().getStringExtra("userEmail");//@랑.지운 이메일값 받아오기
        position = getIntent().getStringExtra("position");//인텐트값으로 포지션 받아왔음
        lastestpost="lastestTest"+position;
        //포지션 값에 따라서 리스너 달아주기

        mDatabase.child("post").child(lastestpost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //DB는 키와 밸류값으로 저장되어 있다. lastestTest+i는 키가 lastestTest+i고 밸류는 4개이다 그래서 클래스 형태로 밸류값을 수신해준거다.
                ContentsCofiguration ctf=snapshot.getValue(ContentsCofiguration.class);
                //ContentsConfiguration 클래스의 리턴값에 따라 4가지 값 받아오기
                contents=ctf.getContent();  //내용
                title=ctf.getTitle();   //제목
                ranUid=ctf.getRanUid();  //포스트 고유 번호
                suggestion=ctf.getSuggestion(); //추천 수
                tvPostTitle.setText(title);  //제목 달기
                tvPostContens.setText(""+contents); //내용 달기
                postViewSuggestionTv.setText(suggestion+"");

                userDatabase=FirebaseDatabase.getInstance().getReference("Users");
                Query myQuery=userDatabase.child(userEmail).child("sug").child(ranUid+"");

                myQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e(TAG, "현 경로 스냅샷값 "+snapshot.getKey());
                        //이 게시물에 추천또는 비추천을 한 유저인지 아닌지 파악
                        if(snapshot.getKey()==null){//키값이 없을 경우
                            suggestBool=true;
                             suggestBtn();

                        }
                        else{//키값이 있을 경우 밸류 값을 받는다
                            suggestPlusMinusStr= (String) snapshot.getValue();
                            suggestBtn();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });


        }
        public void suggestBtn(){

        Log.e(TAG, "suggestBool  들어왔습니다." );

        postViewApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (suggestBool||suggestPlusMinusStr==null) {
                    //추천을 안했다면
                    userDatabase.child(userEmail).child("sug").child(ranUid + "").setValue("approve");//userDB에 추천한 포스트uid 등록
                    suggestion += 1;//추천 값 1 올려주고
                    mDatabase.child("post").child(lastestpost).child("suggestion").setValue(suggestion);//포스트 경로의 추천값 등록
                    postViewSuggestionTv.setText("" + suggestion);//추천 값을 등록해준다
                    postViewApproveBtn.setBackgroundResource(R.mipmap.pressed_approve_icon);
                    //추천을 했으니 버튼 비활성화
                }
                else if(suggestPlusMinusStr.equals("approve")){
                    Log.e(TAG, "이미 추천을 누르셨습니다." );
                }
                else if(suggestPlusMinusStr.equals("disapprove")){
                    userDatabase.child(userEmail).child("sug").child(ranUid + "").removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            Log.e(TAG, "지워졌습니다." );
                            suggestion += 1;//추천 값 1 올려주고
                            mDatabase.child("post").child(lastestpost).child("suggestion").setValue(suggestion);//포스트 경로의 추천값 등록
                            postViewSuggestionTv.setText("" + suggestion);//추천 값을 등록해준다
                            postViewDisapproveBtn.setBackgroundResource(R.mipmap.disapprove_icon);
                        }
                    });
                 //버튼 닫아줍니다.
                }

            }
        });

            postViewDisapproveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (suggestBool||suggestPlusMinusStr==null) {
                        //추천을 안했다면
                        userDatabase.child(userEmail).child("sug").child(ranUid + "").setValue("disapprove");//userDB에 추천한 포스트uid 등록
                        suggestion -= 1;//추천 값 1 올려주고
                        mDatabase.child("post").child(lastestpost).child("suggestion").setValue(suggestion);//포스트 경로의 추천값 등록
                        postViewSuggestionTv.setText("" + suggestion);//추천 값을 등록해준다
                        postViewDisapproveBtn.setBackgroundResource(R.mipmap.pressed_disapprove_icon);
                        //추천을 했으니 버튼 비활성화
                    }
                    else if(suggestPlusMinusStr.equals("disapprove")){
                        Log.e(TAG, "이미 추천을 누르셨습니다." );
                    }
                    else if(suggestPlusMinusStr.equals("approve")){
                        userDatabase.child(userEmail).child("sug").child(ranUid + "").removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                suggestion -= 1;//추천 값 1 올려주고
                                mDatabase.child("post").child(lastestpost).child("suggestion").setValue(suggestion);
                                postViewSuggestionTv.setText("" + suggestion);//추천 값을 등록해준다
                                postViewApproveBtn.setBackgroundResource(R.mipmap.approve_icon);
                                Log.e(TAG, "지워졌습니다." );

                            }
                        });
                        //버튼 닫아줍니다.
                    }

                } });
        }

    }


