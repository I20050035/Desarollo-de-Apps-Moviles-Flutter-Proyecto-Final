package com.miappsoft.biblioteca.Editoriales

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

class Actualizar_Editorial : AppCompatActivity() {

    lateinit var id_Editoriales_A: TextView
    lateinit var uid_UsuarioEditoriales_A: TextView
    lateinit var correo_UsuarioEditoriales_A: TextView
    lateinit var fecha_Hora_RegistroEditoriales_A: TextView

    lateinit var nombresEditorial_A: EditText
    lateinit var paisEditorial_A: EditText
    lateinit var año_FundacionEditorial_A: EditText
    lateinit var libro_Editorial_A: EditText

    lateinit var btnUpdateEditorial: Button

    //Strings para almacenar los datos recuperados de la actividad anterior
    lateinit var id_Editoriales_R: String
    lateinit var uid_UsuarioEditoriales_R: String
    lateinit var correo_UsuarioEditoriales_R: String
    lateinit var fecha_Hora_RegistroEditoriales_R: String
    lateinit var nombresEditorial_R: String
    lateinit var paisEditorial_R: String
    lateinit var año_FundacionEditorial_R: String
    lateinit var libro_Editorial_R: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_editorial)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Actualizar"
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        inicializarVistas()
        recuperarDatos()
        setearDatos()

        btnUpdateEditorial.setOnClickListener{
            actualizarRegistroEditorialBD()
        }

    }

    private fun inicializarVistas() {
        id_Editoriales_A = findViewById(R.id.id_Editoriales_A)
        uid_UsuarioEditoriales_A = findViewById(R.id.uid_UsuarioEditoriales_A)
        correo_UsuarioEditoriales_A = findViewById(R.id.correo_UsuarioEditoriales_A)
        fecha_Hora_RegistroEditoriales_A = findViewById(R.id.fecha_Hora_RegistroEditoriales_A)

        nombresEditorial_A = findViewById(R.id.nombresEditorial_A)
        paisEditorial_A = findViewById(R.id.paisEditorial_A)
        año_FundacionEditorial_A = findViewById(R.id.año_FundacionEditorial_A)
        libro_Editorial_A = findViewById(R.id.libro_Editorial_A)

        btnUpdateEditorial = findViewById(R.id.btnUpdateEditorial)
    }

    private fun recuperarDatos() {
        val intent = intent.extras

        if (intent != null) {
            id_Editoriales_R = intent.getString("idEditorial") ?: ""
            uid_UsuarioEditoriales_R = intent.getString("uidUsuarioEditoriales") ?: ""
            correo_UsuarioEditoriales_R = intent.getString("correo_UsuarioEditoriales") ?: ""
            fecha_Hora_RegistroEditoriales_R = intent.getString("fecha_RegistroEditoriales") ?: "" // aquí iba fecha_Hora_Actual
            nombresEditorial_R = intent.getString("nombresEditoriales") ?: ""
            paisEditorial_R = intent.getString("paisEditorial") ?: ""
            año_FundacionEditorial_R = intent.getString("año_FundacionEditorial") ?: ""
            libro_Editorial_R = intent.getString("libro_Editorial") ?: ""
        }

    }

    private fun setearDatos() {
        id_Editoriales_A.text = id_Editoriales_R
        uid_UsuarioEditoriales_A.text = uid_UsuarioEditoriales_R
        correo_UsuarioEditoriales_A.text = correo_UsuarioEditoriales_R
        fecha_Hora_RegistroEditoriales_A.text = fecha_Hora_RegistroEditoriales_R

        nombresEditorial_A.setText(nombresEditorial_R)
        paisEditorial_A.setText(paisEditorial_R)
        año_FundacionEditorial_A.setText(paisEditorial_R)
        libro_Editorial_A.setText(libro_Editorial_R)
    }

    private fun actualizarRegistroEditorialBD() {
        val nombresEditorialActualizar = nombresEditorial_A.text.toString()
        val paisEditorialActualizar  = paisEditorial_A.text.toString()
        val año_FundacionEditorialActualizar  = año_FundacionEditorial_A.text.toString()
        val libro_EditorialActualizar  = libro_Editorial_A.text.toString()

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val databaseReference = firebaseDatabase.getReference("Editoriales")

        // Consulta
        val query = databaseReference.orderByChild("idEditorial").equalTo(id_Editoriales_R)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    ds.ref.child("nombresEditoriales").setValue(nombresEditorialActualizar)
                    ds.ref.child("paisEditorial").setValue(paisEditorialActualizar)
                    ds.ref.child("año_FundacionEditorial").setValue(año_FundacionEditorialActualizar)
                    ds.ref.child("libro_Editorial").setValue(libro_EditorialActualizar)
                }

                Toast.makeText(this@Actualizar_Editorial, "Registro Actualizado con éxito", Toast.LENGTH_SHORT).show()
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