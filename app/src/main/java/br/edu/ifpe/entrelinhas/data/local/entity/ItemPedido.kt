package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa um item pertencente a um pedido.
// Responsabilidade: armazenar o produto, quantidade e preço de cada item.
@Entity
data class ItemPedido(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val pedidoId: Long,

    val produtoId: Long? = null,

    val descricao: String,

    val quantidade: Int,

    val precoUnitario: Long
)