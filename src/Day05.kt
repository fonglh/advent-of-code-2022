fun main() {
    fun move(supplyStacks: List<ArrayDeque<Char>>, numToMove: Int, src: Int, dst: Int) {
        for(i in 1..numToMove) {
            supplyStacks[dst-1].addLast(supplyStacks[src-1].removeLast())
        }
    }

    fun topCrates(supplyStacks: List<ArrayDeque<Char>>): String {
        val result = StringBuilder()
        for (stack in supplyStacks) {
            result.append(stack.lastOrNull())

        }
        return result.toString()
    }

    fun part1(input: List<String>): String {
        val inputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

        // Test start state
//        var supplyStacks = List<ArrayDeque<Char>>(3) {
//            ArrayDeque()
//        }
//        supplyStacks[0].addAll(listOf('Z', 'N'))
//        supplyStacks[1].addAll(listOf('M', 'C', 'D'))
//        supplyStacks[2].addAll(listOf('P'))

        // Full input start state
        var supplyStacks = List<ArrayDeque<Char>>(9) {
            ArrayDeque()
        }
        supplyStacks[0].addAll(listOf('V', 'C', 'D', 'R', 'Z', 'G', 'B', 'W'))
        supplyStacks[1].addAll(listOf('G', 'W', 'F', 'C', 'B', 'S', 'T', 'V'))
        supplyStacks[2].addAll(listOf('C', 'B', 'S', 'N', 'W'))
        supplyStacks[3].addAll(listOf('Q', 'G', 'M', 'N', 'J', 'V', 'C', 'P'))
        supplyStacks[4].addAll(listOf('T', 'S', 'L', 'F', 'D', 'H', 'B'))
        supplyStacks[5].addAll(listOf('J', 'V', 'T', 'W', 'M', 'N'))
        supplyStacks[6].addAll(listOf('P', 'F', 'L', 'C', 'S', 'T', 'G'))
        supplyStacks[7].addAll(listOf('B', 'D', 'Z'))
        supplyStacks[8].addAll(listOf('M', 'N', 'Z', 'W'))

        input.forEach {
            val (numToMove, src, dst) = inputLineRegex.matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            move(supplyStacks, numToMove.toInt(), src.toInt(), dst.toInt())
        }

        return topCrates(supplyStacks)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    //check(part1(testInput) == "CMZ")
    //check(part2(testInput) == 4)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
