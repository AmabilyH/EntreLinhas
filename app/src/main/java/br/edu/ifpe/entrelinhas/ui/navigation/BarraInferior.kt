// FRONTEIRA: (o grupo escreve aqui o que este arquivo faz e até onde vai a responsabilidade dele)
package br.edu.ifpe.entrelinhas.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.ui.theme.FundoSuave
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario

// Espaço que as listas deixam no fim para a barra não cobrir o último item
val EspacoBarraInferior: Dp = 120.dp

private data class Aba(
    val rota: String,
    @StringRes val rotulo: Int,
    @DrawableRes val icone: Int
)

// Fundo suave + barra flutuante. As telas das 3 abas ficam dentro dela.
@Composable
fun TelaComBarra(
    rotaAtual: String,
    onNavegar: (String) -> Unit,
    conteudo: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoSuave)
    ) {
        conteudo()
        BarraInferior(
            rotaAtual = rotaAtual,
            onNavegar = onNavegar,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun BarraInferior(
    rotaAtual: String,
    onNavegar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val abas = listOf(
        Aba(NavTarget.Home.route, R.string.aba_pedidos, R.drawable.ic_pedidos),
        Aba(NavTarget.Estoque.route, R.string.aba_estoque, R.drawable.ic_estoque),
        Aba(NavTarget.Produtos.route, R.string.aba_produtos, R.drawable.ic_produtos)
    )
    val forma = RoundedCornerShape(32.dp)

    Row(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                shape = forma,
                ambientColor = PrimaryPurple,
                spotColor = PrimaryPurple
            )
            .background(Color.White.copy(alpha = 0.94f))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        abas.forEach { aba ->
            val ativa = aba.rota == rotaAtual
            val corConteudo = if (ativa) Color.White else TextoSecundario

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
                    .background(if (ativa) PrimaryPurple else Color.Transparent)
                    .clickable { onNavegar(aba.rota) }
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = aba.icone),
                    contentDescription = null,
                    tint = corConteudo,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(aba.rotulo),
                    fontSize = 12.sp,
                    fontWeight = if (ativa) FontWeight.Bold else FontWeight.Normal,
                    color = corConteudo
                )
            }
        }
    }
}

// Troca de aba sem empilhar telas e guardando o estado de cada uma
fun NavController.irParaAba(rota: String) {
    navigate(rota) {
        popUpTo(NavTarget.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
