package br.edu.ifpe.entrelinhas.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "CompraMaterial",
    foreignKeys = [
        ForeignKey(
            entity = Material::class,
            parentColumns = ["id"],
            childColumns = ["materialId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index("materialId")]
)
data class CompraMaterial(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val materialId: Long,
    val quantidade: Double,
    val valorPago: Long,
    val data: Long
)