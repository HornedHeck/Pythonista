package lexic.rules

import lexic.Lexem
import lexic.LexemType

private const val AND_KEY = "and"
private const val OR_KEY = "and"

object BoolExpRule : Rule {
	
	override fun match(lexems: List<Lexem>, lineNum: Int): Boolean {
		var c = 0
		var pC = 0
		val groups = mutableListOf<List<Lexem>>()
		for (i in lexems.indices) {
			if (lexems[i].key == AND_KEY || lexems[i].key == OR_KEY) {
				groups.add(lexems.subList(pC, c))
				pC = c + 1
				c += 1
			} else {
				c += 1
			}
		}
		if (pC < lexems.size) {
			groups.add(lexems.subList(pC, c))
		}
		
		if (lexems.size > c) {
			lexems[c].errorAt(lineNum)
			return false
		}
		
		for (e in groups) {
			
			if (e.size == 1) {
				return if (e.first().type == LexemType.BOOL_CONSTANT || e.first().type == LexemType.VARIABLE) {
					true
				} else {
					e.first().errorAt(lineNum)
					false
				}
			}
			if (e.size == 2) {
				e.last().errorAfter(lineNum)
				return false
			}
			
			if (e.none { it.type == LexemType.BOOL_OPERATOR }) {
				e.last().errorAfter(lineNum)
				return false
			}
			
			val boIndex = e.indexOfFirst { it.type == LexemType.BOOL_OPERATOR }
			
			if (boIndex == 0) {
				e.first().errorBefore(lineNum)
				return false
			}
			
			if (boIndex != e.indexOfLast { it.type == LexemType.BOOL_OPERATOR }) {
				e.last { it.type == LexemType.BOOL_OPERATOR }.errorAt(lineNum)
				return false
			}
			
			if (boIndex == e.size - 1) {
				e.last().errorAfter(lineNum)
				return false
			}
			
			val before = e.subList(0, boIndex)
			val after = e.subList(boIndex + 1, e.size)
			if (!ArithmeticExpRule.match(before , lineNum)){
				before.last().errorAfter(lineNum)
				return false
			}
			
			if (!ArithmeticExpRule.match(after , lineNum)){
				after.last().errorAfter(lineNum)
				return false
			}
		}
		
		return true
	}
}

fun main() {
	val tl1 = listOf(
		Lexem(LexemType.NUMBER_CONSTANT, "5"),
		Lexem(LexemType.ARITHMETIC_OPERATOR, "+"),
		Lexem(LexemType.NUMBER_CONSTANT, "5"),
		Lexem(LexemType.BOOL_OPERATOR, "=="),
		Lexem(LexemType.NUMBER_CONSTANT, "10"),
	)
	val tl2 = tl1 + listOf(
		Lexem(LexemType.BOOL_OPERATOR, "and"),
		Lexem(LexemType.NUMBER_CONSTANT, "2"),
		Lexem(LexemType.ARITHMETIC_OPERATOR, "-"),
		Lexem(LexemType.NUMBER_CONSTANT, "2"),
		Lexem(LexemType.BOOL_OPERATOR, "<="),
		Lexem(LexemType.ARITHMETIC_OPERATOR, "10"),
	)
	val tl3 = tl2 + listOf(
		Lexem(LexemType.BOOL_OPERATOR, "or"),
	)
	println(BoolExpRule.match(tl2, 3))
}