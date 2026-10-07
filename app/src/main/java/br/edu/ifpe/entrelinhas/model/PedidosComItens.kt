package br.edu.ifpe.entrelinhas.model

import androidx.room.Embedded
import androidx.room.Relation
import br.edu.ifpe.entrelinhas.data.local.entity.ItemPedido
import br.edu.ifpe.entrelinhas.data.local.entity.Pedido

data class PedidoComItens(
    @Embedded val pedido: Pedido,
    @Relation(parentColumn = "id", entityColumn = "pedidoId")
    val itens: List<ItemPedido>
)