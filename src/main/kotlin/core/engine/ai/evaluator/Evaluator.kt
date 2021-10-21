package core.engine.ai.evaluator

import core.engine.Board
import core.engine.Node

interface Evaluator {

    fun evaluateState(state: Node, player: Int): Int
}