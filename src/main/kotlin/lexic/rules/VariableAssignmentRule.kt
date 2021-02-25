package lexic.rules

import lexic.Lexem
import lexic.LexemType

object VariableAssignmentRule : Rule {
	override fun match(lexems: List<Lexem>, lineNum: Int): Boolean {
		if(lexems.size < 3){
			lexems.last().errorAfter(lineNum)
			return false
		}
		val exp = lexems.subList(2, lexems.size)
		if (exp.size == 1) {
			return if (exp.first().type in listOf(
					LexemType.BOOL_CONSTANT,
					LexemType.VARIABLE,
					LexemType.NUMBER_CONSTANT,
					LexemType.STRING_CONSTANT,
					LexemType.NONE
				)
			) {
				true
			} else {
				exp.first().errorAt(lineNum)
				false
			}
		}
		
		if (exp.size == 2) {
			exp.last().errorAfter(lineNum)
		}
		
		exp.firstOrNull { it.type == LexemType.RAW_NAME }?.run {
			errorAt(lineNum)
			return false
		}
		
		if (lexems.any { it.type == LexemType.BOOL_OPERATOR }) {
			return BoolExpRule.match(exp, lineNum)
		}
		
		return ArithmeticExpRule.match(exp, lineNum)
	}
}