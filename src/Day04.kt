fun main() {
    fun fullyContains(firstElf: List<Int>, secondElf: List<Int>): Boolean {
        val min1 = firstElf[0]
        val max1 = firstElf[1]
        val min2 = secondElf[0]
        val max2 = secondElf[1]

        return (min1 <= min2 && max1 >= max2) ||
                (min2 <= min1 && max2 >= max1)
    }

    fun overlaps(firstElf: List<Int>, secondElf: List<Int>): Boolean {
        val min1 = firstElf[0]
        val max1 = firstElf[1]
        val min2 = secondElf[0]
        val max2 = secondElf[1]

        // https://stackoverflow.com/a/3269471
        return min1 <= max2 && min2 <= max1
    }

    fun overlapsObvious(firstElf: List<Int>, secondElf: List<Int>): Boolean {
        val min1 = firstElf[0]
        val max1 = firstElf[1]
        val min2 = secondElf[0]
        val max2 = secondElf[1]

        return min2 in min1 .. max1 ||
                max2 in min1 .. max1 ||
                min1 in min2 .. max2 ||
                max1 in min2 .. max2
    }

    fun part1(input: List<String>): Int {
        var count = 0
        input.forEach {
            val elfAssignments = it.split(",")
            val firstElf = elfAssignments[0].split("-").map { element -> element.toInt() }
            val secondElf = elfAssignments[1].split("-").map { element -> element.toInt() }
            if (fullyContains(firstElf, secondElf)) {
                count += 1
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        var count = 0
        input.forEach {
            val elfAssignments = it.split(",")
            val firstElf = elfAssignments[0].split("-").map { element -> element.toInt() }
            val secondElf = elfAssignments[1].split("-").map { element -> element.toInt() }
            if (overlaps(firstElf, secondElf)) {
                count += 1
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
