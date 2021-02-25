package lexic

class LexicalAnalyzerImpl : LexicalAnalyzer {
	
	private val variables = mutableListOf<String>()
	
	override fun analyze(lines: List<String> , table: SymbolTable): List<List<Lexem>>? {
		val result = mutableListOf<List<Lexem>>()
		var isTotalValid = true
		lines.map(::prepare).forEachIndexed { i, line ->
			if (line.isBlank()) {
				result.add(emptyList())
				return@forEachIndexed
			}
			val lexems = Lexemizer.leximizeLine(
				table, line
					.split(splitRegex)
//					.map {
//						it.trim()
//					}
					.filter(String::isNotEmpty)
			)
			isTotalValid = if (lexems != null) {
				isTotalValid and RuleSelector.selectRule(lexems, i, table).also {
					if (it) {
						result.add(lexems)
					}
				}
			} else {
				false
			}
		}
		return if (isTotalValid) {
			result
		} else {
			null
		}
	}
	
}

private fun withPattern(pattern: String) = "((?<=$pattern)|(?=$pattern))"

private val splitRegex = Regex(
	withPattern("(\\*\\*)|((?<=[a-zA-Z]i)\\.(?=[a-zA-Z]))|[+\\-*/)(=' :\\]\\[]")
)

private val variableReg = Regex("_*\\w+")

private fun prepare(line: String) = line
	.trimEnd()
	.replace("\t", INDENT)

private const val INDENT = "    "

private const val print = "print"