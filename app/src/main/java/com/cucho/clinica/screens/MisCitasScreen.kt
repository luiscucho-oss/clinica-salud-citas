package com.cucho.clinicasalud.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.model.Cita
import com.cucho.clinicasalud.navigation.ContenedorConMenu

@Composable
fun MisCitasScreen(
    navController: NavController,
    citas: MutableList<Cita>
) {

    var citaACancelar by remember { mutableStateOf<Cita?>(null) }

    ContenedorConMenu(
        navController = navController,
        titulo = "Mis citas"
    ) { espacioSeguro ->

        if (citas.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(espacioSeguro)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Todavia no tienes citas",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Agenda una cita desde la pantalla de inicio",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(espacioSeguro),
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(citas) { cita ->
                    TarjetaCita(
                        cita = cita,
                        onCancelarClick = { citaACancelar = cita }
                    )
                }
            }
        }

        citaACancelar?.let { cita ->
            AlertDialog(
                onDismissRequest = { citaACancelar = null },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Advertencia",
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                title = {
                    Text(text = "Cancelar cita")
                },
                text = {
                    Text(
                        text = "¿Estás seguro de que deseas cancelar la cita con ${cita.nombreMedico} el ${cita.fecha} a las ${cita.hora}?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            citas.remove(cita)
                            citaACancelar = null
                        }
                    ) {
                        Text(
                            text = "Si, cancelar",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { citaACancelar = null }
                    ) {
                        Text(text = "No, volver")
                    }
                }
            )
        }
    }
}

@Composable
fun TarjetaCita(
    cita: Cita,
    onCancelarClick: () -> Unit
) {

    val esConfirmada = cita.estado == "Confirmada"

    val colorFondoEstado = if (esConfirmada) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val colorTextoEstado = if (esConfirmada) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = cita.nombreMedico,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = cita.especialidad,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = cita.fecha + ", " + cita.hora,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(colorFondoEstado)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = cita.estado,
                    style = MaterialTheme.typography.labelSmall,
                    color = colorTextoEstado
                )
            }

            if (esConfirmada) {
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onCancelarClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                    )
                ) {
                    Text(text = "Cancelar cita")
                }
            }
        }
    }
}