package com.example.a2023_cs_28_section_a

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val name = intent.getStringExtra("NAME")
        val phone = intent.getStringExtra("PHONE")
        val email = intent.getStringExtra("EMAIL")
        val eventType = intent.getStringExtra("EVENT_TYPE")
        val date = intent.getStringExtra("DATE")
        val gender = intent.getStringExtra("GENDER")
        val imageUriString = intent.getStringExtra("IMAGE_URI")

        findViewById<TextView>(R.id.tvConfirmName).text = getString(R.string.label_name) + name
        findViewById<TextView>(R.id.tvConfirmPhone).text = getString(R.string.label_phone) + phone
        findViewById<TextView>(R.id.tvConfirmEmail).text = getString(R.string.label_email) + email
        findViewById<TextView>(R.id.tvConfirmEventType).text = getString(R.string.label_event_type) + eventType
        findViewById<TextView>(R.id.tvConfirmDate).text = getString(R.string.label_event_date) + date
        findViewById<TextView>(R.id.tvConfirmGender).text = getString(R.string.label_gender) + gender

        val ivConfirmImage = findViewById<ImageView>(R.id.ivConfirmImage)
        if (!imageUriString.isNullOrEmpty() && imageUriString != "null") {
            ivConfirmImage.setImageURI(Uri.parse(imageUriString))
        } else {
            ivConfirmImage.setImageResource(android.R.drawable.ic_menu_camera)
        }
    }
}