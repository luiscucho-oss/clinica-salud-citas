package com.cucho.clinicasalud.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.data.buscarMedicoPorId
import com.cucho.clinicasalud.navigation.Screen

@Composable
fun ConfirmacionScreen(
    navController: NavController,
    medicoId: Int,
    fechaIndex: Int,
    horaIndex: Int
) {
    val medico = buscarMedicoPorId(medicoId)

    val fechaElegida = medico?.fechas?.getOrNull(fechaIndex) ?: "Fecha no disponible"
    val horaElegida = medico?.horas?.getOrNull(horaIndex) ?: "Hora no disponible"

    Scaffold { espacioSeguro ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioSeguro)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Circulo de exito grande
            Surface(
                modifier = Modifier.size(100.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
                shadowElevation = 4.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Exito",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¡Cita agendada con éxito!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Tu solicitud ha sido procesada correctamente",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // El resumen dentro de una Card con filas de informacion
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Resumen de la cita",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FilaInformacion(
                        icono = Icons.Filled.Person,
                        etiqueta = "MÉDICO",
                        valor = medico?.nombre ?: "Medico"
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    FilaInformacion(
                        icono = Icons.Filled.LocalHospital,
                        etiqueta = "ESPECIALIDAD",
                        valor = medico?.especialidad ?: "Especialidad"
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    FilaInformacion(
                        icono = Icons.Filled.Event,
                        etiqueta = "FECHA",
                        valor = fechaElegida
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )

                    FilaInformacion(
                        icono = Icons.Filled.Schedule,
                        etiqueta = "HORA",
                        valor = horaElegida
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Los dos botones al final
            Button(
                onClick = {
                    navController.navigate(Screen.MisCitas.route) {
                        popUpTo(Screen.Inicio.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Ver mis citas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = {
                    navController.navigate(Screen.Inicio.route) {
                        popUpTo(Screen.Inicio.route) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Volver al inicio",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FilaInformacion(
    icono: ImageVector,
    etiqueta: String,
    valor: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}