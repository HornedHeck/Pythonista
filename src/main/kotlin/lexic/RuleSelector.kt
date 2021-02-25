package lexic

import lexic.LexemType.COLON
import lexic.rules.*

object RuleSelector {
	
	fun selectRule(lexems: List<Lexem>, line: Int, table: SymbolTable) = when {
		lexems.any { it.type == LexemType.SPACE } -> {
			throw IllegalArgumentException(lexems.toString())
		}
		lexems.any { it.type == LexemType.UNKNOWN } -> {
			lexems.first { it.type == LexemType.UNKNOWN }.errorAt(line)
			false
		}
		lexems.first().run { type == LexemType.VARIABLE || type == LexemType.RAW_NAME }
				&& lexems[1].type == LexemType.ASSIGNMENT -> VariableAssignmentRule.match(
			lexems,
			line
		).also {
			if (it && !table.variables.containsKey(lexems.first().key)) {
				table.variables[lexems.first().key] = lexems.first().copy(type = LexemType.VARIABLE)
			}
		}
		lexems.first().key == "def"
				&& lexems.size > 3
				&& lexems[1].type == LexemType.RAW_NAME
				&& lexems[2].type == LexemType.RO_PARENTHESIS
				&& lexems[lexems.lastIndex - 1].type == LexemType.RC_PARENTHESIS
				&& lexems.last().type == COLON -> DefRule.match(lexems, line).also {
			if (it && !table.functions.containsKey(lexems.first().key)) {
				table.functions[lexems.first().key] = lexems.first()
			}
		}
		lexems.first().key == "class"
				&& lexems.size > 3
				&& lexems[1].type == LexemType.RAW_NAME
				&& lexems[2].type == LexemType.RO_PARENTHESIS
				&& lexems[lexems.lastIndex - 1].type == LexemType.RC_PARENTHESIS
				&& lexems.last().type == COLON -> ClassRule.match(lexems, line).also {
			if (it && !table.classes.containsKey(lexems.first().key)) {
				table.classes[lexems.first().key] = lexems.first()
			}
		}
		lexems.first().key in listOf("if", "elif") && lexems.last().type == COLON && lexems.size > 2 -> {
			BoolExpRule.match(lexems.subList(1, lexems.size - 1), line)
		}
		lexems.first().run {
			type == LexemType.FUNCTION || type == LexemType.CLASS
		}
				&& lexems[1].type == LexemType.RO_PARENTHESIS
				&& lexems.size > 2
				&& lexems.any { it.type == LexemType.RC_PARENTHESIS } -> CallRule.match(lexems, line)
		lexems.isNotEmpty() -> {
			println("Warning at ${line + 1} - unused expression")
			true
		}
		else -> false
	}
	
}