package com.example.snsproject;

public class PostData {

    private String title;
    private int suggestion;

    public PostData(int suggestion, String title){
        this.suggestion = suggestion;
        this.title = title;


    }

    public int getSuggestion()
    {
        return this.suggestion;
    }

    public String getTitle()
    {
        return this.title;
    }








}
