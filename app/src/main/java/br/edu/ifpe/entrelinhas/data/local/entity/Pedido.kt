package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa um pedido realizado por um cliente.
// Responsabilidade: armazenar os dados principais de cada pedido.
@Entity
data class Pedido(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val clienteId: Long? = null,

    val nomeCliente: String,

    val data: Long,

    val observacao: String? = null,

    val pago: Boolean = false,

    val entregue: Boolean = false,

    val total: Long
)