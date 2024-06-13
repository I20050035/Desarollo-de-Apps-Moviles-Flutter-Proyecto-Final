package com.miappsoft.biblioteca.Editoriales

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miappsoft.biblioteca.R


public class ViewHolder_Editorial(itemView: View) : RecyclerView.ViewHolder(itemView) {

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
        idEditorial: String,
        uidUsuarioEditoriales: String,
        correoUsuarioEditoriales: String,
        fechaHoraRegistroEditoriales: String,
        nombresEditoriales: String,
        paisEditorial: String,
        año_FundacionEditorial: String,
        libro_Editorial: String,

        ) {
        val id_Editoriales_Item: TextView = mView.findViewById(R.id.id_Editoriales_Item)
        val uid_UsuarioEditoriales_Item: TextView = mView.findViewById(R.id.uid_UsuarioEditoriales_Item)
        val correo_UsuarioEditoriales_Item: TextView = mView.findViewById(R.id.correo_UsuarioEditoriales_Item)
        val fecha_Hora_RegistroEditoriales_Item: TextView = mView.findViewById(R.id.fecha_Hora_RegistroEditoriales_Item)
        val nombresEditorial_Item: TextView = mView.findViewById(R.id.nombresEditorial_Item)
        val paisEditorial_Item: TextView = mView.findViewById(R.id.paisEditorial_Item)
        val año_FundacionEditorial_Item: TextView = mView.findViewById(R.id.año_FundacionEditorial_Item)
        val libro_Editorial_Item: TextView = mView.findViewById(R.id.libro_Editorial_Item)



        id_Editoriales_Item.text = idEditorial
        uid_UsuarioEditoriales_Item.text = uidUsuarioEditoriales
        correo_UsuarioEditoriales_Item.text = correoUsuarioEditoriales
        fecha_Hora_RegistroEditoriales_Item.text = fechaHoraRegistroEditoriales
        nombresEditorial_Item.text = nombresEditoriales
        paisEditorial_Item.text = paisEditorial
        año_FundacionEditorial_Item.text = año_FundacionEditorial
        libro_Editorial_Item.text = libro_Editorial
    }
}