package br.edu.ifpe.entrelinhas.ui.features.categorias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria

// Janelinha para criar (categoria = null) ou editar uma categoria.
// Devolve (nome, precoCombinado).
@Composable
fun CategoriaDialog(
    categoria: Categoria?,
    onFechar: () -> Unit,
    onSalvar: (String, Boolean) -> Unit
) {
    var nome by remember { mutableStateOf(categoria?.nome ?: "") }
    var precoCombinado by remember { mutableStateOf(categoria?.precoCombinado ?: false) }
    var erroNome by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onFechar,
        title = {
            Text(
                stringResource(
                    if (categoria == null) R.string.nova_categoria else R.string.editar_categoria
                )
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = {
                        nome = it
                        erroNome = false
                    },
                    label = { Text(stringResource(R.string.categoria_nome)) },
                    isError = erroNome,
                    supportingText = {
                        if (erroNome) Text(stringResource(R.string.erro_nome_categoria))
                    },
                    singleLine = true
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(
                        checked = precoCombinado,
                        onCheckedChange = { precoCombinado = it }
                    )
                    Text(
                        text = stringResource(R.string.categoria_preco_combinado),
                        modifier = Modifier.padding(start = 12.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (nome.isBlank()) {
                    erroNome = true
                } else {
                    onSalvar(nome.trim(), precoCombinado)
                    onFechar()
                }
            }) { Text(stringResource(R.string.salvar)) }
        },
        dismissButton = {
            TextButton(onClick = onFechar) { Text(stringResource(R.string.cancelar)) }
        }
    )
}
