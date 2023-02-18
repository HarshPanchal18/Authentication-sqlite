package com.example.authentication_with_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.authentication_with_sqlite.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DBHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding= DataBindingUtil.setContentView(binding.root)

        inputValidation=InputValidation(this)
        databaseHelper= DBHelper(this)

        binding.loginbtn.setOnClickListener { verifyFromSQLite() }

        initObjects()

        binding.register.setOnClickListener {
            startActivity(Intent(this,Register::class.java))
        }
    }

    private fun verifyFromSQLite() {
        if(!inputValidation.isInputEditTextFilled(binding.teditMail, binding.tiMail, getString(R.string.error_message_email)))
            return

        if(!inputValidation.isInputEditTextEmail(binding.teditMail, binding.tiMail, getString(R.string.error_message_email)))
            return

        if (!inputValidation.isInputEditTextFilled(binding.teditPassword, binding.tiPassword, getString(R.string.error_message_email)))
            return

        if(
            !databaseHelper.checkUser(binding.teditMail.text.toString().trim{it <= ' '}, binding.teditMail.text.toString().trim{it <= ' '})
        ){
            val accountIntent= Intent(this,UsersList::class.java)
            accountIntent.putExtra("EMAIL",binding.teditMail.text.toString().trim{it <= ' '})
            emptyInputEditText()
            startActivity(accountIntent)
        } else {
            Snackbar.make(binding.nestedScrollView,getString(R.string.error_valid_email_password),Snackbar.LENGTH_SHORT).show()
        }

    }

    fun initObjects() {
        databaseHelper = DBHelper(this)
        inputValidation = InputValidation(this)
    }

    // This method is to empty all input edit text
    fun emptyInputEditText() {
        binding.teditMail.text = null
        binding.teditPassword.text = null
    }
}
