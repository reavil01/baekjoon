package level.`7`

import kotlin.math.sqrt

class Level17 {
    fun `백준 5086번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        while (true) {
            val (a, b) = reader.readLine().split(" ").map { it.toInt() }
            if (a == 0 && b == 0) break

            val compared = when {
                b % a == 0 -> "factor"
                a % b == 0 -> "multiple"
                else -> "neither"
            }
            writer.write("$compared\n")
        }
        writer.flush()
    }

    fun `백준 1037번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val n = reader.readLine().toInt()
        val data = reader.readLine().split(" ").map { it.toInt() }.sorted()
        writer.write("${data.first() * data.last()}")
        writer.flush()
    }

    fun `백준 2609번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (a, b) = reader.readLine().split(" ").map { it.toInt() }

        val gcd = gcd(a, b)
        val lcm = lcm(a, b)
        writer.write("$gcd\n$lcm")
        writer.flush()
    }

    fun lcm(a: Int, b: Int): Int {
        return a * b / gcd(a, b)
    }

    fun gcd(a: Int, b: Int): Int {
        val (a, b) = if (a > b) a to b else b to a
        return if (a % b == 0) b
        else gcd(b, a % b)
    }

    fun `백준 1934번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().split(" ").map { it.toInt() } }

        data.forEach { writer.write("${lcm(it[0], it[1])}\n") }
        writer.flush()
    }

    fun `백준 2981번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val data = Array(n) { reader.readLine().toInt() }.toList().sorted()

        data.zipWithNext().let { it ->
            val list = ArrayList<Int>()
            for (v in it) {
                list.add(v.second - v.first)
            }

            val gcd = multipleGcd(list)
            val divisors = findDivisor(gcd)
            divisors
                .filter { it != 1 }
                .sorted()
                .forEach {
                    writer.write("$it ")
                }
        }
        writer.flush()
    }

    fun findDivisor(v: Int): List<Int> {
        val max = sqrt(v.toDouble()).toInt()
        val divisors = (1..max).filter { v % it == 0 }
        return (divisors + divisors.map { v / it }).distinct()
    }

    fun multipleGcd(data: List<Int>): Int {
        if (data.size == 1) return data[0]
        val ans = ArrayList<Int>()

        val pairs = data.windowed(2, 2, true)
        for (pair in pairs) {
            val v = if (pair.size > 1) gcd(pair[0], pair[1])
            else pair[0]
            ans.add(v)
        }
        return multipleGcd(ans)
    }

    fun `백준 3036번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val radius = reader.readLine().split(" ").map { it.toInt() }

        val firstRadius = radius.first()
        for (i in 1..radius.lastIndex) {
            val r = radius[i]
            val div = gcd(firstRadius, r)
            writer.write("${firstRadius / div}/${r / div}\n")
        }
        writer.flush()
    }

    fun `백준 11050번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val (n, k) = reader.readLine().split(" ").map { it.toInt() }
        val ans = calcBinomialCoefficient(n, k)
        writer.write("$ans")
        writer.flush()
    }

    fun calcBinomialCoefficient(n: Int, k: Int): Int {
        val mem = Array(n + 1) { IntArray(k + 1) }

        for (i in 0..n) {
            mem[i][0] = 1
        }
        for (i in 0..k) {
            mem[i][i] = 1
        }

        for (i in 1..n) {
            for (j in 1..k) {
                mem[i][j] = mem[i - 1][j] + mem[i - 1][j - 1]
            }
        }

        return mem[n][k]
    }

    fun `백준 11051번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()

        val (n, k) = reader.readLine().split(" ").map { it.toInt() }
        val ans = calcBinomialCoefficientForBigNumber(n, k)
        writer.write("$ans")
        writer.flush()
    }

    fun calcBinomialCoefficientForBigNumber(n: Int, k: Int): Int {
        val mem = Array(n + 1) { IntArray(k + 1) }

        for (i in 0..n) {
            mem[i][0] = 1
        }
        for (i in 0..k) {
            mem[i][i] = 1
        }

        for (i in 1..n) {
            for (j in 1..k) {
                mem[i][j] = (mem[i - 1][j] + mem[i - 1][j - 1]) % 10007
            }
        }

        return mem[n][k]
    }
}