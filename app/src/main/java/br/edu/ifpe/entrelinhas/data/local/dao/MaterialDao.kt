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

    @Query("SELECT * FROM Material WHERE quantidade <= avisarEm ORDER BY nome")
    fun listarEstoqueBaixo(): Flow<List<Material>>

    // Facilita dar entrada ou saída no estoque de forma direta pelo ID
    @Query("UPDATE Material SET quantidade = :novaQuantidade WHERE id = :id")
    suspend fun atualizarQuantidade(id: Long, novaQuantidade: Double)
}