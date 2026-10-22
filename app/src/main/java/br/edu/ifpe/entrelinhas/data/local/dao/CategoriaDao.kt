package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {
    @Insert
    suspend fun inserir(categoria: Categoria): Long
    @Update
    suspend fun atualizar(categoria: Categoria)
    @Delete
    suspend fun excluir(categoria: Categoria)
    @Query("SELECT * FROM categoria ORDER BY nome")
    fun listar(): Flow<List<Categoria>>
}
