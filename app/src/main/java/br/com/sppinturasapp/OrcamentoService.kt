package br.com.sppinturasapp

import android.content.Context
import java.security.AccessControlContext

object OrcamentoService {

    fun getOrcamentos (context: Context) : List<Orcamento>{
        val orcamentos = mutableListOf<Orcamento>()

        for (i in 1..10){
            val o = Orcamento()
            o.nomeOrcamento = "Nome do orçamento $i"
            o.cliente = "Cliente $i"
            o.statusOrcamento = "Status orçamento $i"

            orcamentos.add(o)
        }

        return  orcamentos
    }
}