package com.miappsoft.biblioteca

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.util.Patterns
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Registro : AppCompatActivity() {

    private lateinit var nombreET: EditText
    private lateinit var apellidoET: EditText
    private lateinit var telefonoET: EditText
    private lateinit var correoET: EditText
    private lateinit var contraseñaET: EditText
    private lateinit var confirmarContraseñaET: EditText
    private lateinit var registrarUsuario: Button
    private lateinit var tengoUnaCuentaTXT: TextView

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    // Variables para después validar Campos
    private var nombre: String = ""
    private var apellido: String = ""
    private var telefono: String = ""
    private var correo: String = ""
    private var password: String = ""
    private var confirmarPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val actionBar = supportActionBar
        actionBar?.title = "Registrar"

        // Propiedades que permiten crear la flecha hacia atrás
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        // Inicialización de todas las vistas
        nombreET = findViewById(R.id.nombreET)
        apellidoET = findViewById(R.id.apellidoET)
        telefonoET = findViewById(R.id.telefonoET)
        correoET = findViewById(R.id.correoET)
        contraseñaET = findViewById(R.id.contraseñaET)
        confirmarContraseñaET = findViewById(R.id.confirmarContraseñaET)
        registrarUsuario = findViewById(R.id.registrarUsuario)
        tengoUnaCuentaTXT = findViewById(R.id.tengoUnaCuentaTXT)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this@Registro).apply {
            setTitle("Espere por favor")
            setCanceledOnTouchOutside(false) // Con esta línea de código la ejecución del progressDialog no podrá cerrarse cuando el usuario presione en cualquier parte de la pantalla, este se cerrará hasta que finalice
        }

        // Evento para el botón de registrar usuario
        registrarUsuario.setOnClickListener {
            // Método para el proceso de registro
            validarDatos()
        }

        tengoUnaCuentaTXT.setOnClickListener {
            startActivity(Intent(this@Registro, Login::class.java))
        }
    }

    // Método para validar datos
    private fun validarDatos() {
        // Con esto podemos captar la información que sea introducida en nuestros campos EditText
        nombre = nombreET.text.toString()
        apellido = apellidoET.text.toString()
        telefono = telefonoET.text.toString()
        correo = correoET.text.toString()
        password = contraseñaET.text.toString()
        confirmarPassword = confirmarContraseñaET.text.toString()

        // Condición que revisa si el campo está nombre está vacío
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show()
        }
        if (apellido.isEmpty()) {
            Toast.makeText(this, "Ingrese apellido", Toast.LENGTH_SHORT).show()
        }
        if (telefono.isEmpty()) {
            Toast.makeText(this, "Ingrese telefono", Toast.LENGTH_SHORT).show()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            // Condición para validar que la introducción de datos cuando se ingresa correo sea válida y contenga los datos que debe tener un correo
            Toast.makeText(this, "Ingrese un correo válido", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            // Condición que revisa si el campo de contraseña está vacío
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show()
        } else if (confirmarPassword.isEmpty()) {
            // Condición que revisa si el campo de Confirmar contraseña está vacío
            Toast.makeText(this, "Confirme contraseña", Toast.LENGTH_SHORT).show()
        } else if (password != confirmarPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
        } else {
            crearCuenta()
        }
    }

    // Método para crear cuenta
    private fun crearCuenta() {
        progressDialog.setMessage("Creando su cuenta...")
        progressDialog.show()

        // Creación de un usuario en Firebase
        firebaseAuth.createUserWithEmailAndPassword(correo, password)
            .addOnSuccessListener { authResult ->
                guardarInformacion()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this@Registro, "Error al crear la cuenta: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Método para guardar información de registro
    private fun guardarInformacion() {
        progressDialog.setMessage("Guardando su información")

        // Obtener la identificación de un usuario actual
        val id = firebaseAuth.uid

        // Con HashMap enviamos los datos que asignemos en nuestro dispositivo para después proceder a enviarlos a la base de datos
        val datos: HashMap<String, String> = hashMapOf(
            "uid" to id.toString(),
            "correo" to correo,
            "nombres" to nombre,
            "apellidos" to apellido,
            "telefono" to telefono,
            "password" to password,
        )

        // Inicializamos la base
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Usuarios")

        databaseReference.child(id!!)
            .setValue(datos)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this@Registro, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@Registro, MenuPrincipal::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this@Registro, "Error al guardar la información: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
