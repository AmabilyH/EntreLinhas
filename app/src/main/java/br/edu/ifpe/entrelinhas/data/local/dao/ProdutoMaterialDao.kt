package br.edu.ifpe.entrelinhas.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.edu.ifpe.entrelinhas.data.local.entity.ProdutoMaterial
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoMaterialDao {

    // Se o mesmo material for adicionado de novo ao produto, a quantidade é substituída.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(produtoMaterial: ProdutoMaterial)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTodos(itens: List<ProdutoMaterial>)

    @Update
    suspend fun atualizar(produtoMaterial: ProdutoMaterial)

    @Delete
    suspend fun excluir(produtoMaterial: ProdutoMaterial)

    // Para mostrar na tela os materiais de um produto (atualiza sozinho).
    @Query("SELECT * FROM ProdutoMaterial WHERE produtoId = :produtoId")
    fun listarPorProduto(produtoId: Long): Flow<List<ProdutoMaterial>>

    // Para o desconto de estoque ao salvar um pedido (RF10): leitura única.
    @Query("SELECT * FROM ProdutoMaterial WHERE produtoId = :produtoId")
    suspend fun buscarPorProduto(produtoId: Long): List<ProdutoMaterial>

    // Útil ao editar um produto: apaga os materiais antigos e insere os novos.
    @Query("DELETE FROM ProdutoMaterial WHERE produtoId = :produtoId")
    suspend fun excluirPorProduto(produtoId: Long)
}