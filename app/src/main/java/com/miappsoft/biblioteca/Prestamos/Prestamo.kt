package com.miappsoft.biblioteca.Prestamos

data class Prestamo(
    var idPrestamo: String? = null,
    var uidUsuarioPrestamo: String? = null,
    var correoUsuarioPrestamo: String? = null,
    var fechaHoraActualPrestamo: String? = null,
    var nombreSolicitante: String? = null,
    var libro: String? = null,
    var fechaPrestamo: String? = null, // Usar nombre consistente sin acentos
    var fechaDevolucion: String? = null, // Usar nombre consistente sin acentos
    var estadoPrestamo: String? = null
)
