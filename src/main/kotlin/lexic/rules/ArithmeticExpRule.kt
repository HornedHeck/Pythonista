package lexic.rules

import lexic.Lexem
import lexic.LexemType

object ArithmeticExpRule : Rule {
	
	override fun match(lexems: List<Lexem>, lineNum: Int): Boolean {
		if (lexems.size.rem(2) == 0) {
			lexems.last().errorAfter(lineNum)
			return false
		}
		lexems.forEach {
			if (it.type !in arithmeticList) {
				it.errorAt(lineNum)
				return false
			}
		}
		return true
	}
	
	
	private val arithmeticList = listOf(
		LexemType.VARIABLE, LexemType.ARITHMETIC_OPERATOR, LexemType.NUMBER_CONSTANT
	)
}