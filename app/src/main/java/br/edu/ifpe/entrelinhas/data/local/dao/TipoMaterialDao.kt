package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.TipoMaterial
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoMaterialDao {

    @Insert
    suspend fun inserir(tipoMaterial: TipoMaterial): Long

    @Update
    suspend fun atualizar(tipoMaterial: TipoMaterial)

    @Delete
    suspend fun excluir(tipoMaterial: TipoMaterial)

    @Query("SELECT * FROM TipoMaterial ORDER BY nome")
    fun listar(): Flow<List<TipoMaterial>>

    @Query("SELECT * FROM TipoMaterial WHERE id = :id")
    suspend fun buscarPorId(id: Long): TipoMaterial?
}