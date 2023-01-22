package com.example.authentication_with_sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authentication_with_sqlite.model.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar!!.hide()

        inputValidation = InputValidation(this)
        databaseHelper = DBHelper(this)

        register.setOnClickListener { postDataToSQLite() }
        loginbtn.setOnClickListener { finish() }

    }

    private fun postDataToSQLite() {
        if(!inputValidation.isInputEditTextFilled(teditEmail, tilEmail, getString(R.string.error_message_email)))
            return

        if(!inputValidation.isInputEditTextEmail(teditEmail, tilEmail!!, getString(R.string.error_message_email)))
            return

        if (!inputValidation.isInputEditTextFilled(teditPassword, tilPassword, getString(R.string.error_message_password)))
            return

        if(!databaseHelper.checkUser(teditEmail.text.toString().trim())) {
            val user= User(
                name=teditName.text.toString().trim(),
                email=teditEmail.text.toString().trim(),
                password = teditPassword.text.toString().trim()
            )

            databaseHelper.addUser(user)
            Snackbar.make(nestedScrollView!!, getString(R.string.success_message), Snackbar.LENGTH_LONG).show()
            emptyInputEditText()
        } else { // record already exists
            Snackbar.make(nestedScrollView!!, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show()
        }
    }

    private fun emptyInputEditText() {
        teditName!!.text = null
        teditEmail!!.text = null
        teditPassword!!.text = null
        tEditConfirmPassword!!.text = null
    }
}
