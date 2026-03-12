package com.example.a2023_cs_28_section_a

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class EventRegistrationActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var spinnerEventType: Spinner
    private lateinit var btnDatePicker: Button
    private lateinit var tvSelectedDate: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var ivProfile: ImageView
    private lateinit var btnUpload: Button
    private lateinit var cbTerms: CheckBox
    private lateinit var btnSubmit: Button

    private var selectedDate: String = ""
    private var selectedImageUri: Uri? = null

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            ivProfile.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        etFullName = findViewById(R.id.etFullName)
        etPhone = findViewById(R.id.etPhone)
        etEmail = findViewById(R.id.etEmail)
        spinnerEventType = findViewById(R.id.spinnerEventType)
        btnDatePicker = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        rgGender = findViewById(R.id.rgGender)
        ivProfile = findViewById(R.id.ivProfile)
        btnUpload = findViewById(R.id.btnUpload)
        cbTerms = findViewById(R.id.cbTerms)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnDatePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, y, m, d ->
                selectedDate = "$d/${m + 1}/$y"
                tvSelectedDate.text = selectedDate
            }, year, month, day)
            datePickerDialog.show()
        }

        btnUpload.setOnClickListener {
            pickImage.launch("image/*")
        }

        btnSubmit.setOnClickListener {
            if (validateForm()) {
                showConfirmationDialog()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (etFullName.text.toString().isEmpty()) {
            etFullName.error = "Full Name is required"
            return false
        }
        if (etPhone.text.toString().length < 10) {
            etPhone.error = "Enter valid phone number"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = "Enter valid email address"
            return false
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please select an event date", Toast.LENGTH_SHORT).show()
            return false
        }
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please accept terms and conditions", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirm Registration")
            .setMessage("Are you sure you want to submit the registration?")
            .setPositiveButton("Yes") { _, _ ->
                navigateToConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun navigateToConfirmation() {
        val intent = Intent(this, ConfirmationActivity::class.java)
        intent.putExtra("NAME", etFullName.text.toString())
        intent.putExtra("PHONE", etPhone.text.toString())
        intent.putExtra("EMAIL", etEmail.text.toString())
        intent.putExtra("EVENT_TYPE", spinnerEventType.selectedItem.toString())
        intent.putExtra("DATE", selectedDate)
        val gender = findViewById<RadioButton>(rgGender.checkedRadioButtonId).text.toString()
        intent.putExtra("GENDER", gender)
        intent.putExtra("IMAGE_URI", selectedImageUri.toString())
        startActivity(intent)
    }
}