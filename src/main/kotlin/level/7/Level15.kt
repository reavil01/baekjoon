package level.`7`

import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    val reader = System.`in`.bufferedReader()
    val writer = System.out.bufferedWriter()
    val N = reader.readLine().toInt()
    val cost = mutableListOf<IntArray>()
    repeat(N) {
        cost.add(reader.readLine().split(" ").map { it.toInt() }.toIntArray())
    }

    findPath(cost, N)
    writer.write("${cost[0][0]}")
    writer.flush()
}

fun findPath(cost: MutableList<IntArray>, i: Int) {
    if (i + 1 <= cost.lastIndex) {
        for (j in cost[i].indices) {
            cost[i][j] += max(cost[i + 1][j], cost[i + 1][j + 1])
        }
    }

    if (i == 0) return
    findPath(cost, i - 1)
}

class Level15 {
    fun `백준 1003`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()

        for (i in 1..N) {
            val n = reader.readLine().toInt()
            val memZero = IntArray(n + 1)
            val memOne = IntArray(n + 1)
            fibonacciCount(n, memZero, memOne)
            writer.write("${memZero[n]} ${memOne[n]}\n")
        }
        writer.flush()
    }

    private fun fibonacciCount(n: Int, memZero: IntArray, memOne: IntArray) {
        when (n) {
            0 -> memZero[0] = 1
            1 -> memOne[1] = 1
            else -> {
                if (memZero[n - 1] == 0 && memZero[n - 2] == 0) {
                    fibonacciCount(n - 1, memZero, memOne)
                    fibonacciCount(n - 2, memZero, memOne)
                }
                memZero[n] = memZero[n - 2] + memZero[n - 1]
                memOne[n] = memOne[n - 2] + memOne[n - 1]
            }
        }
    }

    val mem = mutableMapOf<Int, Int>()

    fun `백준 9184`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        while (true) {
            val n = reader.readLine().split(" ").map { it.toInt() }
            if (n.all { it == -1 }) break
            val ans = w(n[0], n[1], n[2])
            println("w(${n[0]}, ${n[1]}, ${n[2]}) = $ans")
        }
        writer.flush()
    }

    fun makeKey(a: Int, b: Int, c: Int): Int {
        return a * 10000 + b * 100 + c
    }

    fun w(a: Int, b: Int, c: Int): Int {
        val key = makeKey(a, b, c)
        return when {
            mem.containsKey(key) -> mem[key]!!
            a <= 0 || b <= 0 || c <= 0 -> 1
            a > 20 || b > 20 || c > 20 -> w(20, 20, 20)
            a < b && b < c -> {
                val ans = w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c)
                mem[key] = ans
                ans
            }
            else -> {
                val ans = w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1)
                mem[key] = ans
                ans
            }
        }
    }

    fun `백준 1904`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val mem = IntArray(n + 1)
        val ans = tile(n, mem)
        writer.write("${ans % 15746}")
        writer.flush()
    }

    fun tile(n: Int, mem: IntArray): Int {
        when (n) {
            1 -> mem[1] = 1
            2 -> mem[2] = 2
            else -> {
                if (mem[n - 1] == 0) tile(n - 1, mem)
                if (mem[n - 2] == 0) tile(n - 2, mem)
                mem[n] = (mem[n - 1] % 15746) + (mem[n - 2] % 15746)
            }
        }

        return mem[n]
    }

    fun `백준 9461`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val mem = mutableListOf<Long>()
        mem.addAll(listOf(1, 1, 1, 2, 2, 3, 4, 5, 7, 9))
        repeat(n) {
            val target = reader.readLine().toInt()
            val ans = triangle(target - 1, mem)
            writer.write("$ans\n")
        }
        writer.flush()
    }

    fun triangle(n: Int, mem: MutableList<Long>): Long {
        if (mem.lastIndex < n) {
            if (mem.lastIndex < n - 5) triangle(n - 5, mem)
            if (mem.lastIndex < n - 1) triangle(n - 1, mem)
            mem.add(mem[n - 1] + mem[n - 5])
        }
        return mem[n]
    }

    fun `백준 1149`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()
        val cost = mutableListOf<IntArray>()
        repeat(N) {
            cost.add(reader.readLine().split(" ").map { it.toInt() }.toIntArray())
        }

        val minCost = coloring(cost, N - 1, 1)
        writer.write(minCost)
        writer.flush()
    }

    fun coloring(cost: MutableList<IntArray>, n: Int, i: Int): Int {
        cost[i][0] += min(cost[i - 1][1], cost[i - 1][2])
        cost[i][1] += min(cost[i - 1][0], cost[i - 1][2])
        cost[i][2] += min(cost[i - 1][0], cost[i - 1][1])
        return if (i == n) {
            min(cost[i][0], min(cost[i][1], cost[i][2]))
        } else {
            coloring(cost, n, i + 1)
        }
    }

    fun `백준 1932`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()
        val cost = mutableListOf<IntArray>()
        repeat(N) {
            cost.add(reader.readLine().split(" ").map { it.toInt() }.toIntArray())
        }

        findPath(cost, N)
        writer.write("${cost[0][0]}")
        writer.flush()
    }

    fun findPath(cost: MutableList<IntArray>, i: Int) {
        if (i + 1 <= cost.lastIndex) {
            for (j in cost[i].indices) {
                cost[i][j] += max(cost[i + 1][j], cost[i + 1][j + 1])
            }
        }

        if (i == 0) return
        findPath(cost, i - 1)
    }

    fun `백준 2579`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val N = reader.readLine().toInt()
        val cost = IntArray(N)
        val score = IntArray(N)
        repeat(N) {
            cost[it] = reader.readLine().toInt()
        }

        maxScoreWhenSelectStairs(score, cost, 0)
        writer.write("${score[N - 1]}")
        writer.flush()
    }

    fun maxScoreWhenSelectStairs(score: IntArray, cost: IntArray, i: Int) {
        score[i] = when {
            i > score.lastIndex -> return
            i == 0 -> cost[i]
            i == 1 -> score[i - 1] + cost[i]
            i == 2 -> max(score[i - 2] + cost[i], cost[i - 1] + cost[i])
            else -> max(score[i - 2] + cost[i], score[i - 3] + cost[i - 1] + cost[i])
        }
        maxScoreWhenSelectStairs(score, cost, i + 1)
    }

    fun `백준 1463`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val cntArr = IntArray(n + 1)
        operationToOne(cntArr, 1)

        writer.write("${cntArr[n]}")
        writer.flush()
    }

    fun operationToOne(cntArr: IntArray, n: Int) {
        if (n > cntArr.lastIndex) return

        cntArr[n] = when (n) {
            1 -> 0
            2, 3 -> 1
            else -> {
                val candidate = min(
                    if (n % 2 == 0) cntArr[n / 2] + 1 else Int.MAX_VALUE,
                    if (n % 3 == 0) cntArr[n / 3] + 1 else Int.MAX_VALUE
                )
                min(cntArr[n - 1] + 1, candidate)
            }
        }
        operationToOne(cntArr, n + 1)
    }
}