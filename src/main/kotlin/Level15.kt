import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.collections.ArrayList

fun main() {
    val reader = System.`in`.bufferedReader()
    val writer = System.out.bufferedWriter()
    val (n, k) = reader.readLine().split(" ").map { it.toInt() }
    val data = Array(n) { reader.readLine().split(" ").map { it.toInt() } }
//
//    var max = 0
//    val dp = Array(n) { IntArray(k + 1) }
//    for (i in 0 until n) {
//        for (w in 0..k) {
//            val item = data[i]
////            if () {
//                val value = max(
//                    (if (i > 0 && w + item[0] <= k) dp[i - 1][w+item[0]] else 0) + item[1],
//                    if (i > 0) dp[i - 1][w] else 0
//                )
//                dp[i][w] = value
//                if (max < value) max = value
//            } else {
//                val value = max(
//                    if (i > 0) dp[i - 1][w] else 0,
//                    if(i>0 && w+item[0] <= k) dp[i-1][w+item[0]] + item[1] else 0
//                )
//                dp[i][w] = value
//                if (max < value) max = value
//            }
//        }
//    }
//    println(dp.contentDeepToString())
//    writer.write("$max")
//    writer.flush()
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

    private fun makeKey(a: Int, b: Int, c: Int): Int {
        return a * 10000 + b * 100 + c
    }

    private fun w(a: Int, b: Int, c: Int): Int {
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

    private fun tile(n: Int, mem: IntArray): Int {
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

    private fun triangle(n: Int, mem: MutableList<Long>): Long {
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

    private fun coloring(cost: MutableList<IntArray>, n: Int, i: Int): Int {
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

    private fun findPath(cost: MutableList<IntArray>, i: Int) {
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

    private fun maxScoreWhenSelectStairs(score: IntArray, cost: IntArray, i: Int) {
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

    private fun operationToOne(cntArr: IntArray, n: Int) {
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

    fun `백준 10844`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val mem = ArrayList<IntArray>(n)
        repeat(n) { mem.add(IntArray(10)) }

        calcNumOfStairNumber(mem, 0)
        val result = mem
            .last()
            .filterIndexed { index, i -> index > 0 }
            .fold(0) { acc, i -> (acc + i) % 1000000000 }
        writer.write("${result % 1000000000}")
        writer.flush()
    }

    private fun calcNumOfStairNumber(mem: ArrayList<IntArray>, i: Int) {
        if (mem.lastIndex < i) return
        when (i) {
            0 -> for (j in 1..9) mem[i][j] = 1
            1 -> {
                for (j in 1..8) mem[i][j] = 2
                mem[i][0] = 1
                mem[i][9] = 1
            }
            else ->
                for (j in mem[i].indices) {
                    mem[i][j] += mem[i - 1].getOrElse(j - 1) { 0 } % 1000000000
                    mem[i][j] += mem[i - 1].getOrElse(j + 1) { 0 } % 1000000000
                    mem[i][j] %= 1000000000
                }
        }
        calcNumOfStairNumber(mem, i + 1)
    }

    fun `백준 2156`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val w = ArrayList<Int>(n)
        val mem = IntArray(n)
        repeat(n) { w.add(reader.readLine().toInt()) }

        calcMaxWeight(mem, w, 0)
        writer.write("${mem[n - 1]}")
        writer.flush()
    }

    private fun calcMaxWeight(mem: IntArray, w: ArrayList<Int>, i: Int) {
        when {
            i > mem.lastIndex -> return
            else -> {
                val a = mem.getOrElse(i - 3) { 0 } + w.getOrElse(i - 1) { 0 } + w[i]
                val b = mem.getOrElse(i - 1) { 0 }
                val c = mem.getOrElse(i - 2) { 0 } + w[i]
                mem[i] = max(max(a, b), c)
            }
        }
        calcMaxWeight(mem, w, i + 1)
    }

    fun `백준 11053 - longest increasing subsequence`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = reader.readLine().split(" ").map { it.toInt() }
        val mem = ArrayList<Int>()

        findLongestIncreaseSequence(data, mem, 0)
        writer.write("${mem.size}")
        writer.flush()
    }

    private fun findLongestIncreaseSequence(data: List<Int>, mem: ArrayList<Int>, i: Int) {
        if (i > data.lastIndex) return

        when {
            mem.size == 0 -> mem.add(data[i])
            mem.last() < data[i] -> mem.add(data[i])
            mem.last() > data[i] -> {
                val idx = lowerBound(mem, data[i])
                mem[idx] = data[i]
            }
        }
        return findLongestIncreaseSequence(data, mem, i + 1)
    }

    private fun lowerBound(mem: List<Int>, v: Int): Int {
        if (mem.size == 1) {
            return if (mem[0] >= v) 0 else 1
        }

        val pivot = mem.size / 2
        return if (mem[pivot] < v) {
            lowerBound(mem.subList(pivot, mem.lastIndex + 1), v) + pivot
        } else {
            lowerBound(mem.subList(0, pivot), v)
        }
    }

    fun `백준 2565번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        val lines = ArrayList<Pair<Int, Int>>()
        repeat(n) {
            val (x, y) = reader.readLine().split(" ").map { it.toInt() }
            lines.add(Pair(x, y))
        }
        lines.sortBy { it.first }

        val remains = findLongestIncreaseSubsequence(lines.map { it.second })
        writer.write("${n - remains.size}")
        writer.flush()
    }

    private fun findLongestIncreaseSubsequence(data: List<Int>): List<Int> {
        val list = ArrayList<Int>()
        for (d in data) {
            val idx = binarySearchLowerIndex(list, d)
            if (list.size == idx) list.add(d)
            else list[idx] = d
        }
        return list
    }

    private fun binarySearchLowerIndex(list: MutableList<Int>, value: Int): Int {
        if (list.size == 0) return 0
        if (list.size == 1)
            return if (list.first() >= value) 0 else 1

        val mid = list.size / 2
        return when {
            list[mid] < value -> binarySearchLowerIndex(list.subList(mid, list.size), value) + mid
            else -> binarySearchLowerIndex(list.subList(0, mid), value)
        }
    }

    fun `백준 9251번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val a = reader.readLine()
        val b = reader.readLine()

        val mem = ArrayList<IntArray>(a.length)
        repeat(a.length) { mem.add(IntArray(b.length)) }

        findLongestCommonSubsequence(mem, a, b, 0, 0)
        writer.write("${mem[a.length - 1][b.length - 1]}")
        writer.flush()
    }

    private fun findLongestCommonSubsequence(mem: List<IntArray>, word1: String, word2: String, i: Int, j: Int) {
        for (i in word1.indices) {
            for (j in word2.indices) {
                val a = if (i > 0) mem[i - 1][j] else 0
                val b = if (j > 0) mem[i][j - 1] else 0
                val c = (if (i > 0 && j > 0) mem[i - 1][j - 1] else 0) + (if (word1[i] == word2[j]) 1 else 0)
                val d = max(a, b)
                mem[i][j] = max(c, d)
            }
        }
    }

    fun `백준 1912번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = reader.readLine().split(" ").map { it.toInt() }

        val dp = IntArray(n)
        var ans = Int.MIN_VALUE
        for (i in data.indices) {
            dp[i] = max(if (i > 0) dp[i - 1] + data[i] else Int.MIN_VALUE, data[i])
            ans = max(ans, dp[i])
        }

        writer.write("$ans")
        writer.flush()
    }
}