package br.edu.ifpe.entrelinhas.ui.features.produtos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProdutosViewModel(application: Application) : AndroidViewModel(application) {

    private val banco = AppDatabase.getInstance(application)

    val produtos: StateFlow<List<Produto>> = banco.produtoDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Categorias para escolher no cadastro do produto e para mostrar o nome na lista
    val categorias: StateFlow<List<Categoria>> = banco.categoriaDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // true quando alguma operação no banco falhou (a tela mostra a mensagem)
    private val _erro = MutableStateFlow(false)
    val erro: StateFlow<Boolean> = _erro

    fun limparErro() {
        _erro.value = false
    }

    // preco em centavos
    fun salvar(nome: String, categoriaId: Long, preco: Long) = executar {
        banco.produtoDao().inserir(Produto(categoriaId = categoriaId, nome = nome, preco = preco))
    }

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