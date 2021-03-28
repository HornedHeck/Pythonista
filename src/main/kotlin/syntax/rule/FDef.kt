package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.Node.Chain
import syntax.toPoint

object FDef : Rule {

    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 5) return null
        if (lexems[0].key != "def") return null
        if (lexems[1].type != LexemType.RAW_NAME) return null
        if (lexems[2].type != LexemType.RO_PARENTHESIS) return null
        if (lexems.last().type != LexemType.COLON) return null

//        def name():
        if (lexems[3].type == LexemType.RC_PARENTHESIS) {
            return if (lexems.size == 5) {
                Chain(lexems.map(Lexem::toPoint))
            } else {
                null
            }
        }

//        def name(arg1):
        var lastIndex = 3
        while (lastIndex in lexems.indices && lexems[lastIndex].type == LexemType.RAW_NAME) {
            lastIndex += 1
            if (lexems[lastIndex].type == LexemType.COMMA) {
                lastIndex += 1
            }
        }

        if (lexems[lastIndex].type != LexemType.RC_PARENTHESIS) return null
        if (lexems[lastIndex + 1].type != LexemType.COLON) return null

        return Chain(lexems.map(Lexem::toPoint))
    }
}