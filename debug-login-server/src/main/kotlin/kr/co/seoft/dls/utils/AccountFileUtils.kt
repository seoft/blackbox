package kr.co.seoft.dls.utils

import org.springframework.core.io.FileSystemResource
import java.io.File

object AccountFileUtils {
    fun readAccountFile(): String {
        return try {
            val file = File(FileSystemResource("src/main/resources/accounts.txt").file.absolutePath)
            file.bufferedReader().readText()
        } catch (e: Exception) {
            e.printStackTrace()
            "[]"
        }

    }

    fun saveAccountFile(content: String) {
        val file = File(FileSystemResource("src/main/resources/accounts.txt").file.absolutePath)
        file.writeText(content)
    }

}