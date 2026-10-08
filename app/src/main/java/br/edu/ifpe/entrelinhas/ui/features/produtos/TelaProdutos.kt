package br.edu.ifpe.entrelinhas.ui.features.produtos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.ui.features.components.TituloComBotao
import br.edu.ifpe.entrelinhas.ui.features.components.cartao
import br.edu.ifpe.entrelinhas.ui.navigation.EspacoBarraInferior
import br.edu.ifpe.entrelinhas.ui.navigation.NavTarget
import br.edu.ifpe.entrelinhas.ui.navigation.TelaComBarra
import br.edu.ifpe.entrelinhas.ui.navigation.irParaAba
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario
import br.edu.ifpe.entrelinhas.ui.util.formatarReais

// Aba Produtos: lista os produtos e permite cadastrar um novo.
@Composable
fun TelaProdutos(
    navController: NavController,
    viewModel: ProdutosViewModel = viewModel()
) {
    val produtos by viewModel.produtos.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }

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
                TituloComBotao(
                    titulo = stringResource(R.string.aba_produtos),
                    botao = stringResource(R.string.novo_produto),
                    onClick = { mostrarDialogo = true }
                )
            }

            if (produtos.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.produtos_vazio),
                        fontSize = 15.sp,
                        color = TextoSecundario,
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(20.dp)
                    )
                }
            } else {
                items(produtos, key = { it.id }) { produto ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = produto.nome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextoEscuro
                        )
                        Text(
                            text = "${produto.categoria} · ${formatarReais(produto.preco)}",
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }

        if (mostrarDialogo) {
            NovoProdutoDialog(
                onFechar = { mostrarDialogo = false },
                onSalvar = { nome, categoria, preco -> viewModel.salvar(nome, categoria, preco) }
            )
        }
    }
}