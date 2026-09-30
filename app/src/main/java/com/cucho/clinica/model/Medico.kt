package com.cucho.clinicasalud.model

data class Medico(
    val id: Int,
    val nombre: String,
    val especialidad: String,
    val calificacion: Double,
    val resenas: Int,
    val aniosExperiencia: Int,
    val descripcion: String,
    val fechas: List<String>,
    val horas: List<String>
)