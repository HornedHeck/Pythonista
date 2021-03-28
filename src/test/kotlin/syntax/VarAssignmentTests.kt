package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.VarAssignment

class VarAssignmentTests {

    @Test
    fun `VARIABLE ASSIGN CONST`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "a"),
            Lexem(LexemType.ASSIGNMENT, "="),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
        )

        val res = VarAssignment.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `ARRAY ITEM ASSIGN CONST`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "a"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.ASSIGNMENT, "="),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
        )

        val res = VarAssignment.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `MATRIX ITEM ASSIGN CONST`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "a"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.ASSIGNMENT, "="),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
        )

        val res = VarAssignment.tryParse(lexems)

        assertNotNull(res)
    }

}