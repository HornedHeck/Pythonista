package executor

import syntax.Node

class VariableDelegate {

    private val variables = mutableMapOf<String, Variable>()

    fun update(node : Node){

    }

    fun update(name: String, value: Any) {
        variables[name] = Variable(name, value)
    }

    fun get(name: String): Variable {
        return variables[name] ?: throw Exception("Var not initialized")
    }

}

data class Variable(
    val name: String,
    val value: Any
)