package com.example.webviewexample

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun search(view: View) {

        // Prefix https://
        val prefix = getString(R.string.https)

        // Get input text and the prefix https://
        val url = prefix + text_input_url.editText?.text.toString()
        text_input_url.hideKeyboard()
        Log.d(TAG, "url: $url")

        web_view.loadUrl(url)
        web_view.settings.javaScriptEnabled = true


        // If you want more control over where a clicked link loads,
        // create your own WebViewClient that overrides the shouldOverrideUrlLoading() method.
        web_view.webViewClient = object : WebViewClient() {

            // shouldOverrideUrlLoading() checks whether the URL host matches a specific domain
            // If it matches, then the method returns false in order to not override the URL loading
            // (it allows the WebView to load the URL as usual).
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // show the progress bar
                progress_bar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // hide the progress bar
                progress_bar.visibility = View.GONE
            }
        }



    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    // Hide the keyboard
    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }


}