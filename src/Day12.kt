import kotlin.math.absoluteValue

fun main() {
    fun buildMap(input: List<String>): Array<CharArray> {
        var map = Array(input.size) { CharArray(input[0].length) }
        input.forEachIndexed { index, line ->
           map[index] = line.toCharArray()
        }
        return map
    }

    fun findStartAndEnd(map: Array<CharArray>): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        var start: Pair<Int, Int> = Pair(0, 0)
        var end: Pair<Int, Int> = Pair(0, 0)

        map.forEachIndexed { rowNumber, row ->
            row.forEachIndexed { colNumber, col ->
                if (col == 'S') {
                    start = Pair(rowNumber, colNumber)
                    map[rowNumber][colNumber] = 'a'
                } else if (col == 'E') {
                    end = Pair(rowNumber, colNumber)
                    map[rowNumber][colNumber] = 'z'
                }
            }
        }

        return Pair(start, end)
    }

    fun isValidMove(map: Array<CharArray>, curr: Pair<Int, Int>, nextStep: Pair<Int, Int>): Boolean {
        val currHeight = map[curr.first][curr.second].hashCode()
        val nextHeight = map[nextStep.first][nextStep.second].hashCode()

        return nextHeight in 0..currHeight+1
    }

    fun part1(input: List<String>): Int {
        var map = buildMap(input)
        val distances = HashMap<Pair<Int, Int>, Int>(map.size * map[0].size)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()
        val startAndEnd = findStartAndEnd(map)
        val start = startAndEnd.first
        val end = startAndEnd.second

        distances[start] = 0
        queue.add(start)
        visited.add(start)
        while(queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            val currDistance = distances[curr]!!
            // reached the top
            if (curr == end) {
                return distances[curr]!!
            }

            for (i in -1..1) {
                for (j in -1..1) {
                    //offsets will give up, down, left right
                    if (i.absoluteValue != j.absoluteValue) {
                        // check if new coords are valid
                        val nextStep = Pair(curr.first + i, curr.second + j)
                        if (nextStep.first >=0 && nextStep.first < map.size
                            && nextStep.second >= 0 && nextStep.second < map[0].size
                            && !visited.contains(nextStep)
                            && isValidMove(map, curr, nextStep)) {
                            // no need to handle relaxation step where distances get reduced
                            // since all distances are equal
                            if (!distances.containsKey(nextStep)) {
                                distances[nextStep] = 1 + currDistance
                                queue.add(nextStep)
                            }
                        }
                    }
                }
            }
            visited.add(curr)
        }

        return 0
    }

    // climb down by one, climb up by any amount.
    fun isValidMovePart2(map: Array<CharArray>, curr: Pair<Int, Int>, nextStep: Pair<Int, Int>): Boolean {
        val currHeight = map[curr.first][curr.second].hashCode()
        val nextHeight = map[nextStep.first][nextStep.second].hashCode()

        return nextHeight >= (currHeight - 1)
    }

    fun part2(input: List<String>): Int {
        var map = buildMap(input)
        val distances = HashMap<Pair<Int, Int>, Int>(map.size * map[0].size)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val queue = mutableListOf<Pair<Int, Int>>()
        val startAndEnd = findStartAndEnd(map)
        val start = startAndEnd.first
        val end = startAndEnd.second

        // start from the E point and look for the first 'a'
        distances[end] = 0
        queue.add(end)
        visited.add(end)
        while(queue.isNotEmpty()) {
            val curr = queue.removeFirst()
            val currDistance = distances[curr]!!
            // reached the bottom
            if (map[curr.first][curr.second] == 'a') {
                return distances[curr]!!
            }

            for (i in -1..1) {
                for (j in -1..1) {
                    //offsets will give up, down, left right
                    if (i.absoluteValue != j.absoluteValue) {
                        // check if new coords are valid
                        val nextStep = Pair(curr.first + i, curr.second + j)
                        if (nextStep.first >=0 && nextStep.first < map.size
                            && nextStep.second >= 0 && nextStep.second < map[0].size
                            && !visited.contains(nextStep)
                            && isValidMovePart2(map, curr, nextStep)) {
                            // no need to handle relaxation step where distances get reduced
                            // since all distances are equal
                            if (!distances.containsKey(nextStep)) {
                                distances[nextStep] = 1 + currDistance
                                queue.add(nextStep)
                            }
                        }
                    }
                }
            }
            visited.add(curr)
        }

        println("why here?")
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
