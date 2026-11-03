package br.edu.ifpe.entrelinhas.ui.features.categorias

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriasViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getInstance(application).categoriaDao()

    val categorias: StateFlow<List<Categoria>> = dao.listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // true quando alguma operação no banco falhou (a tela mostra a mensagem)
    private val _erro = MutableStateFlow(false)
    val erro: StateFlow<Boolean> = _erro

    fun limparErro() {
        _erro.value = false
    }

    fun salvar(nome: String, precoCombinado: Boolean) = executar {
        dao.inserir(Categoria(nome = nome, precoCombinado = precoCombinado))
    }

    fun atualizar(categoria: Categoria) = executar { dao.atualizar(categoria) }

    fun excluir(categoria: Categoria) = executar { dao.excluir(categoria) }

    // Toda operação no banco passa por aqui: se falhar, o app não fecha.
    private fun executar(acao: suspend () -> Unit) {
        viewModelScope.launch {
            try {
                acao()
                _erro.value = false
            } catch (e: Exception) {
                _erro.value = true
            }
        }
    }
}