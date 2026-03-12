package com.example.a2023_cs_28_section_a

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val glow    = findViewById<View>(R.id.viewLogoGlow)
        val logo    = findViewById<ImageView>(R.id.ivSplashLogo)
        val appName = findViewById<TextView>(R.id.tvSplashAppName)
        val divider = findViewById<View>(R.id.dividerSplash)
        val welcome = findViewById<TextView>(R.id.tvSplashWelcome)
        val tagline = findViewById<TextView>(R.id.tvSplashTagline)

        listOf(glow, logo, appName, divider, welcome, tagline).forEach { it.alpha = 0f }
        logo.scaleX = 0.4f;  logo.scaleY = 0.4f
        glow.scaleX = 0.3f;  glow.scaleY = 0.3f
        divider.scaleX = 0f
        appName.translationY = 30f
        welcome.translationY = 20f

        glow.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(900)
            .setInterpolator(android.view.animation.DecelerateInterpolator()).start()

        logo.animate().alpha(1f).scaleX(1f).scaleY(1f).setStartDelay(150).setDuration(750)
            .setInterpolator(android.view.animation.OvershootInterpolator(1.2f)).start()

        appName.animate().alpha(1f).translationY(0f).setStartDelay(600).setDuration(600)
            .setInterpolator(android.view.animation.DecelerateInterpolator()).start()

        divider.animate().alpha(1f).scaleX(1f).setStartDelay(950).setDuration(500).start()

        welcome.animate().alpha(1f).translationY(0f).setStartDelay(1150).setDuration(600).start()

        tagline.animate().alpha(1f).setStartDelay(1500).setDuration(700).start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, 2800)
    }
}