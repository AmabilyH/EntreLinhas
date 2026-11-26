// FRONTEIRA: a ser escrito pelo grupo
package br.edu.ifpe.entrelinhas.ui.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.ui.theme.FundoSuave
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario

val FormaCampo = RoundedCornerShape(20.dp)
val FundoCampo = Color.White.copy(alpha = 0.85f)

// Folha que sobe de baixo, com título e botão de fechar (cadastro de produto, categoria...)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FolhaFormulario(
    titulo: String,
    onFechar: () -> Unit,
    conteudo: @Composable ColumnScope.() -> Unit
) {
    val formaFolha = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)

    ModalBottomSheet(
        onDismissRequest = onFechar,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = formaFolha,
        containerColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.18f),
        dragHandle = null
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(formaFolha)
                .background(FundoSuave)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Alça da folha
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TextoSecundario.copy(alpha = 0.4f))
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoEscuro,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onFechar) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.fechar),
                            tint = TextoSecundario
                        )
                    }
                }

                conteudo()

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

// Cartão translúcido que agrupa campos (como o bloco "Itens" do pedido)
@Composable
fun GrupoFormulario(conteudo: @Composable ColumnScope.() -> Unit) {
    val forma = RoundedCornerShape(28.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(forma)
            .background(Color.White.copy(alpha = 0.35f))
            .border(1.dp, Color.White.copy(alpha = 0.8f), forma)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = conteudo
    )
}

// Rótulo pequeno em maiúsculas, como no protótipo
@Composable
fun Rotulo(texto: String, modifier: Modifier = Modifier) {
    Text(
        text = texto.uppercase(),
        fontSize = 11.sp,
        letterSpacing = 1.5.sp,
        fontFamily = FontFamily.Monospace,
        color = TextoSecundario,
        modifier = modifier
    )
}

// Rótulo em cima, campo embaixo
@Composable
fun Bloco(
    rotulo: String,
    modifier: Modifier = Modifier,
    conteudo: @Composable () -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Rotulo(rotulo)
        conteudo()
    }
}

// Campo de texto branco e arredondado, sem contorno
@Composable
fun Campo(
    valor: String,
    onMudou: (String) -> Unit,
    dica: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    teclado: KeyboardType = KeyboardType.Text,
    linhaUnica: Boolean = true
) {
    TextField(
        value = valor,
        onValueChange = onMudou,
        modifier = modifier,
        placeholder = { Text(dica) },
        singleLine = linhaUnica,
        maxLines = if (linhaUnica) 1 else 4,
        shape = FormaCampo,
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = FundoCampo,
            disabledContainerColor = FundoCampo,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = PrimaryPurple,
            focusedTextColor = TextoEscuro,
            unfocusedTextColor = TextoEscuro,
            focusedPlaceholderColor = TextoSecundario,
            unfocusedPlaceholderColor = TextoSecundario
        )
    )
}

// Caixa que abre uma lista de opções. onEscolher recebe a posição da opção escolhida.
@Composable
fun Seletor(
    escolhido: String?,
    dica: String,
    opcoes: List<String>,
    onEscolher: (Int) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    habilitado: Boolean = true
) {
    var aberto by remember { mutableStateOf(false) }

    Box(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(FormaCampo)
                .background(if (habilitado) FundoCampo else FundoCampo.copy(alpha = 0.4f))
                .clickable(enabled = habilitado) { aberto = true }
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = escolhido ?: dica,
                color = if (escolhido != null) TextoEscuro else TextoSecundario,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = TextoSecundario
            )
        }
        DropdownMenu(expanded = aberto, onDismissRequest = { aberto = false }) {
            opcoes.forEachIndexed { indice, texto ->
                DropdownMenuItem(
                    text = { Text(texto) },
                    onClick = {
                        aberto = false
                        onEscolher(indice)
                    }
                )
            }
        }
    }
}

@Composable
fun BotaoPrincipal(
    texto: String,
    onClick: () -> Unit,
    habilitado: Boolean = true,
    comMais: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = habilitado,
        shape = FormaCampo,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryPurple,
            disabledContainerColor = PrimaryPurple.copy(alpha = 0.35f),
            disabledContentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
    ) {
        if (comMais) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.padding(end = 6.dp)
            )
        }
        Text(text = texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

// Linha de um item já adicionado, com a lixeira para remover
@Composable
fun LinhaRemovivel(texto: String, onRemover: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FormaCampo)
            .background(FundoCampo)
            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            color = TextoEscuro,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onRemover) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.remover),
                tint = TextoSecundario
            )
        }
    }
}