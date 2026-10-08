package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {
    @Insert
    suspend fun inserir(produto: Produto): Long
    @Update
    suspend fun atualizar(produto: Produto)
    @Delete
    suspend fun excluir(produto: Produto)
    @Query("SELECT * FROM Produto ORDER BY categoria, nome")
    fun listar(): Flow<List<Produto>>
}