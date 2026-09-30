package com.cucho.clinicasalud.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun MenuLateral(navController: NavController, alCerrar: () -> Unit) {

    // La ruta que se esta mostrando ahora mismo
    val entradaActual by navController.currentBackStackEntryAsState()
    val rutaActual = entradaActual?.destination?.route

    ModalDrawerSheet {

        // Cabecera del menu con los datos del paciente
        Column(modifier = Modifier.padding(24.dp)) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "JP",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Juan Perez",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Paciente",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        Spacer(modifier = Modifier.height(12.dp))

        NavigationDrawerItem(
            label = { Text("Inicio") },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            selected = rutaActual == Screen.Inicio.route,
            onClick = {
                alCerrar()
                navegarDesdeMenu(navController, Screen.Inicio.route)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Mis citas") },
            icon = { Icon(Icons.Filled.CalendarMonth, contentDescription = null) },
            selected = rutaActual == Screen.MisCitas.route,
            onClick = {
                alCerrar()
                navegarDesdeMenu(navController, Screen.MisCitas.route)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Historial medico") },
            icon = { Icon(Icons.Filled.History, contentDescription = null) },
            selected = rutaActual == Screen.Historial.route,
            onClick = {
                alCerrar()
                navegarDesdeMenu(navController, Screen.Historial.route)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Perfil") },
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            selected = rutaActual == Screen.Perfil.route,
            onClick = {
                alCerrar()
                navegarDesdeMenu(navController, Screen.Perfil.route)
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}

private fun navegarDesdeMenu(navController: NavController, ruta: String) {
    navController.navigate(ruta) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}