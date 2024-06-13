package com.miappsoft.biblioteca.Reseñas

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

class Actualizar_Resena : AppCompatActivity() {

    lateinit var id_Reseñas_A: TextView
    lateinit var uid_UsuarioReseñas_A: TextView
    lateinit var correo_UsuarioReseñas_A: TextView
    lateinit var fecha_Hora_RegistroReseñas_A: TextView

    lateinit var libroReseña_A: EditText
    lateinit var autorReseña_A: EditText
    lateinit var fechaReseña_A: EditText
    lateinit var calificacion_A: EditText
    lateinit var comentarios_A: EditText

    lateinit var btnUpdateReseña: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Reseñas_R: String
    lateinit var uid_UsuarioReseñas_R: String
    lateinit var correo_UsuarioReseñas_R: String
    lateinit var fecha_Hora_RegistroReseñas_R: String
    lateinit var libroReseña_R: String
    lateinit var autorReseña_R: String
    lateinit var fechaReseña_R: String
    lateinit var calificacion_R: String
    lateinit var comentarios_R: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_resena)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdateReseña.setOnClickListener{
            actualizarRegistroReseñaBD()
        }

    }

    private fun inicializarVistas() {
        id_Reseñas_A = findViewById(R.id.id_Reseñas_A)
        uid_UsuarioReseñas_A = findViewById(R.id.uid_UsuarioReseñas_A)
        correo_UsuarioReseñas_A = findViewById(R.id.correo_UsuarioReseñas_A)
        fecha_Hora_RegistroReseñas_A = findViewById(R.id.fecha_Hora_RegistroReseñas_A)

        libroReseña_A = findViewById(R.id.libroReseña_A)
        autorReseña_A = findViewById(R.id.autorReseña_A)
        fechaReseña_A = findViewById(R.id.fechaReseña_A)
        calificacion_A = findViewById(R.id.calificacion_A)
        comentarios_A = findViewById(R.id.comentarios_A)

        btnUpdateReseña = findViewById(R.id.btnUpdateReseña)
    }

    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Reseñas_R = intent.getString("idReseña") ?: ""
            uid_UsuarioReseñas_R = intent.getString("uidUsuarioReseñas") ?: ""
            correo_UsuarioReseñas_R = intent.getString("correo_UsuarioReseñas") ?: ""
            fecha_Hora_RegistroReseñas_R = intent.getString("fecha_RegistroReseñas") ?: "" // aquí iba fecha_Hora_Actual
            libroReseña_R = intent.getString("libroReseña") ?: ""
            autorReseña_R = intent.getString("autorReseña") ?: ""
            fechaReseña_R = intent.getString("fechaReseña") ?: ""
            calificacion_R = intent.getString("calificacion") ?: ""
            comentarios_R = intent.getString("comentarios") ?: ""

        }

    }

    private fun setearDatos() {
        id_Reseñas_A.text = id_Reseñas_R
        uid_UsuarioReseñas_A.text = uid_UsuarioReseñas_R
        correo_UsuarioReseñas_A.text = correo_UsuarioReseñas_R
        fecha_Hora_RegistroReseñas_A.text = fecha_Hora_RegistroReseñas_R

        libroReseña_A.setText(libroReseña_R)
        autorReseña_A.setText(autorReseña_R)
        fechaReseña_A.setText(fechaReseña_R)
        calificacion_A.setText(calificacion_R)
        comentarios_A.setText(comentarios_R)

    }


    private fun actualizarRegistroReseñaBD() {
        val libroReseñaActualizar = libroReseña_A.text.toString()
        val autorReseñaActualizar  = autorReseña_A.text.toString()
        val fechaReseñaActualizar  = fechaReseña_A.text.toString()
        val calificacionActualizar  = calificacion_A.text.toString()
        val comentariosActualizar  = comentarios_A.text.toString()


        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Reseñas")

        // Consulta
        val query = databaseReference.orderByChild("idReseña").equalTo(id_Reseñas_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("libroReseña").setValue(libroReseñaActualizar)
                    ds.ref.child("autorReseña").setValue(autorReseñaActualizar)
                    ds.ref.child("fechaReseña").setValue(fechaReseñaActualizar)
                    ds.ref.child("calificacion").setValue(calificacionActualizar)
                    ds.ref.child("comentarios").setValue(comentariosActualizar)
                }

                Toast.makeText(this@Actualizar_Resena, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
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