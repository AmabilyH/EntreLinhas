package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import kotlinx.coroutines.flow.Flow

@Dao
interface MaterialDao {

    @Insert
    suspend fun inserir(material: Material): Long

    @Update
    suspend fun atualizar(material: Material)

    @Delete
    suspend fun excluir(material: Material)

    @Query("SELECT * FROM Material ORDER BY nome")
    fun listar(): Flow<List<Material>>

    @Query("SELECT * FROM Material WHERE id = :id")
    suspend fun buscarPorId(id: Long): Material?
}