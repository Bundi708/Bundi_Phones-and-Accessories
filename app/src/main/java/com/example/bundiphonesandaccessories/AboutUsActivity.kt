package com.example.bundiphonesandaccessories

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class AboutUsActivity : AppCompatActivity() {

    lateinit var tts: TextToSpeech
    lateinit var speechText : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_us)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //code comes here

        //create variables for the views
        val aboutTxt : TextView = findViewById(R.id.aboutTxt)
        val listenBtn : Button = findViewById(R.id.listenBtn)

        //initializa the tts (TextToSpeech)
        tts = TextToSpeech(this){
            if (it == TextToSpeech.SUCCESS){
                tts.language = Locale.US
            }
        }
        //end of tts initialization

        //onclickListener for listen btn
        listenBtn.setOnClickListener {
            val text = aboutTxt.text.toString()

            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
        //end of listen btn

        //find views for speech to text
        val speakBtn = findViewById<Button>(R.id.speakBtn)
        speechText = findViewById(R.id.speechTxt)

        //
        speakBtn.setOnClickListener {
            speakNow()
        }




    }

    //stop the tts from speaking after closing the activity

    override fun onDestroy () {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
    //end of tts cleanup function

    private fun speakNow() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now...")

        try {
            startActivityForResult(intent, 100)
        }catch (e: Exception){
            Toast.makeText(this, "Speech Recognition is not supported", Toast.LENGTH_LONG).show()
        }
    }
    //end of speak Noe function

    //capture the speech entered
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data!= null){
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            speechText.text = result?.get(0) ?: "Could not recognize speech"
        }
    }
}