fun main() {
    fun part1(input: List<String>): Int {
        var score = 0
        input.forEach {
            var moves = it.split(" ")
            if (isDraw(moves[0], moves[1])) {
                score += 3
            }
            if (doesPlayerWin(moves[0], moves[1])) {
                score += 6
            }
            score += playerShapeScore(moves[1])
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        input.forEach {
            var moves = it.split(" ")
            var opponentMove = moves[0]
            var desiredOutcome = moves[1]

            var playerMove = getPlayerMove(opponentMove, desiredOutcome)
            score += when(desiredOutcome) {
                "Y" -> 3
                "Z" -> 6
                else -> 0
            }
            score += playerShapeScore(playerMove)
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

// part 1
fun isDraw(opponentMove: String, playerMove: String): Boolean {
    return (opponentMove == "A" && playerMove == "X") ||
            (opponentMove == "B" && playerMove == "Y") ||
            (opponentMove == "C" && playerMove == "Z")
}

// part 1
fun doesPlayerWin(opponentMove: String, playerMove: String): Boolean {
    return (opponentMove == "A" && playerMove == "Y") ||
            (opponentMove == "B" && playerMove == "Z") ||
            (opponentMove == "C" && playerMove == "X")
}

fun getPlayerMove(opponentMove: String, desiredOutcome: String): String {
    return when (desiredOutcome) {
        // lose
        "X" -> {
            when(opponentMove) {
                "A" -> "Z"
                "B" -> "X"
                "C" -> "Y"
                else -> "invalid"
            }
        }
        // draw
        "Y" -> {
            when(opponentMove) {
                "A" -> "X"
                "B" -> "Y"
                "C" -> "Z"
                else -> "invalid"
            }
        }
        // win
        "Z" -> {
            when(opponentMove) {
                "A" -> "Y"
                "B" -> "Z"
                "C" -> "X"
                else -> "invalid"
            }
        }
        else -> {
            // will never reach here
            "invalid"
        }
    }
}

fun playerShapeScore(playerMove: String): Int {
    return when(playerMove) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> 0
    }
}
