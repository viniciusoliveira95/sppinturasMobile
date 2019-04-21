package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_orcamento_resumo.view.*

class OrcamentoResumoActivity : MenuActivity() {

    private var orcamentos = listOf<Orcamento>()
    var recyclerOrcamentos: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orcamento_resumo)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.resumoOrcamentos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        recyclerOrcamentos = findViewById<RecyclerView>(R.id.recyclerOrcamentos)
        recyclerOrcamentos?.layoutManager = LinearLayoutManager(this)
        recyclerOrcamentos?.itemAnimator = DefaultItemAnimator()
        recyclerOrcamentos?.setHasFixedSize(true)

    }


        override fun onResume() {
            super.onResume()
            // task para recuperar os orcamentos
            taskOrcamentos()
        }
        fun taskOrcamentos() {
            orcamentos = OrcamentoService.getOrcamentos(this)

            // atualizar lista
            recyclerOrcamentos?.adapter = OrcamentoAdapter(orcamentos) {onClickOrcamento(it)}
        }
        // tratamento do evento de clicar em uma disciplina
        fun onClickOrcamento(orcamento: Orcamento) {
            val intent = Intent(this, OrcamentoDetalheActivity::class.java)
            intent.putExtra("orcamento", orcamento)
            startActivity(intent)
        }

}
