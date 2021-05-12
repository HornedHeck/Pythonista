package semantic

import lexical.Lexem
import lexical.LexemType
import syntax.Node

class SemanticAnalyzer {

    private lateinit var functions: List<FunInfo>

    fun start(items: List<Node>): List<FunInfo> {

        functions = items.mapNotNull { tryGetFun(it) }

        items.forEach(this::checkFunctions)

        items.forEach(this::checkIndices)

        return functions
    }

    private fun flatten(node: Node): List<Lexem> = when (node) {
        is Node.Chain -> node.nodes.flatMap { flatten(it) }
        is Node.Point -> listOf(node.lexem)
    }

    private fun checkIndices(item: Node) {
        if (item is Node.Point) return
        item as Node.Chain
        val first = item.nodes.first()
        if (first is Node.Point && first.lexem.type == LexemType.SO_PARENTHESIS) {
            val lastIndex = item.nodes.indexOfLast { it is Node.Point && it.lexem.type == LexemType.SC_PARENTHESIS }
            val items = item.nodes.subList(item.nodes.indexOf(first) + 1, lastIndex).flatMap {
                flatten(it)
            }
            items.any {
                (it.type !in setOf(
                    LexemType.RO_PARENTHESIS,
                    LexemType.RC_PARENTHESIS,
                    LexemType.VARIABLE,
                    LexemType.FUNCTION,
                    LexemType.SO_PARENTHESIS,
                    LexemType.SC_PARENTHESIS,
                    LexemType.DOT,
                    LexemType.STRING_CONSTANT
                )).apply {
                    if (!this) {
                        println("Error at $it in $items")
                    }
                }
            }
        } else {
            item.nodes.forEach(this::checkIndices)
        }
    }

    private fun checkFunctions(item: Node) {
        if (item is Node.Point) {
            return
        }
        item as Node.Chain
        if (item.first().type == LexemType.FUNCTION) {

            val argsChain = item.nodes[1] as? Node.Chain ?: return
            val size = (argsChain.nodes.size + 1) / 2
            val name = item.first().key
            val f = functions.firstOrNull { it.name == name } ?: return
            if (f.args.size != size) {
                println("Error at $name at $name $argsChain")
            }
        } else {
            item.nodes.forEach(this::checkFunctions)
        }
    }

    private fun tryGetFun(node: Node): FunInfo? {
        when (node) {
            is Node.Point -> {
                return null
            }
            is Node.Chain -> {

                val rawLexems = mutableListOf<Lexem>()
                for (n in node.nodes) {
                    rawLexems.add(
                        (node.nodes.first() as? Node.Point)?.lexem ?: return null
                    )
                }

                if (rawLexems.first().key != "def") return null

                val openIndex = rawLexems.indexOfFirst { it.type == LexemType.RO_PARENTHESIS }
                if (openIndex == -1) {
                    println("Error before ${rawLexems.last().key} at ${rawLexems.joinToString()}")
                    return null
                }

                val closeIndex = rawLexems.indexOfFirst { it.type == LexemType.RC_PARENTHESIS }
                if (closeIndex == -1) {
                    println("Error after ${rawLexems.last().key} at ${rawLexems.joinToString()}")
                    return null
                }

                val name = rawLexems.first { it.type == LexemType.RAW_NAME }

                val args = rawLexems.subList(openIndex, closeIndex)
                    .filter {
                        it.type == LexemType.RAW_NAME
                                || it.type == LexemType.VARIABLE
                                || it.type == LexemType.FUNCTION
                    }
                    .map {
                        it.key
                    }

                return FunInfo(name.key, "AL", args)
            }
        }
    }

}
