package br.edu.ifpe.entrelinhas.ui.features.categorias

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import br.edu.ifpe.entrelinhas.ui.features.components.TituloComBotao
import br.edu.ifpe.entrelinhas.ui.features.components.cartao
import br.edu.ifpe.entrelinhas.ui.navigation.EspacoBarraInferior
import br.edu.ifpe.entrelinhas.ui.navigation.NavTarget
import br.edu.ifpe.entrelinhas.ui.navigation.TelaComBarra
import br.edu.ifpe.entrelinhas.ui.navigation.irParaAba
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario

// Tela de categorias (aberta a partir da aba Produtos): listar, adicionar, editar e excluir.
@Composable
fun TelaCategorias(
    navController: NavController,
    viewModel: CategoriasViewModel = viewModel()
) {
    val categorias by viewModel.categorias.collectAsState()
    val erro by viewModel.erro.collectAsState()
    var mostrarNova by remember { mutableStateOf(false) }
    var emEdicao by remember { mutableStateOf<Categoria?>(null) }
    var paraExcluir by remember { mutableStateOf<Categoria?>(null) }

    // A barra inferior continua marcando "Produtos", porque esta tela faz parte dessa aba.
    TelaComBarra(
        rotaAtual = NavTarget.Produtos.route,
        onNavegar = { rota -> navController.irParaAba(rota) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 16.dp,
                end = 20.dp,
                bottom = EspacoBarraInferior
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                TextButton(onClick = { navController.popBackStack() }) {
                    Text(stringResource(R.string.voltar))
                }
            }
            item {
                TituloComBotao(
                    titulo = stringResource(R.string.categorias_titulo),
                    botao = stringResource(R.string.botao_categoria),
                    onClick = { mostrarNova = true }
                )
            }

            if (erro) {
                item {
                    Text(
                        text = stringResource(R.string.erro_salvar),
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                }
            }

            if (categorias.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.categorias_vazio),
                        fontSize = 15.sp,
                        color = TextoSecundario,
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(20.dp)
                    )
                }
            } else {
                items(categorias, key = { it.id }) { categoria ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(start = 20.dp, top = 12.dp, end = 8.dp, bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = categoria.nome,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextoEscuro
                            )
                            if (categoria.precoCombinado) {
                                Text(
                                    text = stringResource(R.string.categoria_preco_combinado),
                                    fontSize = 13.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                        TextButton(onClick = { emEdicao = categoria }) {
                            Text(stringResource(R.string.editar))
                        }
                        TextButton(onClick = { paraExcluir = categoria }) {
                            Text(stringResource(R.string.excluir))
                        }
                    }
                }
            }
        }

        if (mostrarNova) {
            CategoriaDialog(
                categoria = null,
                onFechar = { mostrarNova = false },
                onSalvar = { nome, precoCombinado -> viewModel.salvar(nome, precoCombinado) }
            )
        }

        emEdicao?.let { atual ->
            CategoriaDialog(
                categoria = atual,
                onFechar = { emEdicao = null },
                onSalvar = { nome, precoCombinado ->
                    viewModel.atualizar(atual.copy(nome = nome, precoCombinado = precoCombinado))
                }
            )
        }

        paraExcluir?.let { atual ->
            AlertDialog(
                onDismissRequest = { paraExcluir = null },
                title = { Text(stringResource(R.string.excluir_categoria_titulo)) },
                text = { Text(stringResource(R.string.excluir_categoria_msg)) },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.excluir(atual)
                        paraExcluir = null
                    }) { Text(stringResource(R.string.excluir)) }
                },
                dismissButton = {
                    TextButton(onClick = { paraExcluir = null }) {
                        Text(stringResource(R.string.cancelar))
                    }
                }
            )
        }
    }
}