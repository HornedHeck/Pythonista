import lexical.LexicalAnalyzerImpl
import lexical.SymbolTable
import java.io.File

fun main(args: Array<String>) {
	
	val code = File(args[0]).readLines()
	val table = SymbolTable()
	
	code.forEachIndexed { i, v ->
		println("${i + 1}. $v")
	}
	println("--------------------------------------------------------------------------")
	val lexicalAnalyzer = LexicalAnalyzerImpl()
	val lResult = lexicalAnalyzer.analyze(code, table)
	lResult.forEachIndexed { i, v ->
		println("${i + 1}. $v")
	}
	println("--------------------------------------------------------------------------")
	println("Symbol tables:")
	println("Names:")
	table.names.forEach(::println)
	println("Constants:")
	table.constants.forEach(::println)
}