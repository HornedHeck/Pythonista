package syntax.rule

import lexical.Lexem
import lexical.LexemType
import syntax.Node
import syntax.toPoint

object VarAssignment : Rule {

    override fun tryParse(lexems: List<Lexem>): Node? {
        if (lexems.size < 3) return null

        val assignmentIndex = lexems.indexOfFirst { it.type == LexemType.ASSIGNMENT || it.type == LexemType.ADD_ASSIGNMENT }

        if (assignmentIndex < 1 || assignmentIndex >= lexems.size - 1) return null

        val assignmentItem = if (assignmentIndex == 1) {
            lexems.first()
                .takeIf { it.type == LexemType.VARIABLE || it.type == LexemType.RAW_NAME }
                ?.toPoint()
                ?: return null
        } else {
            ArrIndex.tryParse(lexems.subList(0, assignmentIndex)) ?: return null
        }

        val vItem = VItem.tryParse(lexems.subList(assignmentIndex + 1, lexems.size)) ?: return null

        return Node.Chain(
            listOf(assignmentItem, lexems[assignmentIndex].toPoint(), vItem)
        )
    }
}