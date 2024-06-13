package com.miappsoft.biblioteca.Libros

data class Libro(
    var idLibro: String? = null,
    var uidUsuario: String? = null,
    var correoUsuario: String? = null,
    var fechaHoraActual: String? = null,
    var titulo: String? = null,
    var autor: String? = null,
    var genero: String? = null,
    var añoPublicacion: String? = null,
    var numeroPaginas: String? = null,
    var cantidadDisponible: String? = null
)
