package br.com.sppinturasapp

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import br.com.sppinturasapp.SPPApplication.Companion.temConexao


open class SPPReiceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            var statusConexao = AndroidUtils.isInternetDisponivel(context)

            if(statusConexao == true &&  temConexao == true){
                NotificationUtil.create(context,1, intent,"SPPinturas", context.resources.getString(R.string.conexaoInternetRestabelecida))
                temConexao = false
            }
            else if(statusConexao == false && temConexao == false){
                NotificationUtil.create(context,1, intent,"SPPinturas", context.resources.getString(R.string.semConexaoIntenet))
                temConexao = true
            }
        }
    }
}
