package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Se a categoria for excluída, os produtos dela também são (CASCADE).
// Pedidos antigos continuam salvos: ItemPedido guarda descrição e preço próprios.
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Categoria::class,
            parentColumns = ["id"],
            childColumns = ["categoriaId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categoriaId")]
)
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val categoriaId: Long,
    val nome: String,
    val preco: Long // centavos, como no resto do app
)