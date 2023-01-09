package com.example.neighborsis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    var webView : WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
     webView = findViewById(R.id.webView)
        webView?.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        webView?.loadUrl("https://dunni.co.kr/")

    }
    override fun onBackPressed() {
        if (webView?.canGoBack()!!)
        {
            webView?.goBack()
        }
        else
        {
            finish()
        }
    }
}
