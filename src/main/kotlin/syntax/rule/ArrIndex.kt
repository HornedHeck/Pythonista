package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node

object ArrIndex : Rule {


    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 4) return null

        val sopIndex = lexems.indexOfLast { it.type == LexemType.SO_PARENTHESIS }
        val scpIndex = lexems.indexOfLast { it.type == LexemType.SC_PARENTHESIS }
        if (sopIndex < 1 || scpIndex <= sopIndex) return null

        val node = if (sopIndex == 1 && lexems.first().type == LexemType.VARIABLE) {
            Node.Point(lexems.first())
        } else {
            tryParse(lexems.subList(0, sopIndex)) ?: FCall.tryParse(lexems.subList(0, sopIndex)) ?: return null
        }
        if (node.last() != lexems[sopIndex - 1]) return null

        val vItemRes = VItem.tryParse(lexems.subList(sopIndex + 1, scpIndex)) ?: return null


        if (vItemRes.last() != lexems[scpIndex - 1]) return null

        return Node.Chain(
            listOf(
                node,
                Node.Point(lexems[sopIndex]),
                vItemRes,
                Node.Point(lexems[scpIndex])
            )
        )
    }
}