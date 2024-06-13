package com.miappsoft.biblioteca

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.miappsoft.biblioteca.Autores.MenuAutores
import com.miappsoft.biblioteca.Categorias.MenuCategorias
import com.miappsoft.biblioteca.Editoriales.MenuEditoriales
import com.miappsoft.biblioteca.Libros.MenuLibros
import com.miappsoft.biblioteca.Prestamos.MenuPrestamos
import com.miappsoft.biblioteca.Reseñas.MenuResenas

class MenuPrincipal : AppCompatActivity() {

    private lateinit var btn_LibrosActivty: Button
    private lateinit var btn_PréstamosActivty: Button
    private lateinit var btn_AutoresActivty: Button
    private lateinit var btn_CategoriasActivty: Button
    private lateinit var btn_EditorialesActivty: Button
    private lateinit var btn_ReseñasActivty: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var uidPrincipal: TextView
    private lateinit var nombresPrincipal: TextView
    private lateinit var correoPrincipal: TextView
    private lateinit var progressBarDatos: ProgressBar
    private lateinit var usuarios: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Menu Bibloteca"

        uidPrincipal = findViewById(R.id.uidPrincipal)
        nombresPrincipal = findViewById(R.id.nombresPrincipal)
        correoPrincipal = findViewById(R.id.correoPrincipal)
        progressBarDatos = findViewById(R.id.progressBarDatos)
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios")

        btn_LibrosActivty = findViewById(R.id.btn_LibrosActivty)
        btn_PréstamosActivty = findViewById(R.id.btn_PréstamosActivty)
        btn_AutoresActivty = findViewById(R.id.btn_AutoresActivty)
        btn_CategoriasActivty = findViewById(R.id.btn_CategoriasActivty)
        btn_EditorialesActivty = findViewById(R.id.btn_EditorialesActivty)
        btn_ReseñasActivty = findViewById(R.id.btn_ReseñasActivty)

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!




        btn_LibrosActivty.setOnClickListener {
            val intent = Intent(this, MenuLibros::class.java)
            startActivity(intent)
        }


        btn_PréstamosActivty.setOnClickListener {
            val intent = Intent(this, MenuPrestamos::class.java)
            startActivity(intent)
        }


        btn_AutoresActivty.setOnClickListener {
            val intent = Intent(this, MenuAutores::class.java)
            startActivity(intent)
        }


        btn_CategoriasActivty.setOnClickListener {
            val intent = Intent(this, MenuCategorias::class.java)
            startActivity(intent)
        }


        btn_EditorialesActivty.setOnClickListener {
            val intent = Intent(this, MenuEditoriales::class.java)
            startActivity(intent)
        }


        btn_ReseñasActivty.setOnClickListener {
            val intent = Intent(this, MenuResenas::class.java)
            startActivity(intent)
        }


    }

    private fun comprobarInicioSesion() {
        if (user != null) {
            cargaDeDatos()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        comprobarInicioSesion()
    }

    private fun cargaDeDatos() {
        usuarios.child(user.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    progressBarDatos.visibility = View.GONE
                    uidPrincipal.visibility = View.GONE
                    nombresPrincipal.visibility = View.VISIBLE
                    correoPrincipal.visibility = View.VISIBLE

                    val uid = snapshot.child("uid").getValue(String::class.java) ?: ""
                    val nombres = snapshot.child("nombres").getValue(String::class.java) ?: ""
                    val correo = snapshot.child("correo").getValue(String::class.java) ?: ""

                    uidPrincipal.text = uid
                    nombresPrincipal.text = nombres
                    correoPrincipal.text = correo

                   // habilitarBotones()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejo de errores
            }
        })
    }


}