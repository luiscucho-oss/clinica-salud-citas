package com.cucho.clinicasalud.navigation

sealed class Screen(val route: String) {

    // Destinos del menu lateral
    object Inicio : Screen("inicio")
    object MisCitas : Screen("citas")
    object Historial : Screen("historial")
    object Perfil : Screen("perfil")

    // Flujo secuencial con argumentos
    object PerfilMedico : Screen("medico/{medicoId}") {
        fun createRoute(medicoId: Int): String = "medico/$medicoId"
    }

    object AgendarCita : Screen("agendar/{medicoId}") {
        fun createRoute(medicoId: Int): String = "agendar/$medicoId"
    }

    object Confirmacion : Screen("confirmacion/{medicoId}/{fechaIndex}/{horaIndex}") {
        fun createRoute(medicoId: Int, fechaIndex: Int, horaIndex: Int): String =
            "confirmacion/$medicoId/$fechaIndex/$horaIndex"
    }
}