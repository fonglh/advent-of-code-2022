fun main() {
    data class InputNumber(val originalIndex: Int, val value: Long)

    fun numberAt(input: List<InputNumber>, positionFromZero: Int): Long {
        // find position of 0
        var zeroIndex = 0
        for (i in input.indices) {
            if (input[i].value == 0.toLong()) {
                zeroIndex = i
                break
            }
        }
        return input[(zeroIndex+positionFromZero)%input.size].value
    }

    fun part1(input: List<String>): Int {
        val inputNumbers = input.mapIndexed { idx, value -> InputNumber(idx, value.toLong()) }
        val inputSize = inputNumbers.size
        var outputNumbers = inputNumbers.toMutableList()

        inputNumbers.forEach {
            val currIndex = outputNumbers.indexOf(it)
            var newIndex = (currIndex + it.value)
            if (newIndex <= 0) {
                // quotient is negative
                val quotient = newIndex / (inputSize-1)
                newIndex += (-quotient+1) * (inputSize-1)
            }
            if (newIndex >= inputSize) {
                newIndex %= (inputSize-1)
            }
            outputNumbers.removeAt(currIndex)
            outputNumbers.add(newIndex.toInt(), it)
        }

        return (numberAt(outputNumbers, 1000) +
                numberAt(outputNumbers, 2000) +
                numberAt(outputNumbers, 3000)).toInt()
    }

    fun part2(input: List<String>): Long {
        val inputNumbers = input.mapIndexed { idx, value -> InputNumber(idx, value.toLong() * 811589153.toLong()) }
        val inputSize = inputNumbers.size
        var outputNumbers = inputNumbers.toMutableList()

        repeat(10) {
            inputNumbers.forEach {
                val currIndex = outputNumbers.indexOf(it)
                var newIndex = (currIndex.toLong() + it.value)
                if (newIndex <= 0) {
                    // quotient is negative
                    val quotient = newIndex / (inputSize-1).toLong()
                    newIndex += (-quotient+1.toLong()) * (inputSize-1).toLong()
                }
                if (newIndex >= inputSize) {
                    newIndex %= (inputSize-1).toLong()
                }
                outputNumbers.removeAt(currIndex)
                outputNumbers.add(newIndex.toInt(), it)
            }
        }

        return (numberAt(outputNumbers, 1000) +
                numberAt(outputNumbers, 2000) +
                numberAt(outputNumbers, 3000))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 1623178306.toLong())

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
