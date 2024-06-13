package com.miappsoft.biblioteca.Reseñas

data class Resena(
    var idReseña: String? = null,
    var uidUsuarioReseñas: String? = null,
    var correoUsuarioReseñas: String? = null,
    var fechaHoraActualReseñas: String? = null,
    var libroReseña: String? = null,
    var autorReseña: String? = null,
    var fechaReseña: String? = null,
    var calificacion: String? = null,
    var comentarios: String? = null,
)