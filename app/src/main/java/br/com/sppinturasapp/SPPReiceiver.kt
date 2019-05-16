package br.com.sppinturasapp

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


open class SPPReiceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            var statusConexao = AndroidUtils.isInternetDisponivel(context)
            var getPrefsSemConexao = Prefs.getBoolean("semConexao")

            if(statusConexao == true && getPrefsSemConexao == false){
                NotificationUtil.create(context,1, intent,"SPPinturas", context.resources.getString(R.string.comConexaoIntenet))
                Prefs.setBoolean("semConexao", true)
            }
            else if(statusConexao == false && getPrefsSemConexao == true){
                NotificationUtil.create(context,1, intent,"SPPinturas", context.resources.getString(R.string.semConexaoIntenet))
                Prefs.setBoolean("semConexao", false)
            }
        }
    }
}
