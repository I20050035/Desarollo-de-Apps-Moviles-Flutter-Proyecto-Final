package com.miappsoft.biblioteca.Libros

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R

public class ViewHolder_Libro(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mView: View = itemView
    private var mClickListener: ClickListener? = null

    interface ClickListener {
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }

    fun setOnClickListener(clickListener: ClickListener) {
        mClickListener = clickListener
    }

    init {
        itemView.setOnClickListener {
            mClickListener?.onItemClick(it, bindingAdapterPosition)
        }

        itemView.setOnLongClickListener {
            mClickListener?.onItemLongClick(it, bindingAdapterPosition)
            true
        }
    }

    fun setearDatos(
        context: Context,
        idLibro: String,
        uidUsuario: String,
        correoUsuario: String,
        fechaHoraRegistro: String,
        titulo: String,
        autor: String,
        genero: String,
        añoPublicacion: String,
        numeroPaginas: String,
        cantidadDisponible: String

    ) {
        val id_Libro_Item: TextView = mView.findViewById(R.id.id_Libro_Item)
        val uid_Usuario_Item: TextView = mView.findViewById(R.id.uid_Usuario_Item)
        val correo_Usuario_Item: TextView = mView.findViewById(R.id.correo_Usuario_Item)
        val fecha_Hora_Registro_Item: TextView = mView.findViewById(R.id.fecha_Hora_Registro_Item)
        val tituloLibro_Item: TextView = mView.findViewById(R.id.tituloLibro_Item)
        val autorLibro_Item: TextView = mView.findViewById(R.id.autorLibro_Item)
        val generoLibro_Item: TextView = mView.findViewById(R.id.generoLibro_Item)
        val año_PublicaciónLibro_Item: TextView = mView.findViewById(R.id.año_PublicaciónLibro_Item)
        val numero_PaginasLibro_Item: TextView = mView.findViewById(R.id.numero_PaginasLibro_Item)
        val cantidad_DisponibleLibro_Item: TextView = mView.findViewById(R.id.cantidad_DisponibleLibro_Item)



        id_Libro_Item.text = idLibro
        uid_Usuario_Item.text = uidUsuario
        correo_Usuario_Item.text = correoUsuario
        fecha_Hora_Registro_Item.text = fechaHoraRegistro
        tituloLibro_Item.text = titulo
        autorLibro_Item.text = autor
        generoLibro_Item.text = genero
        año_PublicaciónLibro_Item.text = añoPublicacion
        numero_PaginasLibro_Item.text = numeroPaginas
        cantidad_DisponibleLibro_Item.text = cantidadDisponible

    }
}
