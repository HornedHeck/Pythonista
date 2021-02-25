package lexic.rules

import lexic.Lexem

object FunctionCallRule : Rule {
	
	override fun match(lexems: List<Lexem>, lineNum: Int): Boolean {
		return false
	}
}