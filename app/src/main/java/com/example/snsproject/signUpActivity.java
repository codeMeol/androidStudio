package com.example.snsproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class signUpActivity extends AppCompatActivity {
    String email;
    String pwd;
    String pwdcheck;
    boolean proov;
    String TAG ="로그용 텍스트";

    ActivitySignupBinding activitySignupBinding;

    private FirebaseAuth firebaseAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySignupBinding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(activitySignupBinding.getRoot());


        firebaseAuth = FirebaseAuth.getInstance();


        Log.e(TAG, "onCreate: "+email+pwd+pwdcheck );


        activitySignupBinding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                email = activitySignupBinding.userIdEt.getText().toString().trim();
                pwd = activitySignupBinding.userPwdEt.getText().toString().trim();
                pwdcheck = activitySignupBinding.userPwdEt.getText().toString().trim();
                showProov(email, pwd);

                if (proov) {


                    if (pwd.equals(pwdcheck)) {
                        Log.e("signupActivity", "등록 버튼 " + email + " , " + pwd);
                        final ProgressDialog mDialog = new ProgressDialog(signUpActivity.this);
                        mDialog.setMessage("가입중입니다...");
                        mDialog.show();


                        //파이어베이스에 신규계정 등록하기
                        firebaseAuth.createUserWithEmailAndPassword(email, pwd)
                                .addOnCompleteListener(signUpActivity.this, new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        //가입 성공시
                                        if (task.isSuccessful()) {
                                            mDialog.dismiss();

                                            FirebaseUser user = firebaseAuth.getCurrentUser();
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
                                            reference.child(uid).setValue(hashMap);//users->uid->hashmap


                                            //가입이 이루어져을시 가입 화면을 빠져나감.
                                            Intent intent = new Intent(signUpActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();


                                        } else {
                                            mDialog.dismiss();

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
