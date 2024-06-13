package com.miappsoft.biblioteca.Autores

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miappsoft.biblioteca.R

class AgregarAutor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_autor)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Autor"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}