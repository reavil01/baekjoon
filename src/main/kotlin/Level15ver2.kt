import java.util.*
import kotlin.math.min
import kotlin.math.max

fun main() {

}

class Level15ver2 {
    fun `백준 1003번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        tailrec fun fibonacciCount(n: Int, dp: Array<IntArray>): Pair<Int, Int> {
            if (dp[n][0] > 0) return dp[n][0] to dp[n][1]

            dp[n][0] = 0
            dp[n][1] = 0

            when (n) {
                0 -> dp[n][0] += 1
                1 -> dp[n][1] += 1
                else -> {
                    val (a, b) = fibonacciCount(n - 1, dp)
                    val (c, d) = fibonacciCount(n - 2, dp)
                    dp[n][0] = a + c
                    dp[n][1] = b + d
                }
            }

            return dp[n][0] to dp[n][1]
        }

        repeat(t) {
            val n = reader.readLine().toInt()
            val dp = Array(n + 1) { IntArray(2) { -1 } }
            fibonacciCount(n, dp)
            val ans = "${dp[n][0]} ${dp[n][1]}"
            writer.write("$ans\n")
        }

        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 9184번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val dp = HashMap<Triple<Int, Int, Int>, Int>()

        tailrec fun w(a: Int, b: Int, c: Int): Int {
            val key = Triple(a, b, c)
            if (dp.contains(key)) return dp[key] ?: 0

            dp[key] = when {
                a <= 0 || b <= 0 || c <= 0 -> 1
                a > 20 || b > 20 || c > 20 -> w(20, 20, 20)
                b in (a + 1) until c -> w(a, b, c - 1) + w(a, b - 1, c - 1) - w(a, b - 1, c)
                else -> w(a - 1, b, c) + w(a - 1, b - 1, c) + w(a - 1, b, c - 1) - w(a - 1, b - 1, c - 1)
            }

            return dp[key] ?: 0
        }

        while (true) {
            val (a, b, c) = reader.readLine().split(" ").map { it.toInt() }
            if (a == -1 && b == -1 && c == -1) break
            val ans = w(a, b, c)
            writer.write("w($a, $b, $c) = $ans\n")
        }

        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1904번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val dp = IntArray(n + 1)

        tailrec fun tile(n: Int): Int {
            if (dp[n] > 0) return dp[n]

            dp[n] = when (n) {
                1 -> 1
                2 -> 2
                else -> ((tile(n - 1) % 15746) + (tile(n - 2) % 15746)) % 15746
            }

            return dp[n]
        }

        val ans = tile(n)
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 9461번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val t = reader.readLine().toInt()

        tailrec fun wave(n: Int, dp: LongArray): Long {
            if (dp[n] > 0) return dp[n]

            dp[n] = when (n) {
                1, 2, 3 -> 1
                4, 5 -> 2
                else -> wave(n - 1, dp) + wave(n - 5, dp)
            }

            return dp[n]
        }
        repeat(t) {
            val n = reader.readLine().toInt()
            val dp = LongArray(n + 1)
            val ans = wave(n, dp)
            writer.write("$ans\n")
        }
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1149번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = Array(n) { reader.readLine().split(" ").map { it.toInt() } }
        val dp = Array(n) { IntArray(3) }

        for (i in 0 until n) {
            for (c in 0 until 3) {
                if (i == 0) {
                    dp[i][c] = cost[i][c]
                } else {
                    dp[i][c] =
                        min(dp[i - 1][(c + 1) % 3], dp[i - 1][(c + 2) % 3]) + cost[i][c]
                }
            }
        }

        val ans = dp[n - 1].minOf { it }
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1932번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = Array(n) {
            reader.readLine().split(" ").map { it.toInt() }
        }

        val dp = Array(n) { IntArray(n) }

        for (i in 0 until n) {
            for (j in 0..i) {
                dp[i][j] = when {
                    i == 0 -> cost[i][j]
                    j == 0 -> dp[i - 1][j] + cost[i][j]
                    else -> max(dp[i - 1][j], dp[i - 1][j - 1]) + cost[i][j]
                }
            }
        }

        val ans = dp[n - 1].maxOf { it }
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 2579번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = IntArray(n) { reader.readLine().toInt() }
        val dp = IntArray(n)

        for (i in 0 until n) {
            dp[i] =
                when (i) {
                    0 -> cost[0]
                    1 -> dp[0] + cost[1]
                    2 -> max(cost[0], cost[1]) + cost[2]
                    else -> max(dp[i - 2], dp[i - 3] + cost[i - 1]) + cost[i]
                }
        }
        writer.write("${dp[n - 1]}\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 1463번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val dp = IntArray(n + 1)

        tailrec fun to1(n: Int): Int {
            if (n == 1) return 0
            if (dp[n] != 0) return dp[n]

            dp[n] = listOf(
                if (n % 3 == 0) to1(n / 3) else Int.MAX_VALUE,
                if (n % 2 == 0) to1(n / 2) else Int.MAX_VALUE,
                to1(n - 1)
            ).minOf { it } + 1

            return dp[n]
        }

        val ans = to1(n)
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 10844번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val dp = Array(n + 1) { IntArray(10) }

        fun a(n: Int) {
            if (n == 1) {
                for (i in 1..9) {
                    dp[n][i] = 1
                }
                return
            }

            if (dp[n].all { it == 0 }) a(n - 1)

            for (i in 0..9) {
                dp[n][i] = when (i) {
                    0 -> dp[n - 1][i + 1] % 1000000000
                    9 -> dp[n - 1][i - 1] % 1000000000
                    else -> (dp[n - 1][i - 1] + dp[n - 1][i + 1]) % 1000000000
                }
            }
        }

        a(n)

        val ans = dp[n].fold(0) { acc, it -> (acc + it) % 1000000000 }
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 2156번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = IntArray(n) { reader.readLine().toInt() }
        val dp = IntArray(n)

        tailrec fun dp(s: Int) {
            if (s == n) return

            val a = dp.getOrElse(s - 3) { 0 } + cost.getOrElse(s - 1) { 0 } + cost[s]
            val b = dp.getOrElse(s - 1) { 0 }
            val c = dp.getOrElse(s - 2) { 0 } + cost[s]
            dp[s] = max(a, max(b, c))

            dp(s + 1)
        }

        dp(0)
        val ans = dp[n - 1]
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 11053번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = reader.readLine().split(" ").map { it.toInt() }

        val queue = LinkedList<Int>()

        fun lis() {
            for (c in cost) {
                if (queue.isEmpty()) queue.add(c)
                if (queue.last() < c) queue.add(c)
                if (queue.last() > c) {
                    val idx = queue.withIndex()
                        .findLast { it.value < c }?.index ?: -1
                    queue[idx + 1] = c
                }
            }
        }

        lis()
        writer.write("${queue.size}")
        writer.flush()

        reader.close()
        writer.close()
    }

    fun `백준 11054번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val cost = reader.readLine().split(" ").map { it.toInt() }

        fun lis(i: Int, cost: List<Int>, dp: IntArray, queue: LinkedList<Int>) {
            if (i == n) return

            val c = cost[i]
            when {
                queue.isEmpty() -> queue.add(c)
                queue.last() < c -> {
                    queue.add(c)
                }
                queue.last() > c -> {
                    val idx = queue.withIndex().findLast { it.value < c }?.index ?: -1
                    queue[idx + 1] = c
                }
            }

            dp[i] = queue.size
            lis(i + 1, cost, dp, queue)
        }

        val queue = LinkedList<Int>()
        val dp = IntArray(n)
        lis(0, cost, dp, queue)

        val queue2 = LinkedList<Int>()
        val dp2 = IntArray(n)
        val cost2 = cost.reversed()
        lis(0, cost2, dp2, queue2)
        dp2.reverse()

        val ans = dp.zip(dp2).map { (a, b) -> a + b - 1 }.maxOf { it }
        writer.write("$ans\n")
        writer.flush()

        reader.close()
        writer.close()
    }
}