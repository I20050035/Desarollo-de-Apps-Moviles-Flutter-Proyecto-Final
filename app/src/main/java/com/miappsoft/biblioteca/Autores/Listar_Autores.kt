package com.miappsoft.biblioteca.Autores

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


class Listar_Autores : AppCompatActivity() {

    lateinit var recyclerviewAutores: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Autor, ViewHolder_Autor>
    lateinit var options: FirebaseRecyclerOptions<Autor>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_autores)

        supportActionBar?.apply {
            title = "Registros Autores"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        recyclerviewAutores = findViewById(R.id.recyclerviewAutores)
        recyclerviewAutores.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Autores")
        dialog = Dialog(this)

        ListaAutoresRegistrados()

    }

    private fun ListaAutoresRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Autor>().setQuery(BASE_DE_DATOS, Autor::class.java).build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Autor, ViewHolder_Autor>(options) {
            override fun onBindViewHolder(viewHolderAutores: ViewHolder_Autor, position: Int, autor: Autor) {
                viewHolderAutores.setearDatos(
                    applicationContext,
                    autor.idAutor.toString(),
                    autor.uidUsuarioAutor.toString(),
                    autor.correoUsuarioAutor.toString(),
                    autor.fechaHoraActualAutor.toString(),
                    autor.nombresAutor.toString(),
                    autor.apellidosAutor.toString(),
                    autor.fechaNacimiento.toString(),
                    autor.paisNacimiento.toString(),
                    autor.libroEscrito.toString()
                    )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Autor {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_autor, parent, false)
                val viewHolderAutores = ViewHolder_Autor(view)

                viewHolderAutores.setOnClickListener(object : ViewHolder_Autor.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        //obtenemos los datos del regristro seleccionado para el Actualizar
                        val id_Autor = getItem(position).idAutor
                        val uidUsuarioAutor = getItem(position).uidUsuarioAutor
                        val correoUsuarioAutor = getItem(position).correoUsuarioAutor
                        val fechaHoraRegistroAutor = getItem(position).fechaHoraActualAutor
                        val nombresAutores = getItem(position).nombresAutor
                        val apellidosAutores = getItem(position).apellidosAutor
                        val fechaNacimiento = getItem(position).fechaNacimiento
                        val paisNacimiento = getItem(position).paisNacimiento
                        val libroEscrito = getItem(position).libroEscrito

                        val dialog = Dialog(this@Listar_Autores)
                        dialog.setContentView(R.layout.dialogo_opciones_autor)

                        val cD_EliminarAutores: Button = dialog.findViewById(R.id.cD_EliminarAutores)
                        val cD_ActualizarAutores: Button = dialog.findViewById(R.id.cD_ActualizarAutores)

                        cD_EliminarAutores.setOnClickListener {
                           eliminarAutor(id_Autor.toString())
                            dialog.dismiss()
                        }

                        /*  cD_ActualizarLibro.setOnClickListener{
                              val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                              startActivity(intent)
                              dialog.dismiss()
                          }*/


                        cD_ActualizarAutores.setOnClickListener {
                            val intent = Intent(this@Listar_Autores, Actualizar_Autor::class.java)
                            intent.putExtra("idAutor", id_Autor)
                            intent.putExtra("uidUsuarioAutor", uidUsuarioAutor)
                            intent.putExtra("correo_UsuarioAutor", correoUsuarioAutor)
                            intent.putExtra("fecha_RegistroAutor", fechaHoraRegistroAutor)
                            intent.putExtra("nombresAutor", nombresAutores)
                            intent.putExtra("apellidosAutor", apellidosAutores)
                            intent.putExtra("fechaNacimiento", fechaNacimiento)
                            intent.putExtra("paisNacimiento", paisNacimiento)
                            intent.putExtra("libroEscrito", libroEscrito)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                })

                return viewHolderAutores
            }
        }

        linearLayoutManager = LinearLayoutManager(this@Listar_Autores, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewAutores.layoutManager = linearLayoutManager
        recyclerviewAutores.adapter = firebaseRecyclerAdapter
    }


    private fun eliminarAutor(idAutor: String) {
        val builder = AlertDialog.Builder(this@Listar_Autores)

        builder.setTitle("Eliminar Autor")
        builder.setMessage("¿Realmente deseas eliminar este Autor?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar libro de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idAutor").equalTo(idAutor)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Autores, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Autores, error.message, Toast.LENGTH_SHORT).show()
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