package br.com.sppinturasapp
import com.google.gson.GsonBuilder
import java.io.Serializable


class Pedido : Serializable{
    var idPedido: Long = 0
    var nomeSolicitante = ""
    var email = ""
    var telefone: Long = 0
    var dtPedido = ""
    var descricao = ""

    override fun toString(): String {
        return "Pedido: $nomeSolicitante"
    }

    fun toJson(): String{
        return GsonBuilder().create().toJson(this)
    }
}