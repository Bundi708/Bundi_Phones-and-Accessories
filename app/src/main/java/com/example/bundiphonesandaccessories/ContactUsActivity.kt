package com.example.bundiphonesandaccessories

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_us)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //code comes here

        val smsBtn : Button = findViewById(R.id.sms_btn)
        smsBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:0702689807")
                putExtra("sms_body", "Hello this is a text message")
            }
            startActivity(intent)
        }
        //end of sms btn

        //val for whatsApp
        val whatsApp_btn : Button = findViewById(R.id.whatsapp_btn)
        whatsApp_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/254702689807"))
            startActivity(intent)
        }

        //end of whatsApp btn

        //call btn

        val callBtn: Button = findViewById(R.id.call_btn)
        callBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0702689807"))
            startActivity(intent)
        }
    }
}