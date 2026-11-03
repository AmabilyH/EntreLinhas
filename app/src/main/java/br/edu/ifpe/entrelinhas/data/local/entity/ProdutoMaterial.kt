package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "ProdutoMaterial",
    primaryKeys = ["produtoId", "materialId"],
    foreignKeys = [
        ForeignKey(
            entity = Produto::class,
            parentColumns = ["id"],
            childColumns = ["produtoId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = Material::class,
            parentColumns = ["id"],
            childColumns = ["materialId"],
            onDelete = ForeignKey.Companion.RESTRICT
        )
    ],
    indices = [Index("materialId")]
)
data class ProdutoMaterial(
    val produtoId: Long,
    val materialId: Long,
    val quantidadePorUnidade: Double // quanto do material 1 unidade do produto usa (ex.: 1 broche usa 1 botão = 1.0)
)