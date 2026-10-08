package br.edu.ifpe.entrelinhas.ui.features.estoque

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
import br.edu.ifpe.entrelinhas.ui.util.formatarQuantidade

// Aba Estoque: lista os materiais guardados no banco.
@Composable
fun TelaEstoque(
    navController: NavController,
    viewModel: EstoqueViewModel = viewModel()
) {
    val materiais by viewModel.materiais.collectAsState()

    TelaComBarra(
        rotaAtual = NavTarget.Estoque.route,
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
                    titulo = stringResource(R.string.aba_estoque),
                    botao = stringResource(R.string.novo_material),
                    // TEMPORÁRIO: cria um material de exemplo a cada toque
                    onClick = { viewModel.criarDadosDeExemplo() }
                )
            }

            if (materiais.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.estoque_vazio),
                        fontSize = 15.sp,
                        color = TextoSecundario,
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(20.dp)
                    )
                }
            } else {
                items(materiais, key = { it.id }) { material ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .cartao(28.dp)
                            .padding(20.dp)
                    ) {
                        Text(
                            text = material.nome,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextoEscuro
                        )
                        Text(
                            text = "${formatarQuantidade(material.quantidade)} ${material.unidade}" +
                                    " · avisar em ${formatarQuantidade(material.avisarEm)}",
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}