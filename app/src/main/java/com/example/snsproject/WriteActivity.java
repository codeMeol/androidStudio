package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;


import com.example.snsproject.databinding.ActivityWriteBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class WriteActivity extends AppCompatActivity {
    ActivityWriteBinding activityWriteBinding;
    DatabaseReference reference;
    FirebaseDatabase database;
    String etPostTitle;
    int titleUid;
    ArrayList<String> myList;
    String etPostContent;
    int ranUid, postCount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWriteBinding=ActivityWriteBinding.inflate(getLayoutInflater());

        setContentView(activityWriteBinding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("PostData");
        myList=new ArrayList<String>();
        activityWriteBinding.btnPostSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPostTitle = activityWriteBinding.etPostTitle.getText().toString();
                etPostContent = activityWriteBinding.etPostContents.getText().toString();


                if(etPostTitle.length()>2||etPostContent.length()>10)//제목이 2글자 이상, 내용이 10글자 이상이여야함)
                {

                   writeNewContent(etPostTitle,etPostContent);

                    Intent intent = new Intent(getApplicationContext(),PostActivity.class);
                    startActivity(intent);
                }
            }
        });



    }
    public void writeNewContent(String etPostTitle, String etPostContent) {
             random();//randomUid create



        reference.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()>=1){
                  postCount = (int)snapshot.getChildrenCount();
                for(int ji=1;ji<= postCount;ji++) {
                   reference=  database.getReference("PostData");

                    int finalJi = ji;
                    reference.child("post").child("lastestTest"+ji).child("ranUid").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.e(TAG, "스냅샷의 값: " + snapshot.getValue());
                            ;
                            myList.add(String.valueOf(snapshot.getValue()));
                            Log.e(TAG, "mylist.size "+myList.size() );
                             if(finalJi ==postCount){
                                 onFirebase();
                             }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }else {
                    ContentsCofiguration content = new ContentsCofiguration(etPostTitle, etPostContent, ranUid, 0);
                    reference.child("post").child("lastestTest" + (postCount + 1)).setValue(content);
                }

        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
    public void onFirebase(){
        Log.e(TAG, "마이리스트의 값: "+"   "+myList.size() );
        ContentsCofiguration content = new ContentsCofiguration(etPostTitle, etPostContent,ranUid,0);

        for (int i = 0; i < postCount; i++) {

            if(myList.size()>=1) {
                if (String.valueOf(ranUid).equals(myList.get(i))) {

                    //고유 Uid가 같을 경우 UID 값 재부여
                        random();
                        i=0;//초기화
                } else {

                    // 고유 Uid가 다를 경우 lastest+n번째 값으로 새로 하나 만듬
                    reference.child("post").child("lastestTest" +(postCount+ 1)).setValue(content);
                }
            }
        }
    }

    public void random(){
        Random random = new Random();
        ranUid = random.nextInt(10000);
    }
}

