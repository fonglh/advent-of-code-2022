fun main() {
    fun printForest(forest: Array<IntArray>) {
        for(i in 0 until forest.size) {
            for (j in 0 until forest[i].size) {
                print(forest[i][j])
            }
            println()
        }
    }

    fun buildForest(input: List<String>): Array<IntArray> {
        var forest = Array(input.size) { IntArray(input[0].length) }
        input.forEachIndexed { index, line ->
            forest[index] = line.map { it.toString().toInt() }.toIntArray()
        }
        return forest
    }

    fun isTreeVisible(forest: Array<IntArray>, row: Int, col: Int): Boolean {
        val treeHeight = forest[row][col]
        var leftBlocked = false
        var rightBlocked = false
        var upBlocked = false
        var downBlocked = false

        // Consider refactoring with `all` function, but this manual looping made
        // it much easier to adapt for part 2.

        // Check to the left
        for(currCol in col-1 downTo 0) {
            if(forest[row][currCol] >= treeHeight) {
                leftBlocked = true
                break
            }
        }
        // Check to the right
        for(currCol in col+1 until forest[row].size) {
            if(forest[row][currCol] >= treeHeight) {
                rightBlocked = true
                break
            }
        }
        // Check up
        for(currRow in row-1 downTo 0) {
            if(forest[currRow][col] >= treeHeight) {
                upBlocked = true
                break
            }
        }
        // Check down
        for(currRow in row+1 until forest.size) {
            if(forest[currRow][col] >= treeHeight) {
                downBlocked = true
                break
            }
        }
        return !(leftBlocked && rightBlocked && upBlocked && downBlocked)
    }

    fun part1(input: List<String>): Int {
        var forest = buildForest(input)

        var totalVisible = 0
        for (row in forest.indices) {
            for(col in forest[row].indices) {
                if (isTreeVisible(forest, row, col)) {
                    totalVisible++
                }
            }
        }
        return totalVisible
    }

    fun scenicScore(forest: Array<IntArray>, row: Int, col: Int): Int {
        val treeHeight = forest[row][col]
        var leftView = 0
        var rightView = 0
        var upView = 0
        var downView = 0
        // Check to the left
        for(currCol in col-1 downTo 0) {
            leftView++
            if(forest[row][currCol] >= treeHeight) {
                break
            }
        }
        // Check to the right
        for(currCol in col+1 until forest[row].size) {
            rightView++
            if(forest[row][currCol] >= treeHeight) {
                break
            }
        }
        // Check up
        for(currRow in row-1 downTo 0) {
            upView++
            if(forest[currRow][col] >= treeHeight) {
                break
            }
        }
        // Check down
        for(currRow in row+1 until forest.size) {
            downView++
            if(forest[currRow][col] >= treeHeight) {
                break
            }
        }
        return leftView * rightView * upView * downView
    }

    fun part2(input: List<String>): Int {
        var forest = buildForest(input)
        var highestScenicScore = 0

        for (row in forest.indices) {
            for(col in forest[row].indices) {
                val currScenicScore = scenicScore(forest, row, col)
                if (currScenicScore > highestScenicScore) {
                    highestScenicScore = currScenicScore
                }
            }
        }
        return highestScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
