package level.`7`

import kotlin.math.min
import kotlin.math.sqrt

fun main() {
    val reader = System.`in`.bufferedReader()
    val writer = System.out.bufferedWriter()
    val (n, m) = reader.readLine().split(" ").map { it.toLong() }

    val a = getExponentOfNumFast(n, 5, 5)
    val b = getExponentOfNumFast(m, 5, 5)
    val c = getExponentOfNumFast((n - m), 5, 5)
    val five = a - b - c

    val d = getExponentOfNumFast(n, 2, 2)
    val e = getExponentOfNumFast(m, 2, 2)
    val f = getExponentOfNumFast((n - m), 2, 2)
    val two = d - e - f

    writer.write("${min(two, five)}\n")
    writer.flush()
}

fun getExponentOfNumFast(n: Long, num: Long, unit: Long): Long {
    if (n < num) return 0

    var ans = n / num
    ans += getExponentOfNumFast(n, num * unit, unit)

    return ans
}

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

    fun `백준 1010번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()
        val inputs = Array(n) { reader.readLine().split(" ").map { it.toInt() } }

        val table = makeBinomialCoefficientTable(30, 30)

        inputs
            .asSequence()
            .forEach {
                val ans = table[it[1]][it[0]]
                writer.write("$ans\n")
            }
        writer.flush()
    }

    fun makeBinomialCoefficientTable(n: Int, k: Int): Array<IntArray> {
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

        return mem
    }

    fun `백준 9375번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        repeat(n) {
            val num = reader.readLine().toInt()
            val inputs = HashMap<String, Int>()
            repeat(num) {
                val (item, type) = reader.readLine().split(" ")
                if (inputs[type] == null) {
                    inputs[type] = 1
                } else {
                    inputs[type] = inputs[type]!! + 1
                }
            }

            val ans = inputs.values.fold(1) { acc, it -> acc * (it + 1) } - 1
            writer.write("$ans\n")
        }
        writer.flush()
    }

    fun `백준 1676번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val n = reader.readLine().toInt()

        var two = 0
        var five = 0
        for (i in 1..n) {
            two += getExponentOfNum(i, 2)
            five += getExponentOfNum(i, 5)
        }

        writer.write("${min(two, five)}\n")
        writer.flush()
    }

    fun getExponentOfNum(n: Int, num: Int): Int {
        if (n % num == 0) {
            return getExponentOfNum(n / num, num) + 1
        }
        return 0
    }

    fun `백준 2004번`() {
        val reader = System.`in`.bufferedReader()
        val writer = System.out.bufferedWriter()
        val (n, m) = reader.readLine().split(" ").map { it.toLong() }

        val a = getExponentOfNumFast(n, 5, 5)
        val b = getExponentOfNumFast(m, 5, 5)
        val c = getExponentOfNumFast((n - m), 5, 5)
        val five = a - b - c

        val d = getExponentOfNumFast(n, 2, 2)
        val e = getExponentOfNumFast(m, 2, 2)
        val f = getExponentOfNumFast((n - m), 2, 2)
        val two = d - e - f

        writer.write("${min(two, five)}\n")
        writer.flush()
    }

    fun getExponentOfNumFast(n: Long, num: Long, unit: Long): Long {
        if (n < num) return 0

        var ans = n / num
        ans += getExponentOfNumFast(n, num * unit, unit)

        return ans
    }
}