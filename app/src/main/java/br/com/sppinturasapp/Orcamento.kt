package br.com.sppinturasapp
import java.io.Serializable


class Orcamento : Serializable{
    var id:Long = 0
    var nomeOrcamento = ""
    var cliente = ""
    var statusOrcamento = ""

    override fun toString(): String {
        return "Orçamento: $nomeOrcamento"
    }
}