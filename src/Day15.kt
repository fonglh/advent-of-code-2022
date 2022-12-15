import kotlin.math.absoluteValue

fun main() {
    val inputLineRegex = """Sensor at x=([-\d]+), y=([-\d]+): closest beacon is at x=([-\d]+), y=([-\d]+)""".toRegex()

    data class SensorData(val sensorPos: Pair<Int, Int>, val beaconPos: Pair<Int, Int>, val distance: Int)

    fun manhattanDistance(p1: Pair<Int, Int>, p2: Pair<Int, Int>): Int {
        return (p1.first-p2.first).absoluteValue + (p1.second-p2.second).absoluteValue
    }

    // min and max X coordinates which are relevant
    fun findLeftRightLimits(allSensorData: List<SensorData>): Pair<Int, Int> {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE

        allSensorData.forEach {
            val sensorMinX = it.sensorPos.first - it.distance
            val sensorMaxX = it.sensorPos.first + it.distance
            if (sensorMinX < minX)
                minX = sensorMinX
            if (sensorMaxX > maxX)
                maxX = sensorMaxX
        }

        return Pair(minX, maxX)
    }

    fun countBeaconsInRow(allSensorData: List<SensorData>, targetRow: Int): Int {
        var beacons = mutableSetOf<Pair<Int, Int>>()
        allSensorData.forEach {
            if (it.beaconPos.second == targetRow)
                beacons.add(it.beaconPos)
        }

        return beacons.size
    }

    fun part1(input: List<String>, targetRow: Int): Int {
        var allSensorData = mutableListOf<SensorData>()

        input.forEach {
            val (sensorX, sensorY, beaconX, beaconY) = inputLineRegex.matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            val sensorPos = Pair(sensorX.toInt(), sensorY.toInt())
            val beaconPos = Pair(beaconX.toInt(), beaconY.toInt())
            val distance = manhattanDistance(sensorPos, beaconPos)
            allSensorData.add(SensorData(sensorPos, beaconPos, distance))
        }

        val leftRightLimits = findLeftRightLimits(allSensorData)
        val beaconsInRow = countBeaconsInRow(allSensorData, targetRow)
        var count = 0
        for (x in leftRightLimits.first..leftRightLimits.second) {
            val checkPos = Pair(x, targetRow)
            if (allSensorData.any { manhattanDistance(it.sensorPos, checkPos) <= it.distance }) {
                count++
            }
        }

        return count - beaconsInRow
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput, 10) == 26)
    check(part2(testInput) == 0)

    val input = readInput("Day15")
    println(part1(input, 2000000))
    println(part2(input))
}
