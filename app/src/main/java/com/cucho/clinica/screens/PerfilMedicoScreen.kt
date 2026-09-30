package com.cucho.clinicasalud.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.data.buscarMedicoPorId
import com.cucho.clinicasalud.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(navController: NavController, medicoId: Int) {

    val medico = buscarMedicoPorId(medicoId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil del medico") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                                navController.navigate(
                                    Screen.AgendarCita.createRoute(medico.id)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Agendar cita",
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
                    .verticalScroll(rememberScrollState())
            ) {
                // Cabecera con degradado y el avatar circular superpuesto, centrado
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    // Fondo degradado
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            )
                    )

                    // Avatar circular superpuesto y centrado
                    Surface(
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .size(108.dp),
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface,
                        shadowElevation = 6.dp
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = obtenerIniciales(medico.nombre),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre y Especialidad
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = medico.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = medico.especialidad,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Fila de datos con iconos pequenos: calificacion, resenas y anios de experiencia
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DatoItem(
                            icono = Icons.Filled.Star,
                            valor = medico.calificacion.toString(),
                            etiqueta = "Calificacion"
                        )
                        DatoItem(
                            icono = Icons.Filled.RateReview,
                            valor = "${medico.resenas}",
                            etiqueta = "Reseñas"
                        )
                        DatoItem(
                            icono = Icons.Filled.Badge,
                            valor = "${medico.aniosExperiencia} años",
                            etiqueta = "Experiencia"
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // La descripcion dentro de una Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Acerca del especialista",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = medico.descripcion,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun DatoItem(
    icono: ImageVector,
    valor: String,
    etiqueta: String
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = valor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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