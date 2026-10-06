package br.edu.ifpe.entrelinhas.ui.features.abertura

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.ui.navigation.NavTarget
import kotlinx.coroutines.delay

@Composable
fun TelaAbertura(navController: NavController) {

    val frases = stringArrayResource(R.array.frases_abertura)
    val frase = remember { frases.random() }

    var quantidadePontos by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        delay(3000)

        navController.navigate(NavTarget.Home.route) {
            popUpTo(NavTarget.Splash.route) {
                inclusive = true
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            quantidadePontos =
                if (quantidadePontos == 3) 1 else quantidadePontos + 1
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF8362A6)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Image(
            painter = painterResource(id = R.drawable.icone_entrelinhas),
            contentDescription = stringResource(R.string.icone_descricao),
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = stringResource(R.string.saudacao_abertura)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = frase
        )

        Text(
            text = ".".repeat(quantidadePontos)
        )

        Spacer(modifier = Modifier.height(60.dp))
    }
}