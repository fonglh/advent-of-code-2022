fun main() {
    fun getPriority(input: Char): Int {
        return if (input.isUpperCase()) {
            input.code - 64 + 26
        } else {
            input.code - 96
        }
    }
    fun part1(input: List<String>): Int {
        var prioritySum = 0

        input.forEach {
            val midIndex = it.length / 2
            val firstCompartment = it.substring(0, midIndex)
            val secondCompartment = it.substring(midIndex)
            val firstSet = firstCompartment.toCharArray().toSet()
            val secondSet = secondCompartment.toCharArray().toSet()

            val commonChar = firstSet.intersect(secondSet).elementAt(0)
            prioritySum += getPriority(commonChar)
        }
        return prioritySum
    }

    fun part2(input: List<String>): Int {
        var prioritySum = 0
        var commonCharSet: Set<Char> = emptySet()

        input.forEachIndexed { index, it ->
            if (index % 3 == 0) {
                commonCharSet = it.toCharArray().toSet()
            }
            commonCharSet = commonCharSet.intersect(it.toCharArray().toSet())

            if ((index+1) % 3 == 0) {
                val commonChar = commonCharSet.elementAt(0)
                prioritySum += getPriority(commonChar)
            }
        }
        return prioritySum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
