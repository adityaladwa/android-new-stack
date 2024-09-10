package com.aditya.test_util

import java.io.File


fun getJsonFromResource(
    fileName: String,
    classLoader: ClassLoader? = Thread.currentThread().contextClassLoader
): String {
    val file = File(classLoader?.getResource(fileName)!!.file)
    return file.readText(Charsets.UTF_8)
}