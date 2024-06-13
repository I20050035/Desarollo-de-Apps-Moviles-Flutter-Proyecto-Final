package com.miappsoft.biblioteca.Libros

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

class Listar_Libros : AppCompatActivity() {

    lateinit var recyclerviewLibros: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Libro, ViewHolder_Libro>
    lateinit var options: FirebaseRecyclerOptions<Libro>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_libros)

        supportActionBar?.apply {
            title = "Registros Libros"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        recyclerviewLibros = findViewById(R.id.recyclerviewLibros)
        recyclerviewLibros.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Libros")
        dialog = Dialog(this)
        ListaLibrosRegistrados()

    }

    private fun ListaLibrosRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Libro>().setQuery(BASE_DE_DATOS, Libro::class.java).build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Libro, ViewHolder_Libro>(options) {
            override fun onBindViewHolder(viewHolderLibros: ViewHolder_Libro, position: Int, libro: Libro) {
                viewHolderLibros.setearDatos(
                    applicationContext,
                    libro.idLibro.toString(),
                    libro.uidUsuario.toString(),
                    libro.correoUsuario.toString(),
                    libro.fechaHoraActual.toString(),
                    libro.titulo.toString(),
                    libro.autor.toString(),
                    libro.genero.toString(),
                    libro.añoPublicacion.toString(),
                    libro.numeroPaginas.toString(),
                    libro.cantidadDisponible.toString(),

                    )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Libro {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
                val viewHolderLibros = ViewHolder_Libro(view)

                viewHolderLibros.setOnClickListener(object : ViewHolder_Libro.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                      //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        //obtenemos los datos del regristro seleccionado para el Actualizar
                        val id_Libro = getItem(position).idLibro
                        val uidUsuario = getItem(position).uidUsuario
                        val correoUsuario = getItem(position).correoUsuario
                        val fechaHoraRegistro = getItem(position).fechaHoraActual
                        val titulo = getItem(position).titulo
                        val autor = getItem(position).autor
                        val genero = getItem(position).genero
                        val añoPublicacion = getItem(position).añoPublicacion
                        val numeroPaginas = getItem(position).numeroPaginas
                        val cantidadDisponible = getItem(position).cantidadDisponible

                        val dialog = Dialog(this@Listar_Libros)
                        dialog.setContentView(R.layout.dialogo_opciones_libro)

                        val cD_EliminarLibro: Button = dialog.findViewById(R.id.cD_EliminarLibro)
                        val cD_ActualizarLibro: Button = dialog.findViewById(R.id.cD_ActualizarLibro)

                        cD_EliminarLibro.setOnClickListener {
                            eliminarLibro(id_Libro.toString())
                            dialog.dismiss()
                        }

                      /*  cD_ActualizarLibro.setOnClickListener{
                            val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                            startActivity(intent)
                            dialog.dismiss()
                        }*/


                        cD_ActualizarLibro.setOnClickListener {
                            val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                            intent.putExtra("idLibro", id_Libro)
                            intent.putExtra("uidUsuario", uidUsuario)
                            intent.putExtra("correo_Usuario", correoUsuario)
                            intent.putExtra("fecha_Registro", fechaHoraRegistro)
                            intent.putExtra("titulo", titulo)
                            intent.putExtra("autor", autor)
                            intent.putExtra("genero", genero)
                            intent.putExtra("añoPublicacion", añoPublicacion)
                            intent.putExtra("numeroPaginas", numeroPaginas)
                           intent.putExtra("cantidadDisponible", cantidadDisponible)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                })

                return viewHolderLibros
            }
        }

        linearLayoutManager = LinearLayoutManager(this@Listar_Libros, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewLibros.layoutManager = linearLayoutManager
        recyclerviewLibros.adapter = firebaseRecyclerAdapter
    }


    private fun eliminarLibro(idLibro: String) {
        val builder = AlertDialog.Builder(this@Listar_Libros)

        builder.setTitle("Eliminar Libro")
        builder.setMessage("¿Realmente deseas eliminar este libro?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar libro de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idLibro").equalTo(idLibro)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Libros, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Libros, error.message, Toast.LENGTH_SHORT).show()
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