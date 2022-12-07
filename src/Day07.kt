// Node can be either a file or directory
// directories will have size 0
data class Node(val nodes: MutableList<Node>, val parent: Node?, val name: String, val size: Int)

fun main() {
    fun buildFilesystem(input: List<String>): Node {
        var filesystem = Node(mutableListOf(), null, "/" , 0)
        var currentNode = filesystem

        var inputLineCtr = 1    // skip the first "$ cd /" line
        while (inputLineCtr < input.size) {
            //println(input[inputLineCtr])
            if (input[inputLineCtr] == "$ ls") {
                // parse listing
                inputLineCtr++
                while(inputLineCtr < input.size && input[inputLineCtr][0] != '$') {
                    //println(input[inputLineCtr])
                    val (nodeType, nodeName) = input[inputLineCtr].split(" ")
                    if (nodeType == "dir") {
                        currentNode.nodes.add(Node(mutableListOf(), currentNode, nodeName, 0))
                    } else {
                        val fileSize = nodeType.toInt()
                        currentNode.nodes.add(Node(mutableListOf(), currentNode, nodeName, fileSize))
                    }
                    inputLineCtr++
                }
            } else if (input[inputLineCtr].startsWith("$ cd")) {
                // change directory
                val dirName = input[inputLineCtr].substring(5)
                if (dirName == "..") {
                    currentNode = currentNode.parent!!
                } else {
                    val nodes = currentNode.nodes
                    for (node in nodes) {
                        if (node.name == dirName) {
                            currentNode = node
                            break
                        }
                    }
                }
                inputLineCtr++
            }
        }

        return filesystem
    }

    fun dirSize(node: Node, dirSizeList: MutableList<Int>): Int {
        // Directories have size 0, so this must be a file and thus a leaf node.
        if (node.size > 0) {
            return node.size
        }

        var totalSize = 0
        for (item in node.nodes) {
            totalSize += dirSize(item, dirSizeList)
        }
        dirSizeList.add(totalSize)
        return totalSize
    }

    fun part1(input: List<String>): Int {
        var filesystem = buildFilesystem(input)

        val dirSizes = mutableListOf<Int>()
        dirSize(filesystem, dirSizes)

        return dirSizes.filter { it < 100000 }.sum()
    }

    fun part2(input: List<String>): Int {
        var filesystem = buildFilesystem(input)
        val dirSizes = mutableListOf<Int>()

        val fsSize = dirSize(filesystem, dirSizes)
        val unusedSize = 70000000 - fsSize
        val minDeleteSize = 30000000 - unusedSize

        return dirSizes.filter { it > minDeleteSize }.min()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
