fun main() {
    // convert SNAFU digit (2, 1, 0, -, =) to Dec
    fun snafuDigit(digit: Char): Int {
        return when(digit) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> 999
        }
    }

    fun snafuToDec(snafuNum: String): Long {
        var placeValue = 1.toLong()
        var decimalNumber = 0.toLong()
        snafuNum.reversed().forEach {
            decimalNumber += snafuDigit(it) * placeValue
            placeValue *= 5.toLong()
        }
        return decimalNumber
    }

    fun decToSnafu(input: Long): String {
        var decNum = input
        val snafuDigits = charArrayOf('=', '-', '0', '1', '2')
        val snafuNum = StringBuilder()
        while (decNum > 0) {
            val currentDigit = (decNum+2.toLong()) % 5.toLong()
            decNum = (decNum+2.toLong()) / 5.toLong()
            snafuNum.append(snafuDigits[currentDigit.toInt()])
        }

        return snafuNum.toString().reversed()
    }

    fun part1(input: List<String>): String {
        var fuelSum: Long = 0
        input.forEach {
            fuelSum += snafuToDec(it)
        }
        println(fuelSum)
        return decToSnafu(fuelSum)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day25_test")
    check(part1(testInput) == "2=-1=0")

    val input = readInput("Day25")
    println(part1(input))
}
