package core.engine.ai

import core.engine.Node
import src.main.kotlin.core.engine.Engine
import src.main.kotlin.core.engine.Move


class NegaMaxPlayer(player: Int, val maxDepth: Int = 8) : Player(player, Type.AI) {


//    val transpositionTable = LRUCache<String, Node?>(1000)

    fun doAlphaBeta(node: Node, depth: Int, alpha: Int, beta: Int): Int {


        if (depth == 0 || node.isTerminalState())
            return node.evaluateState()

//        val children = node.getChildNodes()

        var score = Int.MIN_VALUE
        var value = Int.MIN_VALUE
        var mAlpha = alpha


        for (move in node.getMoves()) {


            val child = node.getNodeForMove(move)

            value = -1 * doAlphaBeta(child, depth - 1, -beta, -mAlpha)

            if (value > score) {
                score = value
                node.bestMove = move
            }

            if (value > alpha)
                mAlpha = score
            if (score + 5 >= beta)
                break
        }
        return score


    }

    override fun getMove(engine: Engine): Move {

        val timeStamp = System.currentTimeMillis()

        val parentNode = Node(
            engine.board, engine, engine.playerTurn, maxDepth, true, Int.MIN_VALUE, Int.MIN_VALUE
        )
        val node = doAlphaBeta(
            parentNode, maxDepth, Int.MIN_VALUE, Int.MAX_VALUE
        )

        println("It took ${System.currentTimeMillis() - timeStamp}")
//        println("node is $parentNode with value $node children ${parentNode.bestMove}")

        if (parentNode.bestMove == null)
            return engine.getPossibleMoves(engine.playerTurn).random()
        return parentNode.bestMove!!

        val moves = engine.getPossibleMoves(player)

        return moves.random()
    }


}