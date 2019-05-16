package br.com.sppinturasapp
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.GsonBuilder
import java.io.Serializable

@Entity(tableName = "orcamento")
class Orcamento : Serializable{
    @PrimaryKey
    var idOrcamento: Long = 0
    var nome = ""
    var cliente = ""
    var valorTotal:Double = 0.00
    var statusOrcamento = ""
    var alteradoLocal = 0
    var deletadoLocal = 0

    override fun toString(): String {
        return "Orçamento: $nome"
    }

    fun toJson(): String{
        return GsonBuilder().create().toJson(this)
    }
}