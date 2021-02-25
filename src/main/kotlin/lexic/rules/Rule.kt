package lexic.rules

import lexic.Lexem

interface Rule {
	
	fun match(lexems: List<Lexem>, lineNum: Int): Boolean
	
}