package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.FDef

class FDefTests {

    @Test
    fun `Test def name()`() {
        val lexems = listOf(
            Lexem(LexemType.KEYWORD, "def"),
            Lexem(LexemType.RAW_NAME, "name"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RC_PARENTHESIS, ")"),
            Lexem(LexemType.COLON, ":")
        )

        val res = FDef.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test def name(arg1)`() {
        val lexems = listOf(
            Lexem(LexemType.KEYWORD, "def"),
            Lexem(LexemType.RAW_NAME, "name"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RAW_NAME, "arg1"),
            Lexem(LexemType.RC_PARENTHESIS, ")"),
            Lexem(LexemType.COLON, ":")
        )

        val res = FDef.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test def name(arg1 , arg2)`() {
        val lexems = listOf(
            Lexem(LexemType.KEYWORD, "def"),
            Lexem(LexemType.RAW_NAME, "name"),
            Lexem(LexemType.RO_PARENTHESIS, "("),
            Lexem(LexemType.RAW_NAME, "arg1"),
            Lexem(LexemType.COMMA, ","),
            Lexem(LexemType.RAW_NAME, "arg2"),
            Lexem(LexemType.RC_PARENTHESIS, ")"),
            Lexem(LexemType.COLON, ":")
        )

        val res = FDef.tryParse(lexems)

        assertNotNull(res)
    }

}