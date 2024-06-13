package com.miappsoft.biblioteca.Categorias

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


class Listar_Categorias : AppCompatActivity() {

    lateinit var recyclerviewCategorias: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Categoria, ViewHolder_Categoria>
    lateinit var options: FirebaseRecyclerOptions<Categoria>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_categorias)

        recyclerviewCategorias = findViewById(R.id.recyclerviewCategorias)
        recyclerviewCategorias.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Categorias")
        dialog = Dialog(this)

        ListaCategoriasRegistrados()

    }


    private fun ListaCategoriasRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Categoria>().setQuery(BASE_DE_DATOS, Categoria::class.java).build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Categoria, ViewHolder_Categoria>(options) {
            override fun onBindViewHolder(viewHolderCategorias: ViewHolder_Categoria, position: Int, categoria: Categoria) {
                viewHolderCategorias.setearDatos(
                    applicationContext,
                    categoria.idCategoria.toString(),
                    categoria.uidUsuarioCategorias.toString(),
                    categoria.correoUsuarioCategorias.toString(),
                    categoria.fechaHoraActualCategorias.toString(),
                    categoria.libroCategoria.toString(),
                    categoria.nombreCategoria.toString(),
                    categoria.descripcion.toString(),

                )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Categoria {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
                val viewHolderCategorias = ViewHolder_Categoria(view)

                viewHolderCategorias.setOnClickListener(object : ViewHolder_Categoria.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        //obtenemos los datos del regristro seleccionado para el Actualizar
                        val id_Categoria = getItem(position).idCategoria
                        val uidUsuarioCategorias = getItem(position).uidUsuarioCategorias
                        val correoUsuarioCategorias = getItem(position).correoUsuarioCategorias
                        val fechaHoraRegsitroCategorias = getItem(position).fechaHoraActualCategorias
                        val libroCategoria = getItem(position).libroCategoria
                        val nombreCategoria = getItem(position).nombreCategoria
                        val descripcion = getItem(position).descripcion



                        val dialog = Dialog(this@Listar_Categorias)
                        dialog.setContentView(R.layout.dialogo_opciones_categoria)

                        val cD_EliminarCategorias: Button = dialog.findViewById(R.id.cD_EliminarCategorias)
                        val cD_ActualizarCategorias: Button = dialog.findViewById(R.id.cD_ActualizarCategorias)

                        cD_EliminarCategorias.setOnClickListener {
                            eliminarCategoria(id_Categoria.toString())
                            dialog.dismiss()
                        }

                        /*  cD_ActualizarLibro.setOnClickListener{
                              val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                              startActivity(intent)
                              dialog.dismiss()
                          }*/


                        cD_ActualizarCategorias.setOnClickListener {
                            val intent = Intent(this@Listar_Categorias, Actualizar_Categoria::class.java)
                            intent.putExtra("idCategoria", id_Categoria)
                            intent.putExtra("uidUsuarioCategorias", uidUsuarioCategorias)
                            intent.putExtra("correo_UsuarioCategorias", correoUsuarioCategorias)
                            intent.putExtra("fecha_RegistroCategorias", fechaHoraRegsitroCategorias)
                            intent.putExtra("libroCategoria", libroCategoria)
                            intent.putExtra("nombreCategoria", nombreCategoria)
                            intent.putExtra("descripcion", descripcion)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                })

                return viewHolderCategorias
            }
        }

        linearLayoutManager = LinearLayoutManager(this@Listar_Categorias, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewCategorias.layoutManager = linearLayoutManager
        recyclerviewCategorias.adapter = firebaseRecyclerAdapter
    }


    private fun eliminarCategoria(idCategoria: String) {
        val builder = AlertDialog.Builder(this@Listar_Categorias)

        builder.setTitle("Eliminar Categoria")
        builder.setMessage("¿Realmente deseas eliminar esta Categoria?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar Reseña de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idCategoria").equalTo(idCategoria)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Categorias, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Categorias, error.message, Toast.LENGTH_SHORT).show()
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