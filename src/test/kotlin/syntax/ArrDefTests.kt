package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.ArrDef

class ArrDefTests {

    @Test
    fun `Test EMPTY ARR DEF`() {
        val lexems = listOf(
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
        )

        val res = ArrDef.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test SINGLE CONST ARR DEF`() {
        val lexems = listOf(
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
        )

        val res = ArrDef.tryParse(lexems)

        assertNotNull(res)
    }


    @Test
    fun `Test MULTIPLE CONST ARR DEF`() {
        val lexems = listOf(
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.COMMA, ","),
            Lexem(LexemType.NUMBER_CONSTANT, "2"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
        )

        val res = ArrDef.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test CONST ARR ARR DEF`() {
        val lexems = listOf(
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.COMMA, ","),
            Lexem(LexemType.SO_PARENTHESIS, "["),
            Lexem(LexemType.NUMBER_CONSTANT, "1"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
            Lexem(LexemType.SC_PARENTHESIS, "]"),
        )

        val res = ArrDef.tryParse(lexems)

        assertNotNull(res)
    }

}