package com.cucho.clinicasalud.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.data.listaMedicos
import com.cucho.clinicasalud.model.Medico
import com.cucho.clinicasalud.navigation.ContenedorConMenu
import com.cucho.clinicasalud.navigation.Screen

@Composable
fun InicioScreen(navController: NavController) {

    val especialidades = listOf("Cardiologia", "Pediatria")

    // Estado de la especialidad seleccionada
    var especialidadSeleccionada by rememberSaveable { mutableStateOf("Cardiologia") }

    // Solo los medicos de la especialidad elegida
    val medicosFiltrados = listaMedicos.filter { medico ->
        medico.especialidad == especialidadSeleccionada
    }

    ContenedorConMenu(
        navController = navController,
        titulo = "Clinica Salud+"
    ) { espacioSeguro ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacioSeguro)
        ) {
            // Cabecera con degradado y esquinas inferiores redondeadas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 24.dp,
                            bottomEnd = 24.dp
                        )
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 20.dp)
            ) {
                Column {
                    Text(
                        text = "Hola, Juan",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Encuentra a tu especialista",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chips de especialidades dentro de la cabecera
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(especialidades) { especialidad ->
                            val estaSeleccionada = especialidadSeleccionada == especialidad
                            FilterChip(
                                selected = estaSeleccionada,
                                onClick = { especialidadSeleccionada = especialidad },
                                label = {
                                    Text(
                                        text = especialidad,
                                        fontWeight = if (estaSeleccionada) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.surface,
                                    selectedLabelColor = MaterialTheme.colorScheme.primary,
                                    containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f),
                                    labelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Medicos disponibles",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            // Lista vertical de medicos
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(medicosFiltrados) { medico ->
                    TarjetaMedico(
                        medico = medico,
                        onClick = {
                            navController.navigate(
                                Screen.PerfilMedico.createRoute(medico.id)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TarjetaMedico(medico: Medico, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar circular con iniciales
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

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = "${medico.aniosExperiencia} años de experiencia",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Ver perfil",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
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