// FRONTEIRA: (o grupo escreve aqui o que este arquivo faz e até onde vai a responsabilidade dele)
package br.edu.ifpe.entrelinhas.ui.features.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.model.PedidoComItens
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn

class PedidosViewModel(application: Application) : AndroidViewModel(application) {

    private val banco = AppDatabase.getInstance(application)

    // Se a leitura do banco falhar, a tela mostra a lista vazia em vez de fechar o app
    val pedidos: StateFlow<List<PedidoComItens>> = banco.pedidoDao().listarComItens()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val abertos: StateFlow<Int> = banco.pedidoDao().contarAbertos()
        .catch { emit(0) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val estoqueBaixo: StateFlow<List<Material>> = banco.materialDao().listarEstoqueBaixo()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}
