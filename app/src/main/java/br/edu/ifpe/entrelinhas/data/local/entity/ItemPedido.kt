package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

// Representa um item pertencente a um pedido.
// Responsabilidade: armazenar o produto, quantidade e preço de cada item.
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Pedido::class,
            parentColumns = ["id"],
            childColumns = ["pedidoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Produto::class,
            parentColumns = ["id"],
            childColumns = ["produtoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("pedidoId"), Index("produtoId")]
)
data class ItemPedido(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val pedidoId: Long,

    val produtoId: Long? = null,

    val descricao: String,

    val quantidade: Int,

    val precoUnitario: Long
)