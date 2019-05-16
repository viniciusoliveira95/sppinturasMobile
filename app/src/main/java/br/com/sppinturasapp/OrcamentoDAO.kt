package br.com.sppinturasapp

import android.arch.persistence.room.*

@Dao
interface OrcamentoDAO {
    @Query("SELECT * FROM orcamento where idOrcamento = :id")
    fun getById(id: Long) : Orcamento?

    @Query("SELECT * FROM orcamento")
    fun findAll(): List<Orcamento>

    @Insert
    fun insert(orcamento: Orcamento)

    @Delete()
    fun delete(orcamento: Orcamento)

    @Update
    fun  update(orcamento: Orcamento)

    @Query("SELECT * FROM orcamento WHERE deletadoLocal = 1")
    fun exluidosOffline(): List<Orcamento>

    @Query("SELECT * FROM orcamento WHERE alteradoLocal = 1")
    fun alteradoOffline(): List<Orcamento>

    @Query("SELECT * FROM orcamento WHERE deletadoLocal = 0")
    fun naoExluidosOffline(): List<Orcamento>
}