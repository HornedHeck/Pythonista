package lexic

enum class LexemType {
	VARIABLE, CLASS, FUNCTION,
	BOOL_CONSTANT, STRING_CONSTANT, NUMBER_CONSTANT,
	ARITHMETIC_OPERATOR, BOOL_OPERATOR,
	RAW_NAME,
	RO_PARENTHESIS, RC_PARENTHESIS,
	SO_PARENTHESIS, SC_PARENTHESIS,
	QUOTE, COLON, SPACE,
	KEYWORD,
	ASSIGNMENT,
	NONE,
	UNKNOWN
}

data class Lexem(
	val type: LexemType,
	val key: String
) {
	
	fun errorAt(line: Int) {
		println("Error in ${line + 1} at '$key'")
	}
	
	fun errorAfter(line: Int) {
		println("Error in ${line + 1} after '$key'")
	}
	
	
	fun errorBefore(line: Int) {
		println("Error in ${line + 1} before '$key'")
	}
	
}