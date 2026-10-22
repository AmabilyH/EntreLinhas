package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria")
data class Categoria(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val precoCombinado: Boolean = false
)
