package br.com.sppinturasapp

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "firebase"
    // recebe o novo token criado
    override fun onNewToken(token: String?) {
        super.onNewToken(token)
        Log.d(TAG, "Novo token: $token")
        // guarda token em SharedPreferences
        Prefs.setString("FB_TOKEN", token!!)
    }

    // recebe a notificação de push
    override fun onMessageReceived(mensagemRemota: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived")

        // verifica se a mensagem recebida do firebase é uma notificação
        if (mensagemRemota?.notification != null) {
            val titulo = mensagemRemota?.notification?.title
            val corpo = mensagemRemota?.notification?.body
            Log.d(TAG, "Titulo da mensagem: $titulo")
            Log.d(TAG, "Corpo da mensagem: $corpo")

            showNotification(mensagemRemota)
        }
    }

    private fun showNotification(mensagemRemota: RemoteMessage) {
        // se título for nulo, utilizar nome no app
        val titulo = mensagemRemota?.notification?.title?: getString(R.string.app_name)
        var mensagem = mensagemRemota?.notification?.body!!


        // verificar se existem dados enviados no push
        if(mensagemRemota?.data.isNotEmpty() && mensagemRemota?.data.get("idOrcamento") != null) {
            val intent = Intent(this, OrcamentoDetalheActivity::class.java)
            val idOrcamento = mensagemRemota.data.get("idOrcamento")?.toLong()!!
            mensagem += ""
            // recuperar disciplina no WS
            val orcamento = OrcamentoService.getOrcamento(this, idOrcamento)
            intent.putExtra("orcamento", orcamento)
            NotificationUtil.create(this, 2, intent, titulo, mensagem)
        }

        else if(mensagemRemota?.data.isNotEmpty() && mensagemRemota?.data.get("idPedido") != null){
            val intent = Intent(this, PedidoDetalheActivity::class.java)
            val idPedido = mensagemRemota.data.get("idPedido")?.toLong()!!
            mensagem += ""
            // recuperar disciplina no WS
            val pedido = PedidoService.getPedido(this, idPedido)
            intent.putExtra("pedido", pedido)
            intent.putExtra("novoPedido", false)
            NotificationUtil.create(this, 3, intent, titulo, mensagem)
        }

    }
}