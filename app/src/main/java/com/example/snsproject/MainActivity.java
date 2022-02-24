package com.example.snsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.snsproject.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private FirebaseAuth firebaseAuth;
    private String etID,etPWD;
    String TAG="테스트용 로그";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mainBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etID=mainBinding.mainEmailEt.getText().toString().trim();
                etPWD=mainBinding.mainPwdEt.getText().toString().trim();

                if(etID==null || etID.equals("")||etID.length()==0||
                   etPWD==null || etPWD.equals("")||etPWD.length()==0
                ) {//et 이메일이랑 비밀번호가 공란이 아닐경우만 작동
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호가 비어있습니다.", Toast.LENGTH_SHORT);
                    Log.e("알림", "이메일 또는 비밀번호가 비어있습니다" );
                }
                else if(etID.equals("admin")){
                    Intent intent = new Intent(getApplicationContext(), PostActivity.class);

                    intent.putExtra("userAdmin",etID);
                    startActivity(intent);
                }
                else {

                    firebaseAuth = FirebaseAuth.getInstance();
                    Log.e(TAG, "onClick: " + etID + " ," + etPWD);
                    firebaseAuth.signInWithEmailAndPassword(etID, etPWD).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user=firebaseAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                                ExtractionString ets= new ExtractionString(etID);


                                intent.putExtra("userEmail",ets.getEmail());
                                intent.putExtra("userUID",user.getUid());
                                startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT);
                                Log.e("signInwithEmaswod", "아이디 또는 비밀번호가 맞지 않습니다. " );
                            }


                        }
                    });

                }

            }
        });


        mainBinding.signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signUpActivity.class);
                startActivity(intent);
            }

    });

    }
}
