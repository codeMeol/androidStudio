package com.example.snsproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class PostActivity extends AppCompatActivity {

    ArrayList<PostData> postDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        this.InitializePostData();//리스트뷰 추가한거 구현하는 메소드 생성 및 실행

        ListView listView = (ListView)findViewById(R.id.post_listView); //리스트뷰 연결

        //adapter 새로 생성
        final PostAdapter myPostAdapter = new PostAdapter(PostActivity.this,postDataList);

        //리스트뷰에 어댑터 장착
        listView.setAdapter(myPostAdapter);

        //리스트뷰중에 한개 클릭했을 때 그거에 리스너를 달아서 토스트 메시지 실행
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myPostAdapter.getItem(position).getTitle(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void InitializePostData() {

        postDataList = new ArrayList<PostData>();//PostData=> 데이터 클래스

        postDataList.add(new PostData(10,"이걸 만드네요 개쩐다"));

        postDataList.add(new PostData(5,"그러게요"));

        postDataList.add(new PostData(3,"근데 이걸 데이터베이스에 어케 연결함"));


    }


}
