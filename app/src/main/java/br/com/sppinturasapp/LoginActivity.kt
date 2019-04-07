package br.com.sppinturasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class LoginActivity : MenuActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        val imagem = findViewById<ImageView>(R.id.sppinturasLogo)
        imagem.setImageResource(R.drawable.sppinturas_logo)
        val botaoLogin = findViewById<Button>(R.id.btnLogin)
        botaoLogin.setOnClickListener {onClickLogin()}
    }


    fun onClickLogin(){
        val inputUser = findViewById<EditText>(R.id.inputUsuario)
        val inputSenha = findViewById<EditText>(R.id.inputSenha)
        Toast.makeText(this,"${inputUser.text} : ${inputSenha.text}", Toast.LENGTH_LONG).show()
        var usuarioAutenticado = autenticarLogin(inputUser.text.toString(), inputSenha.text.toString())
        if(usuarioAutenticado == true){
            val intent = Intent(this, OrcamentoResumoActivity::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Usuário e ou senha incorretos",Toast.LENGTH_LONG).show()
        }
    }

    fun autenticarLogin(usuario: String, senha: String): Boolean {
        var autenticado = false
        if(usuario == "vini" && senha == "senha") {
            autenticado = true
        }
        return autenticado
    }
}
