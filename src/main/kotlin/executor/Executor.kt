package executor

import syntax.Node
import java.util.*

class Executor {

    private val classifier = Classifier()
    private val scanner = Scanner(System.`in`)
    private val variableDelegate = VariableDelegate()
    private val functionDelegate = FunctionDelegate(variableDelegate)


    fun execute(program: List<Node>, step: Boolean = false) {
        program.forEach {
            when (classifier.classify(it)) {
                NodeClass.F_DEF -> functionDelegate.add(it)
                NodeClass.F_CAL -> functionDelegate.call(it)
                NodeClass.VAR_ASSIGN -> variableDelegate.update(it)
                NodeClass.LOOP -> execute(((it as Node.Chain).nodes[2] as Node.Chain).nodes)
                NodeClass.IGNORE -> {
//                    No action (comments etc)
                }
            }
        }
    }

}

enum class NodeClass {
    F_DEF,
    F_CAL,
    VAR_ASSIGN,
    LOOP,
    IGNORE
}