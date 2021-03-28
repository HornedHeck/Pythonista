package syntax.rule

import lexical.Lexem
import syntax.Node

interface Rule {

    fun tryParse(lexems: List<Lexem>): Node?

}