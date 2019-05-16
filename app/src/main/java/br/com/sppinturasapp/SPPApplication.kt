package br.com.sppinturasapp

import android.app.Application
import java.lang.IllegalStateException

class SPPApplication: Application() {
    // chamado quando android iniciar o processo da aplicação
    override fun onCreate() {
        super.onCreate()
        appInstance = this
        Prefs.setBoolean("semConexao", false)
    }

    companion object {
        // singleton
        private var appInstance: SPPApplication?  = null
        fun getInstance(): SPPApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configurar application no Android Manifest")
            }
            return appInstance!!
        }
    }

    // chamado quando android terminar processo da aplicação
    override fun onTerminate() {
        super.onTerminate()
    }
}