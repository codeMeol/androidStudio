package kr.ac.poly.smart_coin_saver;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateText {

    private static final String FILE_NAME="RecordedMoney.txt";

    Context mContext = null;
    public CreateText(Context context){
        mContext=context;
    }

    public void save(String strData){
        if(strData == null||strData.equals("")){
            return;
        }

    FileOutputStream fosMemo = null;
    try{
        //파일에 데이터를 쓰기 위해서 output스트림 생성
        fosMemo = mContext.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
        //파일에 메모 적기
        fosMemo.write(strData.getBytes());
        fosMemo.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }
    public String load(){
        try{
            //파일에서 데이터를 읽기 위해서 input 스트림 생성
            FileInputStream fisMemo = mContext.openFileInput(FILE_NAME);

            //데이터를 읽어 온뒤, String 타입 객체로 반환
            byte[] memoData = new byte[fisMemo.available()];
        while(fisMemo.read(memoData)!=-1){}
        return new String(memoData);
        }catch (IOException e){}
        return "";
    }
    public void delete(){
        mContext.deleteFile(FILE_NAME);
    }

}
