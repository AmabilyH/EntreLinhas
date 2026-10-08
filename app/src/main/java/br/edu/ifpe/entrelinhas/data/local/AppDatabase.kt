package br.edu.ifpe.entrelinhas.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifpe.entrelinhas.data.local.dao.ClienteDao
import br.edu.ifpe.entrelinhas.data.local.dao.CompraMaterialDao
import br.edu.ifpe.entrelinhas.data.local.dao.ItemPedidoDao
import br.edu.ifpe.entrelinhas.data.local.dao.MaterialDao
import br.edu.ifpe.entrelinhas.data.local.dao.PedidoDao
import br.edu.ifpe.entrelinhas.data.local.dao.ProdutoDao
import br.edu.ifpe.entrelinhas.data.local.dao.TipoMaterialDao
import br.edu.ifpe.entrelinhas.data.local.entity.Cliente
import br.edu.ifpe.entrelinhas.data.local.entity.CompraMaterial
import br.edu.ifpe.entrelinhas.data.local.entity.ItemPedido
import br.edu.ifpe.entrelinhas.data.local.entity.Material
import br.edu.ifpe.entrelinhas.data.local.entity.Pedido
import br.edu.ifpe.entrelinhas.data.local.entity.Produto
import br.edu.ifpe.entrelinhas.data.local.entity.TipoMaterial

@Database(
    entities = [
        TipoMaterial::class,
        Material::class,
        CompraMaterial::class,
        Cliente::class,
        Produto::class,
        Pedido::class,
        ItemPedido::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tipoMaterialDao(): TipoMaterialDao
    abstract fun materialDao(): MaterialDao
    abstract fun compraMaterialDao(): CompraMaterialDao

    abstract fun clienteDao(): ClienteDao
    abstract fun produtoDao(): ProdutoDao

    abstract fun pedidoDao(): PedidoDao
    abstract fun itemPedidoDao(): ItemPedidoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "entrelinhas.db"
                )
                    // Só para a fase de desenvolvimento: ao mudar a versão, apaga e recria o banco.
                    // Antes de ter dados reais, troque por Migration.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}