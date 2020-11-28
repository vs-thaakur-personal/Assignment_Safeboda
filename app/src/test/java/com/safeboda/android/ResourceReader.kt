package com.safeboda.android

import java.io.File
import java.net.URLDecoder

class ResourceReader {
    fun getFileAsText(obj: Any, fileName: String): String? {
        val classLoader = obj.javaClass.classLoader
        val url = URLDecoder.decode(classLoader?.getResource(fileName.trim())?.file, "UTF-8")
        return File(url).readText()
    }
}