fun main() {
    fun part1(input: List<String>): Int {
        var maxCalories = 0
        var elfCalories = 0

        input.forEach {
            if(it.isNotBlank()) {
                elfCalories += it.toInt()
            } else {
                if (elfCalories > maxCalories) {
                    maxCalories = elfCalories
                }
                elfCalories = 0
            }
        }

        return maxCalories
    }

    fun part2(input: List<String>): Int {
        var elfCalories = 0
        var allElfCalories = mutableListOf<Int>()

        input.forEach {
            if(it.isNotBlank()) {
                elfCalories += it.toInt()
            } else {
                allElfCalories.add(elfCalories)
                elfCalories = 0
            }
        }
        return allElfCalories.sortedDescending().slice(0..2).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
