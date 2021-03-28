package syntax

import lexical.Lexem
import lexical.LexemType
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import syntax.rule.ArithmeticExp

class ArithmeticExpTests {

    @Test
    fun `Test A + B`(){
        val lexems = listOf(
            Lexem(LexemType.VARIABLE , "a"),
            Lexem(LexemType.ARITHMETIC_OPERATOR , "+"),
            Lexem(LexemType.VARIABLE , "b"),
        )

        val res = ArithmeticExp.tryParse(lexems)

        assertNotNull(res)
    }

    @Test
    fun `Test (A + B) * C`(){
        val lexems = listOf(
            Lexem(LexemType.RO_PARENTHESIS , "("),
            Lexem(LexemType.VARIABLE , "a"),
            Lexem(LexemType.ARITHMETIC_OPERATOR , "+"),
            Lexem(LexemType.VARIABLE , "b"),
            Lexem(LexemType.RC_PARENTHESIS , ")"),
            Lexem(LexemType.ARITHMETIC_OPERATOR , "*"),
            Lexem(LexemType.VARIABLE , "c"),
        )

        val res = ArithmeticExp.tryParse(lexems)

        assertNotNull(res)
    }

}