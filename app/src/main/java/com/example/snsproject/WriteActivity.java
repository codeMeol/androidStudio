package com.example.snsproject;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.snsproject.databinding.ActivityWriteBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WriteActivity extends AppCompatActivity {
    ActivityWriteBinding activityWriteBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWriteBinding=ActivityWriteBinding.inflate(getLayoutInflater());

        setContentView(activityWriteBinding.getRoot());





    }
}
