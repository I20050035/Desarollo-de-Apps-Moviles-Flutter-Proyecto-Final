package com.miappsoft.biblioteca.Categorias

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R

public class ViewHolder_Categoria(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        idCategoria: String,
        uidUsuarioCategorias: String,
        correoUsuarioCategorias: String,
        fechaHoraRegistroCategorias: String,
        libroCategoria: String,
        nombreCategoria: String,
        descripcion: String,

        ) {
        val id_Categorias_Item: TextView = mView.findViewById(R.id.id_Categorias_Item)
        val uid_UsuarioCategorias_Item: TextView = mView.findViewById(R.id.uid_UsuarioCategorias_Item)
        val correo_UsuarioCategorias_Item: TextView = mView.findViewById(R.id.correo_UsuarioCategorias_Item)
        val fecha_Hora_RegistroCategorias_Item: TextView = mView.findViewById(R.id.fecha_Hora_RegistroCategorias_Item)
        val libroCategoria_Item: TextView = mView.findViewById(R.id.libroCategoria_Item)
        val nombreCategoria_Item: TextView = mView.findViewById(R.id.nombreCategoria_Item)
        val descripcion_Item: TextView = mView.findViewById(R.id.descripcion_Item)




        id_Categorias_Item.text = idCategoria
        uid_UsuarioCategorias_Item.text = uidUsuarioCategorias
        correo_UsuarioCategorias_Item.text = correoUsuarioCategorias
        fecha_Hora_RegistroCategorias_Item.text = fechaHoraRegistroCategorias
        libroCategoria_Item.text = libroCategoria
        nombreCategoria_Item.text = nombreCategoria
        descripcion_Item.text = descripcion

    }
}