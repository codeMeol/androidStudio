package com.example.snsproject;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snsproject.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class signUpActivity extends AppCompatActivity {
    String email;
    String pwd;
    String pwdcheck;
    boolean proov;
    String TAG ="로그용 텍스트";

    ActivitySignupBinding activitySignupBinding;
    DatabaseReference dbRef;
    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(activitySignupBinding.getRoot());

        dbRef=FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth = FirebaseAuth.getInstance();

        Log.e(TAG, "onCreate: "+email+pwd+pwdcheck );
        //이메일 중복확인 버튼 리스너
        activitySignupBinding.doubleCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이메일 받아와서 string에 넣기
                email = activitySignupBinding.userIdEt.getText().toString().trim();
                ExtractionString ets = new ExtractionString(email);//@.지우기

                //디비 경로로 접속후 리스너 장착
                dbRef.child(ets.getEmail()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e(TAG, "email value: "+snapshot.getValue());
                        //이메일 경로로 값이 이미있으면 만들수 없고 null값이 나오면 만들수 있다
                        //토스트메시지 대신 로그 사용했음
                        if(snapshot.getValue()==null){
                            Log.e(TAG, "싸인업액티비티: "+" 만드실 수 있습니다." );
                        }
                        else
                        {
                            Log.e(TAG, "만드실 수 없습니다. ");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    //에러 났을 경우
                    }
                });


            }
        });
        //가입하기 버튼 눌렀을 때 리스너
        activitySignupBinding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이디,비번,비번확인 3가지 불러온다.
                email = activitySignupBinding.userIdEt.getText().toString().trim();
                pwd = activitySignupBinding.userPwdEt.getText().toString().trim();
                pwdcheck = activitySignupBinding.userPwdEt.getText().toString().trim();
                //아이디 및 비밀번호 규칙 확인
                showProov(email, pwd);
                //확인결과 이상없으면 proov=true
                if (proov) {

                    //비밀번호, 비밀번호 확인 두개가 같을경우
                    if (pwd.equals(pwdcheck)) {
                        Log.e("signupActivity", "등록 버튼 " + email + " , " + pwd);//값 제대로 넘겨졌는지 확인 로그
                        final ProgressDialog mDialog = new ProgressDialog(signUpActivity.this);//잠시 기다리라는 메시지 띄움
                        mDialog.setMessage("가입중입니다...");
                        mDialog.show();

                        //파이어베이스에 신규계정 등록하기
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                          .addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {//task가 값을 가지고 있다.

                               //가입 성공시
                              if (task.isSuccessful()) {
                               mDialog.dismiss();//다이얼로그 지움
                                  //유저 정보 받기
                                  FirebaseUser user = firebaseAuth.getCurrentUser();
                                  //유저 정보중에서 이메일 값 받아서 @.지우기
                                  ExtractionString ets=new ExtractionString(user.getEmail());

                                  Log.e(TAG, "ExtractionString "+", "+ets.getEmail());//@.지운거 확인 로그
                                  //realtimeDB에 들어갈 값 4가지
                                  String email = user.getEmail();
                                  String uid = user.getUid();
                                  String name = activitySignupBinding.userNameEt.getText().toString().trim();
                                  String Birth = activitySignupBinding.userDateOfBirthEt.getText().toString().trim();

                                  //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                  HashMap<Object, String> hashMap = new HashMap<>();

                                  hashMap.put("uid", uid);
                                  hashMap.put("email", email);
                                  hashMap.put("name", name);
                                  hashMap.put("birth", Birth);


                                  FirebaseDatabase database = FirebaseDatabase.getInstance(); //데이터베이스를 사용하겠다.
                                  DatabaseReference reference = database.getReference("Users");  //realtime db에 연결
                                  reference.child(ets.getEmail()).setValue(hashMap);//users->@.지운 이메일->hashmap(key:value)


                                  //가입이 이루어져을시 가입 화면을 빠져나감.
                                  Intent intent = new Intent(signUpActivity.this, MainActivity.class);
                                  startActivity(intent);
                                  finish();


                              } else {
                                  //가입 실패의 경우
                                  mDialog.dismiss();
                                    //토스트로 오류 메시지 띄움
                                  Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                  return;  //해당 메소드 진행을 멈추고 빠져나감.

                               }
                           }
                         });
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"아이디는 이메일 형식에 맞춰서 비밀번호는 7자리 이상입니다.",Toast.LENGTH_SHORT);
            }
        });

        //-------------------------------------------


        // sms인증 부분


        //----------------------------------------------
        ActivityCompat.requestPermissions( signUpActivity.this,  new String[] { Manifest.permission.SEND_SMS}, 1);
        SmsManager sms = SmsManager.getDefault();
        Button signUpSmsBtn = (Button)findViewById(R.id.signUpSmsBtn);
        signUpSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRanNum cr=new createRanNum();
                String t=""+cr.getRanValue();
                sms.sendTextMessage("01011112222", null, "사용자가 요청한 인증 번호입니다."+t, null, null);
            }
        });





    }












    public boolean showProov(String email,String pwd){
        if(email.matches("^[A-z|0-9]([A-z|0-9]*)(@)([A-z]*)(\\.)([A-z]*)$")||
                //이메일 형식이 맞을 때
                pwd.length()>=7){
                //비밀번호가 7자리 이상일 때

          proov=true;
        }

        return proov;
    }


}
