fun main() {
    fun signalStrength(cycle: Int, X: Int): Int {
        if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
            //println(Pair(cycle, X))
            return cycle * X
        }
        return 0
    }
    fun part1(input: List<String>): Int {
        var X = 1
        var cycle = 1
        var signalStrengthSum = 0

        input.forEach {
            if (it == "noop") {
                cycle++
                signalStrengthSum += signalStrength(cycle, X)
            } else {
                val valueToAdd = it.substringAfter(" ").toInt()
                cycle++
                signalStrengthSum += signalStrength(cycle, X)
                X += valueToAdd
                cycle++
                signalStrengthSum += signalStrength(cycle, X)
            }
        }
        return signalStrengthSum
    }

    fun printCRT(crt: Array<Char>) {
        for (row in 0 until 6) {
            val line = StringBuilder()
            for (col in 0 until 40) {
                line.append(crt[row*40+col])
            }
            println(line)
        }
    }

    fun draw(crt: Array<Char>, cycle: Int, X: Int) {
        val colNum = (cycle-1) % 40
        if((colNum) in X-1..X+1) {
            crt[cycle-1] = '#'
        }
    }

    fun part2(input: List<String>) {
        var X = 1
        var cycle = 1
        var crt = Array(240) { '.' }

        // To draw first pixel in top left corner
        draw(crt, cycle, X)
        input.forEach {
            if (it == "noop") {
                cycle++
                draw(crt, cycle, X)
            } else {
                val valueToAdd = it.substringAfter(" ").toInt()
                cycle++
                draw(crt, cycle, X)
                X += valueToAdd
                cycle++
                draw(crt, cycle, X)
            }
        }
        printCRT(crt)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}