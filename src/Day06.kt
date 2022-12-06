fun main() {
    fun part1(input: List<String>): Int {
        val dataStream = input[0]
        for (i in 3..dataStream.length) {
            val last4CharSet = dataStream.slice(i-3..i).toSet()
            if (last4CharSet.size == 4) {
                return i + 1
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val dataStream = input[0]
        for (i in 13..dataStream.length) {
            val last14CharSet = dataStream.slice(i-13..i).toSet()
            if (last14CharSet.size == 14) {
                return i + 1
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
