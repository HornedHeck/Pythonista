package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.toPoint

object VItem : Rule {

    private val singleLexemTypes = listOf(
        LexemType.VARIABLE,
        LexemType.BOOL_CONSTANT,
        LexemType.STRING_CONSTANT,
        LexemType.NUMBER_CONSTANT
    )

    override fun tryParse(lexems: List<Lexem>): Node? {
        return FCall.tryParse(lexems)
            ?: ArrIndex.tryParse(lexems)
            ?: ArrDef.tryParse(lexems)
            ?: ArithmeticExp.tryParse(lexems)
            ?: if (lexems.first().type in singleLexemTypes) {
                lexems.first().toPoint()
            } else {
                null
            }
    }
}