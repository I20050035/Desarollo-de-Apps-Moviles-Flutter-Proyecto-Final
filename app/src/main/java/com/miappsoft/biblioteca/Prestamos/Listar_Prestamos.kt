package com.miappsoft.biblioteca.Prestamos

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
import com.miappsoft.biblioteca.Libros.Actualizar_Libro
import com.miappsoft.biblioteca.Prestamos.ViewHolder_Prestamo
import com.miappsoft.biblioteca.R

public class Listar_Prestamos : AppCompatActivity() {

    lateinit var recyclerviewPrestamos: RecyclerView
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var BASE_DE_DATOS: DatabaseReference
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Prestamo,ViewHolder_Prestamo>
    lateinit var options: FirebaseRecyclerOptions<Prestamo>
    lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_prestamos)

        supportActionBar?.apply {
            title = "Registros Prestamos"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        recyclerviewPrestamos = findViewById(R.id.recyclerviewPrestamos)
        recyclerviewPrestamos.setHasFixedSize(true)

        firebaseDatabase = FirebaseDatabase.getInstance()
        BASE_DE_DATOS = firebaseDatabase.getReference("Prestamos")
        dialog = Dialog(this)


        ListaPrestamosRegistrados()


    }

    private fun ListaPrestamosRegistrados() {
        options = FirebaseRecyclerOptions.Builder<Prestamo>()
            .setQuery(BASE_DE_DATOS, Prestamo::class.java).build()
        firebaseRecyclerAdapter =
            object : FirebaseRecyclerAdapter<Prestamo, ViewHolder_Prestamo>(options) {
                override fun onBindViewHolder(
                    viewHolderPrestamos: ViewHolder_Prestamo,
                    position: Int,
                    prestamo: Prestamo
                ) {
                    viewHolderPrestamos.setearDatos(
                        applicationContext,
                        prestamo.idPrestamo.toString(),
                        prestamo.uidUsuarioPrestamo.toString(),
                        prestamo.correoUsuarioPrestamo.toString(),
                        prestamo.fechaHoraActualPrestamo.toString(),
                        prestamo.nombreSolicitante.toString(),
                        prestamo.libro.toString(),
                        prestamo.fechaPrestamo.toString(),
                        prestamo.fechaDevolucion.toString(),
                        prestamo.estadoPrestamo.toString(),
                    )
                }

                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): ViewHolder_Prestamo {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_prestamo, parent, false)
                    val viewHolderPrestamos = ViewHolder_Prestamo(view)

                    viewHolderPrestamos.setOnClickListener(object :
                        ViewHolder_Prestamo.ClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            //  Toast.makeText(this@Listar_Libros, "on item click", Toast.LENGTH_SHORT).show()
                        }

                        override fun onItemLongClick(view: View, position: Int) {

                            //obtenemos los datos del regristro seleccionado para el Actualizar
                            val id_Prestamo = getItem(position).idPrestamo
                            val uidUsuarioPrestamo = getItem(position).uidUsuarioPrestamo
                            val correoUsuario = getItem(position).correoUsuarioPrestamo
                            val fechaHoraRegistro = getItem(position).fechaHoraActualPrestamo
                            val nombreSolicitante = getItem(position).nombreSolicitante
                            val libro = getItem(position).libro
                            val fechaPrestamo = getItem(position).fechaPrestamo
                            val fechaDevolucion = getItem(position).fechaDevolucion
                            val estadoPrestamo = getItem(position).estadoPrestamo

                            val dialog = Dialog(this@Listar_Prestamos)
                            dialog.setContentView(R.layout.dialogo_opciones_prestamo)

                            val cD_EliminarPrestamo: Button = dialog.findViewById(R.id.cD_EliminarPrestamo)
                            val cD_ActualizarPrestamo: Button = dialog.findViewById(R.id.cD_ActualizarPrestamo)


                            cD_EliminarPrestamo.setOnClickListener {
                                eliminarPrestamo(id_Prestamo.toString())
                                dialog.dismiss()
                            }

                            /*  cD_ActualizarLibro.setOnClickListener{
                              val intent = Intent(this@Listar_Libros, Actualizar_Libro::class.java)
                              startActivity(intent)
                              dialog.dismiss()
                          }*/

                            cD_ActualizarPrestamo.setOnClickListener {
                                val intent = Intent(this@Listar_Prestamos, Actualizar_Prestamo::class.java)
                                intent.putExtra("idPrestamo", id_Prestamo)
                                intent.putExtra("uidUsuarioPrestamo", uidUsuarioPrestamo)
                                intent.putExtra("correo_UsuarioPrestamo", correoUsuario)
                                intent.putExtra("fecha_RegistroPrestamo", fechaHoraRegistro)
                                intent.putExtra("nombreSolicitante", nombreSolicitante)
                                intent.putExtra("libro", libro)
                                intent.putExtra("fechaPrestamo", fechaPrestamo)
                                intent.putExtra("fechaDevolucion", fechaDevolucion)
                                intent.putExtra("estadoPrestamo", estadoPrestamo)
                                startActivity(intent)
                                dialog.dismiss()
                            }

                            dialog.show()
                        }
                    })
                    return viewHolderPrestamos

                }
            }
        linearLayoutManager = LinearLayoutManager(this@Listar_Prestamos, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        recyclerviewPrestamos.layoutManager = linearLayoutManager
        recyclerviewPrestamos.adapter = firebaseRecyclerAdapter
    }

    private fun eliminarPrestamo(idPrestamo: String) {
        val builder = AlertDialog.Builder(this@Listar_Prestamos)

        builder.setTitle("Eliminar Prestamo")
        builder.setMessage("¿Realmente deseas eliminar este Registro?")
        builder.setPositiveButton("Sí") { dialog, _ ->
            // Eliminar libro de la base de datos
            val query = BASE_DE_DATOS.orderByChild("idPrestamo").equalTo(idPrestamo)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Usamos el for para recorrer en la base de datos los libros registrados por los usuarios
                    for (ds in snapshot.children) {
                        ds.ref.removeValue()
                    }
                    Toast.makeText(this@Listar_Prestamos, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Listar_Prestamos, error.message, Toast.LENGTH_SHORT).show()
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
