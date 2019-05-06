package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
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
