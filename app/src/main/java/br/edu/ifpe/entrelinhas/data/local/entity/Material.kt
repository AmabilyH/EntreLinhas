package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Material",
    foreignKeys = [
        ForeignKey(
            entity = TipoMaterial::class,
            parentColumns = ["id"],
            childColumns = ["tipoId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("tipoId")]
)
data class Material(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val tipoId: Long,
    val unidade: String,
    val quantidade: Double,
    val avisarEm: Double,
    val custoUnidade: Long,
    val linkLoja: String? = null
)