package com.miappsoft.biblioteca.Prestamos

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

class AgregarPrestamo : AppCompatActivity() {

    private lateinit var uid_UsuarioPrestamos: TextView
    private lateinit var correo_UsuarioPrestamos: TextView
    private lateinit var fecha_Hora_actualPrestamos: TextView
    private lateinit var nombreSolicitanteET: EditText
    private lateinit var libroET: EditText
    private lateinit var fecha_PréstamoET: EditText
    private lateinit var fecha_DevoluciónET: EditText
    private lateinit var estadoPrestamoET: EditText
    private lateinit var btnCreatePrestamo: Button

    private lateinit var bdFirebase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_prestamo)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Agregar Prestamos"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVariables()
        obtenerDatos()
        obtenerFechaHoraActual()
        // Configurar el botón para llamar al método agregar
        btnCreatePrestamo.setOnClickListener {
            agregarPrestamo()
        }

    }



    private fun inicializarVariables() {
        // Inicialización de las variables
        uid_UsuarioPrestamos = findViewById(R.id.uid_UsuarioPrestamos)
        correo_UsuarioPrestamos = findViewById(R.id.correo_UsuarioPrestamos)
        fecha_Hora_actualPrestamos = findViewById(R.id.fecha_Hora_actualPrestamos)
        nombreSolicitanteET = findViewById(R.id.nombreSolicitanteET)
        libroET = findViewById(R.id.libroET)
        fecha_PréstamoET = findViewById(R.id.fecha_PréstamoET)
        fecha_DevoluciónET = findViewById(R.id.fecha_DevoluciónET)
        estadoPrestamoET = findViewById(R.id.estadoPrestamoET)
        btnCreatePrestamo = findViewById(R.id.btnCreatePrestamo)
        bdFirebase = FirebaseDatabase.getInstance().reference
    }

    // Método para obtener datos
    private fun obtenerDatos() {
        val uidRecuperado = intent.getStringExtra("Uid")
        val correoRecuperado = intent.getStringExtra("Correo")

        uid_UsuarioPrestamos.text = uidRecuperado
        correo_UsuarioPrestamos.text = correoRecuperado
    }

    private fun obtenerFechaHoraActual() {
        val fechaHoraRegistro = SimpleDateFormat("dd-MM-yyyy/HH:mm:ss a", Locale.getDefault()).format(System.currentTimeMillis())
        fecha_Hora_actualPrestamos.text = fechaHoraRegistro
    }

    private fun agregarPrestamo() {
        val uidUsuarioPrestamosText = uid_UsuarioPrestamos.text.toString()
        val correoUsuarioPrestamosText = correo_UsuarioPrestamos.text.toString()
        val fechaHoraActualPrestamosText = fecha_Hora_actualPrestamos.text.toString()
        val nombreSolicitante = nombreSolicitanteET.text.toString()
        val libro = libroET.text.toString()
        val fechaPrestamo = fecha_PréstamoET.text.toString()
        val fechaDevolucion = fecha_DevoluciónET.text.toString()
        val estadoPrestamo = estadoPrestamoET.text.toString()

        // Validar Datos
        if (uidUsuarioPrestamosText.isNotEmpty() &&
            correoUsuarioPrestamosText.isNotEmpty() &&
            fechaHoraActualPrestamosText.isNotEmpty() &&
            nombreSolicitante.isNotEmpty() &&
            libro.isNotEmpty() &&
            fechaPrestamo.isNotEmpty() &&
            fechaDevolucion.isNotEmpty() &&
            estadoPrestamo.isNotEmpty()) {

            val prestamo = Prestamo(
                idPrestamo = correoUsuarioPrestamosText + " / " + fechaHoraActualPrestamosText,
                nombreSolicitante = nombreSolicitante,
                libro = libro,
                fechaPrestamo = fechaPrestamo,
                fechaDevolucion = fechaDevolucion,
                estadoPrestamo = estadoPrestamo
            )

            val PrestamoUsuario = bdFirebase.push().key
            val nombreBD = "Prestamos"
            if (PrestamoUsuario != null) {
                bdFirebase.child(nombreBD).child(PrestamoUsuario).setValue(prestamo)
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