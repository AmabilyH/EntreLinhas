package br.edu.ifpe.entrelinhas.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.model.PedidoComItens
import br.edu.ifpe.entrelinhas.ui.features.components.cartao
import br.edu.ifpe.entrelinhas.ui.navigation.EspacoBarraInferior
import br.edu.ifpe.entrelinhas.ui.navigation.NavTarget
import br.edu.ifpe.entrelinhas.ui.navigation.TelaComBarra
import br.edu.ifpe.entrelinhas.ui.navigation.irParaAba
import br.edu.ifpe.entrelinhas.ui.theme.AlertaFundo
import br.edu.ifpe.entrelinhas.ui.theme.AlertaIcone
import br.edu.ifpe.entrelinhas.ui.theme.ChipNeutroFundo
import br.edu.ifpe.entrelinhas.ui.theme.FonteCaligrafica
import br.edu.ifpe.entrelinhas.ui.theme.NegativoFundo
import br.edu.ifpe.entrelinhas.ui.theme.NegativoTexto
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.RoxoProfundo
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario
import br.edu.ifpe.entrelinhas.ui.theme.VerdePago
import br.edu.ifpe.entrelinhas.ui.theme.VerdePagoFundo
import br.edu.ifpe.entrelinhas.ui.util.formatarReais
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Tela principal (aba Pedidos).
@Composable
fun TelaPedidos(
    navController: NavController,
    viewModel: PedidosViewModel = viewModel()
) {
    val pedidos by viewModel.pedidos.collectAsState()
    val abertos by viewModel.abertos.collectAsState()
    val estoqueBaixo by viewModel.estoqueBaixo.collectAsState()
    val produtos by viewModel.produtos.collectAsState()
    val clientes by viewModel.clientes.collectAsState()

    var mostrarNovoPedido by remember { mutableStateOf(false) }

    TelaComBarra(
        rotaAtual = NavTarget.Home.route,
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
            item { Cabecalho(abertos = abertos) }

            // Valores provisórios em zero. A ligação ao banco é o RF12 (faturamento).
            item { CartaoFaturamento(ganho = 0L, gasto = 0L) }

            if (estoqueBaixo.isNotEmpty()) {
                item { AvisoEstoqueBaixo(nomes = estoqueBaixo.map { it.nome }) }
            }

            // Só visual por enquanto. A busca de verdade é o RF05.
            item { CampoBusca() }

            item {
                TituloPedidos(onNovoPedido = { mostrarNovoPedido = true })
            }

            if (pedidos.isEmpty()) {
                item { MensagemListaVazia() }
            } else {
                items(pedidos, key = { it.pedido.id }) { item ->
                    CartaoPedido(item = item)
                }
            }
        }

        if (mostrarNovoPedido) {
            NovoPedidoSheet(
                produtos = produtos,
                clientes = clientes,
                onFechar = { mostrarNovoPedido = false },
                onSalvar = { dados ->
                    viewModel.salvarPedido(dados)
                    mostrarNovoPedido = false
                }
            )
        }
    }
}

@Composable
private fun Cabecalho(abertos: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.icone_entrelinhas),
            contentDescription = stringResource(R.string.icone_descricao),
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.app_name),
                fontFamily = FonteCaligrafica,
                fontSize = 30.sp,
                color = RoxoProfundo
            )
            Text(
                text = stringResource(R.string.app_subtitulo),
                fontSize = 13.sp,
                color = TextoSecundario
            )
        }

        val textoAbertos = if (abertos == 1) {
            stringResource(R.string.um_aberto)
        } else {
            stringResource(R.string.n_abertos, abertos)
        }
        Text(
            text = textoAbertos,
            fontSize = 13.sp,
            color = TextoSecundario,
            modifier = Modifier
                .cartao(20.dp)
                .padding(horizontal = 14.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun CartaoFaturamento(ganho: Long, gasto: Long) {
    val lucro = ganho - gasto
    val percentual = if (ganho > 0) lucro * 100.0 / ganho else 0.0
    val fracao = if (ganho > 0) (lucro.toFloat() / ganho).coerceIn(0f, 1f) else 0f
    val positivo = lucro >= 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .cartao(32.dp)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.faturamento_titulo).uppercase(),
                fontSize = 12.sp,
                letterSpacing = 1.5.sp,
                color = TextoSecundario
            )
            Chip(
                texto = stringResource(R.string.lucro_percentual, formatarPercentual(percentual)),
                fundo = if (positivo) VerdePagoFundo else NegativoFundo,
                corTexto = if (positivo) VerdePago else NegativoTexto
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ValorFaturamento(
                rotulo = stringResource(R.string.ganho),
                valor = formatarReais(ganho),
                cor = TextoEscuro
            )
            ValorFaturamento(
                rotulo = stringResource(R.string.gasto),
                valor = formatarReais(gasto),
                cor = TextoEscuro
            )
            ValorFaturamento(
                rotulo = stringResource(R.string.lucro),
                valor = formatarReais(lucro),
                cor = if (positivo) VerdePago else NegativoTexto
            )
        }

        // Barra de progresso: quanto do ganho virou lucro
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(ChipNeutroFundo)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fracao)
                    .fillMaxHeight()
                    .background(VerdePago)
            )
        }
    }
}

@Composable
private fun ValorFaturamento(rotulo: String, valor: String, cor: Color) {
    Column {
        Text(text = rotulo, fontSize = 13.sp, color = TextoSecundario)
        Text(
            text = valor,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = cor
        )
    }
}

@Composable
private fun AvisoEstoqueBaixo(nomes: List<String>) {
    val rotulo = stringResource(R.string.estoque_baixo)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AlertaFundo)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = AlertaIcone,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = buildAnnotatedString {
                append(rotulo)
                append(" ")
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(nomes.joinToString(", "))
                }
            },
            fontSize = 15.sp,
            color = TextoEscuro,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
private fun CampoBusca() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .cartao(28.dp)
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = TextoSecundario,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = stringResource(R.string.busca_dica),
            fontSize = 16.sp,
            color = TextoSecundario,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
private fun TituloPedidos(onNovoPedido: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.pedidos_titulo),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextoEscuro
        )
        Button(
            onClick = onNovoPedido,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = stringResource(R.string.novo_pedido),
                modifier = Modifier.padding(start = 6.dp)
            )
        }
    }
}

@Composable
private fun MensagemListaVazia() {
    Text(
        text = stringResource(R.string.lista_vazia),
        fontSize = 15.sp,
        color = TextoSecundario,
        modifier = Modifier
            .fillMaxWidth()
            .cartao(28.dp)
            .padding(20.dp)
    )
}

@Composable
private fun CartaoPedido(item: PedidoComItens) {
    val pedido = item.pedido
    val resumo = resumoDoPedido(item)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .cartao(28.dp)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pedido.nomeCliente,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoEscuro,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = formatarReais(pedido.total),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoEscuro
            )
        }

        if (resumo.isNotEmpty()) {
            Text(text = resumo, fontSize = 15.sp, color = TextoSecundario)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (pedido.pago) {
                    Chip(
                        texto = stringResource(R.string.pago).uppercase(),
                        fundo = VerdePago,
                        corTexto = Color.White
                    )
                } else {
                    Chip(
                        texto = stringResource(R.string.nao_pago).uppercase(),
                        fundo = ChipNeutroFundo,
                        corTexto = TextoSecundario
                    )
                }
                if (pedido.entregue) {
                    Chip(
                        texto = stringResource(R.string.entregue).uppercase(),
                        fundo = PrimaryPurple,
                        corTexto = Color.White
                    )
                } else {
                    Chip(
                        texto = stringResource(R.string.para_fazer).uppercase(),
                        fundo = ChipNeutroFundo,
                        corTexto = TextoSecundario
                    )
                }
            }

            Text(
                text = formatarData(pedido.data),
                fontSize = 12.sp,
                color = TextoSecundario,
                maxLines = 1
            )
            // A lixeira é só visual. Excluir pedido é o RF16.
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.excluir_pedido),
                tint = TextoSecundario,
                modifier = Modifier
                    .padding(start = 10.dp)
                    .size(20.dp)
            )
        }
    }
}

@Composable
private fun Chip(texto: String, fundo: Color, corTexto: Color) {
    Text(
        text = texto,
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.8.sp,
        color = corTexto,
        maxLines = 1,
        modifier = Modifier
            .background(fundo, CircleShape)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

// "6× Foto Polaroid · Com a frase ..." (itens, depois a observação)
private fun resumoDoPedido(item: PedidoComItens): String {
    val itens = item.itens.joinToString(" · ") { "${it.quantidade}× ${it.descricao}" }
    val observacao = item.pedido.observacao?.takeIf { it.isNotBlank() }
    return listOfNotNull(itens.takeIf { it.isNotEmpty() }, observacao).joinToString(" · ")
}

private fun formatarPercentual(valor: Double): String =
    String.format(Locale.forLanguageTag("pt-BR"), "%.1f%%", valor)

// "06 de out."
private fun formatarData(milissegundos: Long): String =
    SimpleDateFormat("dd 'de' MMM", Locale.forLanguageTag("pt-BR")).format(Date(milissegundos))