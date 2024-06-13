package com.miappsoft.biblioteca.Editoriales

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

class AgregarEditorial : AppCompatActivity() {

    private lateinit var uid_UsuarioEditoriales: TextView
    private lateinit var correo_UsuarioEditoriales: TextView
    private lateinit var fecha_Hora_actualEditoriales: TextView
    private lateinit var nombresEditorialET: EditText
    private lateinit var paisEditorialET: EditText
    private lateinit var año_FundacionEditorialET: EditText
    private lateinit var libro_EditorialET: EditText

    private lateinit var btnCreateEditorial: Button

    private lateinit var bdFirebase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_editorial)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Editorial"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        obtenerDatos()
        obtenerFechaHoraActual()

        btnCreateEditorial.setOnClickListener{
            agregarEditorial()
        }


    }

    private fun inicializarVariables() {
        uid_UsuarioEditoriales = findViewById(R.id.uid_UsuarioEditoriales)
        correo_UsuarioEditoriales = findViewById(R.id.correo_UsuarioEditoriales)
        fecha_Hora_actualEditoriales = findViewById(R.id.fecha_Hora_actualEditoriales)

        nombresEditorialET = findViewById(R.id.nombresEditorialET)
        paisEditorialET = findViewById(R.id.paisEditorialET)
        año_FundacionEditorialET = findViewById(R.id.año_FundacionEditorialET)
        libro_EditorialET = findViewById(R.id.libro_EditorialET)
        btnCreateEditorial = findViewById(R.id.btnCreateEditorial)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }

    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_UsuarioEditoriales.text = uidRecuperado
        correo_UsuarioEditoriales.text = correoRecuperado
    }


    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actualEditoriales.text = fechaHoraRegistro
    }


    private fun agregarEditorial() {
        val uidUsuarioEditorialesText = uid_UsuarioEditoriales.text.toString()
        val correoUsuarioEditorialesText = correo_UsuarioEditoriales.text.toString()
        val fechaHoraActualEditorialesText = fecha_Hora_actualEditoriales.text.toString()
        val nombreEditorial = nombresEditorialET.text.toString()
        val paisEditorial = paisEditorialET.text.toString()
        val añoFundacionEditorial = año_FundacionEditorialET.text.toString()
        val libroEditorial = libro_EditorialET.text.toString()

        // Validar Datos
        if (uidUsuarioEditorialesText.isNotEmpty() && correoUsuarioEditorialesText.isNotEmpty() && fechaHoraActualEditorialesText.isNotEmpty() && nombreEditorial.isNotEmpty() && paisEditorial.isNotEmpty() && añoFundacionEditorial.isNotEmpty() &&
            libroEditorial.isNotEmpty()) {

            val editorial = Editorial(
                uidUsuarioEditoriales = uidUsuarioEditorialesText,
                idEditorial = correoUsuarioEditorialesText +" / "+ fechaHoraActualEditorialesText,
                nombresEditoriales = nombreEditorial,
                paisEditorial = paisEditorial,
                año_FundacionEditorial = añoFundacionEditorial,
                libro_Editorial = libroEditorial
            )

            val editorialUsuario = bdFirebase.push().key
            val nombreBD = "Editoriales"
            if (editorialUsuario != null) {
                bdFirebase.child(nombreBD).child(editorialUsuario).setValue(editorial)
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