package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifpe.entrelinhas.data.local.entity.ItemPedido
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemPedidoDao {

    @Insert
    suspend fun inserirTodos(itens: List<ItemPedido>)

    @Query("SELECT * FROM ItemPedido WHERE pedidoId = :pedidoId")
    fun listarPorPedido(pedidoId: Long): Flow<List<ItemPedido>>
}