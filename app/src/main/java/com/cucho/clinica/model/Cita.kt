package com.cucho.clinicasalud.model

data class Cita(
    val nombreMedico: String,
    val especialidad: String,
    val fecha: String,
    val hora: String,
    val estado: String
)