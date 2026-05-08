package com.example.bundiphonesandaccessories

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize mobile ads
        MobileAds.initialize(applicationContext)
        val adRequest = AdRequest.Builder().build()
        val adView: AdView = findViewById(R.id.adView)
        adView.loadAd(adRequest)

        // Find Buttons
        val signUpBtn: Button = findViewById(R.id.signup)
        val signInBtn: Button = findViewById(R.id.signin)
        val aboutUsBtn: Button = findViewById(R.id.aboutus)
        val contactUsBtn: Button = findViewById(R.id.contactus)
        val logOutBtn: Button = findViewById(R.id.logout)
        val usernameTxt: TextView = findViewById(R.id.username_txt)

        // Button Listeners
        signUpBtn.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }

        signInBtn.setOnClickListener {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }

        aboutUsBtn.setOnClickListener {
            startActivity(Intent(applicationContext, AboutUsActivity::class.java))
        }

        contactUsBtn.setOnClickListener {
            startActivity(Intent(applicationContext, ContactUsActivity::class.java))
        }

        logOutBtn.setOnClickListener {
            getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply()
            recreate()
        }

        // Session Management
        val prefs = getSharedPreferences("user_session", MODE_PRIVATE)
        val username = prefs.getString("username", "")

        if (username.isNullOrEmpty()) {
            logOutBtn.visibility = View.GONE
            usernameTxt.visibility = View.GONE
            signInBtn.visibility = View.VISIBLE
            signUpBtn.visibility = View.VISIBLE
        } else {
            signInBtn.visibility = View.GONE
            signUpBtn.visibility = View.GONE
            logOutBtn.visibility = View.VISIBLE
            usernameTxt.visibility = View.VISIBLE
            usernameTxt.text = "Welcome $username"
        }

        // --- LOAD PRODUCTS ---
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        val progressBar: ProgressBar = findViewById(R.id.progress_bar)
        
        val apiHelper = ApiHelper(this)
        val productsUrl = "https://bundi.alwaysdata.net/api/get_products"
        
        apiHelper.loadProducts(productsUrl, recyclerView, progressBar)
    }
}
