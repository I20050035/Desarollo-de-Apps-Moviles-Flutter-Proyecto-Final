package com.miappsoft.biblioteca

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity




class MainActivity : AppCompatActivity() {

   private lateinit var btn_Login: Button
   private lateinit var btn_Registro: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_Login = findViewById(R.id.btn_Login) // Si estás trabajando en una actividad

        btn_Registro = findViewById(R.id.btn_Registro) // Si estás trabajando en una actividad


        btn_Login.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btn_Registro.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }
}