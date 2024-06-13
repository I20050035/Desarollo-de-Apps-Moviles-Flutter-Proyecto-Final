package com.miappsoft.biblioteca.Categorias

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

class Actualizar_Categoria : AppCompatActivity() {

    lateinit var id_Categorias_A: TextView
    lateinit var uid_UsuarioCategorias_A: TextView
    lateinit var correo_UsuarioCategorias_A: TextView
    lateinit var fecha_Hora_RegistroCategorias_A: TextView

    lateinit var libroCategoria_A: EditText
    lateinit var nombreCategoria_A: EditText
    lateinit var descripcion_A: EditText

    lateinit var btnUpdateCategoria: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Categorias_R: String
    lateinit var uid_UsuarioCategorias_R: String
    lateinit var correo_UsuarioCategorias_R: String
    lateinit var fecha_Hora_RegistroCategorias_R: String
    lateinit var libroCategoria_R: String
    lateinit var nombreCategoria_R: String
    lateinit var descripcion_R: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_categoria)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdateCategoria.setOnClickListener{
            actualizarRegistroCategoriaBD()
        }

    }


    private fun inicializarVistas() {
        id_Categorias_A = findViewById(R.id.id_Categorias_A)
        uid_UsuarioCategorias_A = findViewById(R.id.uid_UsuarioCategorias_A)
        correo_UsuarioCategorias_A = findViewById(R.id.correo_UsuarioCategorias_A)
        fecha_Hora_RegistroCategorias_A = findViewById(R.id.fecha_Hora_RegistroCategorias_A)

        libroCategoria_A = findViewById(R.id.libroCategoria_A)
        nombreCategoria_A = findViewById(R.id.nombreCategoria_A)
        descripcion_A = findViewById(R.id.descripcion_A)
        btnUpdateCategoria = findViewById(R.id.btnUpdateCategoria)

    }


    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Categorias_R = intent.getString("idCategoria") ?: ""
            uid_UsuarioCategorias_R = intent.getString("uidUsuarioCategorias") ?: ""
            correo_UsuarioCategorias_R = intent.getString("correo_UsuarioCategorias") ?: ""
            fecha_Hora_RegistroCategorias_R = intent.getString("fecha_RegistroCategorias") ?: "" // aquí iba fecha_Hora_Actual
            libroCategoria_R = intent.getString("libroCategoria") ?: ""
            nombreCategoria_R = intent.getString("nombreCategoria") ?: ""
            descripcion_R = intent.getString("descripcion") ?: ""


        }

    }

    private fun setearDatos() {
        id_Categorias_A.text = id_Categorias_R
        uid_UsuarioCategorias_A.text = uid_UsuarioCategorias_R
        correo_UsuarioCategorias_A.text = correo_UsuarioCategorias_R
        fecha_Hora_RegistroCategorias_A.text = fecha_Hora_RegistroCategorias_R

        libroCategoria_A.setText(libroCategoria_R)
        nombreCategoria_A.setText(nombreCategoria_R)
        descripcion_A.setText(descripcion_R)

    }

    private fun actualizarRegistroCategoriaBD() {
        val libroCategoriaActualizar = libroCategoria_A.text.toString()
        val nombreCategoriaActualizar  = nombreCategoria_A.text.toString()
        val descripcionActualizar  = descripcion_A.text.toString()

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Categorias")

        // Consulta
        val query = databaseReference.orderByChild("idCategoria").equalTo(id_Categorias_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("libroCategoria").setValue(libroCategoriaActualizar)
                    ds.ref.child("nombreCategoria").setValue(nombreCategoriaActualizar)
                    ds.ref.child("descripcion").setValue(descripcionActualizar)
                }

                Toast.makeText(this@Actualizar_Categoria, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
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