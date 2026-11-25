package br.edu.ifpe.entrelinhas.ui.features.produtos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import br.edu.ifpe.entrelinhas.ui.util.reaisParaCentavos

// Janelinha para cadastrar um produto. Devolve (nome, id da categoria, preço em centavos).
@Composable
fun NovoProdutoDialog(
    categorias: List<Categoria>,
    onFechar: () -> Unit,
    onSalvar: (String, Long, Long) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf<Categoria?>(null) }
    var preco by remember { mutableStateOf("") }
    var erroNome by remember { mutableStateOf(false) }
    var erroCategoria by remember { mutableStateOf(false) }
    var erroPreco by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onFechar,
        title = { Text(stringResource(R.string.produto_dialog_titulo)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nome,
                    onValueChange = {
                        nome = it
                        erroNome = false
                    },
                    label = { Text(stringResource(R.string.produto_nome)) },
                    isError = erroNome,
                    supportingText = {
                        if (erroNome) Text(stringResource(R.string.erro_nome_produto))
                    },
                    singleLine = true
                )

                if (categorias.isEmpty()) {
                    Text(
                        text = stringResource(R.string.produto_sem_categorias),
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    SeletorCategoria(
                        escolhida = categoria,
                        categorias = categorias,
                        onEscolher = {
                            categoria = it
                            erroCategoria = false
                        }
                    )
                    if (erroCategoria) {
                        Text(
                            text = stringResource(R.string.erro_categoria_produto),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                OutlinedTextField(
                    value = preco,
                    onValueChange = {
                        preco = it
                        erroPreco = false
                    },
                    label = { Text(stringResource(R.string.produto_preco)) },
                    isError = erroPreco,
                    supportingText = {
                        if (erroPreco) Text(stringResource(R.string.erro_valor_invalido))
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = categorias.isNotEmpty(),
                onClick = {
                    val centavos = reaisParaCentavos(preco)
                    erroNome = nome.isBlank()
                    erroCategoria = categoria == null
                    erroPreco = centavos == null || centavos <= 0
                    val escolhida = categoria
                    if (!erroNome && !erroCategoria && !erroPreco && escolhida != null && centavos != null) {
                        onSalvar(nome.trim(), escolhida.id, centavos)
                        onFechar()
                    }
                }
            ) { Text(stringResource(R.string.salvar)) }
        },
        dismissButton = {
            TextButton(onClick = onFechar) { Text(stringResource(R.string.cancelar)) }
        }
    )
}

// Botão que abre uma lista com as categorias cadastradas.
@Composable
private fun SeletorCategoria(
    escolhida: Categoria?,
    categorias: List<Categoria>,
    onEscolher: (Categoria) -> Unit
) {
    var aberto by remember { mutableStateOf(false) }

    Column {
        OutlinedButton(
            onClick = { aberto = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = escolhida?.nome ?: stringResource(R.string.produto_categoria),
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(expanded = aberto, onDismissRequest = { aberto = false }) {
            categorias.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.nome) },
                    onClick = {
                        aberto = false
                        onEscolher(item)
                    }
                )
            }
        }
    }
}