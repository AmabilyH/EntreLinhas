package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.CompraMaterial
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraMaterialDao {

    @Insert
    suspend fun inserir(compraMaterial: CompraMaterial): Long

    @Update
    suspend fun atualizar(compraMaterial: CompraMaterial)

    @Delete
    suspend fun excluir(compraMaterial: CompraMaterial)

    @Query("SELECT * FROM CompraMaterial ORDER BY data DESC")
    fun listar(): Flow<List<CompraMaterial>>

    @Query("SELECT * FROM CompraMaterial WHERE id = :id")
    suspend fun buscarPorId(id: Long): CompraMaterial?

    @Query("SELECT * FROM CompraMaterial WHERE materialId = :materialId ORDER BY data DESC")
    fun listarPorMaterial(materialId: Long): Flow<List<CompraMaterial>>
}