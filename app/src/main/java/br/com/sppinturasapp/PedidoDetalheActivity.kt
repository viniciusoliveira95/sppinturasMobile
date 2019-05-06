package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


class PedidoDetalheActivity : MenuActivity() {

    var pedido: Pedido? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedido_detalhe)
        supportActionBar?.title = getString(R.string.novoPedido)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        var novoPedido = intent.getSerializableExtra("novoPedido") as Boolean


        //recupera os campos da view
        var idPedido = findViewById<TextView>(R.id.inputIdPedido)
        var labelIdPedido = findViewById<TextView>(R.id.labelIdPedido)
        var dataPedido = findViewById<TextView>(R.id.inputDataPedido)
        var nomeSolicitante = findViewById<EditText>(R.id.inputNomeSolicitante)
        var email = findViewById<EditText>(R.id.inputEmail)
        var telefone = findViewById<EditText>(R.id.inputTelefone)
        var descriao = findViewById<EditText>(R.id.inputDescricao)

        //abre um formulario limpo caso seja um novo pedido ou preechido caso seja uma edição de pedido
        if(!novoPedido){
            pedido = intent.getSerializableExtra("pedido") as Pedido

            idPedido.text = pedido?.idPedido.toString()
            dataPedido.text = pedido?.dtPedido
            nomeSolicitante.setText(pedido?.nomeSolicitante)
            email.setText(pedido?.email)
            telefone.setText(pedido?.telefone.toString())
            descriao.setText(pedido?.descricao)
        }
        else{
            val dateNow = Calendar.getInstance().time
            val df = SimpleDateFormat("dd-MM-yyyy")
            val dateNowString = df.format(dateNow)

            idPedido.visibility = View.GONE
            labelIdPedido.visibility = View.GONE
            dataPedido.text = dateNowString
        }

        val btnSalvar = findViewById<Button>(R.id.btnSalvarPedido)
        btnSalvar.setOnClickListener {
            var pedido = Pedido()
            pedido.dtPedido = dataPedido.text.toString()
            pedido.nomeSolicitante = nomeSolicitante.text.toString()
            pedido.email = email.text.toString()
            pedido.telefone = telefone.text.toString().toLong()
            pedido.descricao = descriao.text.toString()

            if(idPedido.text.toString() == ""){
                postPedido(pedido)
            }
            else{
                pedido.idPedido = idPedido.text.toString().toLong()
                putPedido(pedido)
            }
        }

        val btnCancelar = findViewById<Button>(R.id.btnCancelarPedido)
        btnCancelar.setOnClickListener{cancelar()}

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

    private fun cancelar(){
        finish()
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        var idPedido = findViewById<TextView>(R.id.inputIdPedido)
        var dataPedido = findViewById<TextView>(R.id.inputDataPedido)
        var nomeSolicitante = findViewById<EditText>(R.id.inputNomeSolicitante)
        var email = findViewById<EditText>(R.id.inputEmail)
        var telefone = findViewById<EditText>(R.id.inputTelefone)
        var descriao = findViewById<EditText>(R.id.inputDescricao)

        if (id == R.id.actionSalvar){
            var pedido = Pedido()
            pedido.dtPedido = dataPedido.text.toString()
            pedido.nomeSolicitante = nomeSolicitante.text.toString()
            pedido.email = email.text.toString()
            pedido.telefone = telefone.text.toString().toLong()
            pedido.descricao = descriao.text.toString()

            if(idPedido.text.toString() == ""){
                postPedido(pedido)
            }
            else{
                pedido.idPedido = idPedido.text.toString().toLong()
                putPedido(pedido)
            }
        }

        else if (id == R.id.actionExluir){
            if(idPedido.text.toString() == ""){
                finish()
            }
            else{
            confirmarExlusao(idPedido.text.toString().toLong())
            }
        }


        else if (id == R.id.actionNovo){
            finish()
            val intent = Intent(this, PedidoDetalheActivity::class.java)
            intent.putExtra("novoPedido", true)
            startActivity(intent)
        }


        return super.onOptionsItemSelected(item)
    }


    private fun putPedido(pedido: Pedido){
        Thread{
            PedidoService.put(pedido)
            finish()
            runOnUiThread{
                finish()
            }
        }.start()
    }

    private fun postPedido(pedido: Pedido){
        Thread{
            PedidoService.post(pedido)
            finish()
            runOnUiThread{
                finish()
            }
        }.start()
    }


    private fun exluir(idPedido: Long){
        Thread{
            PedidoService.delete(idPedido)
            finish()
            runOnUiThread{
                finish()
            }
        }.start()
    }

    private fun confirmarExlusao(idOrcamento: Long){
        AlertDialog.Builder(this, R.style.SppAlertDialog)
            .setTitle(R.string.app_name)
            .setMessage(R.string.desejaExluirPedido)
            .setPositiveButton(R.string.sim){
                    dialog, which ->
                dialog.dismiss()
                exluir(idOrcamento)}
            .setNegativeButton(R.string.nao) {
                    dialog, which -> dialog.dismiss()
            }.create().show()
    }


}
