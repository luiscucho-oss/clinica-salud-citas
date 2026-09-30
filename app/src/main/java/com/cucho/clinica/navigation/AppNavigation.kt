package com.cucho.clinicasalud.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cucho.clinicasalud.data.citasIniciales
import com.cucho.clinicasalud.model.Cita
import com.cucho.clinicasalud.screens.AgendarCitaScreen
import com.cucho.clinicasalud.screens.ConfirmacionScreen
import com.cucho.clinicasalud.screens.HistorialScreen
import com.cucho.clinicasalud.screens.InicioScreen
import com.cucho.clinicasalud.screens.MisCitasScreen
import com.cucho.clinicasalud.screens.PerfilMedicoScreen
import com.cucho.clinicasalud.screens.PerfilScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Lista de citas compartida por toda la app
    val citas = remember {
        mutableStateListOf<Cita>().also { lista ->
            lista.addAll(citasIniciales)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route
    ) {
        composable(Screen.Inicio.route) {
            InicioScreen(navController)
        }
        composable(Screen.MisCitas.route) {
            MisCitasScreen(navController, citas)
        }
        composable(Screen.Historial.route) {
            HistorialScreen(navController)
        }
        composable(Screen.Perfil.route) {
            PerfilScreen(navController)
        }

        composable(
            route = Screen.PerfilMedico.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            PerfilMedicoScreen(navController, medicoId)
        }

        composable(
            route = Screen.AgendarCita.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            AgendarCitaScreen(navController, medicoId, citas)
        }

        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(
                navArgument("medicoId") { type = NavType.IntType },
                navArgument("fechaIndex") { type = NavType.IntType },
                navArgument("horaIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val medicoId = backStackEntry.arguments?.getInt("medicoId") ?: 0
            val fechaIndex = backStackEntry.arguments?.getInt("fechaIndex") ?: 0
            val horaIndex = backStackEntry.arguments?.getInt("horaIndex") ?: 0
            ConfirmacionScreen(navController, medicoId, fechaIndex, horaIndex)
        }
    }
}