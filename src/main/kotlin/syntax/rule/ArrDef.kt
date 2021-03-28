package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.toPoint

object ArrDef : Rule {

    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 2) return null

        if (lexems.first().type != LexemType.SO_PARENTHESIS) return null

        if (lexems[1].type == LexemType.SC_PARENTHESIS) {
            return Node.Chain(
                lexems.subList(0, 2).map(Lexem::toPoint),
            )
        }


        var vItem = VItem.tryParse(lexems.subList(1, lexems.size)) ?: return null

        var lastIndex = lexems.indexOf(vItem.last())

        val nodes = mutableListOf(vItem)

        while (lastIndex + 1 in lexems.indices && lexems[lastIndex + 1].type == LexemType.COMMA) {
            nodes.add(lexems[lastIndex + 1].toPoint())

            vItem = VItem.tryParse(lexems.subList(lastIndex + 2, lexems.size)) ?: return null

            nodes.add(vItem)

            lastIndex = lexems.indexOf(vItem.last())
        }

        if (lexems[lastIndex + 1].type != LexemType.SC_PARENTHESIS) return null

        return Node.Chain(
            listOf(lexems.first().toPoint()) + nodes + listOf(lexems[lastIndex + 1].toPoint())
        )

    }
}