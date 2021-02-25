package lexic

class SymbolTable {
	
	val variables = mutableMapOf<String, Lexem>()
	val functions = mutableMapOf<String, Lexem>()
	val classes = mutableMapOf<String, Lexem>()
	val constants = mutableMapOf<String, Lexem>()
	
}