package com.miappsoft.biblioteca.Reseñas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.miappsoft.biblioteca.R
import java.text.SimpleDateFormat
import java.util.Locale

class AgregarResena : AppCompatActivity() {

    private lateinit var uid_UsuarioReseñas: TextView
    private lateinit var correo_UsuarioReseñas: TextView
    private lateinit var fecha_Hora_actualReseñas: TextView
    private lateinit var libroReseñaET: EditText
    private lateinit var autorReseñaET: EditText
    private lateinit var fechaReseñaET: EditText
    private lateinit var calificacionET: EditText
    private lateinit var comentariosET: EditText


    private lateinit var btnCreateReseña: Button

    private lateinit var bdFirebase: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_resena)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Reseña"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        obtenerDatos()
        obtenerFechaHoraActual()

        btnCreateReseña.setOnClickListener{
            agregarReseña()
        }

    }

    private fun inicializarVariables() {
        uid_UsuarioReseñas = findViewById(R.id.uid_UsuarioReseñas)
        correo_UsuarioReseñas = findViewById(R.id.correo_UsuarioReseñas)
        fecha_Hora_actualReseñas = findViewById(R.id.fecha_Hora_actualReseñas)

        libroReseñaET = findViewById(R.id.libroReseñaET)
        autorReseñaET = findViewById(R.id.autorReseñaET)
        fechaReseñaET = findViewById(R.id.fechaReseñaET)
        calificacionET = findViewById(R.id.calificacionET)
        comentariosET = findViewById(R.id.comentariosET)
        btnCreateReseña = findViewById(R.id.btnCreateReseña)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }


    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_UsuarioReseñas.text = uidRecuperado
        correo_UsuarioReseñas.text = correoRecuperado
    }


    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actualReseñas.text = fechaHoraRegistro
    }


    private fun agregarReseña() {
        val uidUsuarioReseñasText = uid_UsuarioReseñas.text.toString()
        val correoUsuarioReseñasText = correo_UsuarioReseñas.text.toString()
        val fechaHoraActualReseñasText = fecha_Hora_actualReseñas.text.toString()
        val libroReseña = libroReseñaET.text.toString()
        val autorReseña = autorReseñaET.text.toString()
        val fechaReseña = fechaReseñaET.text.toString()
        val calificacion = calificacionET.text.toString()
        val comentarios = comentariosET.text.toString()


        // Validar Datos
        if (uidUsuarioReseñasText.isNotEmpty() && correoUsuarioReseñasText.isNotEmpty() && fechaHoraActualReseñasText.isNotEmpty() && libroReseña.isNotEmpty() && autorReseña.isNotEmpty() && fechaReseña.isNotEmpty() &&
            calificacion.isNotEmpty() && comentarios.isNotEmpty()) {

            val resena = Resena(
                uidUsuarioReseñas = uidUsuarioReseñasText,
                idReseña = correoUsuarioReseñasText +" / "+ fechaHoraActualReseñasText,
                libroReseña = libroReseña,
                autorReseña = autorReseña,
                fechaReseña = fechaReseña,
                calificacion = calificacion,
                comentarios = comentarios
            )

            val reseñaUsuario = bdFirebase.push().key
            val nombreBD = "Reseñas"
            if (reseñaUsuario != null) {
                bdFirebase.child(nombreBD).child(reseñaUsuario).setValue(resena)
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