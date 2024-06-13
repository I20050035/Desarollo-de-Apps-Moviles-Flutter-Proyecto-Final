package com.miappsoft.biblioteca.Libros

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
import com.miappsoft.biblioteca.R

class AgregarLibro : AppCompatActivity() {

    private lateinit var uid_Usuario: TextView
    private lateinit var correo_Usuario: TextView
    private lateinit var fecha_Hora_actual: TextView
    private lateinit var tituloLibroET: EditText
    private lateinit var autorLibroET: EditText
    private lateinit var generoLibroET: EditText
    private lateinit var añoPublicacionLibroET: EditText
    private lateinit var numero_PaginasLibroET: EditText
    private lateinit var cantidadDisponibleLibroET: EditText
    private lateinit var btnCreateLibro: Button

    private lateinit var bdFirebase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_libro)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Agregar Libro"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        // Obtener datos del intent
        obtenerDatos()
        obtenerFechaHoraActual()
        // Configurar el botón para llamar al método agregar
        btnCreateLibro.setOnClickListener {
            agregarLibro()
        }


    }

    private fun inicializarVariables() {
        uid_Usuario = findViewById(R.id.uid_Usuario)
        correo_Usuario = findViewById(R.id.correo_Usuario)
        fecha_Hora_actual = findViewById(R.id.fecha_Hora_actual)

        tituloLibroET = findViewById(R.id.tituloLibroET)
        autorLibroET = findViewById(R.id.autorLibroET)
        generoLibroET = findViewById(R.id.generoLibroET)
        añoPublicacionLibroET = findViewById(R.id.año_PublicaciónLibroET)
        numero_PaginasLibroET = findViewById(R.id.numero_PaginasLibroET)
        cantidadDisponibleLibroET = findViewById(R.id.cantidad_DisponibleLibroET)
        btnCreateLibro = findViewById(R.id.btnCreateLibro)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }

    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_Usuario.text = uidRecuperado
        correo_Usuario.text = correoRecuperado
    }

    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actual.text = fechaHoraRegistro
    }



    private fun agregarLibro() {
        val uidUsuarioText = uid_Usuario.text.toString()
        val correoUsuarioText = correo_Usuario.text.toString()
        val fechaHoraActualText = fecha_Hora_actual.text.toString()
        val titulo = tituloLibroET.text.toString()
        val autor = autorLibroET.text.toString()
        val genero = generoLibroET.text.toString()
        val añoPublicacion = añoPublicacionLibroET.text.toString()
        val numero_Paginas = numero_PaginasLibroET.text.toString()
        val cantidadDisponible = cantidadDisponibleLibroET.text.toString()

        // Validar Datos
        if (uidUsuarioText.isNotEmpty() && correoUsuarioText.isNotEmpty() && fechaHoraActualText.isNotEmpty() && titulo.isNotEmpty() && autor.isNotEmpty() && genero.isNotEmpty() &&
            añoPublicacion.isNotEmpty() && numero_Paginas.isNotEmpty() && cantidadDisponible.isNotEmpty()) {

            val libro = Libro(
                uidUsuario = uidUsuarioText,
                idLibro = correoUsuarioText +" / "+ fechaHoraActualText,
                titulo = titulo,
                autor = autor,
                genero = genero,
                añoPublicacion = añoPublicacion,
                numeroPaginas = numero_Paginas + " " + "Paginas",
                cantidadDisponible = cantidadDisponible + " " + "Unidades"
            )

            val libroUsuario = bdFirebase.push().key
            val nombreBD = "Libros"
            if (libroUsuario != null) {
                bdFirebase.child(nombreBD).child(libroUsuario).setValue(libro)
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
