package br.com.sppinturasapp
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "pedido")
class Pedido : Serializable{
    @PrimaryKey
    var idPedido: Long = 0
    var nomeSolicitante = ""
    var email = ""
    var telefone: Long = 0
    var dtPedido = ""
    var descricao = ""
    var criadoNoApp = 0
    var alteradoLocal = 0
    var deletadoLocal = 0

    override fun toString(): String {
        return "Pedido: $nomeSolicitante"
    }

    fun toJson(): String{
        return GsonBuilder().create().toJson(this)
    }
}