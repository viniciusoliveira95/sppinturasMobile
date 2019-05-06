package br.com.sppinturasapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.security.AccessControlContext




object PedidoService {

    fun getPedidos (context: Context) : List<Pedido>{
        if (AndroidUtils.isInternetDisponivel(context)){
            val url = "$host/pedidos"
            val json = HttpHelper.get(url)
            return parseJson<List<Pedido>>(json)
        }
        else{
            return ArrayList<Pedido>()
        }

    }

    fun post(pedido: Pedido): Response{
        val json = HttpHelper.post("$host/pedidos", pedido.toJson())
        return parseJson<Response>(json)
    }

    fun delete(idPedido: Long): Response{
        val url = "$host/pedidos/${idPedido}"
        val json = HttpHelper.delete(url)
        return parseJson<Response>(json)
    }

    fun put (pedido: Pedido): Response{
        val url = "$host/pedidos/${pedido.idPedido}"
        val json = HttpHelper.put(url, pedido.toJson())
        return parseJson<Response>(json)
    }

    inline fun <reified T> parseJson(json: String): T{
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }

}