package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.ArrIndex

class ArrIndexTests {

    @Test
    fun `Test VARIABLE CONSTANT parsing`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "array"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]")
        )

        val res = ArrIndex.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test VARIABLE FUNCTION parsing`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "array"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.FUNCTION, "len"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RC_PARENTHESIS, ")"),
            Lexem(LexemType.SC_PARENTHESIS, "]")
        )

        val res = ArrIndex.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test ARR_IND CONSTANT parsing`() {
        val lexems = listOf(
            Lexem(LexemType.VARIABLE, "array"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]")
        )

        val res = ArrIndex.tryParse(lexems)

        assertNotNull(res)
    }

}