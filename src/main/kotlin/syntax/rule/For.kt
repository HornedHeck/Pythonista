package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.toPoint

object For : Rule {


    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 5) return null

        if (lexems.first().key != "for") return null

        if (lexems.last().type != LexemType.COLON) return null

        if (lexems[1].type != LexemType.RAW_NAME) return null

        if (lexems[2].key != "in") return null

        val vItem = if (lexems.size == 5) {
            lexems[3].takeIf { it.type == LexemType.VARIABLE }?.toPoint()
        } else {
            val toParse = lexems.subList(3, lexems.size - 1)
            ArrDef.tryParse(toParse) ?: ArrIndex.tryParse(toParse) ?: FCall.tryParse(toParse)
        } ?: return null

        return Node.Chain(
            lexems.take(3).map(Lexem::toPoint).toList() + listOf(
                vItem, lexems.last().toPoint()
            )
        )
    }


}