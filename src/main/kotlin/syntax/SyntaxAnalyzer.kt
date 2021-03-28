package syntax

import lexical.Lexem
import lexical.LexemType
import lexical.SymbolTable
import syntax.rule.FCall
import syntax.rule.FDef
import syntax.rule.For
import syntax.rule.VarAssignment

object SyntaxAnalyzer {

    fun analyze(lexems: List<List<Lexem>>, table: SymbolTable): List<Node> {

        val nodes = mutableListOf<Node>()

        lexems.forEach { list ->

            val mapped = list.applyTable(table)

            val varAssignmentRes = VarAssignment.tryParse(mapped)
            varAssignmentRes?.let {
                nodes.add(it)
                (it as Node.Chain).nodes.first()
            }?.also {
                val lexem = (it as Node.Point).lexem
                moveToVariable(lexem, table)
                return@forEach
            }

            val fDefRes = FDef.tryParse(mapped)
            if (fDefRes != null) {
                val fName = mapped[1]
                table.names.remove(fName.key)
                table.functions[fName.key] = fName
                mapped.subList(3, mapped.size)
                    .filter { it.type == LexemType.RAW_NAME }
                    .forEach { moveToVariable(it, table) }
                return@forEach
            }

            val forRes = For.tryParse(mapped)
            if (forRes != null) {
                moveToVariable(mapped[1], table)
                return@forEach
            }

            val fCallRes = FCall.tryParse(mapped)
            if (fCallRes != null && fCallRes.last() == mapped.last()) {
                return@forEach
            }

            println(
                "Error at : ${list.joinToString(" ") { it.key }}"
            )
        }
        return nodes
    }

    private fun List<Lexem>.applyTable(table: SymbolTable): List<Lexem> = map {
        if (it.type != LexemType.RAW_NAME) {
            it
        } else {
            when {
                table.functions.containsKey(it.key) -> it.copy(type = LexemType.FUNCTION)
                table.variables.containsKey(it.key) -> it.copy(type = LexemType.VARIABLE)
                else -> it
            }
        }
    }

    private fun moveToVariable(lexem: Lexem, table: SymbolTable) {
        table.names.remove(lexem.key)
        table.variables[lexem.key] = lexem
    }

}