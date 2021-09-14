import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.system.exitProcess


fun main() {

}

class Level9 {
    fun `백준 1978`() {
        val n = readLine()!!.toInt()
        val input = readLine()!!.split(" ").map { it.toInt() }
        val ans = input.filter { isPrime(it) }.count()
        println(ans)
    }

    fun `백준 2581`() {
        val M = readLine()!!.toInt()
        val N = readLine()!!.toInt()
        val primes = (M..N).filter { isPrime(it) }
        when (primes.size) {
            0 -> println(-1)
            else -> {
                println(primes.sum())
                println(primes.minOf { it })
            }
        }
    }

    fun `백준 11653`() {
        var target = readLine()!!.toInt()
        var n = 2
        for (i in 1..10000000) {
            when {
                target == 1 -> break
                target % n == 0 -> {
                    target /= n
                    println(n)
                }
                else -> n++
            }
        }
    }

    fun `백준 1929`() {
        val (m, n) = readLine()!!.split(" ").map { it.toInt() }
        val writer = BufferedWriter(OutputStreamWriter(System.out))
        val max = sqrt(n.toDouble()).toInt()

        var primeList = (2..n).toList()
        for (i in 1..max) {
            if (isPrime(i, primeList, max)) {
                primeList = filteredList(i, primeList)
            }
        }

        writer.use { writer ->
            primeList
                .forEach {
                    if (it >= m) {
                        writer.write("$it")
                        writer.newLine()
                    }
                }
            writer.flush()
        }
    }

    fun `백준 9020`() {
        val n = readLine()!!.toInt()
        val max = 10000
        val primes = getPrimes(max)

        for (i in 1..n) {
            val goldNum = readLine()!!.toInt()
            val ans = primes
                .filter { it <= goldNum / 2 }
                .filter { (goldNum - it) in primes }
                .sortedBy { abs(2 * it - goldNum) }
                .first()
            println("$ans ${goldNum - ans}")
        }
    }

    fun `백준 1085`() {
        val (x, y, w, h) = readLine()!!.split(" ").map { it.toInt() }
        val ans = listOf(x, y, w - x, h - y).minOf { it }
        println(ans)
    }

    private fun getPrimes(max: Int): List<Int> {
        var result = (2..max).toList()
        var i = 2;
        while (i * i <= max) {
            result = result.filter { num -> num == i || num % i != 0 }
            i++
        }
        return result
    }

    private fun filteredList(n: Int, list: List<Int>): List<Int> {
        return list.filter { it == n || it % n != 0 }
    }

    private fun isPrime(n: Int): Boolean {
        return when {
            n == 1 -> false
            n == 2 -> true
            n % 2 == 0 -> false
            else -> {
                for (i in n - 1 downTo 2) {
                    if (n % i == 0) return false
                }
                true
            }
        }
    }

    private fun isPrime(n: Int, primeList: List<Int>, max: Int): Boolean {
        return when {
            n == 1 -> false
            n == 2 -> true
            else -> {
                for (i in primeList) {
                    if (i > max) return true
                    if (n == i) return true
                    if (n % i == 0) return false
                }
                true
            }
        }
    }

    fun `백준 3009`() {
        val mapX = mutableMapOf<Int, Int>()
        val mapY = mutableMapOf<Int, Int>()
        for (i in 1..3) {
            val (x, y) = readLine()!!.split(" ").map { it.toInt() }
            mapX[x] = mapX[x]?.plus(1) ?: 1
            mapY[y] = mapY[y]?.plus(1) ?: 1
        }
        val x = findOneTimeOccurNumber(mapX)
        val y = findOneTimeOccurNumber(mapY)
        println("$x $y")
    }

    private fun findOneTimeOccurNumber(list: Map<Int, Int>) =
        list.filter { it.value % 2 != 0 }
            .map { it.key }
            .first()

    fun `백준 4153`() {
        while (true) {
            val inputs = readLine()!!.split(" ").map { it.toInt() }
            if (inputs.all { it == 0 }) exitProcess(0)

            val max = inputs.maxOf { it }
            val others = inputs.filter { it < max }.sumOf { it * it }
            if (max * max == others) println("right") else println("wrong")
        }
    }

    fun `백준 3053`() {
        val r = readLine()!!.toDouble()
        val euclid = PI * r * r
        val taxi = 2 * r * r
        println("%4f".format(euclid))
        println("%4f".format(taxi))
    }

    fun `백준 1002`() {
        val n = readLine()!!.toInt()
        for (i in 1..n) {
            val inputs = readLine()!!.split(" ").map { it.toDouble() }
            val p1 = inputs[0] to inputs[1]
            val r1 = inputs[2]
            val p2 = inputs[3] to inputs[4]
            val r2 = inputs[5]
            val dist = calcDistance(p1, p2)
            val minRadius = abs(r1 - r2)
            val maxRadius = abs(r1 + r2)
            val result = when {
                p1 == p2 && r1 == r2 -> -1
                minRadius < dist && dist < maxRadius -> 2
                minRadius > dist || dist > maxRadius || dist == 0.0 -> 0
                else -> 1
            }
            println(result)
        }
    }

    private fun calcDistance(p1: Pair<Double, Double>, p2: Pair<Double, Double>): Double {
        val (x1, y1) = p1
        val (x2, y2) = p2
        return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    }
}