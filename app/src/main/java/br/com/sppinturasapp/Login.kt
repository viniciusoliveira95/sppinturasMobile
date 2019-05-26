package br.com.sppinturasapp

import com.google.gson.GsonBuilder

class Login {
    var usuario = ""
    var senha = ""
    var autenticado = ""

    fun toJson(): String{
        return GsonBuilder().create().toJson(this)
    }
}