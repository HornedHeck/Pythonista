package lexical

class LexicalAnalyzerImpl : LexicalAnalyzer {
	
	override fun analyze(lines: List<String>, table: SymbolTable) = lines.asSequence()
		.map { prepare(it) }
		.mapIndexed { i, line ->
			if (line.isBlank()) {
				emptyList()
			} else {
				Lexemizer.leximizeLine(
					table,
					line
						.split(splitRegex)
						.filter(String::isNotEmpty),
					i
				)
			}
		}.toList()
}

private fun withPattern(pattern: String) = "((?<=$pattern)|(?=$pattern))"

private val splitRegex = Regex(
	withPattern("(\\*\\*)|((?<=[a-zA-Z_\\])])\\.(?=[a-zA-Z]))|[\\-*/)(' :\\]\\[,]|\\+(?!=)|=(?<!=)")
)

//val temp = Regex("(\\*\\*)|((?<=[a-zA-Z_\\])])\\.(?=[a-zA-Z]))|[\\-*/)(' :\\]\\[,]|\\+(?!=)|=(?<!=)")

private fun prepare(line: String) = line
	.trimEnd()
	.replace("\t", INDENT)

private const val INDENT = "    "