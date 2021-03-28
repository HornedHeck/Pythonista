package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.Node.Chain

object FCall : Rule {

    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 3) return null
        if (lexems[0].type != LexemType.FUNCTION) return null
        if (lexems[1].type != LexemType.RO_PARENTHESIS) return null

        if (lexems[2].type == LexemType.RC_PARENTHESIS) {
            return Chain(
                listOf(
                    Node.Point(lexems[0]),
                    Node.Point(lexems[1]),
                    Node.Point(lexems[2])
                )
            )
        }

        val vItemRes = VItem.tryParse(lexems.subList(2, lexems.size)) ?: return null
        var lastIndex = lexems.indexOf(vItemRes.last())

        if (lastIndex + 1 !in lexems.indices) return null

        if (lexems[lastIndex + 1].type != LexemType.COMMA) {

            if (lexems[lastIndex + 1].type != LexemType.RC_PARENTHESIS) return null

            return Chain(
                listOf(
                    Node.Point(lexems[0]),
                    Node.Point(lexems[1]),
                    vItemRes,
                    Node.Point(lexems[lastIndex + 1])
                )
            )
        }

        val nodes = mutableListOf(vItemRes)



        while (lastIndex in lexems.indices && lexems[lastIndex + 1].type == LexemType.COMMA) {
            nodes.add(Node.Point(lexems[lastIndex + 1]))

            val vItem = VItem.tryParse(lexems.subList(lastIndex + 2, lexems.size)) ?: return null
            nodes.add(vItem)
            lastIndex = lexems.indexOf(vItem.last())
        }

        if (lexems[lastIndex + 1].type != LexemType.RC_PARENTHESIS) return null

        return Chain(
            listOf(
                Node.Point(lexems[0]),
                Node.Point(lexems[1])
            ) + nodes + listOf(Node.Point(lexems[lastIndex + 1]))
        )

    }
}