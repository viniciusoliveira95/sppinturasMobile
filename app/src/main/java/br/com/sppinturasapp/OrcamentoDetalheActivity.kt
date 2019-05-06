package br.com.sppinturasapp

import android.content.ClipData
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pedido_resumo.view.*

class OrcamentoDetalheActivity : MenuActivity() {

    var orcamento: Orcamento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orcamento_detalhe)
        supportActionBar?.title = getString(R.string.orcamentoDetalhe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        orcamento = intent.getSerializableExtra("orcamento") as Orcamento

        //recuperar campos da view
        var idOrcamento = findViewById<TextView>(R.id.inputIdOrcamento)
        var nomeOrcamento = findViewById<EditText>(R.id.inputNomeOrcamento)
        var valor = findViewById<EditText>(R.id.inputValor)
        var nomeCliente = findViewById<EditText>(R.id.inputCliente)
        var statusOrcamento = findViewById<EditText>(R.id.inputStatus)

        //setar valor para os campos da view
        idOrcamento.text = orcamento?.idOrcamento.toString()
        nomeOrcamento.setText(orcamento?.nome)
        valor.setText(orcamento?.valorTotal.toString())
        nomeCliente.setText(orcamento?.cliente)
        statusOrcamento.setText(orcamento?.statusOrcamento)

        val btnSalvar = findViewById<Button>(R.id.btnSalvarOrcamento)
        btnSalvar.setOnClickListener{
            val orcamento = Orcamento()
            orcamento.idOrcamento = idOrcamento.text.toString().toLong()
            orcamento.nome = nomeOrcamento.text.toString()
            orcamento.valorTotal = valor.text.toString().toDouble()
            orcamento.cliente = nomeCliente.text.toString()
            orcamento.statusOrcamento = statusOrcamento.text.toString()
            salvar(orcamento)
        }

        val btnCancelar = findViewById<Button>(R.id.btnCancelarOrcamento)
        btnCancelar.setOnClickListener{cancelar()}
    }


    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        invalidateOptionsMenu()
        menu?.findItem(R.id.actionBuscar)?.isVisible = false
        menu?.findItem(R.id.actionSalvar)?.isVisible = true
        menu?.findItem(R.id.actionCancelar)?.isVisible = true
        menu?.findItem(R.id.actionExluir)?.isVisible = true

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        var idOrcamento = findViewById<TextView>(R.id.inputIdOrcamento)
        var nomeOrcamento = findViewById<EditText>(R.id.inputNomeOrcamento)
        var valor = findViewById<EditText>(R.id.inputValor)
        var nomeCliente = findViewById<EditText>(R.id.inputCliente)
        var statusOrcamento = findViewById<EditText>(R.id.inputStatus)

        if (id == R.id.actionSalvar){
            val orcamento = Orcamento()
            orcamento.idOrcamento = idOrcamento.text.toString().toLong()
            orcamento.nome = nomeOrcamento.text.toString()
            orcamento.valorTotal = valor.text.toString().toDouble()
            orcamento.cliente = nomeCliente.text.toString()
            orcamento.statusOrcamento = statusOrcamento.text.toString()
            salvar(orcamento)
        }

        else if (id == R.id.actionExluir){
            confirmarExlusao(idOrcamento.text.toString().toLong())
        }

        return super.onOptionsItemSelected(item)
    }


    private fun cancelar(){
        finish()
    }


    private fun salvar(orcamento: Orcamento){
        Thread{
            OrcamentoService.put(orcamento)
            finish()
            runOnUiThread{
                finish()
            }
        }.start()
    }


    private fun exluir(idOrcamento: Long){
        Thread{
            OrcamentoService.delete(idOrcamento)
            finish()
            runOnUiThread{
                finish()
            }
        }.start()
    }


    private fun confirmarExlusao(idOrcamento: Long){
        AlertDialog.Builder(this, R.style.SppAlertDialog)
            .setTitle(R.string.app_name)
            .setMessage(R.string.desejaExluirOrcamento)
            .setPositiveButton(R.string.sim){
                dialog, which ->
                    dialog.dismiss()
                    exluir(idOrcamento)}
            .setNegativeButton(R.string.nao) {
                    dialog, which -> dialog.dismiss()
            }.create().show()
    }

}
