package com.example.a2023_cs_28_section_a

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Staggered entrance animations
        val views: List<View> = listOf(
            findViewById(R.id.ivEventBanner),
            findViewById(R.id.tvMainBadge),
            findViewById(R.id.tvMainTitle),
            findViewById(R.id.tvMainDescription),
            findViewById(R.id.dividerMain),
            findViewById(R.id.btnRegister),
            findViewById(R.id.tvMainFooter)
        )
        views.forEachIndexed { i, v ->
            v.alpha = 0f
            v.translationY = 40f
            v.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((i * 100L) + 80L)
                .setDuration(550)
                .setInterpolator(android.view.animation.DecelerateInterpolator())
                .start()
        }

        // Navigate to Event Registration
        findViewById<Button>(R.id.btnRegister).setOnClickListener {
            startActivity(Intent(this, EventRegistrationActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}