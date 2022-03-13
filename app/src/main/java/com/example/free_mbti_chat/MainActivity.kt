package com.example.free_mbti_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //로그인 버튼 리스너
        btn_main_login.setOnClickListener{
            val intent = Intent(this,CheckMbti::class.java)
            startActivity(intent)
        }
        //회원가입 부분
        tv_signup.setOnClickListener{
         //   val intent = Intent(this,CheckMbti::class.java)
         //   startActivity(intent)
        }
        tv_findpass.setOnClickListener {

        }




        }
    }
