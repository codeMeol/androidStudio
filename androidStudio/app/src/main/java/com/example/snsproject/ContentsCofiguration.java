package com.example.snsproject;

public class ContentsCofiguration {

    public String title;
    public String content;
    public int ranUid;
    public int suggestion;
    public ContentsCofiguration() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ContentsCofiguration(String title, String content, int ranUid ,int suggestion) {
        this.title = title;
        this.content = content;
        this.ranUid = ranUid;
        this.suggestion= suggestion;
    }

    public String getTitle(){

        return title;
    }
    public String getContent(){

        return content;
    }
    public int getRanUid(){

        return ranUid;
    }
    public int getSuggestion(){
        return suggestion;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setContent(String content){
        this.content=content;
    }

    public void setSuggestion(int suggestion){
        this.suggestion=suggestion;
    }

    public void setRanUid(int ranUid){
        this.ranUid=ranUid;
    }



}
