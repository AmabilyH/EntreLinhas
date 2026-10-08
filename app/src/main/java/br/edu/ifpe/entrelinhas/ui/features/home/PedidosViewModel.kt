// FRONTEIRA: (o grupo escreve aqui o que este arquivo faz e até onde vai a responsabilidade dele)
package br.edu.ifpe.entrelinhas.ui.features.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import br.edu.ifpe.entrelinhas.data.local.AppDatabase
import br.edu.ifpe.entrelinhas.data.local.entity.Cliente
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.Pedido
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
import br.edu.ifpe.entrelinhas.model.PedidoComItens
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    // Produtos e clientes para escolher no formulário de novo pedido
    val produtos: StateFlow<List<Produto>> = banco.produtoDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val clientes: StateFlow<List<Cliente>> = banco.clienteDao().listar()
        .catch { emit(emptyList()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // Grava cliente (se for para salvar), pedido e itens juntos:
    // se algo falhar no meio, nada fica gravado pela metade.
    fun salvarPedido(dados: NovoPedidoDados) {
        viewModelScope.launch {
            banco.withTransaction {
                val clienteId: Long? = dados.clienteId
                    ?: (if (dados.salvarCliente) {
                        banco.clienteDao().inserir(Cliente(nome = dados.nome, telefone = dados.telefone))
                    } else {
                        null
                    })

                val total = dados.itens.sumOf { it.quantidade * it.precoUnitario }
                val pedidoId = banco.pedidoDao().inserir(
                    Pedido(
                        clienteId = clienteId,
                        nomeCliente = dados.nome,
                        data = dados.dataMillis,
                        observacao = dados.observacao,
                        total = total
                    )
                )
                banco.itemPedidoDao().inserirTodos(dados.itens.map { it.copy(pedidoId = pedidoId) })
            }
        }
    }
}