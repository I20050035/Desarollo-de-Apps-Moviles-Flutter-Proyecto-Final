package com.miappsoft.biblioteca.Categorias

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.miappsoft.biblioteca.R
import com.miappsoft.biblioteca.Reseñas.Resena
import java.text.SimpleDateFormat
import java.util.Locale

class AgregarCategoria : AppCompatActivity() {

    private lateinit var uid_UsuarioCategorias: TextView
    private lateinit var correo_UsuarioCategorias: TextView
    private lateinit var fecha_Hora_actualCategorias: TextView
    private lateinit var libroCategoriaET: EditText
    private lateinit var nombreCategoriaET: EditText
    private lateinit var descripcionET: EditText


    private lateinit var btnCreateCategoria: Button

    private lateinit var bdFirebase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_categoria)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Categoria"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        obtenerDatos()
        obtenerFechaHoraActual()

        btnCreateCategoria.setOnClickListener{
            agregarCategoria()
        }

    }

    private fun inicializarVariables() {
        uid_UsuarioCategorias = findViewById(R.id.uid_UsuarioCategorias)
        correo_UsuarioCategorias = findViewById(R.id.correo_UsuarioCategorias)
        fecha_Hora_actualCategorias = findViewById(R.id.fecha_Hora_actualCategorias)

        libroCategoriaET = findViewById(R.id.libroCategoriaET)
        nombreCategoriaET = findViewById(R.id.nombreCategoriaET)
        descripcionET = findViewById(R.id.descripcionET)
        btnCreateCategoria = findViewById(R.id.btnCreateCategoria)

        bdFirebase = FirebaseDatabase.getInstance().reference
    }


    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_UsuarioCategorias.text = uidRecuperado
        correo_UsuarioCategorias.text = correoRecuperado
    }


    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actualCategorias.text = fechaHoraRegistro
    }


    private fun agregarCategoria() {
        val uidUsuarioCategoriasText = uid_UsuarioCategorias.text.toString()
        val correoUsuarioCategoriasText = correo_UsuarioCategorias.text.toString()
        val fechaHoraActualCategoriasText = fecha_Hora_actualCategorias.text.toString()
        val libroCategoria = libroCategoriaET.text.toString()
        val nombreCategoria = nombreCategoriaET.text.toString()
        val descripcion = descripcionET.text.toString()



        // Validar Datos
        if (uidUsuarioCategoriasText.isNotEmpty() && correoUsuarioCategoriasText.isNotEmpty() && fechaHoraActualCategoriasText.isNotEmpty() && libroCategoria.isNotEmpty() && nombreCategoria.isNotEmpty() && descripcion.isNotEmpty()) {

            val categoria = Categoria(
                uidUsuarioCategorias = uidUsuarioCategoriasText,
                idCategoria = correoUsuarioCategoriasText +" / "+ fechaHoraActualCategoriasText,
                libroCategoria = libroCategoria,
                nombreCategoria = nombreCategoria,
                descripcion = descripcion
            )

            val categoriaUsuario = bdFirebase.push().key
            val nombreBD = "Categorias"
            if (categoriaUsuario != null) {
                bdFirebase.child(nombreBD).child(categoriaUsuario).setValue(categoria)
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