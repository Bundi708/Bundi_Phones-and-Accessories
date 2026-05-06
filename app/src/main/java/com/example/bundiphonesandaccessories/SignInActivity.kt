package com.example.bundiphonesandaccessories

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //code comes here

        //variables fro all the views
        val emailEditText : EditText = findViewById(R.id.email)
        val passwordEditText : EditText = findViewById(R.id.password)
        val loginBtn : Button = findViewById(R.id.login_btn)
        val signupTxt : TextView = findViewById(R.id.signup_txt)

        //intent to open sign uo activity
        signupTxt.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))


        }
        //end of sign up activity

        // handle log on

        loginBtn.setOnClickListener {
            //create user data variable

//            val userData = RequestParams()

            //retrieve the value i.e email and password from the views

            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            //add email and password to userdata variable

//            userData.put("email", email)
//            userData.put("password", password)

            //API sign in end points
            val api = "https://bundi.alwaysdata.net/api/signin"

            //send userdata to api using api helper
//            val helper = ApiHelper(applicationContext)
//            helper.post_login(api, userData)



        }
    }
}