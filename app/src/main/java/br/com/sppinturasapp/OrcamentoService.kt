package br.com.sppinturasapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.security.AccessControlContext


val host = "http://10.0.2.2:5000"
val TAG = "ws_sppinturasApp"


object OrcamentoService {

    fun getOrcamentos (context: Context) : List<Orcamento>{
        //val orcamentos = mutableListOf<Orcamento>()
        if (AndroidUtils.isInternetDisponivel(context)){
            val url = "$host/orcamentos"
            val json = HttpHelper.get(url)
            return parseJson<List<Orcamento>>(json)
        }
        else{
            return ArrayList<Orcamento>()
        }

    }

    fun post(orcamento: Orcamento): Response{
        val json = HttpHelper.post("$host/orcamentos", orcamento.toJson())
        return parseJson<Response>(json)
    }

    fun delete(idOrcamento: Long): Response{
        val url = "$host/orcamentos/${idOrcamento}"
        val json = HttpHelper.delete(url)
        return parseJson<Response>(json)
    }

    fun put (orcamento: Orcamento): Response{
        val url = "$host/orcamentos/${orcamento.idOrcamento}"
        val json = HttpHelper.put(url, orcamento.toJson())
        return parseJson<Response>(json)
    }

    inline fun <reified T> parseJson(json: String): T{
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }

}