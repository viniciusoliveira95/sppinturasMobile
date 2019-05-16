package br.com.sppinturasapp

import android.arch.persistence.room.Room

object DatabaseManager {
    // singleton
    private var dbInstance: SPPDatabase
    init {
        val appContext = SPPApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
            appContext, // contexto global
            SPPDatabase::class.java, // Referência da classe do banco
            "spp.sqlite" // nome do arquivo do banco
        ).fallbackToDestructiveMigration().build()
    }

    fun getOrcamentoDAO(): OrcamentoDAO {
        return dbInstance.orcamentoDAO()
    }

    fun getPedidoDAO(): PedidoDAO{
        return dbInstance.pedidoDAO()
    }

}