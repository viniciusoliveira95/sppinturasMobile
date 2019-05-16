package br.com.sppinturasapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.security.AccessControlContext




object PedidoService {
    val dao = DatabaseManager.getPedidoDAO()

    fun getPedidos (context: Context) : List<Pedido>{
        var pedidos = ArrayList<Pedido>()
        if (AndroidUtils.isInternetDisponivel(context)){
            sincronizacao()
            val url = "$host/pedidos"
            val json = HttpHelper.get(url)
            pedidos = parseJson(json)
            //salvar offline
            for(p in pedidos){
                saveOffline(p)
            }

            return pedidos
        }
        else{
            val pedidos = dao.naoExluidosOffline()
            return pedidos
        }
    }

    fun getPedido (context: Context, id: Long): Pedido? {

        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/pedidos/${id}"
            val json = HttpHelper.get(url)
            val pedido = parseJson<Pedido>(json)

            return pedido
        } else {
            val dao = DatabaseManager.getPedidoDAO()
            val pedido = dao.getById(id)

            return pedido
        }

    }

    fun post(pedido: Pedido): Response{
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)) {
            pedido.criadoNoApp = 1
            val json = HttpHelper.post("$host/pedidos", pedido.toJson())
            return parseJson<Response>(json)
        }
        else{
            pedido.criadoNoApp = 1
            pedido.idPedido = getLastId() + 1
            saveOffline(pedido)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }
    }

    fun delete(pedido: Pedido): Response{
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)){
            val url = "$host/pedidos/${pedido.idPedido}"
            val json = HttpHelper.delete(url)
            dao.delete(pedido)
            return parseJson<Response>(json)
        }else{
            pedido.deletadoLocal = 1
            dao.update(pedido)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }
    }

    fun put (pedido: Pedido): Response{
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)){
            val url = "$host/pedidos/${pedido.idPedido}"
            val json = HttpHelper.put(url, pedido.toJson())
            return parseJson<Response>(json)
        }
        else{
            pedido.alteradoLocal = 1
            dao.update(pedido)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }

    inline fun <reified T> parseJson(json: String): T{
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }

    fun saveOffline(pedido: Pedido) : Boolean {
        if (! existePedido(pedido)) {
            dao.insert(pedido)
        }
        return true
    }
    fun existePedido(pedido: Pedido): Boolean {
        return dao.getById(pedido.idPedido) != null
    }

    fun getLastId(): Long{
        return dao.getMaxId()
    }

    fun sincronizacao(){
        var deletados = dao.exluidosOffline()
        if(deletados.count() > 0){
            for(d in deletados){
                if(d.criadoNoApp == 1){
                    dao.delete(d)
                }
                else{
                    delete(d)
                    dao.delete(d)
                }
            }
        }

        var alterados = dao.alteradoOffline()
        if (alterados.count() > 0){
            for(a in alterados){
                if(a.criadoNoApp == 0){
                    put(a)
                    a.alteradoLocal = 0
                    dao.update(a)
                }
            }
        }

        var criados = dao.criadoNoApp()
        if (criados.count() > 0){
            for(c in criados){
                post(c)
                c.criadoNoApp = 0
                c.alteradoLocal = 0
                dao.update(c)
            }
        }
    }

}