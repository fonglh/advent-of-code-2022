fun main() {
    val inputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

    // Full input start state
    fun initSupplyStacks(): List<ArrayDeque<Char>> {
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

        return supplyStacks
    }

    // Test start state
    fun initTestSupplyStacks(): List<ArrayDeque<Char>> {
        var supplyStacks = List<ArrayDeque<Char>>(3) {
            ArrayDeque()
        }
        supplyStacks[0].addAll(listOf('Z', 'N'))
        supplyStacks[1].addAll(listOf('M', 'C', 'D'))
        supplyStacks[2].addAll(listOf('P'))

        return supplyStacks
    }

    fun move(supplyStacks: List<ArrayDeque<Char>>, numToMove: Int, src: Int, dst: Int) {
        for(i in 1..numToMove) {
            supplyStacks[dst-1].addLast(supplyStacks[src-1].removeLast())
        }
    }

    fun movePartTwo(supplyStacks: List<ArrayDeque<Char>>, numToMove: Int, src: Int, dst: Int) {
        var tmpStack = ArrayDeque<Char>(numToMove)

        for(i in 1..numToMove) {
            tmpStack.addLast(supplyStacks[src-1].removeLast())
        }

        for(i in 1..numToMove) {
            supplyStacks[dst-1].addLast(tmpStack.removeLast())
        }
    }

    fun topCrates(supplyStacks: List<ArrayDeque<Char>>): String {
        val result = StringBuilder()
        for (stack in supplyStacks) {
            result.append(stack.lastOrNull())

        }
        return result.toString()
    }

    fun part1(supplyStacks: List<ArrayDeque<Char>>, input: List<String>): String {
        input.forEach {
            val (numToMove, src, dst) = inputLineRegex.matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            move(supplyStacks, numToMove.toInt(), src.toInt(), dst.toInt())
        }

        return topCrates(supplyStacks)
    }

    fun part2(supplyStacks: List<ArrayDeque<Char>>, input: List<String>): String {
        input.forEach {
            val (numToMove, src, dst) = inputLineRegex.matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            movePartTwo(supplyStacks, numToMove.toInt(), src.toInt(), dst.toInt())
        }

        return topCrates(supplyStacks)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(initTestSupplyStacks(), testInput) == "CMZ")
    check(part2(initTestSupplyStacks(), testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(initSupplyStacks(), input))
    println(part2(initSupplyStacks(), input))
}
