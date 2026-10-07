package br.edu.ifpe.entrelinhas.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Cor principal (decidida no Canvas)
val PrimaryPurple = Color(0xFF8362A6)
val OnPrimaryWhite = Color(0xFFFFFFFF)

// Rosa do degradê do ícone (cor secundária)
val RosaSuave = Color(0xFFF6B8DC)

// Textos
val TextoEscuro = Color(0xFF2B2233)
val TextoSecundario = Color(0xFF6E6578)
val RoxoProfundo = Color(0xFF5E4580)

// Cartões e chips
val CartaoBranco = Color(0xCCFFFFFF)
val ChipNeutroFundo = Color(0xFFECE8F0)
val VerdePago = Color(0xFF8DE3D5)
val VerdePagoFundo = Color(0xFFD8F5EF)
val NegativoTexto = Color(0xFFE73255)
val NegativoFundo = Color(0xFFFBE0E6)
val AlertaFundo = Color(0xFFFFF1D6)
val AlertaIcone = Color(0xFFE69A1A)

// Fundos em degradê (de cima para baixo)
val FundoSuave = Brush.verticalGradient(
    listOf(Color(0xFFEFE6F8), Color(0xFFF8EEF8), Color(0xFFFDEEF5))
)
val FundoAbertura = Brush.verticalGradient(
    listOf(Color(0xFFC3A9DE), Color(0xFFE6CFEA), Color(0xFFFAE3EF))
)