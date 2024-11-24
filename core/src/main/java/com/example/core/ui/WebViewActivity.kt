package com.example.core.ui

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.example.core.R

class WebViewActivity : AppCompatActivity() {
    companion object {
        const val URL = "url"
    }

    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var siteUrl = ""

        if (intent.extras != null) {
            val url = intent.getStringExtra(URL)
            if (url != null) {
                siteUrl = url
            }
        }

        webView = findViewById(R.id.webView1)
        webView.loadUrl(siteUrl)
    }
}