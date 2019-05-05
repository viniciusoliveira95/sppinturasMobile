package br.com.sppinturasapp
import com.google.gson.GsonBuilder
import java.io.Serializable


class Orcamento : Serializable{
    var idOrcamento: Long = 0
    var nome = ""
    var cliente = ""
    var valorTotal:Double = 0.00
    var statusOrcamento = ""

    override fun toString(): String {
        return "Orçamento: $nome"
    }

    fun toJson(): String{
        return GsonBuilder().create().toJson(this)
    }
}