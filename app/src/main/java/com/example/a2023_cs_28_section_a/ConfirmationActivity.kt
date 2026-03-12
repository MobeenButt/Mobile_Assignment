package com.example.a2023_cs_28_section_a

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        // ── Bind and populate views ──────────────────────────────────
        val name      = intent.getStringExtra("NAME")      ?: ""
        val phone     = intent.getStringExtra("PHONE")     ?: ""
        val email     = intent.getStringExtra("EMAIL")     ?: ""
        val eventType = intent.getStringExtra("EVENT_TYPE") ?: ""
        val date      = intent.getStringExtra("DATE")      ?: ""
        val gender    = intent.getStringExtra("GENDER")    ?: ""
        val imageUriStr = intent.getStringExtra("IMAGE_URI") ?: ""

        findViewById<TextView>(R.id.tvConfirmName).text   = name
        findViewById<TextView>(R.id.tvConfirmPhone).text  = phone
        findViewById<TextView>(R.id.tvConfirmEmail).text  = email
        findViewById<TextView>(R.id.tvConfirmEvent).text  = eventType
        findViewById<TextView>(R.id.tvConfirmDate).text   = date
        findViewById<TextView>(R.id.tvConfirmGender).text = gender

        // Profile image
        if (imageUriStr.isNotEmpty()) {
            try {
                val iv = findViewById<ImageView>(R.id.ivConfirmImage)
                iv.setImageURI(Uri.parse(imageUriStr))
                iv.scaleType = ImageView.ScaleType.CENTER_CROP
            } catch (_: Exception) { /* keep placeholder */ }
        }

        // ── Entrance animations ──────────────────────────────────────
        val checkIcon = findViewById<ImageView>(R.id.ivSuccessCheck)
        val card      = findViewById<View>(R.id.cardConfirmDetails)
        val btnBack   = findViewById<Button>(R.id.btnBackHome)

        checkIcon.scaleX = 0f
        checkIcon.scaleY = 0f
        checkIcon.alpha  = 0f
        card.alpha       = 0f
        card.translationY = 60f
        btnBack.alpha    = 0f

        checkIcon.animate()
            .scaleX(1f).scaleY(1f).alpha(1f)
            .setDuration(650)
            .setInterpolator(android.view.animation.OvershootInterpolator(1.4f))
            .start()

        card.animate()
            .alpha(1f).translationY(0f)
            .setStartDelay(400)
            .setDuration(600)
            .setInterpolator(android.view.animation.DecelerateInterpolator())
            .start()

        btnBack.animate()
            .alpha(1f)
            .setStartDelay(750)
            .setDuration(500)
            .start()

        // ── Back to home ─────────────────────────────────────────────
        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}