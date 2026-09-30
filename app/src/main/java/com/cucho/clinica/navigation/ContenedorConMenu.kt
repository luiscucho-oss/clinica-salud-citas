package com.cucho.clinicasalud.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContenedorConMenu(
    navController: NavController,
    titulo: String,
    content: @Composable (PaddingValues) -> Unit
) {
    // Guarda si el menu esta abierto o cerrado
    val estadoDrawer = rememberDrawerState(initialValue = DrawerValue.Closed)

    // Necesario porque abrir y cerrar el menu son funciones suspend
    val alcance = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = estadoDrawer,
        drawerContent = {
            MenuLateral(
                navController = navController,
                alCerrar = {
                    alcance.launch { estadoDrawer.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(titulo) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                alcance.launch { estadoDrawer.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            },
            content = content
        )
    }
}