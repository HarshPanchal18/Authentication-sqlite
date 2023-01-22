package com.example.authentication_with_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputValidation=InputValidation(this)
        databaseHelper= DBHelper(this)

        loginbtn.setOnClickListener { verifyFromSQLite() }

        initObjects()

        register.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
    }

    private fun verifyFromSQLite() {
        if(!inputValidation.isInputEditTextFilled(tedit_mail, ti_mail, getString(R.string.error_message_email)))
            return

        if(!inputValidation.isInputEditTextEmail(tedit_mail, ti_mail!!, getString(R.string.error_message_email)))
            return

        if (!inputValidation.isInputEditTextFilled(tedit_password!!, ti_password!!, getString(R.string.error_message_email)))
            return

        if(
            !databaseHelper.checkUser(tedit_mail.text.toString().trim{it <= ' '}, tedit_mail.text.toString().trim{it <= ' '})
        ){
            val accountIntent= Intent(this,UsersList::class.java)
            accountIntent.putExtra("EMAIL",tedit_mail.text.toString().trim{it <= ' '})
            emptyInputEditText()
            startActivity(accountIntent)
        } else {
            Snackbar.make(nestedScrollView,getString(R.string.error_valid_email_password),Snackbar.LENGTH_SHORT).show()
        }

    }

    fun initObjects() {
        databaseHelper = DBHelper(this)
        inputValidation = InputValidation(this)
    }

    // This method is to empty all input edit text
    fun emptyInputEditText() {
        tedit_mail.text = null
        tedit_password.text = null
    }
}
