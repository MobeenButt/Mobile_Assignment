package com.example.a2023_cs_28_section_a

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class EventRegistrationActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerEventType: Spinner
    private lateinit var tvSelectedDate: TextView
    private lateinit var btnDatePicker: Button
    private lateinit var rgGender: RadioGroup
    private lateinit var ivProfile: ImageView
    private lateinit var btnUpload: Button
    private lateinit var cbTerms: CheckBox
    private lateinit var btnSubmit: Button

    private var selectedDate: String = ""
    private var imageUri: Uri? = null

    // Image picker launcher
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
                ivProfile.setImageURI(uri)
                ivProfile.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_registration)

        bindViews()
        setupSpinner()
        setupDatePicker()
        setupUpload()
        setupSubmit()
        animateEntrance()
    }

    private fun bindViews() {
        etFullName      = findViewById(R.id.etFullName)
        etPhone         = findViewById(R.id.etPhone)
        etEmail         = findViewById(R.id.etEmail)
        spinnerEventType = findViewById(R.id.spinnerEventType)
        tvSelectedDate  = findViewById(R.id.tvSelectedDate)
        btnDatePicker   = findViewById(R.id.btnDatePicker)
        rgGender        = findViewById(R.id.rgGender)
        ivProfile       = findViewById(R.id.ivProfile)
        btnUpload       = findViewById(R.id.btnUpload)
        cbTerms         = findViewById(R.id.cbTerms)
        btnSubmit       = findViewById(R.id.btnSubmit)
    }

    private fun setupSpinner() {
        val items = listOf(
            getString(R.string.spinner_hint),
            "Seminar", "Workshop", "Conference", "Webinar", "Cultural Event"
        )

        val adapter = object : ArrayAdapter<String>(
            this, R.layout.spinner_item, items
        ) {
            override fun isEnabled(position: Int) = position != 0

            override fun getDropDownView(
                position: Int, convertView: android.view.View?, parent: android.view.ViewGroup
            ): android.view.View {
                val view = super.getDropDownView(position, convertView, parent)
                val tv = view as TextView
                tv.setTextColor(
                    if (position == 0)
                        android.graphics.Color.parseColor("#5A5A8A")
                    else
                        android.graphics.Color.parseColor("#F4F0FF")
                )
                tv.setBackgroundColor(android.graphics.Color.parseColor("#131325"))
                tv.setPadding(48, 0, 48, 0)
                return view
            }
        }
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerEventType.adapter = adapter
    }

    private fun setupDatePicker() {
        btnDatePicker.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                this,
                android.R.style.Theme_Material_Dialog,
                { _, year, month, day ->
                    selectedDate = "%02d/%02d/%04d".format(day, month + 1, year)
                    tvSelectedDate.text = selectedDate
                    tvSelectedDate.setTextColor(android.graphics.Color.parseColor("#F4F0FF"))
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupUpload() {
        btnUpload.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
    }

    private fun setupSubmit() {
        btnSubmit.setOnClickListener { validateAndSubmit() }
    }

    private fun validateAndSubmit() {
        val name   = etFullName.text.toString().trim()
        val phone  = etPhone.text.toString().trim()
        val email  = etEmail.text.toString().trim()
        val eventIdx = spinnerEventType.selectedItemPosition

        // ── Validation ──────────────────────────────────────────────
        if (name.isEmpty()) {
            etFullName.error = "Full name is required"
            etFullName.requestFocus(); return
        }
        if (name.length < 3) {
            etFullName.error = "Name must be at least 3 characters"
            etFullName.requestFocus(); return
        }
        if (phone.isEmpty()) {
            etPhone.error = "Phone number is required"
            etPhone.requestFocus(); return
        }
        if (!phone.matches(Regex("^[0-9]{10}$"))) {
            etPhone.error = "Enter a valid 10-digit phone number"
            etPhone.requestFocus(); return
        }
        if (email.isEmpty()) {
            etEmail.error = "Email address is required"
            etEmail.requestFocus(); return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Enter a valid email address"
            etEmail.requestFocus(); return
        }
        if (eventIdx == 0) {
            Toast.makeText(this, "Please select an event type", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select the event date", Toast.LENGTH_SHORT).show()
            return
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
            return
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please accept the Terms & Conditions", Toast.LENGTH_SHORT).show()
            return
        }

        // ── All good — confirmation dialog ───────────────────────────
        showConfirmationDialog(name, phone, email, eventIdx)
    }

    private fun showConfirmationDialog(name: String, phone: String, email: String, eventIdx: Int) {
        AlertDialog.Builder(this, R.style.Theme_App_AlertDialog)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setPositiveButton(getString(R.string.dialog_submit)) { _, _ ->
                navigateToConfirmation(name, phone, email, eventIdx)
            }
            .setNegativeButton(getString(R.string.dialog_review), null)
            .show()
    }

    private fun navigateToConfirmation(name: String, phone: String, email: String, eventIdx: Int) {
        val gender = if (rgGender.checkedRadioButtonId != -1)
            findViewById<RadioButton>(rgGender.checkedRadioButtonId).text.toString()
                .replace("♂  ", "").replace("♀  ", "")
        else ""

        val eventType = spinnerEventType.getItemAtPosition(eventIdx).toString()

        val intent = Intent(this, ConfirmationActivity::class.java).apply {
            putExtra("NAME",       name)
            putExtra("PHONE",      phone)
            putExtra("EMAIL",      email)
            putExtra("EVENT_TYPE", eventType)
            putExtra("DATE",       selectedDate)
            putExtra("GENDER",     gender)
            putExtra("IMAGE_URI",  imageUri?.toString() ?: "")
        }
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun animateEntrance() {
        val root = findViewById<View>(android.R.id.content)
        root.alpha = 0f
        root.animate().alpha(1f).setDuration(400).setStartDelay(50).start()
    }
}