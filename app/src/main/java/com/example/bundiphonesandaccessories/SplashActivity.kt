package com.example.bundiphonesandaccessories

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //code comes here
        //find views
        val splashImg : ImageView = findViewById(R.id.splashImg)
        val splashTxt : TextView = findViewById(R.id.splash2Txt)

        //animate the views
        splashImg.alpha = 0f
        splashImg.animate().alpha(1f).setDuration(3000).start()

        splashTxt.alpha = 0f
        splashTxt.animate().alpha(1f).setDuration(4000).start()

        //open activity main automatically after 5 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()//prevent the user from seeing the splash again
        },5000)

    }
}