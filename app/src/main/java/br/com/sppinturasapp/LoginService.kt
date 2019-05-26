package br.com.sppinturasapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object LoginService {
    fun autenticarLogin(login: Login): Boolean{
        var autenticado = false
        if(AndroidUtils.isInternetDisponivel(SPPApplication.getInstance().applicationContext)){
            val json = HttpHelper.post("$host/login", login.toJson())
            var login = parseJson<Login>(json)

            return login.autenticado.toBoolean()
        }
        return autenticado
    }

    inline fun <reified T> parseJson(json: String): T{
        val type = object : TypeToken<T>(){}.type
        return Gson().fromJson<T>(json, type)
    }
}