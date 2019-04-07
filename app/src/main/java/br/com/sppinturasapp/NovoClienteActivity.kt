package br.com.sppinturasapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NovoClienteActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_cliente)
        supportActionBar?.title = getString(R.string.novoCliente)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
