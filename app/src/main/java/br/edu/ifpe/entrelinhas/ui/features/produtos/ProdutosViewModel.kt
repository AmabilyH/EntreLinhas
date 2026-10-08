// FRONTEIRA: (o grupo escreve aqui o que este arquivo faz e até onde vai a responsabilidade dele)
package br.edu.ifpe.entrelinhas.ui.features.produtos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
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

    // preco em centavos
    fun salvar(nome: String, categoria: String, preco: Long) {
        viewModelScope.launch {
            banco.produtoDao().inserir(Produto(nome = nome, categoria = categoria, preco = preco))
        }
    }
}