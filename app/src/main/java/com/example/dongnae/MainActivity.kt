package com.example.dongnae

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dongnae.databinding.ActivityMainBinding
import java.lang.NullPointerException
import android.os.Handler
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isFocusLi: Boolean

        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {

        }
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )


        binding.dongnaeWebView.settings.apply {
            javaScriptEnabled = true//자바 스크립트 허용
            domStorageEnabled = true// 오늘 다시 보지 않기 기능 을 위한 저장소 캐쉬 저장
            setSupportMultipleWindows(true)
            binding.dongnaeWebView.webChromeClient = WebChromeClient()
        }

        binding.dongnaeWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view!!.loadUrl(
                    "javascript:var s = document.querySelectorAll('#lnb ul li');" +
                            "s.forEach((element,index)=>{" +
                            "var a= element.querySelector('a');" +
                            "a.setAttribute('onClick', `androidClick('\${a.getAttribute('href')}')`);" +
                            "});" +
                            "function androidClick(link){ " +
                            "WebAppInterface.showToast(link);" +
                            "}"
                )
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

        }
        binding.dongnaeWebView.addJavascriptInterface(WebAppInterface(this), "WebAppInterface")
        binding.dongnaeWebView.evaluateJavascript("script", null);
        binding.dongnaeWebView.loadUrl("https://dunni.co.kr/")
        Thread {
            val doc = Jsoup.connect("https://m.dunni.co.kr/product/list_thumb.html?cate_no=23/").get()
            val title = doc.title()
            val temele: Elements = doc.select("div#lnb ul li.xans-record-")

                val oneTitle: Element = temele.get(0)
                Log.d("준영테스트",oneTitle.toString())
                if(oneTitle.text().equals("언니옷가게")) {
                 //   oneTitle.attr("")
                }
            Log.d("text 테스트",temele!!.text())

        }.start()


    }


    /** Instantiate the interface and set the context  */
    class WebAppInterface(ctx: Context) {
        lateinit var string1: String;

        @JavascriptInterface
        fun showToast(toast: String) {
            string1 = toast
            Log.d("준영 테스트", string1)
        }


    }


}
