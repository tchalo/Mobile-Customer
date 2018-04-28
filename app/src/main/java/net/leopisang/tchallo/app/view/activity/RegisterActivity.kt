package net.leopisang.tchallo.app.view.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import kotlinx.android.synthetic.main.activity_register.*
import net.leopisang.tchallo.R
import android.R.string.cancel
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initial()

        btnSignIn.setOnClickListener { loginScreen() }
        btnSignUp.setOnClickListener { attemptRegister() }
    }

    private fun loginScreen(){
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun initial() {
        //llBackgroundLogin.setBackgroundColor(resources.getColor(R.color.apps_color))
        ivRegisterLogo.setImageResource(R.mipmap.apps_logo)
    }

    private fun attemptRegister() {
        // Reset errors.
        name.error = null
        number.error = null
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        // Store values at the time of the login attempt.
        val nameStr = name.text.toString()
        val numberStr = number.text.toString()
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()
        val confirmStr = confirmpassword.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid confirm password, if the user entered password.
        if(!TextUtils.isEmpty(passwordStr) && isPasswordValid(passwordStr)){
            if(TextUtils.isEmpty(confirmStr)){
                confirmpassword.error = getString(R.string.error_field_required)
                focusView = confirmpassword
                cancel = true
            }
            else if(!isConfirmPasswordValid(confirmStr, passwordStr)){
                confirmpassword.error = getString(R.string.error_invalid_confirm_password)
                focusView = confirmpassword
                cancel = true
            }
        }

        // Check for a valid password, if the user entered one.
        if(!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)){
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        //Check for valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        //Check whether the phone number is empty or not.
        if (TextUtils.isEmpty(numberStr)) {
            number.error = getString(R.string.error_field_required)
            focusView = number
            cancel = true
        } //Check valid phone number?

        //Check whether the name is empty or not.
        if (TextUtils.isEmpty(nameStr)) {
            name.error = getString(R.string.error_field_required)
            focusView = name
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user register attempt.
            //Utils.hideKeyboard(this)
            //showProgress(true)
            //checkRegister(emailStr, passwordStr)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }

    private fun isConfirmPasswordValid(confirmPassword: String, password: String): Boolean{
        return confirmPassword == password
    }

    //DO you want to exit?
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setMessage("Do you want to Exit?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            //if user pressed "yes", then he is allowed to exit from application
            finish()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            //if user select "No", just cancel this dialog and continue with app
            dialog.cancel()
        })
        val alert = builder.create()
        alert.show()
    }

}
