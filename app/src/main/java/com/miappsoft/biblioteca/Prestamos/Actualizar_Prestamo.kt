package com.miappsoft.biblioteca.Prestamos

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miappsoft.biblioteca.R

class Actualizar_Prestamo : AppCompatActivity() {

    lateinit var id_Prestamos_A: TextView
    lateinit var uid_UsuarioPrestamos_A: TextView
    lateinit var correo_UsuarioPrestamos_A: TextView
    lateinit var fecha_Hora_RegistroPrestamos_A: TextView

    lateinit var nombreSolicitante_A: EditText
    lateinit var libro_A: EditText
    lateinit var fecha_Préstamo_A: EditText
    lateinit var fecha_Devolución_A: EditText
    lateinit var estadoPrestamo_A: EditText

    lateinit var btnUpdatePrestamo: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Prestamo_R: String
    lateinit var uid_UsuarioPrestamo_R: String
    lateinit var correo_UsuarioPrestamo_R: String
    lateinit var fecha_RegistroPrestamo_R: String
    lateinit var nombreSolicitante_R: String
    lateinit var libro_R: String
    lateinit var fechaPrestamo_R: String
    lateinit var fechaDevolucion_R: String
    lateinit var estadoPrestamo_R: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_prestamo)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdatePrestamo.setOnClickListener{
            actualizarRegistroLibroBD()
        }

    }


    private fun inicializarVistas() {
        id_Prestamos_A = findViewById(R.id.id_Prestamos_A)
        uid_UsuarioPrestamos_A = findViewById(R.id.uid_UsuarioPrestamos_A)
        correo_UsuarioPrestamos_A = findViewById(R.id.correo_UsuarioPrestamos_A)
        fecha_Hora_RegistroPrestamos_A = findViewById(R.id.fecha_Hora_RegistroPrestamos_A)

        nombreSolicitante_A = findViewById(R.id.nombreSolicitante_A)
        libro_A = findViewById(R.id.libro_A)
        fecha_Préstamo_A = findViewById(R.id.fecha_Préstamo_A)
        fecha_Devolución_A = findViewById(R.id.fecha_Devolución_A)
        estadoPrestamo_A = findViewById(R.id.estadoPrestamo_A)
        btnUpdatePrestamo = findViewById(R.id.btnUpdatePrestamo)
    }


    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Prestamo_R = intent.getString("idPrestamo") ?: ""
            uid_UsuarioPrestamo_R = intent.getString("uidUsuarioPrestamo") ?: ""
            correo_UsuarioPrestamo_R = intent.getString("correo_UsuarioPrestamo") ?: ""
            fecha_RegistroPrestamo_R = intent.getString("fecha_RegistroPrestamo") ?: "" // aquí iba fecha_Hora_Actual
            nombreSolicitante_R = intent.getString("nombreSolicitante") ?: ""
            libro_R = intent.getString("libro") ?: ""
            fechaPrestamo_R = intent.getString("fechaPrestamo") ?: ""
            fechaDevolucion_R = intent.getString("fechaDevolucion") ?: ""
            estadoPrestamo_R = intent.getString("estadoPrestamo") ?: ""
        }
    }

    private fun setearDatos() {
        id_Prestamos_A.text = id_Prestamo_R
        uid_UsuarioPrestamos_A.text = uid_UsuarioPrestamo_R
        correo_UsuarioPrestamos_A.text = correo_UsuarioPrestamo_R
        fecha_Hora_RegistroPrestamos_A.text = fecha_RegistroPrestamo_R

        nombreSolicitante_A.setText(nombreSolicitante_R)
        libro_A.setText(libro_R)
        fecha_Préstamo_A.setText(fechaPrestamo_R)
        fecha_Devolución_A.setText(fechaDevolucion_R)
        estadoPrestamo_A.setText(estadoPrestamo_R)
    }

    private fun actualizarRegistroLibroBD() {
        val nombreSolicitanteActualizar = nombreSolicitante_A.text.toString()
        val libroActualizar  = libro_A.text.toString()
        val fechaPrestamoActualizar  = fecha_Préstamo_A.text.toString()
        val fechaDevolucionActualizar  = fecha_Devolución_A.text.toString()
        val estadoPrestamoActualizar  = estadoPrestamo_A.text.toString()

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Prestamos")

        // Consulta
        val query = databaseReference.orderByChild("idPrestamo").equalTo(id_Prestamo_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("nombreSolicitante").setValue(nombreSolicitanteActualizar)
                    ds.ref.child("libro").setValue(libroActualizar)
                    ds.ref.child("fechaPrestamo").setValue(fechaPrestamoActualizar)
                    ds.ref.child("fechaDevolucion").setValue(fechaDevolucionActualizar)
                    ds.ref.child("estadoPrestamo").setValue(estadoPrestamoActualizar)
                }

                Toast.makeText(this@Actualizar_Prestamo, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}