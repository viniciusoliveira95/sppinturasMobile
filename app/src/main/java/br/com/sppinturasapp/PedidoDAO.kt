package br.com.sppinturasapp

import android.arch.persistence.room.*

@Dao
interface PedidoDAO {
    @Query("SELECT * FROM pedido where idPedido = :id")
    fun getById(id: Long) : Pedido?

    @Query("SELECT * FROM pedido")
    fun findAll(): List<Pedido>

    @Insert
    fun insert(pedido: Pedido)

    @Delete
    fun delete(pedido: Pedido)

    @Update
    fun update(pedido: Pedido)

    @Query ("SELECT MAX(idPedido) FROM pedido")
    fun getMaxId(): Long

    @Query("SELECT * FROM pedido WHERE deletadoLocal = 1")
    fun exluidosOffline(): List<Pedido>

    @Query("SELECT * FROM pedido WHERE criadoNoApp = 1")
    fun criadoNoApp(): List<Pedido>

    @Query("SELECT * FROM pedido WHERE alteradoLocal = 1")
    fun alteradoOffline(): List<Pedido>

    @Query("SELECT * FROM pedido WHERE deletadoLocal = 0")
    fun naoExluidosOffline(): List<Pedido>

}