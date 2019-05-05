package br.com.sppinturasapp

import android.os.Bundle
import android.view.Menu

class PedidoDetalheActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_pedido)
        supportActionBar?.title = getString(R.string.novoPedido)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        invalidateOptionsMenu()
        menu?.findItem(R.id.actionBuscar)?.isVisible = false
        menu?.findItem(R.id.actionNovo)?.isVisible = true
        menu?.findItem(R.id.actionSalvar)?.isVisible = true
        menu?.findItem(R.id.actionCancelar)?.isVisible = true
        menu?.findItem(R.id.actionExluir)?.isVisible = true

        return super.onPrepareOptionsMenu(menu)
    }
}
