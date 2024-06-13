package com.miappsoft.biblioteca

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {

    private lateinit var correoLogin: EditText
    private lateinit var passLogin: EditText
    private lateinit var btn_Logeo: Button
    private lateinit var usuarioNuevoTXT: TextView
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    // Variables para validar datos
    private var correo: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Login"

        // Propiedades que permiten crear la flecha hacia atrás
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        correoLogin = findViewById(R.id.correoLogin)
        passLogin = findViewById(R.id.passLogin)
        btn_Logeo = findViewById(R.id.btn_Logeo)
        usuarioNuevoTXT = findViewById(R.id.usuarioNuevoTXT)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this).apply {
            setTitle("Espere por favor")
            setCanceledOnTouchOutside(false)
        }

        // Evento para iniciar sesión
        btn_Logeo.setOnClickListener {
            validarDatos()
        }

        usuarioNuevoTXT.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }

    // Método para validar datos
    private fun validarDatos() {
        // Obtiene los datos escritos en las cajas de texto
        correo = correoLogin.text.toString()
        password = passLogin.text.toString()

        when {
            !Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> {
                Toast.makeText(this, "Correo inválido", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
            }
            else -> {
                loginDeUsuario()
            }
        }
    }

    private fun loginDeUsuario() {
        progressDialog.setMessage("Iniciando sesión...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    val user: FirebaseUser? = firebaseAuth.currentUser
                    startActivity(Intent(this, MenuPrincipal::class.java))
                    Toast.makeText(this, "Bienvenido(a): ${user?.email}", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Verifique si el correo y contraseña son los correctos", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
