package com.example.authentication_with_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authentication_with_sqlite.databinding.ActivityMainBinding
import com.example.authentication_with_sqlite.databinding.ActivityRegisterBinding
import com.example.authentication_with_sqlite.model.User
import com.google.android.material.snackbar.Snackbar

class Register : AppCompatActivity() {

    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DBHelper
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        inputValidation = InputValidation(this)
        databaseHelper = DBHelper(this)

        binding.register.setOnClickListener { postDataToSQLite() }
        binding.loginbtn.setOnClickListener { finish() }

    }

    private fun postDataToSQLite() {
        if(!inputValidation.isInputEditTextFilled(binding.teditEmail, binding.tilEmail, getString(R.string.error_message_email)))
            return

        if(!inputValidation.isInputEditTextEmail(binding.teditEmail, binding.tilEmail, getString(R.string.error_message_email)))
            return

        if (!inputValidation.isInputEditTextFilled(binding.teditPassword, binding.tilPassword, getString(R.string.error_message_password)))
            return

        if(!databaseHelper.checkUser(binding.teditEmail.text.toString().trim())) {
            val user= User(
                name=binding.teditName.text.toString().trim(),
                email=binding.teditEmail.text.toString().trim(),
                password = binding.teditPassword.text.toString().trim()
            )

            databaseHelper.addUser(user)
            Snackbar.make(binding.nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else { // record already exists
            Snackbar.make(binding.nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun emptyInputEditText() {
        binding.teditName.text = null
        binding.teditEmail.text = null
        binding.teditPassword.text = null
        binding.tEditConfirmPassword.text = null
    }
}
