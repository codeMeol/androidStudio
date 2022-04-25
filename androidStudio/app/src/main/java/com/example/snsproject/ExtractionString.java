package com.example.snsproject;

public class ExtractionString {
    private String Email,frontEmail,backEmail,secondEmail;
    char charAt;


    public ExtractionString(String s){
        this.Email=s;


    }
    public String getEmail(){
        int n=Email.indexOf("@");


            // @ 이라는 값이 있다면
            frontEmail= Email.substring(0,n);
            backEmail=Email.substring(n+1);
            //첫번째 자리부터 at자리까지 문자열을 가져온다
            secondEmail=frontEmail+backEmail;
        n=secondEmail.indexOf(".");
        frontEmail= secondEmail.substring(0,n);
        backEmail=secondEmail.substring(n+1);
        return frontEmail+backEmail;
    }
}
