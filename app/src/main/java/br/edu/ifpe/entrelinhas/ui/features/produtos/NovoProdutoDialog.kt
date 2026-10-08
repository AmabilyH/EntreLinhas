package br.edu.ifpe.entrelinhas.ui.features.produtos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.ifpe.entrelinhas.ui.util.reaisParaCentavos

// Janelinha para cadastrar um produto. Devolve (nome, categoria, preço em centavos).
@Composable
fun NovoProdutoDialog(
    onFechar: () -> Unit,
    onSalvar: (String, String, Long) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onFechar,
        title = { Text("Novo produto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") }
                )
                OutlinedTextField(
                    value = categoria,
                    onValueChange = { categoria = it },
                    label = { Text("Categoria") }
                )
                OutlinedTextField(
                    value = preco,
                    onValueChange = { preco = it },
                    label = { Text("Preço (R$)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val centavos = reaisParaCentavos(preco)
                if (nome.isNotBlank() && categoria.isNotBlank() && centavos != null) {
                    onSalvar(nome.trim(), categoria.trim(), centavos)
                    onFechar()
                }
            }) { Text("Salvar") }
        },
        dismissButton = {
            TextButton(onClick = onFechar) { Text("Cancelar") }
        }
    )
}