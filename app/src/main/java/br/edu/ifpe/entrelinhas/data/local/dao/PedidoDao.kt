package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import br.edu.ifpe.entrelinhas.data.local.entity.Pedido
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {

    @Query("SELECT * FROM Pedido ORDER BY entregue ASC, pago DESC, data ASC")
    fun listar(): Flow<List<Pedido>>

    @Query("SELECT COUNT(*) FROM Pedido WHERE entregue = 0")
    fun contarAbertos(): Flow<Int>
}