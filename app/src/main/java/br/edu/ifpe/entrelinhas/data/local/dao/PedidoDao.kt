package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.Pedido
import br.edu.ifpe.entrelinhas.model.PedidoComItens
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Transaction
    @Query("SELECT * FROM Pedido ORDER BY entregue ASC, pago DESC, data ASC")
    fun listarComItens(): Flow<List<PedidoComItens>>

    @Query("SELECT * FROM Pedido ORDER BY entregue ASC, pago DESC, data ASC")
    fun listar(): Flow<List<Pedido>>

    @Query("SELECT COUNT(*) FROM Pedido WHERE entregue = 0")
    fun contarAbertos(): Flow<Int>

    // --- FUNÇÕES ESSENCIAIS QUE ESTAVAM FALTANDO ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(pedido: Pedido): Long

    @Update
    suspend fun atualizar(pedido: Pedido)

    @Delete
    suspend fun deletar(pedido: Pedido)

    @Query("SELECT * FROM Pedido WHERE id = :id")
    suspend fun buscarPorId(id: Long): Pedido?
}
