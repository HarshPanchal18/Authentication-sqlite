package com.example.authentication_with_sqlite

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class InputValidation(private val context: Context) {

    fun isInputEditTextFilled(tiEditText: TextInputEditText, tiInputLayout: TextInputLayout, message:String) : Boolean {
        // to check InputEditText filled
        val value=tiEditText.text.toString().trim()
        if(value.isEmpty()){
            tiInputLayout.error=message
            hideKeyboardFrom(tiEditText)
            return false
        }
        tiInputLayout.isErrorEnabled=false
        return true
    }

    // to check InputEditText has valid email
    fun isInputEditTextEmail(tiEditText: TextInputEditText, tiInputLayout: TextInputLayout, message:String) : Boolean {
        val value=tiEditText.text.toString().trim()
        if(value.isEmpty() or !Patterns.EMAIL_ADDRESS.matcher(value).matches()){
            tiInputLayout.error=message
            hideKeyboardFrom(tiEditText)
            return false
        }
        tiInputLayout.isErrorEnabled=false
        return true
    }

    // to check both InputEditText value matches
    fun isInputEditTextMatches(textInputEditText1: TextInputEditText, textInputEditText2: TextInputEditText, textInputLayout: TextInputLayout, message: String): Boolean {
        val value1 = textInputEditText1.text.toString().trim()
        val value2 = textInputEditText2.text.toString().trim()

        if (!value1.contentEquals(value2)) {
            textInputLayout.error = message
            hideKeyboardFrom(textInputEditText2)
            return false
        }
        textInputLayout.isErrorEnabled = false
        return true
    }

    private fun hideKeyboardFrom(view: View) { // to hide keyboard
        val imm=context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken,WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }
}
