package kr.co.seoft.dls.model

fun getSalt1(s: String): String {
    return try {
        val num = s
            .firstOrNull { it.digitToIntOrNull() != null }
            ?.digitToInt()
            ?: error("can't convert salt")
        "${s[num]}${s[num+1]}"
    } catch (e: Exception) {
        e.printStackTrace()
        "${s[0]}${s[1]}"
    }
}

var SALT2 = ""
var SALT3 = ""