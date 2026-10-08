package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.edu.ifpe.entrelinhas.data.local.entity.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert
    suspend fun inserir(cliente: Cliente): Long

    @Query("SELECT * FROM Cliente ORDER BY nome")
    fun listar(): Flow<List<Cliente>>
}