package com.example.snsproject;

import java.util.Random;

public class createRanNum {
    Random random;
    int ranValue;//4자리 인증번호 담을 정수형 데이터타입 변수

    public createRanNum(){

    random =new Random();
    ranValue=random.nextInt(9999);

    }

    public int getRanValue() {


        return ranValue;
    }
}
