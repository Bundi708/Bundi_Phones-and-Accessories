package com.example.bundiphonesandaccessories

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.loopj.android.http.RequestParams

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Get views from layout
        val productImage: ImageView = findViewById(R.id.payment_product_photo)
        val productName: TextView = findViewById(R.id.payment_product_name)
        val productDesc: TextView = findViewById(R.id.payment_product_description)
        val productCost: TextView = findViewById(R.id.payment_product_cost)
        val phoneNumberEditText: EditText = findViewById(R.id.phone_number)
        val payBtn: Button = findViewById(R.id.pay_btn)

        // 2. Retrieve data from Intent
        val name = intent.getStringExtra("product_name")
        val desc = intent.getStringExtra("product_description")
        val cost = intent.getIntExtra("product_cost", 0)
        val image = intent.getStringExtra("product_image")

        // 3. Populate Views
        productName.text = name
        productDesc.text = desc
        productCost.text = "Ksh $cost"

        val imageUrl = "https://bundi.alwaysdata.net/static/images/$image"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.mipmap.ic_launcher)
            .into(productImage)

        // 4. Handle Payment Button
        payBtn.setOnClickListener {
            val phone = phoneNumberEditText.text.toString().trim()
            if (phone.isEmpty()) {
                Toast.makeText(this, "Please enter your M-Pesa number", Toast.LENGTH_SHORT).show()
            } else {
                val apiHelper = ApiHelper(this)
                // Updated to match the Flask API endpoint name
                val mpesaUrl = "https://bundi.alwaysdata.net/api/mpesa_payment"
                val params = RequestParams().apply {
                    put("phone", phone)
                    put("amount", cost)
                    put("product_name", name)
                }
                apiHelper.post(mpesaUrl, params)
            }
        }
    }
}
