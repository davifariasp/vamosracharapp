package com.example.vamosrachar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener{
    private var tts:TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tts = TextToSpeech(this, this)

        //botao de falar
        val fab_sound: FloatingActionButton = findViewById<FloatingActionButton>(R.id.sound_fab)
        //botao de compartilhar
        val fab_share: FloatingActionButton = findViewById<FloatingActionButton>(R.id.share_fab)

        //campo resultado
        var result: TextView = findViewById(R.id.result)

        //pegando os campos de texto digitado
        var f_people: EditText = findViewById(R.id.people_field)
        var f_cash: EditText = findViewById(R.id.cash_field)


        //alteracoes
        f_people.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val c = f_cash.text.toString()
                val p = f_people.text.toString()

                if(c.isEmpty() || p.isEmpty()){
                    Log.e("pdm", "Valor em branco")
                    result.setText("0,00")
                } else if ((c.toInt()).equals(0) || (p.toInt()).equals(0) ) {
                    Log.e("pdm", "Divisão por 0")
                    result.setText("0,00")
                } else {
                    Log.v("pdm", "Você alterou o valor")
                    val valor = (c.toFloat() / p.toFloat())
                    val df = DecimalFormat("#.##")
                    result.setText("${(df.format(valor)).toString()}")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        f_cash.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val c = f_cash.text.toString()
                val p = f_people.text.toString()

                if(c.isEmpty() || p.isEmpty()){
                    Log.e("pdm", "Valor em branco")
                    result.setText("0,00")
                } else if ((c.toInt()).equals(0) || (p.toInt()).equals(0) ) {
                    Log.e("pdm", "Divisão por 0")
                    result.setText("0,00")
                } else {
                    Log.v("pdm", "Você alterou o valor")
                    val valor = (c.toFloat() / p.toFloat())
                    val df = DecimalFormat("#.##")
                    result.setText("${(df.format(valor)).toString()}")
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })


        //botoes
        fab_sound.setOnClickListener {
            val falar = ("Deu " + result.text.toString() + "reais para cada!")

            tts!!.speak(falar, TextToSpeech.QUEUE_FLUSH, null,"")
        }

        fab_share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Ficou R$ ${result.text.toString()} para cada, faz o pix!")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.forLanguageTag("pt-BR"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                Log.v("TTS","Deu certo!")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }
}

