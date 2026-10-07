package br.edu.ifpe.entrelinhas.ui.features.abertura

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.ui.navigation.NavTarget
import br.edu.ifpe.entrelinhas.ui.theme.FonteCaligrafica
import br.edu.ifpe.entrelinhas.ui.theme.FundoAbertura
import br.edu.ifpe.entrelinhas.ui.theme.OnPrimaryWhite
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.RoxoProfundo
import kotlinx.coroutines.delay

@Composable
fun TelaAbertura(navController: NavController) {

    val frases = stringArrayResource(R.array.frases_abertura)
    val frase = remember { frases.random() }

    // Depois de 3 segundos vai para a tela principal e tira a abertura da pilha
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(NavTarget.Home.route) {
            popUpTo(NavTarget.Splash.route) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoAbertura)
            .statusBarsPadding()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        // O shadow com forma também recorta a imagem nos cantos arredondados
        Image(
            painter = painterResource(id = R.drawable.icone_entrelinhas),
            contentDescription = stringResource(R.string.icone_descricao),
            modifier = Modifier
                .size(200.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(48.dp),
                    ambientColor = PrimaryPurple,
                    spotColor = PrimaryPurple
                )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.saudacao_abertura),
            fontFamily = FonteCaligrafica,
            fontSize = 88.sp,
            color = OnPrimaryWhite,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = frase,
            fontSize = 18.sp,
            color = RoxoProfundo.copy(alpha = 0.8f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        PontinhosCarregando()

        Spacer(modifier = Modifier.height(48.dp))
    }
}

@Composable
private fun PontinhosCarregando() {
    val transicao = rememberInfiniteTransition(label = "pontinhos")
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        repeat(3) { indice ->
            val alfa by transicao.animateFloat(
                initialValue = 0.25f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(indice * 200)
                ),
                label = "ponto$indice"
            )
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .alpha(alfa)
                    .background(PrimaryPurple, CircleShape)
            )
        }
    }
}