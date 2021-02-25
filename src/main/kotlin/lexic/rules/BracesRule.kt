package lexic.rules

import lexic.Lexem

object BracesRule : Rule {
	
	override fun match(lexems: List<Lexem>, lineNum: Int) =
		lexems.count { it.key == "(" } == lexems.count { it.key == ")" }
}