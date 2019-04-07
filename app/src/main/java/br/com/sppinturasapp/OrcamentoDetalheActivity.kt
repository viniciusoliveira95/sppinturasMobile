package br.com.sppinturasapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class OrcamentoDetalheActivity : MenuActivity() {

    var orcamento: Orcamento? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orcamento_detalhe)
        supportActionBar?.title = getString(R.string.orcamentoDetalhe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        orcamento = intent.getSerializableExtra("orcamento") as Orcamento

        //recuperar campos da view
        var txtNomeOrcamto = findViewById<TextView>(R.id.nomeOrcamento)
        var txtNomeCliente = findViewById<TextView>(R.id.nomeCliente)
        var txtStatusOrcamento = findViewById<TextView>(R.id.statusOrcamento)

        //setar valor para os campos da view
        txtNomeOrcamto.text = orcamento?.nomeOrcamento
        txtNomeCliente.text = orcamento?.cliente
        txtStatusOrcamento.text = orcamento?.statusOrcamento
    }
}
