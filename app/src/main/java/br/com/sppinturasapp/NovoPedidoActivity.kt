package br.com.sppinturasapp

import android.os.Bundle

class NovoPedidoActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_pedido)
        supportActionBar?.title = getString(R.string.novoPedido)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
