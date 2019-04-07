package br.com.sppinturasapp

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

open class MenuActivity : AppCompatActivity() {

    private val TAG = "SpPinturasApp"
    private val className: String
        get(){
            val s = javaClass.name
            return s.substring(s.lastIndexOf("."))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, className + ".onCreate() chamado")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, className + ".onStart() chamado")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, className + ".onRestart() chamado")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, className + ".onResume() chamado")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, className + ".onPause() chamado")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, className + ".onStop() chamado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, className + ".onDestroy() chamado")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.actionClientes){
            if(this.className == ".ClientesActivity"){
                onRestart()
            }
            else{
                val intent = Intent(this, ClientesActivity::class.java)
                startActivity(intent)
            }
        }
        else if (id == R.id.actionOrcamentos){
            if(this.className == ".OrcamentoResumoActivity"){
                onRestart()
            }
            else{
                val intent = Intent(this, OrcamentoResumoActivity::class.java)
                startActivity(intent)
            }
        }
        else if (id == android.R.id.home){
            finish()
        }
        else if (id == R.id.btnSair){
            desologar()
        }

        return super.onOptionsItemSelected(item)
    }

    fun desologar(){
        val returnIntent = Intent(this, LoginActivity::class.java)
        startActivity(returnIntent)
    }
}
