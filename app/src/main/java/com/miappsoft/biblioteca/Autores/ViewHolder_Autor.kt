package com.miappsoft.biblioteca.Autores

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R

public class ViewHolder_Autor(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        idAutor: String,
        uidUsuarioAutor: String,
        correoUsuarioAutor: String,
        fechaHoraRegistroAutor: String,
        nombresAutor: String,
        apellidosAutor: String,
        fechaNacimiento: String,
        paisNacimiento: String,
        libroEscrito: String,

    ) {
        val id_Autores_Item: TextView = mView.findViewById(R.id.id_Autores_Item)
        val uid_UsuarioAutores_Item: TextView = mView.findViewById(R.id.uid_UsuarioAutores_Item)
        val correo_UsuarioAutores_Item: TextView = mView.findViewById(R.id.correo_UsuarioAutores_Item)
        val fecha_Hora_RegistroAutores_Item: TextView = mView.findViewById(R.id.fecha_Hora_RegistroAutores_Item)
        val nombresAutor_Item: TextView = mView.findViewById(R.id.nombresAutor_Item)
        val apellidosAutor_Item: TextView = mView.findViewById(R.id.apellidosAutor_Item)
        val fecha_Nacimiento_Item: TextView = mView.findViewById(R.id.fecha_Nacimiento_Item)
        val pais_Nacimiento_Item: TextView = mView.findViewById(R.id.pais_Nacimiento_Item)
        val libro_Escrito_Item: TextView = mView.findViewById(R.id.libro_Escrito_Item)



        id_Autores_Item.text = idAutor
        uid_UsuarioAutores_Item.text = uidUsuarioAutor
        correo_UsuarioAutores_Item.text = correoUsuarioAutor
        fecha_Hora_RegistroAutores_Item.text = fechaHoraRegistroAutor
        nombresAutor_Item.text = nombresAutor
        apellidosAutor_Item.text = apellidosAutor
        fecha_Nacimiento_Item.text = fechaNacimiento
        pais_Nacimiento_Item.text = paisNacimiento
        libro_Escrito_Item.text = libroEscrito
    }
}
