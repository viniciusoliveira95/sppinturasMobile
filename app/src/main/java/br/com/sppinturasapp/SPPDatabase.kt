package br.com.sppinturasapp

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Orcamento::class, Pedido::class), version = 4)
abstract class SPPDatabase: RoomDatabase() {
    abstract fun orcamentoDAO(): OrcamentoDAO
    abstract fun pedidoDAO(): PedidoDAO
}