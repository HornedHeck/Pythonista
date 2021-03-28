import lexical.Lexem
import lexical.LexemType
import lexical.LexicalAnalyzerImpl
import lexical.SymbolTable
import syntax.Node
import syntax.SyntaxAnalyzer
import java.awt.Point
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

    table.functions["int"] = Lexem(LexemType.FUNCTION, "int")
    table.functions["input"] = Lexem(LexemType.FUNCTION, "input")
    table.functions["print"] = Lexem(LexemType.FUNCTION, "print")
    table.functions["len"] = Lexem(LexemType.FUNCTION, "len")
    table.functions["index"] = Lexem(LexemType.FUNCTION, "index")

    val syntaxResult = SyntaxAnalyzer.analyze(lResult, table)

    println(syntaxResult)

}
//
//private fun getPointWidth(node: Node): Int = if (node is Point) {
//    1
//} else {
//    (node as Node.Chain).nodes.map(::getPointWidth).sum()
//}