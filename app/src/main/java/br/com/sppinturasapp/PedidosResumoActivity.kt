package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Button

class PedidosResumoActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.pedidos)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        val botaoNovoPediso = findViewById<Button>(R.id.btnNovoPedido)
        botaoNovoPediso.setOnClickListener{onClickNovoCliente()}
    }

    /*
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        menu?.findItem(R.id.actionNovo)?.isVisible = true

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }
    */


    fun onClickNovoCliente(){
        val intent = Intent(this, PedidoDetalheActivity::class.java)
        startActivity(intent)
    }
}
