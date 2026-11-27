// FRONTEIRA: a ser escrito pelo grupo
package br.edu.ifpe.entrelinhas.ui.features.produtos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.ProdutoMaterial
import br.edu.ifpe.entrelinhas.ui.features.components.Bloco
import br.edu.ifpe.entrelinhas.ui.features.components.BotaoPrincipal
import br.edu.ifpe.entrelinhas.ui.features.components.Campo
import br.edu.ifpe.entrelinhas.ui.features.components.FolhaFormulario
import br.edu.ifpe.entrelinhas.ui.features.components.GrupoFormulario
import br.edu.ifpe.entrelinhas.ui.features.components.LinhaRemovivel
import br.edu.ifpe.entrelinhas.ui.features.components.Rotulo
import br.edu.ifpe.entrelinhas.ui.features.components.Seletor
import br.edu.ifpe.entrelinhas.ui.theme.NegativoTexto
import br.edu.ifpe.entrelinhas.ui.theme.TextoSecundario
import br.edu.ifpe.entrelinhas.ui.util.formatarQuantidade
import br.edu.ifpe.entrelinhas.ui.util.reaisParaCentavos

// Folha para cadastrar um produto com os materiais que ele usa (quantos forem necessários).
// Devolve (nome, id da categoria, preço em centavos, materiais com produtoId = 0).
@Composable
fun NovoProdutoSheet(
    categorias: List<Categoria>,
    materiais: List<Material>,
    onFechar: () -> Unit,
    onSalvar: (String, Long, Long, List<ProdutoMaterial>) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf<Categoria?>(null) }
    var preco by remember { mutableStateOf("") }

    // Materiais usados (produtoId = 0 até o produto ser gravado)
    var usados by remember { mutableStateOf(listOf<ProdutoMaterial>()) }
    var materialEscolhido by remember { mutableStateOf<Material?>(null) }
    var quantidade by remember { mutableStateOf("") }

    var erro by remember { mutableStateOf<String?>(null) }
    val msgNome = stringResource(R.string.erro_nome_produto)
    val msgCategoria = stringResource(R.string.erro_categoria_produto)
    val msgValor = stringResource(R.string.erro_valor_invalido)
    val msgMaterial = stringResource(R.string.erro_material_obrigatorio)

    FolhaFormulario(
        titulo = stringResource(R.string.produto_dialog_titulo),
        onFechar = onFechar
    ) {
        Bloco(stringResource(R.string.produto_nome)) {
            Campo(
                valor = nome,
                onMudou = {
                    nome = it
                    erro = null
                },
                dica = stringResource(R.string.produto_nome_dica)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Bloco(stringResource(R.string.produto_categoria), Modifier.weight(1f)) {
                Seletor(
                    escolhido = categoria?.nome,
                    dica = stringResource(R.string.produto_categoria),
                    opcoes = categorias.map { it.nome },
                    habilitado = categorias.isNotEmpty(),
                    onEscolher = { indice ->
                        categoria = categorias[indice]
                        erro = null
                    }
                )
            }
            Bloco(stringResource(R.string.produto_preco), Modifier.weight(1f)) {
                Campo(
                    valor = preco,
                    onMudou = {
                        preco = it
                        erro = null
                    },
                    dica = "0,00",
                    teclado = KeyboardType.Decimal
                )
            }
        }

        if (categorias.isEmpty()) {
            Text(
                text = stringResource(R.string.produto_sem_categorias),
                fontSize = 13.sp,
                color = NegativoTexto
            )
        }

        // ---------- Materiais usados ----------
        GrupoFormulario {
            Rotulo(stringResource(R.string.produto_materiais_titulo))

            if (materiais.isEmpty()) {
                Text(
                    text = stringResource(R.string.produto_sem_materiais),
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Bloco(stringResource(R.string.produto_material_escolha), Modifier.weight(1f)) {
                    Seletor(
                        escolhido = materialEscolhido?.nome,
                        dica = stringResource(R.string.produto_material_escolha),
                        opcoes = materiais.map { it.nome },
                        habilitado = materiais.isNotEmpty(),
                        onEscolher = { indice ->
                            materialEscolhido = materiais[indice]
                            erro = null
                        }
                    )
                }
                Bloco(stringResource(R.string.produto_material_quantidade), Modifier.weight(1f)) {
                    Campo(
                        valor = quantidade,
                        onMudou = {
                            quantidade = it
                            erro = null
                        },
                        dica = "1",
                        teclado = KeyboardType.Decimal
                    )
                }
            }

            BotaoPrincipal(
                texto = stringResource(R.string.produto_adicionar_material),
                comMais = true,
                habilitado = materialEscolhido != null,
                onClick = {
                    val escolhido = materialEscolhido
                    val valor = quantidade.trim().replace(",", ".").toDoubleOrNull()
                    if (escolhido != null && valor != null && valor > 0.0) {
                        // Se o material já estava na lista, a quantidade é substituída.
                        usados = usados.filter { it.materialId != escolhido.id } +
                                ProdutoMaterial(
                                    produtoId = 0,
                                    materialId = escolhido.id,
                                    quantidadePorUnidade = valor
                                )
                        materialEscolhido = null
                        quantidade = ""
                        erro = null
                    } else {
                        erro = msgValor
                    }
                }
            )
        }

        // Materiais já adicionados
        usados.forEachIndexed { indice, item ->
            val material = materiais.firstOrNull { it.id == item.materialId }
            LinhaRemovivel(
                texto = "${material?.nome.orEmpty()} · ${formatarQuantidade(item.quantidadePorUnidade)} ${material?.unidade.orEmpty()}",
                onRemover = { usados = usados.filterIndexed { i, _ -> i != indice } }
            )
        }

        erro?.let { mensagem ->
            Text(text = mensagem, fontSize = 13.sp, color = NegativoTexto)
        }
        BotaoPrincipal(
            texto = stringResource(R.string.produto_salvar),
            habilitado = categorias.isNotEmpty() && materiais.isNotEmpty(),
            onClick = {
                val centavos = reaisParaCentavos(preco)
                val escolhida = categoria

                // Se ela escolheu um material e digitou a quantidade, mas não tocou em
                // "Adicionar material", ele conta como adicionado.
                val digitado = materialEscolhido?.let { m ->
                    val valor = quantidade.trim().replace(",", ".").toDoubleOrNull()
                    if (valor != null && valor > 0.0) {
                        ProdutoMaterial(produtoId = 0, materialId = m.id, quantidadePorUnidade = valor)
                    } else {
                        null
                    }
                }
                val materiaisFinais = if (digitado != null) {
                    usados.filter { it.materialId != digitado.materialId } + digitado
                } else {
                    usados
                }

                if (nome.isBlank()) {
                    erro = msgNome
                } else if (escolhida == null) {
                    erro = msgCategoria
                } else if (centavos == null || centavos <= 0) {
                    erro = msgValor
                } else if (materiaisFinais.isEmpty()) {
                    erro = msgMaterial
                } else {
                    onSalvar(nome.trim(), escolhida.id, centavos, materiaisFinais)
                    onFechar()
                }
            }
        )
    }
}