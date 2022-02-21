package com.example.snsproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ContentsCofiguration> postData;


    public PostAdapter(Context context, ArrayList<ContentsCofiguration> data) {
        mContext = context;
        postData = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //모르겠음
    @Override
    //자식뷰의 개수를 리턴하는 메소드
    public int getCount() {
        return postData.size();
    }
    //어댑터 뷰의 자식뷰가 n개라면 , 어댑터 객체가 갖는 항목의 개수 역시 n개입니다 getitem 메소드는 항목들 중 하나를 리턴합니다.
    //여기에서 항목은 자식 뷰의 내용을 갖는 객체입니다.

    @Override
    public ContentsCofiguration getItem(int position) {
        return postData.get(position);
    }
    //모르겠음
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View conventView, ViewGroup parent) {

        View view = mLayoutInflater.inflate(R.layout.activity_post_data, null);


        TextView postTitle = (TextView)view.findViewById(R.id.postTitle);
        TextView grade = (TextView)view.findViewById(R.id.suggestion);


        postTitle.setText(postData.get(position).getTitle()); //포스트 데이터에서 형태에 따라 받아온거임
        grade.setText(String.valueOf(postData.get(position).getSuggestion()));//추천수라서 string형태로 바꿔줬음


        return view;
    }
}
