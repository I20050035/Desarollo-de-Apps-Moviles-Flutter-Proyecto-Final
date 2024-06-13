package com.miappsoft.biblioteca.Autores

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miappsoft.biblioteca.R

class Actualizar_Autor : AppCompatActivity() {

    lateinit var id_Autores_A: TextView
    lateinit var uid_UsuarioAutores_A: TextView
    lateinit var correo_UsuarioAutores_A: TextView
    lateinit var fecha_Hora_RegistroAutores_A: TextView

    lateinit var nombresAutor_A: EditText
    lateinit var apellidosAutor_A: EditText
    lateinit var fecha_Nacimiento_A: EditText
    lateinit var pais_Nacimiento_A: EditText
    lateinit var libro_Escrito_A: EditText

    lateinit var btnUpdateAutor: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Autores_R: String
    lateinit var uid_UsuarioAutores_R: String
    lateinit var correo_UsuarioAutores_R: String
    lateinit var fecha_Hora_RegistroAutores_R: String
    lateinit var nombresAutor_R: String
    lateinit var apellidosAutor_R: String
    lateinit var fecha_Nacimiento_R: String
    lateinit var pais_Nacimiento_R: String
    lateinit var libro_Escrito_R: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_autor)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdateAutor.setOnClickListener{
            actualizarRegistroAutorBD()
        }

    }

    private fun inicializarVistas() {
        id_Autores_A = findViewById(R.id.id_Autores_A)
        uid_UsuarioAutores_A = findViewById(R.id.uid_UsuarioAutores_A)
        correo_UsuarioAutores_A = findViewById(R.id.correo_UsuarioAutores_A)
        fecha_Hora_RegistroAutores_A = findViewById(R.id.fecha_Hora_RegistroAutores_A)

        nombresAutor_A = findViewById(R.id.nombresAutor_A)
        apellidosAutor_A = findViewById(R.id.apellidosAutor_A)
        fecha_Nacimiento_A = findViewById(R.id.fecha_Nacimiento_A)
        pais_Nacimiento_A = findViewById(R.id.pais_Nacimiento_A)
        libro_Escrito_A = findViewById(R.id.libro_Escrito_A)
        btnUpdateAutor = findViewById(R.id.btnUpdateAutor)
    }


    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Autores_R = intent.getString("idAutor") ?: ""
            uid_UsuarioAutores_R = intent.getString("uidUsuarioAutor") ?: ""
            correo_UsuarioAutores_R = intent.getString("correo_UsuarioAutor") ?: ""
            fecha_Hora_RegistroAutores_R = intent.getString("fecha_RegistroAutor") ?: "" // aquí iba fecha_Hora_Actual
            nombresAutor_R = intent.getString("nombresAutor") ?: ""
            apellidosAutor_R = intent.getString("apellidosAutor") ?: ""
            fecha_Nacimiento_R = intent.getString("fechaNacimiento") ?: ""
            pais_Nacimiento_R = intent.getString("paisNacimiento") ?: ""
            libro_Escrito_R = intent.getString("libroEscrito") ?: ""
        }

    }

    private fun setearDatos() {
        id_Autores_A.text = id_Autores_R
        uid_UsuarioAutores_A.text = uid_UsuarioAutores_R
        correo_UsuarioAutores_A.text = correo_UsuarioAutores_R
        fecha_Hora_RegistroAutores_A.text = fecha_Hora_RegistroAutores_R

        nombresAutor_A.setText(nombresAutor_R)
        apellidosAutor_A.setText(apellidosAutor_R)
        fecha_Nacimiento_A.setText(fecha_Nacimiento_R)
        pais_Nacimiento_A.setText(pais_Nacimiento_R)
        libro_Escrito_A.setText(libro_Escrito_R)
    }


    private fun actualizarRegistroAutorBD() {
        val nombreAutorActualizar = nombresAutor_A.text.toString()
        val apellidosAutorActualizar  = apellidosAutor_A.text.toString()
        val fechaNacimientoActualizar  = fecha_Nacimiento_A.text.toString()
        val paisNacimientoActualizar  = pais_Nacimiento_A.text.toString()
        val libroEscritoActualizar  = libro_Escrito_A.text.toString()

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Autores")

        // Consulta
        val query = databaseReference.orderByChild("idAutor").equalTo(id_Autores_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("nombresAutor").setValue(nombreAutorActualizar)
                    ds.ref.child("apellidosAutor").setValue(apellidosAutorActualizar)
                    ds.ref.child("fechaNacimiento").setValue(fechaNacimientoActualizar)
                    ds.ref.child("paisNacimiento").setValue(paisNacimientoActualizar)
                    ds.ref.child("libroEscrito").setValue(libroEscritoActualizar)
                }

                Toast.makeText(this@Actualizar_Autor, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
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