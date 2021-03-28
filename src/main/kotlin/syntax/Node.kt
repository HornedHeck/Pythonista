package syntax

import lexical.Lexem

sealed class Node {

    abstract fun last(): Lexem

    data class Point(
        val lexem: Lexem
    ) : Node() {

        override fun last() = lexem

    }

    data class Chain(
        val nodes: List<Node>
    ) : Node() {

        override fun last() = nodes.last().last()

    }

}

fun Lexem.toPoint() = Node.Point(
    this
)