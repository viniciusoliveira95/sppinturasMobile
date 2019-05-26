package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.*

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val imagem = findViewById<ImageView>(R.id.sppinturasLogo)
        imagem.setImageResource(R.drawable.sppinturas_logo)

        var lembrar = Prefs.getBoolean("lembrar")
        if (lembrar) {
            var lembrarNome = Prefs.getString("lembrarNome")
            var lembrarSenha = Prefs.getString("lembrarSenha")
            findViewById<EditText>(R.id.inputUsuario).setText(lembrarNome)
            findViewById<EditText>(R.id.inputSenha).setText(lembrarSenha)
            findViewById<CheckBox>(R.id.checkLembrar).isChecked = lembrar
        }

        val botaoLogin = findViewById<Button>(R.id.btnLogin)
        botaoLogin.setOnClickListener {onClickLogin()}
    }


    override fun onResume() {
        super.onResume()
        if(intent.hasExtra("idOrcamento")) abrirOrcamento()
        else if(intent.hasExtra("idPedido")) abrirPedido()
    }

    fun abrirOrcamento() {
        Thread {
            var idOrcamento = intent.getStringExtra("idOrcamento")?.toLong()!!
            val orcamento = OrcamentoService.getOrcamento(this, idOrcamento)
            runOnUiThread {
                val intent = Intent(this, OrcamentoDetalheActivity::class.java)
                intent.putExtra("orcamento", orcamento)
                startActivity(intent)
            }
        }.start()
    }

    fun abrirPedido() {
        Thread {
            var idPedido = intent.getStringExtra("idPedido")?.toLong()!!
            val pedido = PedidoService.getPedido(this, idPedido)
            runOnUiThread {
                val intent = Intent(this, PedidoDetalheActivity::class.java)
                intent.putExtra("pedido", pedido)
                intent.putExtra("novoPedido", false)
                startActivity(intent)
            }
        }.start()
    }

    fun onClickLogin(){
        val campoUser = findViewById<EditText>(R.id.inputUsuario)
        val campoSenha = findViewById<EditText>(R.id.inputSenha)
        val checkBoxLembrar = findViewById<CheckBox>(R.id.checkLembrar)

        val inputUser = campoUser.text.toString()
        val inputSenha = campoSenha.text.toString()

        // armazenar valor do checkbox
        Prefs.setBoolean("lembrar", checkBoxLembrar.isChecked)
        // verificar se é para lembrar nome e senha
        if (checkBoxLembrar.isChecked) {
            Prefs.setString("lembrarNome", inputUser)
            Prefs.setString("lembrarSenha", inputSenha)
        } else{
            Prefs.setString("lembrarNome", "")
            Prefs.setString("lembrarSenha", "")
        }

        var login = Login()
        login.usuario = inputUser
        login.senha = inputSenha
        autenticarLogin(login)

    }

    fun autenticarLogin(login: Login){
        Thread{
            val autenticado = LoginService.autenticarLogin(login)
            runOnUiThread{
                if(autenticado == true){
                    val intent = Intent(this, OrcamentoResumoActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Usuário e ou senha incorretos",Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}
