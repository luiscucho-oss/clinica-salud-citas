package com.cucho.clinicasalud.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
        }
    ) { espacioSeguro ->

        if (medico == null) {
            Text(
                text = "No se encontro el medico",
                modifier = Modifier
                    .padding(espacioSeguro)
                    .padding(24.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(espacioSeguro)
                    .padding(24.dp)
            ) {

                Text(
                    text = medico.nombre,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = medico.especialidad,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Primera seleccion unica: la fecha
                Text(
                    text = "Selecciona fecha",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    medico.fechas.forEachIndexed { indice, fecha ->
                        FilterChip(
                            selected = fechaSeleccionada == indice,
                            onClick = { fechaSeleccionada = indice },
                            label = { Text(fecha) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Segunda seleccion unica: la hora
                Text(
                    text = "Selecciona hora",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.selectableGroup(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    medico.horas.forEachIndexed { indice, hora ->
                        FilterChip(
                            selected = horaSeleccionada == indice,
                            onClick = { horaSeleccionada = indice },
                            label = { Text(hora) }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

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
                    // Solo se habilita cuando AMBAS estan elegidas
                    enabled = fechaSeleccionada >= 0 && horaSeleccionada >= 0,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar cita")
                }
            }
        }
    }
}