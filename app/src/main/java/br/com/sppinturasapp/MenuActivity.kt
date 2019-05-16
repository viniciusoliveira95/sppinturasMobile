package br.com.sppinturasapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle


open class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var filtro: IntentFilter? = null
    var receiver = SPPReiceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        filtro = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(receiver, filtro)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private val className: String
        get(){
            val s = javaClass.name
            return s.substring(s.lastIndexOf("."))
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        // vincular evento de buscar
        (menu?.findItem(R.id.actionBuscar)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                // ação enquanto está digitando
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // ação  quando terminou de buscar e enviou
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.actionBuscar){
            Toast.makeText(this, "Botão de buscar", Toast.LENGTH_LONG).show()
        }
        else if (id == R.id.actionAtualizar){
            finish()
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    // configuração do navigation Drawer com a toolbar
    fun configuraMenuLateral() {
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        var menuLateral = findViewById<DrawerLayout>(R.id.layoutMenuLateral)
        // ícone de menu (hamburger) para mostrar o menu
        var toogle = ActionBarDrawerToggle(
            this,
            menuLateral,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        menuLateral.addDrawerListener(toogle)
        toogle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.menu_lateral)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_orcamentos -> {
                if(this.className == ".OrcamentoResumoActivity"){
                    onRestart()
                }
                else{
                    val intent = Intent(this, OrcamentoResumoActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.nav_pedidos -> {
                if(this.className == ".PedidosResumoActivity"){
                    onRestart()
                }
                else{
                    val intent = Intent(this, PedidosResumoActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.nav_config -> {
                Toast.makeText(this, "Clicou Config", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_sair -> {
                deslogar()
            }
        }
        // fecha menu depois de tratar o evento
        val drawer = findViewById<DrawerLayout>(R.id.layoutMenuLateral)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    fun deslogar(){
        val returnIntent = Intent(this, LoginActivity::class.java)
        startActivity(returnIntent)
    }
}
