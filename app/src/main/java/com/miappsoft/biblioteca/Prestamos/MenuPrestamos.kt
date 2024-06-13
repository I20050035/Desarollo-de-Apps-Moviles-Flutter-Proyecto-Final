package com.miappsoft.biblioteca.Prestamos

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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
import com.miappsoft.biblioteca.Libros.AgregarLibro
import com.miappsoft.biblioteca.Libros.Listar_Libros
import com.miappsoft.biblioteca.R

class MenuPrestamos : AppCompatActivity() {

    private lateinit var btn_ActivityAgregarPrestamos: Button
    private lateinit var btn_ActivityLeerPrestamos: Button
    private lateinit var uidPrincipal: TextView
    private lateinit var nombresPrincipal: TextView
    private lateinit var correoPrincipal: TextView
    private lateinit var progressBarDatos: ProgressBar
    private lateinit var usuarios: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_prestamos)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.title = "Menu Prestamos"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        btn_ActivityAgregarPrestamos = findViewById(R.id.btn_ActivityAgregarPrestamos)
        btn_ActivityLeerPrestamos = findViewById(R.id.btn_ActivityLeerPrestamos)

        uidPrincipal = findViewById(R.id.uidPrincipal)
        nombresPrincipal = findViewById(R.id.nombresPrincipal)
        correoPrincipal = findViewById(R.id.correoPrincipal)
        progressBarDatos = findViewById(R.id.progressBarDatos)
        usuarios = FirebaseDatabase.getInstance().getReference("Usuarios")
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!

        cargaDeDatosMenuPrestamos()


        btn_ActivityAgregarPrestamos.setOnClickListener {
            // Obtenemos la información de los TextViews
            val uidUsuario = uidPrincipal.text.toString()
            val correoUsuario = correoPrincipal.text.toString()

            // Pasamos los datos a la siguiente actividad
            val intent = Intent(this, AgregarPrestamo::class.java).apply {
                putExtra("Uid", uidUsuario)
                putExtra("Correo", correoUsuario)
            }
            startActivity(intent)
        }


        btn_ActivityLeerPrestamos.setOnClickListener {
            val intent = Intent(this, Listar_Prestamos::class.java)
            startActivity(intent)
        }

    }



    private fun cargaDeDatosMenuPrestamos() {
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