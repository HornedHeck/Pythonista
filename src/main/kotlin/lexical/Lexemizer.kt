package lexical

import synt.Lexem
import synt.LexemType

object Lexemizer {
	
	fun leximizeLine(table: SymbolTable, line: List<String>, lineNumber: Int) = if (
		line.first().trimStart().startsWith("#")
	) {
		// Comment line
		emptyList()
	} else {
		line.map {
			Lexem(
				key = it,
				type = when {
					it == "." -> LexemType.DOT
					it == "," -> LexemType.COMMA
					it == "[" -> LexemType.SO_PARENTHESIS
					it == "]" -> LexemType.SC_PARENTHESIS
					it == "(" -> LexemType.RO_PARENTHESIS
					it == ")" -> LexemType.RC_PARENTHESIS
					it == "'" -> LexemType.QUOTE
					it == "=" -> LexemType.ASSIGNMENT
					it == " " -> LexemType.SPACE
					it == ":" -> LexemType.COLON
					it in keywords -> LexemType.KEYWORD
					it.matches(numberRegex) -> LexemType.NUMBER_CONSTANT
					it in arithmeticOperators -> LexemType.ARITHMETIC_OPERATOR
					it in boolOperators -> LexemType.BOOL_OPERATOR
					it in boolConstants -> LexemType.BOOL_CONSTANT
					it.matches(nameRegex) -> LexemType.RAW_NAME
					else -> LexemType.UNKNOWN
				}
			)
		}
			.concatStrings(table)
			.asSequence()
			.proceedConstants(table)
			.proceedNames(table)
			.proceedUnknowns(lineNumber)
			.toList()
	}
	
	
	private fun List<Lexem>.concatStrings(table: SymbolTable): List<Lexem> {
		val indices = mapIndexed { i, l -> i to l }.filter { it.second.type == LexemType.QUOTE }.map { it.first }
		if (indices.size.rem(2) != 0) {
			return listOf(
				Lexem(
					LexemType.UNKNOWN,
					joinToString("") { it.key }
				)
			)
		}
		var pI = 0
		val result = mutableListOf<Lexem>()
		for (i in indices.indices step 2) {
			result.addAll(subList(pI, indices[i]).filter { it.type != LexemType.SPACE })
			result.add(
				Lexem(
					LexemType.STRING_CONSTANT,
					subList(indices[i], indices[i + 1] + 1).joinToString("") { it.key }
				).also {
					if (!table.constants.containsKey(it.key)) {
						table.constants[it.key] = it
					}
				}
			)
			pI = indices[i + 1] + 1
		}
		if (pI < lastIndex) {
			result.addAll(subList(pI, size).filter { it.type != LexemType.SPACE })
		}
		return result.toList()
	}
	
	private val constTypes = listOf(LexemType.STRING_CONSTANT, LexemType.NUMBER_CONSTANT, LexemType.BOOL_CONSTANT)
	
	private fun Sequence<Lexem>.proceedConstants(table: SymbolTable) = map {
		if (it.type in constTypes && !table.constants.containsKey(it.key)) {
			table.constants[it.key] = it
		}
		it
	}
	
	private fun Sequence<Lexem>.proceedNames(table: SymbolTable) = map {
		if (it.type == LexemType.RAW_NAME && !table.names.containsKey(it.key)) {
			table.names[it.key] = it
		}
		it
	}
	
	private fun Sequence<Lexem>.proceedUnknowns(line: Int) = map {
		if (it.type == LexemType.UNKNOWN) {
			it.errorAt(line)
		}
		it
	}
	
	private val numberRegex = Regex("([0-9]+\\.[0-9]+)|[0-9]+")
	private val arithmeticOperators = listOf(
		"+", "-", "*", "/", "**", "%"
	)
	private val boolOperators = listOf(
		"==", "!=", "<=", ">=", "<", ">", "and", "or"
	)
	
	private val nameRegex = Regex("_*[a-zA-Z][a-zA-Z0-9_]*")
	
	private val boolConstants = listOf("True", "False")
	
	private val keywords = listOf(
		"await", "else", "import", "pass",
		"None", "break", "except", "in", "raise",
		"class", "finally", "is", "return",
		"continue", "for", "lambda", "try",
		"as", "def", "from", "nonlocal", "while",
		"assert", "del", "global", "not", "with",
		"async", "elif", "if", "yield"
	)
}