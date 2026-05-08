package com.example.bundiphonesandaccessories

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.loopj.android.http.RequestParams

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //code comes here
        //variables for all the views

        val usernameEditText : EditText = findViewById(R.id.username)
        val emailEditText : EditText = findViewById(R.id.email)
        val passwordEditText : EditText = findViewById(R.id.password)
        val phoneEditText : EditText = findViewById(R.id.phone)
        val signInTxt : TextView = findViewById(R.id.signin_txt)
        val signUpBtn : Button = findViewById(R.id.signup)

        //intent to go to the sign in activity

        signInTxt.setOnClickListener {
            val intent = Intent(applicationContext, SignInActivity::class.java)
            startActivity(intent)
        }
        //end of intent to sign in activity

        signUpBtn.setOnClickListener {
            val userData = RequestParams()

            //retrieve data from the views
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username!="" && email!="" && phone!="" && password!=""){
                //add the data to the container
                userData.put("username", username)
                userData.put("email", email)
                userData.put("phone",phone)
                userData.put("password", password)

                //api sign up endpoint
                val api = "https://bundi.alwaysdata.net/api/signup"

                //get the api helper

                val helper = ApiHelper(applicationContext)
                helper.post(api, userData)
            }else{
                Toast.makeText(applicationContext,"Please fill out the form completely", Toast.LENGTH_SHORT).show()
            }

        }


    }
}