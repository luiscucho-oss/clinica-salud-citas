package com.cucho.clinicasalud.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cucho.clinicasalud.navigation.ContenedorConMenu

@Composable
fun HistorialScreen(navController: NavController) {

    val historial = listOf(
        "Consulta cardiologica — 12 de agosto",
        "Analisis de sangre — 30 de julio",
        "Control pediatrico — 18 de junio",
        "Electrocardiograma — 05 de mayo"
    )

    ContenedorConMenu(
        navController = navController,
        titulo = "Historial medico"
    ) { espacioSeguro ->
        LazyColumn(
            modifier = Modifier.padding(espacioSeguro),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(historial) { registro ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = registro,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Atendido",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}