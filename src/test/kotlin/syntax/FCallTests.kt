package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.FCall

class FCallTests {

    @Test
    fun `Test FUNCTION`() {
        val lexems = listOf(
            Lexem(LexemType.FUNCTION, "len"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RC_PARENTHESIS, ")")
        )

        val res = FCall.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test FUNCTION ARG1`() {
        val lexems = listOf(
            Lexem(LexemType.FUNCTION, "len"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.VARIABLE, "a"),
            Lexem(LexemType.RC_PARENTHESIS, ")")
        )

        val res = FCall.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test FUNCTION FUNCTION`() {
        val lexems = listOf(
            Lexem(LexemType.FUNCTION, "len"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.FUNCTION, "input"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RC_PARENTHESIS, ")"),
            Lexem(LexemType.RC_PARENTHESIS, ")")
        )

        val res = FCall.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test FUNCTION ARG1 , ARG2`() {
        val lexems = listOf(
            Lexem(LexemType.FUNCTION, "len"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.VARIABLE, "a"),
            Lexem(LexemType.COMMA, ","),
            Lexem(LexemType.VARIABLE, "b"),
            Lexem(LexemType.RC_PARENTHESIS, ")")
        )

        val res = FCall.tryParse(lexems)

        assertNotNull(res)
    }

}