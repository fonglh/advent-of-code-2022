fun main() {
    data class InputNumber(val originalIndex: Int, val value: Int)

    fun numberAt(input: List<InputNumber>, positionFromZero: Int): Int {
        // find position of 0
        var zeroIndex = 0
        for (i in input.indices) {
            if (input[i].value == 0) {
                zeroIndex = i
                break
            }
        }
        return input[(zeroIndex+positionFromZero)%input.size].value
    }

    fun part1(input: List<String>): Int {
        val inputNumbers = input.mapIndexed { idx, value -> InputNumber(idx, value.toInt()) }
        val inputSize = inputNumbers.size
        var outputNumbers = inputNumbers.toMutableList()
        inputNumbers.forEach {
            val currIndex = outputNumbers.indexOf(it)
            var newIndex = (currIndex + it.value)
            while (newIndex <= 0) {
                newIndex += (inputSize-1)
            }
            while (newIndex > inputSize) {
                newIndex -= (inputSize-1)
            }
            outputNumbers.removeAt(currIndex)
            outputNumbers.add(newIndex, it)
        }

        return numberAt(outputNumbers, 1000) +
                numberAt(outputNumbers, 2000) +
                numberAt(outputNumbers, 3000)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 0)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
