// FRONTEIRA: (o grupo escreve aqui o que este arquivo faz e até onde vai a responsabilidade dele)
package br.edu.ifpe.entrelinhas.ui.features.estoque

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.TipoMaterial
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EstoqueViewModel(application: Application) : AndroidViewModel(application) {

    private val banco = AppDatabase.getInstance(application)

    val materiais: StateFlow<List<Material>> = banco.materialDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // TEMPORÁRIO: serve só para ver a lista funcionando.
    // Apague esta função quando existir o formulário de material.
    fun criarDadosDeExemplo() {
        viewModelScope.launch {
            val tipoId = banco.tipoMaterialDao().inserir(TipoMaterial(nome = "Material para broches"))
            banco.materialDao().inserir(
                Material(
                    nome = "Botão 25mm (par)",
                    tipoId = tipoId,
                    unidade = "un",
                    quantidade = 20.0,
                    avisarEm = 20.0,
                    custoUnidade = 80
                )
            )
        }
    }
}