package lexical

import synt.Lexem

class SymbolTable {
	
	val names = mutableMapOf<String, Lexem>()
	val variables = mutableMapOf<String, Lexem>()
	val functions = mutableMapOf<String, Lexem>()
	val classes = mutableMapOf<String, Lexem>()
	val constants = mutableMapOf<String, Lexem>()
	
}