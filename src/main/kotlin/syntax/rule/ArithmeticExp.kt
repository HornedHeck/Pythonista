package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.toPoint

object ArithmeticExp : Rule {

    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 3) return null

        val item = if (lexems.first().type == LexemType.RO_PARENTHESIS) {
            scopedRange(lexems, 0).takeIf { it != -1 }?.let { VItem.tryParse(lexems.subList(1, it)) }
        } else {
            val index = lexems.indexOfFirst { it.type == LexemType.ARITHMETIC_OPERATOR }
            if (index !in lexems.indices) return null
            VItem.tryParse(lexems.subList(0, index))
        } ?: return null

        val lastIndex = lexems.indexOf(item.last())

        val startIndex = if (lexems[lastIndex + 1].type == LexemType.RC_PARENTHESIS) {
            lastIndex + 2
        } else {
            lastIndex + 1
        }

        if (lexems[startIndex].type != LexemType.ARITHMETIC_OPERATOR) return null

        val action = lexems[startIndex].toPoint()

        val secondArg = VItem.tryParse(lexems.subList(startIndex + 1, lexems.size)) ?: return null

        return Node.Chain(
            listOf(item, action, secondArg)
        )
    }


}

fun scopedRange(lexems: List<Lexem>, start: Int): Int {
    var lastIndex = -1
    var counter = 0
    for (i in start until lexems.size) {
        when (lexems[i].type) {
            LexemType.RO_PARENTHESIS -> counter += 1
            LexemType.RC_PARENTHESIS -> counter -= 1
            else -> {
            }
        }
        if (counter < 0) {
            return -1
        }

        if (counter == 0) {
            lastIndex = i
            break
        }
    }

    return lastIndex + 1
}