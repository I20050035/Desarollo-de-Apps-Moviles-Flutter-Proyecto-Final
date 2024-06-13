package com.miappsoft.biblioteca.Prestamos

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R

public class ViewHolder_Prestamo(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        idPrestamo: String,
        uidUsuarioPrestamo: String,
        correoUsuarioPrestamo: String,
        fechaHoraRegistroPrestamo: String,
        nombreSolicitante: String,
        libro: String,
        fechaPrestamo: String,
        fechaDevolucion: String,
        estadoPrestamo: String,


    ) {
        val id_Prestamos_Item: TextView = mView.findViewById(R.id.id_Prestamos_Item)
        val uid_UsuarioPrestamos_Item: TextView = mView.findViewById(R.id.uid_UsuarioPrestamos_Item)
        val correo_UsuarioPrestamos_Item: TextView = mView.findViewById(R.id.correo_UsuarioPrestamos_Item)
        val fecha_Hora_RegistroPrestamos_Item: TextView = mView.findViewById(R.id.fecha_Hora_RegistroPrestamos_Item)
        val nombreSolicitante_Item: TextView = mView.findViewById(R.id.nombreSolicitante_Item)
        val libro_Item: TextView = mView.findViewById(R.id.libro_Item)
        val fecha_Préstamo_Item: TextView = mView.findViewById(R.id.fecha_Préstamo_Item)
        val fecha_Devolución_Item: TextView = mView.findViewById(R.id.fecha_Devolución_Item)
        val estadoPrestamo_Item: TextView = mView.findViewById(R.id.estadoPrestamo_Item)



        id_Prestamos_Item.text = idPrestamo
        uid_UsuarioPrestamos_Item.text = uidUsuarioPrestamo
        correo_UsuarioPrestamos_Item.text = correoUsuarioPrestamo
        fecha_Hora_RegistroPrestamos_Item.text = fechaHoraRegistroPrestamo
        nombreSolicitante_Item.text = nombreSolicitante
        libro_Item.text = libro
        fecha_Préstamo_Item.text = fechaPrestamo
        fecha_Devolución_Item.text = fechaDevolucion
        estadoPrestamo_Item.text = estadoPrestamo

    }
}