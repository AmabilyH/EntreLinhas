package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val categoria: String,
    val preco: Long // centavos, como no resto do app
)