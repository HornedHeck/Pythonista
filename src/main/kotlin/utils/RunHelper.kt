package utils

import java.io.InputStreamReader
import java.io.PrintStream
import java.util.*

fun evaluate(path: String) {
    val proc = Runtime.getRuntime().exec("python $path")
    val s = PrintStream(proc.outputStream, true)
    val t = enableConsole(s)
    InputStreamReader(proc.inputStream).forEachLine { println(it) }
    s.close()
}

fun enableConsole(stream: PrintStream) = Thread {
    val scanner = Scanner(System.`in`)
    while (!stream.checkError()) {
        stream.println(scanner.nextLine())
    }
}.apply { start() }