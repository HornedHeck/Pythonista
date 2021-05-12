package executor

import syntax.Node

class Classifier {

    fun classify(node: Node): NodeClass {
        return NodeClass.IGNORE
    }

}