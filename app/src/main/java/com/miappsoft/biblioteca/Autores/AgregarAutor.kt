package com.miappsoft.biblioteca.Autores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.miappsoft.biblioteca.Libros.Libro
import com.miappsoft.biblioteca.R

class AgregarAutor : AppCompatActivity() {

    private lateinit var uid_UsuarioAutores: TextView
    private lateinit var correo_UsuarioAutores: TextView
    private lateinit var fecha_Hora_actualAutores: TextView
    private lateinit var nombresAutorET: EditText
    private lateinit var apellidosAutorET: EditText
    private lateinit var fecha_NacimientoET: EditText
    private lateinit var pais_NacimientoET: EditText
    private lateinit var libro_EscritoET: EditText

    private lateinit var btnCreateAutor: Button

    private lateinit var bdFirebase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_autor)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Autor"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        obtenerDatos()
        obtenerFechaHoraActual()

        btnCreateAutor.setOnClickListener{
            agregarAutor()
        }

    }

    private fun inicializarVariables() {
        uid_UsuarioAutores = findViewById(R.id.uid_UsuarioAutores)
        correo_UsuarioAutores = findViewById(R.id.correo_UsuarioAutores)
        fecha_Hora_actualAutores = findViewById(R.id.fecha_Hora_actualAutores)

        nombresAutorET = findViewById(R.id.nombresAutorET)
        apellidosAutorET = findViewById(R.id.apellidosAutorET)
        fecha_NacimientoET = findViewById(R.id.fecha_NacimientoET)
        pais_NacimientoET = findViewById(R.id.pais_NacimientoET)
        libro_EscritoET = findViewById(R.id.libro_EscritoET)
        btnCreateAutor = findViewById(R.id.btnCreateAutor)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }

    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_UsuarioAutores.text = uidRecuperado
        correo_UsuarioAutores.text = correoRecuperado
    }


    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actualAutores.text = fechaHoraRegistro
    }



    private fun agregarAutor() {
        val uidUsuarioAutoresText = uid_UsuarioAutores.text.toString()
        val correoUsuarioAutoresText = correo_UsuarioAutores.text.toString()
        val fechaHoraActualAutoresText = fecha_Hora_actualAutores.text.toString()
        val nombresAutor = nombresAutorET.text.toString()
        val apellidosAutor = apellidosAutorET.text.toString()
        val fechaNacimiento = fecha_NacimientoET.text.toString()
        val paisNacimiento = pais_NacimientoET.text.toString()
        val libroEscrito = libro_EscritoET.text.toString()

        // Validar Datos
        if (uidUsuarioAutoresText.isNotEmpty() && correoUsuarioAutoresText.isNotEmpty() && fechaHoraActualAutoresText.isNotEmpty() && nombresAutor.isNotEmpty() && apellidosAutor.isNotEmpty() && fechaNacimiento.isNotEmpty() &&
            paisNacimiento.isNotEmpty() && libroEscrito.isNotEmpty()) {

            val autor = Autor(
                uidUsuarioAutor = uidUsuarioAutoresText,
                idAutor = correoUsuarioAutoresText +" / "+ fechaHoraActualAutoresText,
                nombresAutor = nombresAutor,
                apellidosAutor = apellidosAutor,
                fechaNacimiento = fechaNacimiento,
                paisNacimiento = paisNacimiento,
                libroEscrito = libroEscrito
            )

            val autorUsuario = bdFirebase.push().key
            val nombreBD = "Autores"
            if (autorUsuario != null) {
                bdFirebase.child(nombreBD).child(autorUsuario).setValue(autor)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Registro agregado exitosamente", Toast.LENGTH_SHORT).show()
                        onBackPressed()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error al agregar registro", Toast.LENGTH_SHORT).show()
                    }
            }
        } else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}