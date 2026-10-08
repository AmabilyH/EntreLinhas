package br.edu.ifpe.entrelinhas.ui.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.entrelinhas.ui.theme.CartaoBranco
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro

@Composable
fun TituloComBotao(titulo: String, botao: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = titulo,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextoEscuro
        )
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryPurple)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Text(text = botao, modifier = Modifier.padding(start = 6.dp))
        }
    }
}

// Cartão translúcido com borda branca, igual ao protótipo
fun Modifier.cartao(raio: Dp): Modifier {
    val forma = RoundedCornerShape(raio)
    return this
        .clip(forma)
        .background(CartaoBranco)
        .border(1.dp, Color.White, forma)
}