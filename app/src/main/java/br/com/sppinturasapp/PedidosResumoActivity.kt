package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.*
import android.view.Menu
import android.widget.Button

class PedidosResumoActivity : MenuActivity() {

    private var pedidos = listOf<Pedido>()
    var recyclerPedidos: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_resumo)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.pedidos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        recyclerPedidos = findViewById<RecyclerView>(R.id.recyclerPedidos)
        recyclerPedidos?.layoutManager = LinearLayoutManager(this)
        recyclerPedidos?.itemAnimator = DefaultItemAnimator()
        recyclerPedidos?.setHasFixedSize(true)

        val botaoNovoPedido = findViewById<Button>(R.id.btnNovoPedido)
        botaoNovoPedido.setOnClickListener{onClickNovoCliente()}
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        (menu?.findItem(R.id.actionBuscar)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                val textoBusca = newText.toLowerCase()
                var pedidosFiltrados = arrayListOf<Pedido>()

                for(pedido in pedidos){
                    if(pedido.nomeSolicitante.toLowerCase().contains(textoBusca) || pedido.dtPedido.toLowerCase().contains(textoBusca)){
                        pedidosFiltrados.add(pedido)
                    }
                }
                recyclerPedidos?.adapter = PedidoAdapter(pedidosFiltrados) {onClickPedido(it)}
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
        // task para recuperar os orcamentos
        getPedidos()
    }
    fun getPedidos() {
        // Criar a Thread
        Thread{
            //Código para procurar os orçamentos
            //que sera executado em segundo plano / Thread separada
            this.pedidos = PedidoService.getPedidos(this)
            runOnUiThread{
                // Código para atualizar a UI com a lista de disciplinas
                recyclerPedidos?.adapter = PedidoAdapter(pedidos) {onClickPedido(it)}
            }
        }.start()
    }

    fun onClickNovoCliente(){
        val intent = Intent(this, PedidoDetalheActivity::class.java)
        intent.putExtra("novoPedido", true)
        startActivity(intent)
    }

    fun onClickPedido(pedido: Pedido) {
        val intent = Intent(this, PedidoDetalheActivity::class.java)
        intent.putExtra("novoPedido", false)
        intent.putExtra("pedido", pedido)
        startActivity(intent)
    }
}
