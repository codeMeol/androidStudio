package com.example.snsproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {
    String email;
    String pwd;
    String pwdcheck;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        Button signUpBtn=(Button)findViewById(R.id.signup_btn);
        EditText userID = (EditText)findViewById(R.id.userId_et);
        EditText userPWD = (EditText)findViewById(R.id.userPwd_et);
        EditText userPwdCheck = (EditText)findViewById(R.id.userPwdCheck_et);
        EditText userBirth = (EditText)findViewById(R.id.userDateOfBirth_et);
        EditText userName = (EditText)findViewById(R.id.userName_et);

//         email = userID.getText().toString().trim();
//         pwd = userPWD.getText().toString().trim();
//         pwdcheck = userPwdCheck.getText().toString().trim();

        email="길동";
        pwd="123455555";
        pwdcheck="123455555";

        Log.e("signupActivity", "onCreate: "+pwd+", "+ email+", "+ pwdcheck );
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pwd.equals(pwdcheck)) {
                    Log.e("signupActivity", "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(signUpActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();


                    //파이어베이스에 신규계정 등록하기
                    firebaseAuth.createUserWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //가입 성공시
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();

                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        String email = user.getEmail();
                                        String uid = user.getUid();
                                        String name = userName.getText().toString().trim();
                                        String Birth = userBirth.getText().toString().trim();

                                        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                        HashMap<Object, String> hashMap = new HashMap<>();

                                        hashMap.put("uid", uid);
                                        hashMap.put("email", email);
                                        hashMap.put("name", name);
//                                        hashMap.put("birth", Birth);


                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = database.getReference("Users");
                                        reference.child(uid).setValue(hashMap);


                                        //가입이 이루어져을시 가입 화면을 빠져나감.
                                        Intent intent = new Intent(signUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
                                        return;  //해당 메소드 진행을 멈추고 빠져나감.

                                    }

                                }
                            });


                }
            }
        });
    }
}
