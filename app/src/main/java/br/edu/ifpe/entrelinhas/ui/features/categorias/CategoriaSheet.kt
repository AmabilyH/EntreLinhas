// FRONTEIRA: a ser escrito pelo grupo
package br.edu.ifpe.entrelinhas.ui.features.categorias

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.entrelinhas.R
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import br.edu.ifpe.entrelinhas.ui.features.components.Bloco
import br.edu.ifpe.entrelinhas.ui.features.components.BotaoPrincipal
import br.edu.ifpe.entrelinhas.ui.features.components.Campo
import br.edu.ifpe.entrelinhas.ui.features.components.FolhaFormulario
import br.edu.ifpe.entrelinhas.ui.features.components.FormaCampo
import br.edu.ifpe.entrelinhas.ui.features.components.FundoCampo
import br.edu.ifpe.entrelinhas.ui.theme.NegativoTexto
import br.edu.ifpe.entrelinhas.ui.theme.PrimaryPurple
import br.edu.ifpe.entrelinhas.ui.theme.TextoEscuro

// Folha para criar (categoria = null) ou editar uma categoria.
// Devolve (nome, precoCombinado).
@Composable
fun CategoriaSheet(
    categoria: Categoria?,
    onFechar: () -> Unit,
    onSalvar: (String, Boolean) -> Unit
) {
    var nome by remember { mutableStateOf(categoria?.nome ?: "") }
    var precoCombinado by remember { mutableStateOf(categoria?.precoCombinado ?: false) }
    var erroNome by remember { mutableStateOf(false) }

    FolhaFormulario(
        titulo = stringResource(
            if (categoria == null) R.string.nova_categoria else R.string.editar_categoria
        ),
        onFechar = onFechar
    ) {
        Bloco(stringResource(R.string.categoria_nome)) {
            Campo(
                valor = nome,
                onMudou = {
                    nome = it
                    erroNome = false
                },
                dica = stringResource(R.string.categoria_nome_dica)
            )
        }

        if (erroNome) {
            Text(
                text = stringResource(R.string.erro_nome_categoria),
                fontSize = 13.sp,
                color = NegativoTexto
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(FormaCampo)
                .background(FundoCampo)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.categoria_preco_combinado),
                fontSize = 14.sp,
                color = TextoEscuro,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = precoCombinado,
                onCheckedChange = { precoCombinado = it },
                colors = SwitchDefaults.colors(checkedTrackColor = PrimaryPurple)
            )
        }

        BotaoPrincipal(
            texto = stringResource(R.string.categoria_salvar),
            onClick = {
                if (nome.isBlank()) {
                    erroNome = true
                } else {
                    onSalvar(nome.trim(), precoCombinado)
                    onFechar()
                }
            }
        )
    }
}