package com.example.androidtemplate.ui.composables

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun YouTubeWebView(url: String) {
    val context = LocalContext.current
    val activity = context as? Activity

    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.mediaPlaybackRequiresUserGesture = false

                // Needed for fullscreen
                webChromeClient = object : WebChromeClient() {
                    private var customView: View? = null
                    private var customViewCallback: WebChromeClient.CustomViewCallback? = null

                    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                        if (customView != null) {
                            callback?.onCustomViewHidden()
                            return
                        }

                        customView = view
                        customViewCallback = callback
                        activity?.window?.decorView?.let { decor ->
                            (decor as ViewGroup).addView(view)
                            decor.systemUiVisibility = (
                                    View.SYSTEM_UI_FLAG_FULLSCREEN
                                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                    )
                        }
                    }

                    override fun onHideCustomView() {
                        activity?.window?.decorView?.let { decor ->
                            customView?.let {
                                (decor as ViewGroup).removeView(it)
                            }
                            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                        }
                        customView = null
                        customViewCallback?.onCustomViewHidden()
                    }
                }

                loadUrl(url)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}