package com.miappsoft.biblioteca.Editoriales

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.miappsoft.biblioteca.R

class Listar_Editoriales : AppCompatActivity() {

    lateinit var recyclerviewEditoriales: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Editorial, ViewHolder_Editorial>
    lateinit var options: FirebaseRecyclerOptions<Editorial>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_editoriales)

        supportActionBar?.apply {
            title = "Registros Editoriales"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        recyclerviewEditoriales = findViewById(R.id.recyclerviewEditoriales)
        recyclerviewEditoriales.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Editoriales")
        dialog = Dialog(this)

        ListaEditoresRegistrados()
    }


    private fun ListaEditoresRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Editorial>().setQuery(BASE_DE_DATOS, Editorial::class.java).build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Editorial, ViewHolder_Editorial>(options) {
            override fun onBindViewHolder(viewHolderEditoriales: ViewHolder_Editorial, position: Int, editorial: Editorial) {
                viewHolderEditoriales.setearDatos(
                    applicationContext,
                    editorial.idEditorial.toString(),
                    editorial.uidUsuarioEditoriales.toString(),
                    editorial.correoUsuarioEditoriales.toString(),
                    editorial.fechaHoraActualEditoriales.toString(),
                    editorial.nombresEditoriales.toString(),
                    editorial.paisEditorial.toString(),
                    editorial.año_FundacionEditorial.toString(),
                    editorial.libro_Editorial.toString(),
                )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Editorial {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_editorial, parent, false)
                val viewHolderEditorial = ViewHolder_Editorial(view)

                viewHolderEditorial.setOnClickListener(object : ViewHolder_Editorial.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        //obtenemos los datos del regristro seleccionado para el Actualizar
                        val id_Editorial = getItem(position).idEditorial
                        val uidUsuarioEditoriales = getItem(position).uidUsuarioEditoriales
                        val correoUsuarioEditoriales = getItem(position).correoUsuarioEditoriales
                        val fechaHoraRegistroEditoriales = getItem(position).fechaHoraActualEditoriales
                        val nombresEditoriales = getItem(position).nombresEditoriales
                        val paisEditorial = getItem(position).paisEditorial
                        val año_FundacionEditorial = getItem(position).año_FundacionEditorial
                        val libro_Editorial = getItem(position).libro_Editorial

                        val dialog = Dialog(this@Listar_Editoriales)
                        dialog.setContentView(R.layout.dialogo_opciones_editorial)

                        val cD_EliminarEditoriales: Button = dialog.findViewById(R.id.cD_EliminarEditoriales)
                        val cD_ActualizarEditoriales: Button = dialog.findViewById(R.id.cD_ActualizarEditoriales)

                        cD_EliminarEditoriales.setOnClickListener {
                            eliminarEditorial(id_Editorial.toString())
                            dialog.dismiss()
                        }

                        /*  cD_ActualizarLibro.setOnClickListener{
                              val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                              startActivity(intent)
                              dialog.dismiss()
                          }*/


                        cD_ActualizarEditoriales.setOnClickListener {
                            val intent = Intent(this@Listar_Editoriales, Actualizar_Editorial::class.java)
                            intent.putExtra("idEditorial", id_Editorial)
                            intent.putExtra("uidUsuarioEditoriales", uidUsuarioEditoriales)
                            intent.putExtra("correo_UsuarioEditoriales", correoUsuarioEditoriales)
                            intent.putExtra("fecha_RegistroEditoriales", fechaHoraRegistroEditoriales)
                            intent.putExtra("nombresEditoriales", nombresEditoriales)
                            intent.putExtra("paisEditorial", paisEditorial)
                            intent.putExtra("año_FundacionEditorial", año_FundacionEditorial)
                            intent.putExtra("libro_Editorial", libro_Editorial)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                })

                return viewHolderEditorial
            }
        }

        linearLayoutManager = LinearLayoutManager(this@Listar_Editoriales, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewEditoriales.layoutManager = linearLayoutManager
        recyclerviewEditoriales.adapter = firebaseRecyclerAdapter
    }


    private fun eliminarEditorial(idEditorial: String) {
        val builder = AlertDialog.Builder(this@Listar_Editoriales)

        builder.setTitle("Eliminar Editorial")
        builder.setMessage("¿Realmente deseas eliminar este Editorial?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar libro de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idEditorial").equalTo(idEditorial)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Editoriales, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Editoriales, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    override fun onStart() {
        super.onStart()
        firebaseRecyclerAdapter.startListening()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}