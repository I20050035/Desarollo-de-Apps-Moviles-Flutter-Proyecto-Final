package com.miappsoft.biblioteca.Categorias

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miappsoft.biblioteca.Autores.AgregarAutor
import com.miappsoft.biblioteca.Autores.Listar_Autores
import com.miappsoft.biblioteca.R

class MenuCategorias : AppCompatActivity() {

    private lateinit var btn_ActivityAgregarCategorias: Button
    private lateinit var btn_ActivityLeerCategorias: Button
    private lateinit var uidPrincipal: TextView
    private lateinit var nombresPrincipal: TextView
    private lateinit var correoPrincipal: TextView
    private lateinit var progressBarDatos: ProgressBar
    private lateinit var usuarios: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_categorias)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Menu Categorias"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        btn_ActivityAgregarCategorias = findViewById(R.id.btn_ActivityAgregarCategorias)
        btn_ActivityLeerCategorias = findViewById(R.id.btn_ActivityLeerCategorias)

        uidPrincipal = findViewById(R.id.uidPrincipal)
        nombresPrincipal = findViewById(R.id.nombresPrincipal)
        correoPrincipal = findViewById(R.id.correoPrincipal)
        progressBarDatos = findViewById(R.id.progressBarDatos)
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios")
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!

        cargaDeDatosMenuCategorias()

        btn_ActivityAgregarCategorias.setOnClickListener {
            // Obtenemos la información de los TextViews
            val uidUsuario = uidPrincipal.text.toString()
            val correoUsuario = correoPrincipal.text.toString()

            // Pasamos los datos a la siguiente actividad
            val intent = Intent(this, AgregarCategoria::class.java).apply {
                putExtra("Uid", uidUsuario)
                putExtra("Correo", correoUsuario)
            }
            startActivity(intent)
        }

        btn_ActivityLeerCategorias.setOnClickListener {
            val intent = Intent(this, Listar_Categorias::class.java)
            startActivity(intent)
        }

    }


    private fun cargaDeDatosMenuCategorias() {
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}