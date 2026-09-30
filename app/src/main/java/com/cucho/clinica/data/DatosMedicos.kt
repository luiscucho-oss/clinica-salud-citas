package com.cucho.clinicasalud.data

import com.cucho.clinicasalud.model.Medico

val listaMedicos = listOf(
    Medico(
        id = 1,
        nombre = "Dra. Ana Torres",
        especialidad = "Cardiologia",
        calificacion = 4.9,
        resenas = 128,
        aniosExperiencia = 12,
        descripcion = "Especialista en arritmias e hipertension, con formacion en la Clinica Mayo.",
        fechas = listOf("Jue 26", "Vie 27", "Sab 28"),
        horas = listOf("9:00 am", "10:30 am", "3:00 pm")
    ),
    Medico(
        id = 2,
        nombre = "Dr. Luis Vega",
        especialidad = "Pediatria",
        calificacion = 4.7,
        resenas = 95,
        aniosExperiencia = 8,
        descripcion = "Pediatra con enfoque en control de crecimiento y vacunacion infantil.",
        fechas = listOf("Jue 26", "Vie 27", "Lun 30"),
        horas = listOf("8:00 am", "11:00 am", "4:00 pm")
    ),
    Medico(
        id = 3,
        nombre = "Dra. Rosa Diaz",
        especialidad = "Cardiologia",
        calificacion = 4.8,
        resenas = 143,
        aniosExperiencia = 15,
        descripcion = "Cardiologa clinica, experta en prevencion y rehabilitacion cardiaca.",
        fechas = listOf("Vie 27", "Sab 28", "Lun 30"),
        horas = listOf("9:30 am", "1:00 pm", "5:00 pm")
    ),
    Medico(
        id = 4,
        nombre = "Dr. Mario Salas",
        especialidad = "Pediatria",
        calificacion = 4.6,
        resenas = 71,
        aniosExperiencia = 6,
        descripcion = "Pediatra especializado en alergias y asma infantil.",
        fechas = listOf("Jue 26", "Sab 28", "Lun 30"),
        horas = listOf("10:00 am", "2:00 pm", "6:00 pm")
    )
)

fun buscarMedicoPorId(id: Int): Medico? {
    return listaMedicos.find { medico -> medico.id == id }
}