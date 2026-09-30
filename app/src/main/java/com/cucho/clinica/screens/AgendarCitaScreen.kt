package com.cucho.clinicasalud.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.data.buscarMedicoPorId
import com.cucho.clinicasalud.model.Cita
import com.cucho.clinicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    navController: NavController,
    medicoId: Int,
    citas: MutableList<Cita>
) {

    val medico = buscarMedicoPorId(medicoId)

    // -1 significa que todavia no se eligio
    var fechaSeleccionada by rememberSaveable { mutableIntStateOf(-1) }
    var horaSeleccionada by rememberSaveable { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agendar cita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (medico != null) {
                Surface(
                    shadowElevation = 8.dp,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = {
                                citas.add(
                                    Cita(
                                        nombreMedico = medico.nombre,
                                        especialidad = medico.especialidad,
                                        fecha = medico.fechas[fechaSeleccionada],
                                        hora = medico.horas[horaSeleccionada],
                                        estado = "Confirmada"
                                    )
                                )
                                navController.navigate(
                                    Screen.Confirmacion.createRoute(
                                        medicoId = medico.id,
                                        fechaIndex = fechaSeleccionada,
                                        horaIndex = horaSeleccionada
                                    )
                                )
                            },
                            enabled = fechaSeleccionada >= 0 && horaSeleccionada >= 0,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Confirmar cita",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    ) { espacioSeguro ->

        if (medico == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(espacioSeguro)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontro el medico",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(espacioSeguro)
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Card arriba con los datos del medico
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(56.dp),
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = obtenerIniciales(medico.nombre),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = medico.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = medico.especialidad,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = medico.calificacion.toString(),
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Primera seleccion unica: la fecha
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selecciona fecha",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    medico.fechas.forEachIndexed { indice, fecha ->
                        val estaSeleccionada = fechaSeleccionada == indice
                        FilterChip(
                            selected = estaSeleccionada,
                            onClick = { fechaSeleccionada = indice },
                            label = {
                                Text(
                                    text = fecha,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (estaSeleccionada) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Segunda seleccion unica: la hora
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selecciona hora",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    medico.horas.forEachIndexed { indice, hora ->
                        val estaSeleccionada = horaSeleccionada == indice
                        FilterChip(
                            selected = estaSeleccionada,
                            onClick = { horaSeleccionada = indice },
                            label = {
                                Text(
                                    text = hora,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (estaSeleccionada) FontWeight.Bold else FontWeight.Normal,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

private fun obtenerIniciales(nombre: String): String {
    val partes = nombre.split(" ").filter { it.isNotBlank() }
    val nombresSinTitulo = partes.filter { !it.startsWith("Dr", ignoreCase = true) }
    return when {
        nombresSinTitulo.size >= 2 -> "${nombresSinTitulo[0].first()}${nombresSinTitulo[1].first()}".uppercase()
        nombresSinTitulo.isNotEmpty() -> "${nombresSinTitulo[0].first()}".uppercase()
        partes.size >= 2 -> "${partes[0].first()}${partes[1].first()}".uppercase()
        partes.isNotEmpty() -> "${partes[0].first()}".uppercase()
        else -> "M"
    }
}