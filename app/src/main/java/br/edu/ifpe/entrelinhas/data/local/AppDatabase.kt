package br.edu.ifpe.entrelinhas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifpe.entrelinhas.data.local.dao.CompraMaterialDao
import br.edu.ifpe.entrelinhas.data.local.dao.MaterialDao
import br.edu.ifpe.entrelinhas.data.local.dao.TipoMaterialDao
import br.edu.ifpe.entrelinhas.data.local.entity.CompraMaterial
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.TipoMaterial

@Database(
    entities = [
        TipoMaterial::class,
        Material::class,
        CompraMaterial::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tipoMaterialDao(): TipoMaterialDao
    abstract fun materialDao(): MaterialDao
    abstract fun compraMaterialDao(): CompraMaterialDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "entrelinhas.db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}