package br.edu.ifpe.entrelinhas.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button

@Composable
fun TelaPedidos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "EntreLinhas")

        Text(text = "0 abertos")

        Text(text = "Faturamento do mês")

        Text(text = "Pesquisar por nome ou data...")

        Text(text = "Pedidos")

        Button(
            onClick = { },
        ) {
            Text(text = "Novo pedido")
        }

        Text(text = "Nenhum pedido por aqui ainda. Toque em 'Novo pedido' para anotar o primeiro.")
    }
}