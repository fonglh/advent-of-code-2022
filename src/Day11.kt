fun main() {
    class Monkey(lowestCommonMultiple: Long, gotRelief: Boolean) {
        var items: ArrayDeque<Long> = ArrayDeque()
        var operation: (Long) -> Long = { i -> i }
        // Returns index of next monkey to throw item to
        var test: (Long) -> Int = { _ -> 0 }
        var inspectionCount: Long = 0
        // lowest common multiple of the test function divisors.
        // taking modulo of the lcm keeps the worry levels under control.
        val lowestCommonMultiple: Long = lowestCommonMultiple
        val gotRelief = gotRelief

        // Pair<DestinationMonkey, new worry level>
        private fun throwItem(): Pair<Int, Long> {
            var item = items.removeFirst()
            item = if (gotRelief) {
                operation(item) / 3
            } else {
                operation(item) % lowestCommonMultiple
            }
            inspectionCount++
            val destMonkey = test(item)
            return Pair(destMonkey, item)
        }

        fun throwAllItems(): List<Pair<Int, Long>> {
            val list = mutableListOf<Pair<Int, Long>>()
            while(items.isNotEmpty()) {
                list.add(throwItem())
            }
            return list
        }
    }

    fun processThrownItems(monkeys: List<Monkey>, thrownItems: List<Pair<Int, Long>>) {
        for (item in thrownItems) {
            monkeys[item.first].items.add(item.second)
        }
    }

    fun testMonkeys(gotRelief: Boolean): List<Monkey> {
        val list = mutableListOf<Monkey>()
        // lowest common multiple of the test function divisors.
        // taking % of the lcm keeps the worry levels under control.
        val lowestCommonMultiple = (23*19*13*17).toLong()

        var monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(79, 98))
        monkey.operation = { old -> old * 19 }
        monkey.test = { worry -> if (worry%23 == 0.toLong()) 2 else 3 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(54, 65, 75, 74))
        monkey.operation = { old -> old + 6 }
        monkey.test = { worry -> if (worry%19 == 0.toLong()) 2 else 0 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(79, 60, 97))
        monkey.operation = { old -> old * old }
        monkey.test = { worry -> if (worry%13 == 0.toLong()) 1 else 3 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(74))
        monkey.operation = { old -> old + 3 }
        monkey.test = { worry -> if (worry%17 == 0.toLong()) 0 else 1 }
        list.add(monkey)

        return list
    }

    fun inputMonkeys(gotRelief: Boolean): List<Monkey> {
        val list = mutableListOf<Monkey>()
        // lowest common multiple of the test function divisors.
        // taking % of the lcm keeps the worry levels under control.
        val lowestCommonMultiple = (5*7*13*11*3*2*17*19).toLong()

        var monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(52, 78, 79, 63, 51, 94))
        monkey.operation = { old -> old * 13 }
        monkey.test = { worry -> if (worry%5 == 0.toLong()) 1 else 6 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(77, 94, 70, 83, 53))
        monkey.operation = { old -> old + 3 }
        monkey.test = { worry -> if (worry%7 == 0.toLong()) 5 else 3 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(98, 50, 76))
        monkey.operation = { old -> old * old }
        monkey.test = { worry -> if (worry%13 == 0.toLong()) 0 else 6 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(92, 91, 61, 75, 99, 63, 84, 69))
        monkey.operation = { old -> old + 5 }
        monkey.test = { worry -> if (worry%11 == 0.toLong()) 5 else 7 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(51, 53, 83, 52))
        monkey.operation = { old -> old + 7 }
        monkey.test = { worry -> if (worry%3 == 0.toLong()) 2 else 0 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(76, 76))
        monkey.operation = { old -> old + 4 }
        monkey.test = { worry -> if (worry%2 == 0.toLong()) 4 else 7 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(75, 59, 93, 69, 76, 96, 65))
        monkey.operation = { old -> old * 19 }
        monkey.test = { worry -> if (worry%17 == 0.toLong()) 1 else 3 }
        list.add(monkey)

        monkey = Monkey(lowestCommonMultiple, gotRelief)
        monkey.items.addAll(listOf(89))
        monkey.operation = { old -> old + 2 }
        monkey.test = { worry -> if (worry%19 == 0.toLong()) 2 else 4 }
        list.add(monkey)

        return list
    }

    fun part1(monkeys: List<Monkey>): Long {
        for (i in 1..20) {
            for (monkey in monkeys) {
                val thrownItems = monkey.throwAllItems()
                processThrownItems(monkeys, thrownItems)
            }
        }

        var inspectionCounts = monkeys.map { monkey -> monkey.inspectionCount }
        inspectionCounts = inspectionCounts.sortedDescending()

        return inspectionCounts[0] * inspectionCounts[1]
    }

    fun part2(monkeys: List<Monkey>): Long {
        for (i in 1..10000) {
            for (monkey in monkeys) {
                val thrownItems = monkey.throwAllItems()
                processThrownItems(monkeys, thrownItems)
            }
        }

        var inspectionCounts = monkeys.map { monkey -> monkey.inspectionCount }
        inspectionCounts = inspectionCounts.sortedDescending()

        return inspectionCounts[0] * inspectionCounts[1]
    }

    // test if implementation meets criteria from the description, like:
    check(part1(testMonkeys(true)) == 10605.toLong())
    check(part2(testMonkeys(false)) == 2713310158)

    println(part1(inputMonkeys(true)))
    println(part2(inputMonkeys(false)))
}
