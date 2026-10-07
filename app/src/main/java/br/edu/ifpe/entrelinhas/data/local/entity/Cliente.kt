package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Representa um cliente cadastrado no sistema.
// Responsabilidade: armazenar somente os dados básicos do cliente.
@Entity
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String,

    val telefone: String? = null
)