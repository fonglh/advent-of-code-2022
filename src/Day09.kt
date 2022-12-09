fun main() {
    fun isAdjacent(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
        for (row in head.first-1..head.first+1) {
            for (col in head.second-1..head.second+1) {
                if (tail.first == row && tail.second == col)
                    return true
            }
        }
        return false
    }

    // must call after every step of head so head is at most 2 steps away from tail
    fun moveTail(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
        // Already adjacent, do nothing
        if (isAdjacent(head, tail))
            return tail
        // same row
        if (head.first == tail.first) {
            return if (head.second > tail.second) {
                Pair(tail.first, tail.second + 1)
            } else {
                Pair(tail.first, tail.second - 1)
            }
        } else if (head.second == tail.second) {    // same column
            return if (head.first > tail.first) {
                Pair(tail.first+1, tail.second)
            } else {
                Pair(tail.first-1, tail.second)
            }
        } else {
            return if (head.first > tail.first && head.second > tail.second) {     //up right
                Pair(tail.first+1, tail.second+1)
            } else if (head.first < tail.first && head.second < tail.second) {  //down left
                Pair(tail.first-1, tail.second-1)
            } else if (head.first > tail.first && head.second < tail.second) { //up left
                Pair(tail.first+1, tail.second-1)
            } else {    //down right
                Pair(tail.first-1, tail.second+1)
            }

        }
        return tail
    }

    fun part1(input: List<String>): Int {
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        var tailCovered: MutableSet<Pair<Int, Int>> = mutableSetOf(tail)

        input.forEach {
            val steps = it.substringAfter(" ").toInt()
            when(it.substringBefore(" ")) {
                "U" -> {
                    for(i in 1..steps) {
                        head = Pair(head.first+1, head.second)
                        tail = moveTail(head, tail)
                        tailCovered.add(tail)
                    }
                }
                "D" -> {
                    for(i in 1..steps) {
                        head = Pair(head.first-1, head.second)
                        tail = moveTail(head, tail)
                        tailCovered.add(tail)
                    }

                }
                "L" -> {
                    for(i in 1..steps) {
                        head = Pair(head.first, head.second-1)
                        tail = moveTail(head, tail)
                        tailCovered.add(tail)
                    }
                }
                "R" -> {
                    for(i in 1..steps) {
                        head = Pair(head.first, head.second+1)
                        tail = moveTail(head, tail)
                        tailCovered.add(tail)
                    }
                }
                else -> throw Exception()
            }
        }
        return tailCovered.size
    }

    fun ropeToString(rope: Array<Pair<Int, Int>>): String {
        var output = StringBuilder()
        for (knot in rope) {
            output.append(knot)
        }
        return output.toString()
    }

    fun cascade(rope: Array<Pair<Int, Int>>): Array<Pair<Int, Int>> {
        for(i in 0 until rope.size-1) {
            rope[i+1] = moveTail(rope[i], rope[i+1])
        }
        return rope
    }

    fun part2(input: List<String>): Int {
        var rope = Array<Pair<Int, Int>>(10) { Pair(0, 0) }
        var tailCovered: MutableSet<Pair<Int, Int>> = mutableSetOf(rope.last())

        input.forEach {
            val steps = it.substringAfter(" ").toInt()
            when(it.substringBefore(" ")) {
                "U" -> {
                    for(i in 1..steps) {
                        var head = rope[0]
                        rope[0] = Pair(head.first+1, head.second)
                        rope = cascade(rope)
                        tailCovered.add(rope.last())
                    }
                }
                "D" -> {
                    for(i in 1..steps) {
                        var head = rope[0]
                        rope[0] = Pair(head.first-1, head.second)
                        rope = cascade(rope)
                        tailCovered.add(rope.last())
                    }

                }
                "L" -> {
                    for(i in 1..steps) {
                        var head = rope[0]
                        rope[0] = Pair(head.first, head.second-1)
                        rope = cascade(rope)
                        tailCovered.add(rope.last())
                    }
                }
                "R" -> {
                    for(i in 1..steps) {
                        var head = rope[0]
                        rope[0] = Pair(head.first, head.second+1)
                        rope = cascade(rope)
                        tailCovered.add(rope.last())
                    }
                }
                else -> throw Exception()
            }
        }
        return tailCovered.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
