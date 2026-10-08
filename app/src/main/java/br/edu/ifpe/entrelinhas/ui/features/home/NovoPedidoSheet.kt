package br.edu.ifpe.entrelinhas.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.entrelinhas.data.local.entity.Cliente
import br.edu.ifpe.entrelinhas.data.local.entity.ItemPedido
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
import br.edu.ifpe.entrelinhas.ui.theme.FundoSuave
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.RoxoProfundo
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario
import br.edu.ifpe.entrelinhas.ui.util.formatarReais
import br.edu.ifpe.entrelinhas.ui.util.reaisParaCentavos
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

// Tudo o que o formulário entrega para o ViewModel gravar.
// Os itens saem com pedidoId = 0; o ViewModel coloca o id certo ao gravar.
data class NovoPedidoDados(
    val clienteId: Long?,        // não nulo = cliente que já estava cadastrado
    val nome: String,
    val telefone: String?,
    val salvarCliente: Boolean,  // true = cadastrar essa pessoa para os próximos pedidos
    val dataMillis: Long,
    val observacao: String?,
    val itens: List<ItemPedido>
)

private val FormaCampo = RoundedCornerShape(20.dp)
private val FundoCampo = Color.White.copy(alpha = 0.85f)

// Folha que sobe de baixo para anotar um novo pedido.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoPedidoSheet(
    produtos: List<Produto>,
    clientes: List<Cliente>,
    onFechar: () -> Unit,
    onSalvar: (NovoPedidoDados) -> Unit
) {
    // Cliente
    var cliente by remember { mutableStateOf<Cliente?>(null) } // null = cliente novo
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var salvarCliente by remember { mutableStateOf(false) }
    var dataMillis by remember { mutableStateOf(System.currentTimeMillis()) }
    var mostrarCalendario by remember { mutableStateOf(false) }

    // Item que está sendo montado
    var categoria by remember { mutableStateOf<String?>(null) }
    var produto by remember { mutableStateOf<Produto?>(null) }
    var quantidade by remember { mutableStateOf("1") }
    var preco by remember { mutableStateOf("") }

    // Pedido
    var itens by remember { mutableStateOf(listOf<ItemPedido>()) }
    var observacao by remember { mutableStateOf("") }

    val categorias = remember(produtos) { produtos.map { it.categoria }.distinct() }
    val produtosDaCategoria = remember(produtos, categoria) {
        produtos.filter { it.categoria == categoria }
    }
    val total = itens.sumOf { it.quantidade * it.precoUnitario }

    val formaFolha = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
    val formaCartao = RoundedCornerShape(28.dp)

    ModalBottomSheet(
        onDismissRequest = onFechar,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = formaFolha,
        containerColor = Color.Transparent,
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

                // Título e botão de fechar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Novo pedido",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoEscuro,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onFechar) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = TextoSecundario
                        )
                    }
                }

                // ---------- Cliente ----------
                Bloco("Cliente") {
                    Seletor(
                        escolhido = cliente?.nome ?: "Cliente novo",
                        dica = "Cliente novo",
                        opcoes = listOf("Cliente novo") + clientes.map { it.nome },
                        onEscolher = { indice ->
                            cliente = if (indice == 0) null else clientes[indice - 1]
                        }
                    )
                }

                if (cliente == null) {
                    Campo(
                        valor = nome,
                        onMudou = { nome = it },
                        dica = "Nome de quem pediu"
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Bloco("Telefone (opcional)", Modifier.weight(1f)) {
                            Campo(
                                valor = telefone,
                                onMudou = { telefone = it },
                                dica = "(00) 00000-0000",
                                teclado = KeyboardType.Phone
                            )
                        }
                        Bloco("Data", Modifier.weight(1f)) {
                            CampoData(dataMillis) { mostrarCalendario = true }
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = salvarCliente,
                            onCheckedChange = { salvarCliente = it },
                            colors = CheckboxDefaults.colors(checkedColor = PrimaryPurple)
                        )
                        Text(
                            text = "Salvar esta pessoa para os próximos pedidos",
                            fontSize = 13.sp,
                            color = TextoSecundario
                        )
                    }
                } else {
                    Bloco("Data") {
                        CampoData(dataMillis) { mostrarCalendario = true }
                    }
                }

                // ---------- Itens ----------
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(formaCartao)
                        .background(Color.White.copy(alpha = 0.35f))
                        .border(1.dp, Color.White.copy(alpha = 0.8f), formaCartao)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Rotulo("Itens")

                    if (produtos.isEmpty()) {
                        Text(
                            text = "Cadastre um produto na aba Produtos para poder montar o pedido.",
                            fontSize = 14.sp,
                            color = TextoSecundario
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Seletor(
                            escolhido = categoria,
                            dica = "Categoria",
                            opcoes = categorias,
                            habilitado = categorias.isNotEmpty(),
                            modifier = Modifier.weight(1f),
                            onEscolher = { indice ->
                                categoria = categorias[indice]
                                produto = null
                                preco = ""
                            }
                        )
                        Seletor(
                            escolhido = produto?.nome,
                            dica = "Produto",
                            opcoes = produtosDaCategoria.map { it.nome },
                            habilitado = categoria != null,
                            modifier = Modifier.weight(1f),
                            onEscolher = { indice ->
                                val escolhido = produtosDaCategoria[indice]
                                produto = escolhido
                                preco = centavosParaTexto(escolhido.preco) // pode ser ajustado
                            }
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Bloco("Quantidade", Modifier.weight(1f)) {
                            Campo(
                                valor = quantidade,
                                onMudou = { quantidade = it.filter(Char::isDigit) },
                                dica = "1",
                                teclado = KeyboardType.Number
                            )
                        }
                        Bloco("Preço do produto", Modifier.weight(1f)) {
                            Campo(
                                valor = preco,
                                onMudou = { preco = it },
                                dica = "R$ 0,00",
                                teclado = KeyboardType.Decimal
                            )
                        }
                    }

                    BotaoPrincipal(
                        texto = "Adicionar item",
                        comMais = true,
                        habilitado = produto != null,
                        onClick = {
                            val escolhido = produto
                            val qtd = quantidade.toIntOrNull()
                            val centavos = reaisParaCentavos(preco)
                            if (escolhido != null && qtd != null && qtd > 0 && centavos != null) {
                                // descricao e precoUnitario são copiados de propósito: se o preço
                                // do produto mudar depois, este pedido continua com o valor da época
                                itens = itens + ItemPedido(
                                    pedidoId = 0,
                                    produtoId = escolhido.id,
                                    descricao = escolhido.nome,
                                    quantidade = qtd,
                                    precoUnitario = centavos
                                )
                                produto = null
                                preco = ""
                                quantidade = "1"
                            }
                        }
                    )
                }

                // Itens já adicionados
                itens.forEachIndexed { indice, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(FormaCampo)
                            .background(FundoCampo)
                            .padding(start = 16.dp, top = 4.dp, bottom = 4.dp, end = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${item.quantidade}× ${item.descricao}",
                            color = TextoEscuro,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = formatarReais(item.quantidade * item.precoUnitario),
                            fontWeight = FontWeight.SemiBold,
                            color = TextoEscuro
                        )
                        IconButton(
                            onClick = { itens = itens.filterIndexed { i, _ -> i != indice } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Remover item",
                                tint = TextoSecundario
                            )
                        }
                    }
                }

                // ---------- Fechamento ----------
                Bloco("Observação") {
                    Campo(
                        valor = observacao,
                        onMudou = { observacao = it },
                        dica = "Detalhes do pedido",
                        linhaUnica = false
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(FormaCampo)
                        .background(PrimaryPurple.copy(alpha = 0.12f))
                        .padding(horizontal = 18.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Rotulo("Total", Modifier.weight(1f))
                    Text(
                        text = formatarReais(total),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = RoxoProfundo
                    )
                }

                BotaoPrincipal(
                    texto = "Salvar pedido",
                    habilitado = itens.isNotEmpty() && (cliente != null || nome.isNotBlank()),
                    onClick = {
                        val existente = cliente
                        onSalvar(
                            NovoPedidoDados(
                                clienteId = existente?.id,
                                nome = existente?.nome ?: nome.trim(),
                                telefone = if (existente == null) telefone.trim().ifBlank { null } else null,
                                salvarCliente = existente == null && salvarCliente,
                                dataMillis = dataMillis,
                                observacao = observacao.trim().ifBlank { null },
                                itens = itens
                            )
                        )
                    }
                )

                Spacer(Modifier.height(16.dp))
            }
        }

        if (mostrarCalendario) {
            val estadoData = rememberDatePickerState(
                initialSelectedDateMillis = localParaUtc(dataMillis)
            )
            DatePickerDialog(
                onDismissRequest = { mostrarCalendario = false },
                confirmButton = {
                    TextButton(onClick = {
                        estadoData.selectedDateMillis?.let { dataMillis = utcParaLocal(it) }
                        mostrarCalendario = false
                    }) { Text("OK") }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarCalendario = false }) { Text("Cancelar") }
                }
            ) { DatePicker(state = estadoData) }
        }
    }
}

// ---------- Peças visuais do formulário ----------

// Rótulo pequeno em maiúsculas, como no protótipo
@Composable
private fun Rotulo(texto: String, modifier: Modifier = Modifier) {
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
private fun Bloco(
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
private fun Campo(
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
private fun Seletor(
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
private fun CampoData(dataMillis: Long, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(FormaCampo)
            .background(FundoCampo)
            .clickable(onClick = onClick)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatarDataCompleta(dataMillis),
            color = TextoEscuro,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Escolher data",
            tint = TextoSecundario
        )
    }
}

@Composable
private fun BotaoPrincipal(
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

// ---------- Funções de apoio ----------

// 800 vira "8,00"
private fun centavosParaTexto(centavos: Long): String =
    String.format(Locale.forLanguageTag("pt-BR"), "%.2f", centavos / 100.0)

private fun formatarDataCompleta(milissegundos: Long): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("pt-BR")).format(Date(milissegundos))

// O calendário trabalha em UTC. Sem converter, o dia escolhido apareceria um dia antes no Brasil.
private fun localParaUtc(localMillis: Long): Long {
    val local = Calendar.getInstance().apply { timeInMillis = localMillis }
    return Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
        clear()
        set(local.get(Calendar.YEAR), local.get(Calendar.MONTH), local.get(Calendar.DAY_OF_MONTH))
    }.timeInMillis
}

private fun utcParaLocal(utcMillis: Long): Long {
    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { timeInMillis = utcMillis }
    return Calendar.getInstance().apply {
        clear()
        // meio-dia, para a data não escorregar por causa de horário de verão
        set(utc.get(Calendar.YEAR), utc.get(Calendar.MONTH), utc.get(Calendar.DAY_OF_MONTH), 12, 0)
    }.timeInMillis
}