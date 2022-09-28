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
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.util.*

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //botao de falar
        val fab_sound: FloatingActionButton = findViewById<FloatingActionButton>(R.id.sound_fab)
        //botao de compartilhar
        val fab_share: FloatingActionButton = findViewById<FloatingActionButton>(R.id.share_fab)
        //botao de informacao
        val fab_info: FloatingActionButton = findViewById<FloatingActionButton>(R.id.info_fab)

        //campo resultado
        var result: TextView = findViewById(R.id.result)

        //pegando os campos de texto digitado
        var f_people: EditText = findViewById(R.id.people_field)
        var f_cash: EditText = findViewById(R.id.cash_field)


        //alteracoes
        f_people.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val c = (f_cash.text).toString()
                val p = (f_people.text).toString()

                if(c.isEmpty() || p.isEmpty()){
                    Log.e("pdm", "Valor em branco")
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
                val c = (f_cash.text).toString()
                val p = (f_people.text).toString()

                if(c.isEmpty() || p.isEmpty()){
                    Log.e("pdm", "Valor em branco")
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

        fab_info.setOnClickListener {
            //todo info
        }


    }
}

