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

    fun getPerimeterPoints(sensor: SensorData, limit: Int): Set<Pair<Int, Int>> {
        var perimeterPoints = mutableSetOf<Pair<Int, Int>>()

        // top to right
        for (deltaX in 0..(sensor.distance+1)) {
            val deltaY = -(sensor.distance+1 - deltaX)
            val perimeterX = sensor.sensorPos.first + deltaX
            val perimeterY = sensor.sensorPos.second + deltaY

            if (perimeterX in 0..limit && perimeterY in 0..limit) {
                perimeterPoints.add(Pair(perimeterX, perimeterY))
            }
        }

        // right to bottom
        for (deltaX in (sensor.distance+1) downTo 0) {
            val deltaY = sensor.distance+1 - deltaX
            val perimeterX = sensor.sensorPos.first + deltaX
            val perimeterY = sensor.sensorPos.second + deltaY

            if (perimeterX in 0..limit && perimeterY in 0..limit) {
                perimeterPoints.add(Pair(perimeterX, perimeterY))
            }
        }

        // bottom to left
        for (deltaX in 0 downTo -(sensor.distance+1)) {
            val deltaY = sensor.distance+1 + deltaX
            val perimeterX = sensor.sensorPos.first + deltaX
            val perimeterY = sensor.sensorPos.second + deltaY

            if (perimeterX in 0..limit && perimeterY in 0..limit) {
                perimeterPoints.add(Pair(perimeterX, perimeterY))
            }
        }

        //left to top
        for (deltaX in -(sensor.distance+1)..0) {
            val deltaY = -(sensor.distance+1 + deltaX)
            val perimeterX = sensor.sensorPos.first + deltaX
            val perimeterY = sensor.sensorPos.second + deltaY

            if (perimeterX in 0..limit && perimeterY in 0..limit) {
                perimeterPoints.add(Pair(perimeterX, perimeterY))
            }
        }

        return perimeterPoints
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

    fun part2(input: List<String>, maxSearchCoord: Int): Long {
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

        allSensorData.forEach {sensor: SensorData ->
            val searchPoints = getPerimeterPoints(sensor, maxSearchCoord)
            searchPoints.forEach { candidatePos: Pair<Int, Int> ->
                if (allSensorData.none { manhattanDistance(it.sensorPos, candidatePos) <= it.distance }) {
                    println(candidatePos)
                    return (candidatePos.first.toLong() * 4000000.toLong() + candidatePos.second.toLong())
                }
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput, 10) == 26)
    check(part2(testInput, 20) == 56000011.toLong())

    val input = readInput("Day15")
    println(part1(input, 2000000))
    println(part2(input, 4000000))
}
