package com.miappsoft.biblioteca.Reseñas

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R

public class ViewHolder_Resena(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        idReseña: String,
        uidUsuarioReseñas: String,
        correoUsuarioReseñas: String,
        fechaHoraRegistroReseñas: String,
        libroReseña: String,
        autorReseña: String,
        fechaReseña: String,
        calificacion: String,
        comentarios: String,

        ) {
        val id_Reseñas_Item: TextView = mView.findViewById(R.id.id_Reseñas_Item)
        val uid_UsuarioReseñas_Item: TextView = mView.findViewById(R.id.uid_UsuarioReseñas_Item)
        val correo_UsuarioReseñas_Item: TextView = mView.findViewById(R.id.correo_UsuarioReseñas_Item)
        val fecha_Hora_RegistroReseñas_Item: TextView = mView.findViewById(R.id.fecha_Hora_RegistroReseñas_Item)
        val libroReseña_Item: TextView = mView.findViewById(R.id.libroReseña_Item)
        val autorReseña_Item: TextView = mView.findViewById(R.id.autorReseña_Item)
        val fechaReseña_Item: TextView = mView.findViewById(R.id.fechaReseña_Item)
        val calificacion_Item: TextView = mView.findViewById(R.id.calificacion_Item)
        val comentarios_Item: TextView = mView.findViewById(R.id.comentarios_Item)



        id_Reseñas_Item.text = idReseña
        uid_UsuarioReseñas_Item.text = uidUsuarioReseñas
        correo_UsuarioReseñas_Item.text = correoUsuarioReseñas
        fecha_Hora_RegistroReseñas_Item.text = fechaHoraRegistroReseñas
        libroReseña_Item.text = libroReseña
        autorReseña_Item.text = autorReseña
        fechaReseña_Item.text = fechaReseña
        calificacion_Item.text = calificacion
        comentarios_Item.text = comentarios

    }
}