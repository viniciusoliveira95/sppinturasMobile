package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.*
import android.widget.Toast
import android.view.Menu
import android.widget.Button
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu?.findItem(R.id.actionBuscar)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                val textoBusca = newText.toLowerCase()
                var orcamentosFiltrados = arrayListOf<Orcamento>()

                for(orcamento in orcamentos){
                    if(orcamento.nome.toLowerCase().contains(textoBusca) || orcamento.cliente.toLowerCase().contains(textoBusca)){
                        orcamentosFiltrados.add(orcamento)
                    }
                }
                recyclerOrcamentos?.adapter = OrcamentoAdapter(orcamentosFiltrados) {onClickOrcamento(it)}
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // ação  quando terminou de buscar e enviou
                return false
            }

        })
        return true
    }

    override fun onResume() {
        super.onResume()
        getOrcamentos()
    }

    fun getOrcamentos() {
        // Criar a Thread
        Thread{
            //Código para procurar os orçamentos
            //que sera executado em segundo plano / Thread separada
            this.orcamentos = OrcamentoService.getOrcamentos(this)
            runOnUiThread{
                // Código para atualizar a UI com a lista de disciplinas
                recyclerOrcamentos?.adapter = OrcamentoAdapter(orcamentos) {onClickOrcamento(it)}
            }
        }.start()
    }
    // tratamento do evento de clicar em um orçamento
    fun onClickOrcamento(orcamento: Orcamento) {
        val intent = Intent(this, OrcamentoDetalheActivity::class.java)
        intent.putExtra("orcamento", orcamento)
        startActivity(intent)
    }

}
