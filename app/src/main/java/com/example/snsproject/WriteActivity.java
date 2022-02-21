package com.example.snsproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;


import com.example.snsproject.databinding.ActivityWriteBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {
    ActivityWriteBinding activityWriteBinding;
    DatabaseReference reference;
    FirebaseDatabase database;
    String etPostTitle;
    String postUid;
    String etPostContent;
    int ranUid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWriteBinding=ActivityWriteBinding.inflate(getLayoutInflater());

        setContentView(activityWriteBinding.getRoot());
        database = FirebaseDatabase.getInstance();
        reference=database.getReference("PostData");
        Random random = new Random();
         ranUid = random.nextInt(10000);
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
        ContentsCofiguration content = new ContentsCofiguration(etPostTitle, etPostContent,ranUid,0);

        reference.child("post").child("lastestTest").setValue(content);

    }
}

