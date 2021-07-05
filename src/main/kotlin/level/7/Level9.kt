package level.`7`

import java.io.BufferedWriter
import java.io.OutputStreamWriter
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.sqrt


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
}