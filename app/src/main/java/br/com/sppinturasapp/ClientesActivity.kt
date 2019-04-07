package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button

class ClientesActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clientes)
        supportActionBar?.title = getString(R.string.clientes)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val botaoNovoCliente = findViewById<Button>(R.id.btnNovoCliente)
        botaoNovoCliente.setOnClickListener{onClickNovoCliente()}
    }


    fun onClickNovoCliente(){
        val intent = Intent(this, NovoClienteActivity::class.java)
        startActivity(intent)
    }
}
