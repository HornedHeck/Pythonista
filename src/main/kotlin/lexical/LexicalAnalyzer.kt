package lexical

interface LexicalAnalyzer {
	
	fun analyze (lines : List<String> , table: SymbolTable) : List<List<Lexem>>?
	
}