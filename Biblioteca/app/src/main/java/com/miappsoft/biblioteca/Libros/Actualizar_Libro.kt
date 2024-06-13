package com.miappsoft.biblioteca.Libros

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

class Actualizar_Libro : AppCompatActivity() {

    lateinit var id_Libro_A: TextView
    lateinit var uid_Usuario_A: TextView
    lateinit var correo_Usuario_A: TextView
    lateinit var fecha_Hora_Registro_A: TextView

    lateinit var tituloLibro_A: EditText
    lateinit var autorLibro_A: EditText
    lateinit var generoLibro_A: EditText
    lateinit var año_PublicaciónLibro_A: EditText
    lateinit var numero_PaginasLibro_A: EditText
    lateinit var cantidad_DisponibleLibro_A: EditText

    lateinit var btnUpdateLibro: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Libro_R: String
    lateinit var uid_Usuario_R: String
    lateinit var correo_Usuario_R: String
    lateinit var fecha_Registro_R: String
    lateinit var tituloLibro_R: String
    lateinit var autorLibro_R: String
    lateinit var generoLibro_R: String
    lateinit var año_PublicaciónLibro_R: String
    lateinit var numero_PaginasLibro_R: String
    lateinit var cantidad_DisponibleLibro_R: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_libro)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdateLibro.setOnClickListener{
            actualizarRegistroLibroBD()
        }

    }

    private fun inicializarVistas() {
        id_Libro_A = findViewById(R.id.id_Libro_A)
        uid_Usuario_A = findViewById(R.id.uid_Usuario_A)
        correo_Usuario_A = findViewById(R.id.correo_Usuario_A)
        fecha_Hora_Registro_A = findViewById(R.id.fecha_Hora_Registro_A)

        tituloLibro_A = findViewById(R.id.tituloLibro_A)
        autorLibro_A = findViewById(R.id.autorLibro_A)
        generoLibro_A = findViewById(R.id.generoLibro_A)
        año_PublicaciónLibro_A = findViewById(R.id.año_PublicaciónLibro_A)
        numero_PaginasLibro_A = findViewById(R.id.numero_PaginasLibro_A)
        cantidad_DisponibleLibro_A = findViewById(R.id.cantidad_DisponibleLibro_A)

        btnUpdateLibro = findViewById(R.id.btnUpdateLibro)
    }

    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Libro_R = intent.getString("idLibro") ?: ""
            uid_Usuario_R = intent.getString("uidUsuario") ?: ""
            correo_Usuario_R = intent.getString("correo_Usuario") ?: ""
            fecha_Registro_R = intent.getString("fecha_Registro") ?: "" // aquí iba fecha_Hora_Actual
            tituloLibro_R = intent.getString("titulo") ?: ""
            autorLibro_R = intent.getString("autor") ?: ""
            generoLibro_R = intent.getString("genero") ?: ""
            año_PublicaciónLibro_R = intent.getString("añoPublicacion") ?: ""
            numero_PaginasLibro_R = intent.getString("numeroPaginas") ?: ""
            cantidad_DisponibleLibro_R = intent.getString("cantidadDisponible") ?: ""
        }
    }

    private fun setearDatos() {
        id_Libro_A.text = id_Libro_R
        uid_Usuario_A.text = uid_Usuario_R
        correo_Usuario_A.text = correo_Usuario_R
        fecha_Hora_Registro_A.text = fecha_Registro_R
        tituloLibro_A.setText(tituloLibro_R)
        autorLibro_A.setText(autorLibro_R)
        generoLibro_A.setText(generoLibro_R)
        año_PublicaciónLibro_A.setText(año_PublicaciónLibro_R)
        numero_PaginasLibro_A.setText(numero_PaginasLibro_R)
        cantidad_DisponibleLibro_A.setText(cantidad_DisponibleLibro_R)
    }


    private fun actualizarRegistroLibroBD() {
        val tituloActualizar = tituloLibro_A.text.toString()
        val autorActualizar  = autorLibro_A.text.toString()
        val generoActualizar  = generoLibro_A.text.toString()
        val añoPublicacionActualizar  = año_PublicaciónLibro_A.text.toString()
        val numeroPaginasActualizar  = numero_PaginasLibro_A.text.toString()
        val cantidadDisponibleActualizar  = cantidad_DisponibleLibro_A.text.toString()

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Libros")

        // Consulta
        val query = databaseReference.orderByChild("idLibro").equalTo(id_Libro_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("titulo").setValue(tituloActualizar)
                    ds.ref.child("autor").setValue(autorActualizar)
                    ds.ref.child("genero").setValue(generoActualizar)
                    ds.ref.child("añoPublicacion").setValue(añoPublicacionActualizar)
                    ds.ref.child("numeroPaginas").setValue(numeroPaginasActualizar)
                    ds.ref.child("cantidadDisponible").setValue(cantidadDisponibleActualizar)
                }

                Toast.makeText(this@Actualizar_Libro, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
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