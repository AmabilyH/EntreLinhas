package br.edu.ifpe.entrelinhas.ui.features.produtos

import android.app.Application
import androidx.room.withTransaction
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.ProdutoMaterial
import kotlinx.coroutines.flow.map
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

    // Materiais do estoque para escolher no cadastro do produto
    val materiais: StateFlow<List<Material>> = banco.materialDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Materiais usados por cada produto, agrupados pelo id do produto
    val materiaisPorProduto: StateFlow<Map<Long, List<ProdutoMaterial>>> =
        banco.produtoMaterialDao().listarTodos()
            .map { vinculos -> vinculos.groupBy { it.produtoId } }
            .catch { emit(emptyMap()) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyMap())
    // true quando alguma operação no banco falhou (a tela mostra a mensagem)
    private val _erro = MutableStateFlow(false)
    val erro: StateFlow<Boolean> = _erro

    fun limparErro() {
        _erro.value = false
    }

    // preco em centavos. Os materiais chegam com produtoId = 0; aqui entra o id certo.
    // Produto e materiais são gravados juntos: se algo falhar, nada fica pela metade.
    fun salvar(nome: String, categoriaId: Long, preco: Long, materiais: List<ProdutoMaterial>) = executar {
        banco.withTransaction {
            val produtoId = banco.produtoDao().inserir(
                Produto(categoriaId = categoriaId, nome = nome, preco = preco)
            )
            banco.produtoMaterialDao().inserirTodos(materiais.map { it.copy(produtoId = produtoId) })
        }
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