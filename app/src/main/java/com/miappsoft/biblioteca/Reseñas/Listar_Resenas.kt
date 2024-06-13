package com.miappsoft.biblioteca.Reseñas

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

class Listar_Resenas : AppCompatActivity() {

    lateinit var recyclerviewReseñas: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Resena, ViewHolder_Resena>
    lateinit var options: FirebaseRecyclerOptions<Resena>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_resenas)

        recyclerviewReseñas = findViewById(R.id.recyclerviewResenas)
        recyclerviewReseñas.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Reseñas")
        dialog = Dialog(this)

        ListaReseñasRegistrados()

    }


    private fun ListaReseñasRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Resena>().setQuery(BASE_DE_DATOS, Resena::class.java).build()
        firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Resena, ViewHolder_Resena>(options) {
            override fun onBindViewHolder(viewHolderResenas: ViewHolder_Resena, position: Int, resena: Resena) {
                viewHolderResenas.setearDatos(
                    applicationContext,
                    resena.idReseña.toString(),
                    resena.uidUsuarioReseñas.toString(),
                    resena.correoUsuarioReseñas.toString(),
                    resena.fechaHoraActualReseñas.toString(),
                    resena.libroReseña.toString(),
                    resena.autorReseña.toString(),
                    resena.fechaReseña.toString(),
                    resena.calificacion.toString(),
                    resena.comentarios.toString()
                )
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder_Resena {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_resena, parent, false)
                val viewHolderResenas = ViewHolder_Resena(view)

                viewHolderResenas.setOnClickListener(object : ViewHolder_Resena.ClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                    }

                    override fun onItemLongClick(view: View, position: Int) {

                        //obtenemos los datos del regristro seleccionado para el Actualizar
                        val id_Reseña = getItem(position).idReseña
                        val uidUsuarioReseñas = getItem(position).uidUsuarioReseñas
                        val correoUsuarioReseñas = getItem(position).correoUsuarioReseñas
                        val fechaHoraRegistroReseñas = getItem(position).fechaHoraActualReseñas
                        val libroReseña = getItem(position).libroReseña
                        val autorReseña = getItem(position).autorReseña
                        val fechaReseña = getItem(position).fechaReseña
                        val calificacion = getItem(position).calificacion
                        val comentarios = getItem(position).comentarios


                        val dialog = Dialog(this@Listar_Resenas)
                        dialog.setContentView(R.layout.dialogo_opciones_resena)

                        val cD_EliminarReseñas: Button = dialog.findViewById(R.id.cD_EliminarReseñas)
                        val cD_ActualizarReseñas: Button = dialog.findViewById(R.id.cD_ActualizarReseñas)

                        cD_EliminarReseñas.setOnClickListener {
                            eliminarReseña(id_Reseña.toString())
                            dialog.dismiss()
                        }

                        /*  cD_ActualizarLibro.setOnClickListener{
                              val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                              startActivity(intent)
                              dialog.dismiss()
                          }*/


                        cD_ActualizarReseñas.setOnClickListener {
                            val intent = Intent(this@Listar_Resenas, Actualizar_Resena::class.java)
                            intent.putExtra("idReseña", id_Reseña)
                            intent.putExtra("uidUsuarioReseñas", uidUsuarioReseñas)
                            intent.putExtra("correo_UsuarioReseñas", correoUsuarioReseñas)
                            intent.putExtra("fecha_RegistroReseñas", fechaHoraRegistroReseñas)
                            intent.putExtra("libroReseña", libroReseña)
                            intent.putExtra("autorReseña", autorReseña)
                            intent.putExtra("fechaReseña", fechaReseña)
                            intent.putExtra("calificacion", calificacion)
                            intent.putExtra("comentarios", comentarios)
                            startActivity(intent)
                            dialog.dismiss()
                        }

                        dialog.show()
                    }
                })

                return viewHolderResenas
            }
        }

        linearLayoutManager = LinearLayoutManager(this@Listar_Resenas, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewReseñas.layoutManager = linearLayoutManager
        recyclerviewReseñas.adapter = firebaseRecyclerAdapter
    }


    private fun eliminarReseña(idReseña: String) {
        val builder = AlertDialog.Builder(this@Listar_Resenas)

        builder.setTitle("Eliminar Reseña")
        builder.setMessage("¿Realmente deseas eliminar este Reseña?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar Reseña de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idReseña").equalTo(idReseña)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Resenas, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Resenas, error.message, Toast.LENGTH_SHORT).show()
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