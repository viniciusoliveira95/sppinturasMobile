package br.com.sppinturasapp

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL
import java.security.AccessControlContext


val host = "http://vinidev95.pythonanywhere.com"
val TAG = "ws_sppinturasApp"

object OrcamentoService {
    val dao = DatabaseManager.getOrcamentoDAO()

    fun getOrcamentos (context: Context) : List<Orcamento>{
        var orcamentos = ArrayList<Orcamento>()
        if (AndroidUtils.isInternetDisponivel(context)){
            sincronizacao()
            val url = "$host/orcamentos"
            val json = HttpHelper.get(url)
            orcamentos = parseJson(json)
            //salvar offline
            for(o in orcamentos){
                saveOffline(o)
            }

            return orcamentos
        }
        else{

            val orcamentos = dao.naoExluidosOffline()
            return orcamentos
        }

    }

    fun getOrcamento (context: Context, id: Long): Orcamento? {

        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/orcamentos/${id}"
            val json = HttpHelper.get(url)
            val orcamento = parseJson<Orcamento>(json)

            return orcamento
        } else {
            val dao = DatabaseManager.getOrcamentoDAO()
            val orcamento = dao.getById(id)

            return orcamento
        }

    }

    fun delete(orcamento: Orcamento): Response{
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)){
            val url = "$host/orcamentos/${orcamento.idOrcamento}"
            val json = HttpHelper.delete(url)
            dao.delete(orcamento)
            return parseJson<Response>(json)
        }else{
            orcamento.deletadoLocal = 1
            dao.update(orcamento)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }

    }

    fun put (orcamento: Orcamento): Response{
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)) {
            val url = "$host/orcamentos/${orcamento.idOrcamento}"
            val json = HttpHelper.put(url, orcamento.toJson())
            return parseJson<Response>(json)
        }
        else{
            orcamento.alteradoLocal = 1
            dao.update(orcamento)
            return Response(status = "OK", msg = "Dados salvos localmente")
        }
    }

    inline fun <reified T> parseJson(json: String): T{
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }


    fun saveOffline(orcamento: Orcamento) : Boolean {
        if (! existeOrcamento(orcamento)) {
            dao.insert(orcamento)
        }
        return true
    }

    fun existeOrcamento(orcamento: Orcamento): Boolean {
        return dao.getById(orcamento.idOrcamento) != null
    }

    fun sincronizacao(){
        var deletados = dao.exluidosOffline()
        if(deletados.count() > 0){
            for(d in deletados){
                delete(d)
                dao.delete(d)
            }
        }

        var alterados = dao.alteradoOffline()
        if (alterados.count() > 0){
            for(a in alterados){
                put(a)
                a.alteradoLocal = 0
                dao.update(a)
            }
        }
    }
}